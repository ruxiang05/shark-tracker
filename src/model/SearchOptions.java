package model;

import controller.Sharks.SharkController;

/**
 * This class represents the data from the search options in the search screen
 * @see controller.SearchScreenController
 */
public class SearchOptions {
    private String gender;
    private String tagLocation;
    private String stageOfLife;
    private String timePeriod;

    /**
     * The constructor takes all first option from the options lists
     */
    public SearchOptions() {
        gender = SharkConstants.genders[0];
        tagLocation = SharkController.tagLocations[0];
        stageOfLife = SharkConstants.stageOfLives[0];
        timePeriod = SharkConstants.trackRanges[0];
    }

    /**
     * This method returns the gender
     * @return String
     */
    public String getGender() {
        return gender;
    }

    /**
     * This method sets the gender
     * @param gender - given gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * This method returns the tag location
     * @return String
     */
    public String getTagLocation() {
        return tagLocation;
    }

    /**
     * This method sets the tag location
     * @param tagLocation - given tag location
     */
    public void setTagLocation(String tagLocation) {
        this.tagLocation = tagLocation;
    }

    /**
     * This method returns the stage of life
     * @return String
     */
    public String getStageOfLife() {
        return stageOfLife;
    }

    /**
     * This method sets the stageOfLife
     * @param stageOfLife - given stage of life
     */
    public void setStageOfLife(String stageOfLife) {
        this.stageOfLife = stageOfLife;
    }

    /**
     * This method return the time period
     * @return String
     */
    public String getTimePeriod() {
        return timePeriod;
    }

    /**
     * This method sets the time period
     * @param timePeriod - given time period
     */
    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }
}
