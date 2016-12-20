package controller.Sharks;

import api.jaws.Jaws;
import api.jaws.Location;
import api.jaws.Ping;
import api.jaws.Shark;
import model.*;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.*;
import java.util.concurrent.*;

/**
 * Used for controlling access to the jaws API for the purpose of searching
 * shark data
 * Created by Adam on 07/03/2016.
 */
public class SharkController {
    //Constants relating to tag location and copyright acknowledgement
    //Implemented here rather than sharkConstants in order for them to be instantiated
    //via Jaws API
    public static final String[] tagLocations;
    public static final String acknowledgement;

    //The internal implementation of the Jaws library provided as part of the coursework
    private static final Jaws jawsApi;
    //boolean indicating if the user has requested a force refresh
    private static boolean forceRefresh;


    //Caching models for shark data//
    //(HashMap<timePeriod, PingList>) Data on pings found over the past 24 hours, week and month
    private static PingData pingData;
    //(HashMap<sharkName, Shark>) Data on the sharks mapped to a sharkName
    private static SharksData sharksData;
    //(HashMap<sharkName, SharkLocation>) Data on the sharks last location
    private static SharkLocations sharksLocations;
    //ArrayList<String> of all shark names
    private static ArrayList<String> sharkNames;

    /**
     * Static block for initialising API, variables and caching models in controller
     */
    static{
        //Construct a new jaws object to access Jaws API
        jawsApi = new Jaws("MLiTb0BdM0BggYBO", "Y3yih8WEfHs0m1pi");
        //Pull acknowledgement from Jaws api
        acknowledgement = jawsApi.getAcknowledgement();
        //Pull tag locations from the API
        ArrayList<String> tagLocs = jawsApi.getTagLocations();
        //Configure tag locations array, with enough space for the "Any" options
        tagLocations = new String[tagLocs.size() + 1];
        //Add "Any" option
        tagLocations[0] = "All";
        //Add location specific options
        for(int i = 1; i <= tagLocs.size(); i++){
            tagLocations[i] = tagLocs.get(i - 1);
        }

        //Build empty cache models
        pingData = new PingData();
        sharksData = new SharksData();
        sharksLocations = new SharkLocations();
    }

    /**
     * Method for getting SearchResults by a given time period. This returns
     * a searchresults object - a collection searchresult which contain shark info
     * and the time of the ping
     * @param timePeriod - The time period to return search results for
     *                   accepted values: "Last 24 Hours", "Last Week", "Last Month"
     * @return - A SearchResults object, contain SearchResult for that period of time
     */
    public static SearchResults getSharksByTimePeriod(String timePeriod){
        //get a list of the pings from the cache for given timeperiod
        PingList pings = getPingsFromCache(timePeriod);
        //Create a new hashset of names (i.e. only keep unique shark names)
        HashSet<String> names = getUniqueNamesFromPings(pings);
        //run method for downloading any uncached shark details
        downloadAndStoreSharkData(names);
        //create a new search results object
        SearchResults results = new SearchResults();
        //Create DateTime formatter to convert Jaws API times into Joda DateTime
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        //Loop through each ping
        for(Ping p : pings){
            //create a dateTime object representing time of ping
            DateTime timeOfPing = DateTime.parse(p.getTime(), dateTimeFormatter);
            //add new search result with shark data and time of ping
            results.add(new SearchResult(sharksData.get(p.getName()), timeOfPing));
        }
        //sort the search results into time order, and then reverse
        Collections.sort(results);
        Collections.reverse(results);

        return results;
    }

    /**
     * Returns a set of unique shark names from the provided ping list in the form of
     * a hashset (primarily used by the download sharks data method)
     * @param pings - a list of pings (a PingList model)
     * @return - a hashset containing all the unique names in ping list
     */
    private static HashSet<String> getUniqueNamesFromPings(PingList pings){
        //construct a new hashset
        HashSet<String> names = new HashSet<>();
        //loop through each ping and add a the names
        for(Ping p : pings){
            names.add(p.getName());
        }
        //return the hashset
        return names;
    }

    /**
     * Method for downloading and caching shark data for use within the the shark
     * Controller class. This is designed to speed up search results time by
     * storing sharks which have already been requested and services them from
     * local data rather than remote data
     * @param sharks - a hashset containing a list of shark names to download
     */
    private static void downloadAndStoreSharkData(Set<String> sharks){
        //create a set to hold all the sharks for who data has yet to be retrieved
        HashSet<String> dataPullSet = new HashSet<>();
        for(String s : sharks){
            if(!sharksData.containsKey(s)){
                dataPullSet.add(s);
            }
        }
        //if the set for which local data does not exist is zero, then return and end
        //method
        if(dataPullSet.size() == 0){
            return;
        }

        //create a new multi thread service (Executor Service) with enough threads
        //for each name
        ExecutorService executorService = Executors.newFixedThreadPool(dataPullSet.size());

        //else create a set of futures to wait for shark results
        Set<Future<Shark>> output = new HashSet<>();

        //for each name in the set of names to be downloaded, create a callable
        //anonymous interface to download the data for that shark
        for(String name : dataPullSet){
            Callable<Shark> callable = new Callable<Shark>() {
                @Override
                public Shark call() throws Exception {
                    return jawsApi.getShark(name);
                }
            };
            output.add(executorService.submit(callable));
        }

        //await those callable threads to return their data
        for(Future<Shark> fs : output){
            try {
                Shark shark = fs.get();
                sharksData.put(shark.getName(), shark);
            } catch(Exception e){
                //e.printStackTrace();
                System.out.println("API refused to service request");
            }
        }
    }

