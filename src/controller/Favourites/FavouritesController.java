
package controller.Favourites;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Controller for managing the favourite sharks of the users. this class is
 * static and holds a persistent cached record of all the users favourited sharks
 * it then writes these to a csv when they are changed
 *
 * Created by Beatrice on 20/03/16.
 */
public class FavouritesController {


    private static String currentUser = "Default";
    private static HashMap<String, ArrayList<String>> sharkAndUser = new HashMap<>();
    private static final String path;

    /**
     * Static block for initialising path variable and getting names of the
     * current users and their favourite sharks
     */
    static{
        path = FavouritesController.class.getResource("sharknames.csv").getPath();
        getNames();
    }

    /**
     * Gets current user.
     *
     * @return currently set user of the system
     */
    public static String getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets current user to a new one.
     *
     * @param currentUser - sets the current user of the system (helper variable for user persistence)
     */
    public static void setCurrentUser(String currentUser) {
        FavouritesController.currentUser = currentUser;
    }

    /**
     * Gets the favourite sharks of the current users.
     *
     * @return list of sharks
     */
    public static ArrayList<String> getCurrentUsersFavouriteSharks() {
        return sharkAndUser.getOrDefault(currentUser, new ArrayList<>());
    }

    /**
     * Removes a shark from both the array list and the csv file.
     *
     * @param user - the username for whom to remove a favourite shark
     * @param sharkName - the shark name to remove from the users favourite sharks list
     * @return list of sharks updated without the users unfavourited shark
     */
    public static void removeFavouriteShark(String user, String sharkName) {
        getNames();
        //remove shark from hashmap
        sharkAndUser.get(user).remove(sharkName);
        File sharkNames =  new File(path);
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(sharkNames, false));
            for(String username : sharkAndUser.keySet()){
                ArrayList<String> sharks = sharkAndUser.get(username);
                for(String shark : sharks){
                    bw.write(username + "," + shark + "\n");
                }
            }
            bw.close();
        } catch(Exception e){
            System.out.println("We had trouble removing this favourite shark");
        }
        getNames();
    }

    /**
     * Checks if a user has a certain shark in his favourites
     *
     * @param user - the user to check to see if they have the given shark favourited
     * @param sharkName - the shark to check to see if the user has favourited
     * @return = boolean indicating if given user has favourited given shark
     */
    public static boolean isAFavourite(String user, String sharkName) {
        ArrayList<String> usersFavourites = sharkAndUser.getOrDefault(user, new ArrayList<>());
        return usersFavourites.contains(sharkName);
    }

    /**
     * Gets sharks' names from a csv file and adds them to an array list
     *
     * @see BufferedReader
     * @see FileReader
     */
    private static void getNames() {
        sharkAndUser = new HashMap<>();
        String line;
        String[] word;
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                word = line.split(",");
                if(word.length == 2) {
                    ArrayList<String> favSharkNames = sharkAndUser.getOrDefault(word[0], new ArrayList<>());
                    favSharkNames.add(word[1]);
                    sharkAndUser.putIfAbsent(word[0], favSharkNames);
                }
            }

        } catch (IOException e) {
            System.out.println("We ran into some problems loading favourites");
        }
    }

    /**
     * Allows the user to add new sharks to the csv file
     *
     * @param userName - the username to add the shark for
     * @param sharkName - the shark name to favourite for the given user
     * @see BufferedWriter
     * @see FileWriter
     */
   public static void addSharkNames(String userName, String sharkName) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path, true));
            bw.write(userName + "," + sharkName + "\n");
            bw.close();
            getNames();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * returns an array list of all the users in the system, with default
     * at the top.
     * @return - an arraylist of all the usernames in the system
     */
    public static ArrayList<String> getAllUsernames(){
        ArrayList<String> usernames = new ArrayList<>(sharkAndUser.keySet());
        usernames.remove("Default");
        usernames.add(0, "Default");
        return usernames;
    }
}