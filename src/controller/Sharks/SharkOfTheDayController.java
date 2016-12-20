package controller.Sharks;

import org.joda.time.DateTime;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import static controller.Sharks.SharkController.getSharkNames;
import static controller.Sharks.SharkController.getVideoURLForShark;


/**
 * Created by Beatrice on 24/03/16.
 */
public class SharkOfTheDayController {

    //details about current shark of the day
    private static DateTime lastChanged = null;
    private static String sharkName = null;
    private static String sharkUrl = null;

    /**
     * Static method gets the last shark of the day from file then checks
     * it is still in date
     */
    static{
        getSharkOfTheDay();
        checkSharkOfTheDay();

    }

    /**
     * Method for getting the name of the current shark of the day,
     * will change the shark of the day if the shark of the day is out of date
     * @return - the name of the current shark of the day
     */
    public static String getSharkName() {
        checkSharkOfTheDay();
        return sharkName;
    }

    /**
     * Method for getting the video URL of the shark of the day,
     * will change the shark of the day if the shark of the day is out of date
     * @return - the URL of the shark of the day (if one exists)
     */
    public static String getSharkUrl() {
        checkSharkOfTheDay();
        return sharkUrl;
    }

    /**
     * this method adds a shark of the day to the csv file.
     *
     * @see BufferedWriter
     * @see FileWriter
     */
    private static void checkSharkOfTheDay() {
        if(lastChanged == null){
            getNewSharkOfDay();
            sharkUrl = getVideoURLForShark(sharkName);
        } else if(lastChanged.plusDays(1).isBeforeNow()){
            getNewSharkOfDay();
            sharkUrl = getVideoURLForShark(sharkName);
        }
    }

    /**
     * this method gets the shark of the day from the csv file.
     *
     * @see BufferedReader
     * @see FileReader
     */
    private static void getSharkOfTheDay() {
        String path = SharkOfTheDayController.class.getResource("sharkoftheday.csv").getPath();
        BufferedReader br;
        String line;
        String csvSplitBy = ",";
        String[] word;

        try {
            br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                word = line.split(csvSplitBy);
                if (word.length == 2) {
                    sharkName = word[0];
                    lastChanged = DateTime.parse(word[1]);
                    sharkUrl = SharkController.getVideoURLForShark(sharkName);
                }
            }
        } catch (IOException e) {
            System.out.println("Something went wrong with getting the shark of the day");
        }
    }


    /**
     * Method randomly generates a new shark of the day, writes it to the file
     * and loads it into the static variables within this class
     */
    private static void getNewSharkOfDay(){
        ArrayList<String> sharkNames = getSharkNames();
        int index = new Random().nextInt(sharkNames.size()- 1);
        sharkName = sharkNames.get(index);
        lastChanged = DateTime.now();

        String path = SharkOfTheDayController.class.getResource("sharkoftheday.csv").getPath();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path, false));
            bw.write(sharkName + "," + lastChanged.toString());
            bw.close();
        } catch (IOException e) {
            System.out.println("Something went wrong with setting a new shark of the day");
        }
    }
}
