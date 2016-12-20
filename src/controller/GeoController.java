package controller;

import com.google.maps.*;
import com.google.maps.model.*;

/**
 *
 * Used for accessing the google maps api for the purpose of calculating distance
 * from king's and the event of a sharknado
 *
 * This is essentially a static class. In making all the methods static we can funnel
 * all of the API calls through one single APIContext. This lowers our overall call rate
 * and means only one api connection is ever in play
 *
 * Created by Adam on 07/03/2016.
 */
public class GeoController {

    //radius of the earth in kilometers
    private static final double radiusOfEarth = 6372.8;
    //GeoApi context (defines the connection to google Maps Web Services API)
    private static GeoApiContext geoApiContext = new GeoApiContext().setApiKey("AIzaSyBA6EoHlX3VwQSCMbulgdMONT6XLIoiZKQ");
    //The result of a geocoding search for the location of KCL
    private static GeocodingResult homeGeocode;

    /**
     * Initialises the homeGeocode field in this class by getting a Longitude and Latitude
     * of a location at King's
     */
    static {
        GeocodingApiRequest request = GeocodingApi.geocode(geoApiContext, "King's College London, Strand, London, WC2R 2LS");
        try{
            GeocodingResult[] results = request.await();
            homeGeocode = results[0];

        } catch(Exception e){

        }

    }

    /**
     * Returns a boolean for a given shark location indicating if - in fact - that shark
     * is participating in some for of Shark Twister, Tornado or other high velocity
     * body of wind over land
     * @param latitude - The latitude of the given shark
     * @param longtitude - The logtitude of the given shark
     * @return - A boolean which is true if the shark is over land, and false if not
     */
    public static boolean isSharknado(double latitude, double longtitude) {
        try{
            PendingResult<ElevationResult> awaitingResult = ElevationApi.getByPoint(geoApiContext, new LatLng(latitude, longtitude));
            ElevationResult result = awaitingResult.await();
            return result.elevation > 0;
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error: Exception thrown in Sharknado Try Catch");
            return false;
        }

    }

    /**
     * Haversine Distance is a mathematical relationship used to calculate the distance
     * between two Longtitude and Latitude points. This particular method calculates the
     * distance in kilometers.
     *
     *
     *
     * @param coordsA - The first Long-Lat pair
     * @param coordsB - The Second Long-Lat pair
     * @return - the distance between the pair of co-ordinates
     */
    private static double haversineDistanceKM(LatLng coordsA, LatLng coordsB) {
        double dLat = Math.toRadians(coordsB.lat - coordsA.lat);
        double dLon = Math.toRadians(coordsB.lng - coordsA.lng);
        double latA = Math.toRadians(coordsA.lat);
        double latB = Math.toRadians(coordsB.lat);

        double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(latA) * Math.cos(latB);
        double c = 2 * Math.asin(Math.sqrt(a));
        return radiusOfEarth * c;
    }

    /**
     * Boilerplate method for finding the distance of a point (i.e. a shark) from king's
     * in Kilometers.
     * @param lat - the latitude of the point
     * @param lng - the longtitude of a point
     * @return - the distance in kilometers of the point from KCL
     */
    public static double getDistanceFromKclKm(double lat, double lng){
        LatLng sharkCoords = new LatLng(lat, lng);
        return haversineDistanceKM(homeGeocode.geometry.location, sharkCoords);
    }
}