    /**
     * get a list of Pings from the cache for a given time period, if the cache
     * does not have those pings cached, then this method will download and cache
     * those pings for future use
     * @param timePeriod - the time period for which to return pings for
     * @return - a PingList containing all pings for the specified time period
     */
    private static PingList getPingsFromCache(String timePeriod){
        //the list of pings to be returned
        PingList pings;
        //if we already have a cache value for the time period
        if(pingData.containsKey(timePeriod)){
            //get the this cached value
            pings = pingData.get(timePeriod);
            //if the time this cache valued was last updated was greater than 5 minutes
            //ago
            if(pings.getTimeUpdated().plusMinutes(5).isBeforeNow() || forceRefresh){
                //check with the API to see if theres been a change to data since the last update
                if(!pings.getServerSideUpdate().equals(jawsApi.getLastUpdated())) {
                    //if there has, then clear this value and redownload it
                    pings.clear();
                    pings.addAll(getPingsByTimePeriod(timePeriod));
                    //set the time of the last server side update
                    pings.setServerSideUpdate(jawsApi.getLastUpdated());
                }
            }
            //Else if the data doesn't exist in the cache
        } else {
            //create the value and download it
            pings = new PingList(jawsApi.getLastUpdated());
            pings.addAll(getPingsByTimePeriod(timePeriod));
            pingData.put(timePeriod, pings);
        }
        //return the data
        return pings;
    }

    /**
     * Method for directly getting pings from the API, takes the given timeperiod
     * and returns the api result for that given timeperiod
     * @param timePeriod - the timeperiod to return an arraylist of pings for
     * @return - An arraylist of Pings for that time period
     */
    public static ArrayList<Ping> getPingsByTimePeriod(String timePeriod){
        switch (timePeriod){
            case "Last 24 Hours":
                return jawsApi.past24Hours();
            case "Last Week":
                return jawsApi.pastWeek();
            case "Last Month":
                return jawsApi.pastMonth();
        }
        return new ArrayList<>();
    }

    /**
     * Takes a shark name and attempts to find locally cached data on that shark
     * if not found downloads said data and caches it
     * @param name - the name of the shark to get data for
     * @return - the data on that shark
     */
    public static Shark getSharkByName(String name) {
        //if the local cache has data, return it
        if(sharksData.containsKey(name)){
            return sharksData.get(name);
            //else download the data and return it
        } else {
            HashSet<String> names = new HashSet<>();
            names.add(name);
            downloadAndStoreSharkData(names);
            return sharksData.get(name);
        }
    }

    /**
     * Gets the last location of a given shark (By name) and caches this location
     * attempts to retrieve first from the cache, checking to see how up to date the
     * data is. if data is not in cache or out of date, it'll be updated
     * @param sharkName - the name of the shark to return a location for
     * @return - the location of the shark requested
     */
    public static Location getLastLocationOfShark(String sharkName){
        //if the cache contains a location
        if(sharksLocations.containsKey(sharkName)){
            //get that SharkLocation model
            SharkLocation sharkLocation = sharksLocations.get(sharkName);
            //if the time that location was last updated was more than 3 minutes ago
            if(sharkLocation.getValue().plusMinutes(3).isBeforeNow()){
                //update the location from the API
                SharkLocation updatedLocation = new SharkLocation(
                        jawsApi.getLastLocation(sharkName),
                        DateTime.now());
                sharksLocations.replace(sharkName, updatedLocation);
            }
        } else {
            //if the data doesn't exist in the cache, then put it in the cache
            SharkLocation currentLocation = new SharkLocation(
                    jawsApi.getLastLocation(sharkName),
                    DateTime.now());
            sharksLocations.put(sharkName, currentLocation);
        }
        //return the location in the cache
        return sharksLocations.get(sharkName).getKey();
    }


    /**
     * Method for getting all sharks tracked by ocearch and returning them if
     * they are of the given gender. Very resource intensive in the first use
     * case
     *
     * @param gender - the gender of sharks to return for
     * @return
     */
    public static SharksData getAllSharksOfGender(String gender){
        downloadAndStoreSharkData(new HashSet<>(getSharkNames()));
        SharksData result = new SharksData();
        for(String sharkName : sharksData.keySet()){
            Shark shark = sharksData.get(sharkName);
            if(shark.getGender().equals(gender)){
                result.put(sharkName, shark);
            }
        }
        return result;
    }

    /**
     * Makes the controller poll the API for new data, will still not update if
     * the API informs the controller that no data has changed since the last update
     */
    public static void requestForceRefresh(){
        forceRefresh = true;
    }

    /**
     * This cached method returns the names of all sharks from a cached source (yay!)
     *
     * @return - An ArrayList of Strings with all the sharks names
     */
    protected static ArrayList<String> getSharkNames(){
        if(sharkNames == null){
            sharkNames = jawsApi.getSharkNames();
        }
        return sharkNames;
    }

    protected static String getVideoURLForShark(String sharkName){
        return jawsApi.getVideo(sharkName);
    }
}
