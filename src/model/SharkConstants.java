package model;

/**
 * This class represents the classifications for the search options
 * @see SearchOptions
 * @see view.SearchScreen
 * @see controller.SearchScreenController
 */
public class SharkConstants {
    /**
     * Classification of stage of life, including the default one "Any"
     */
    public static final String[] stageOfLives = new String[]{"All", "Mature", "Immature", "Undetermined"};
    /**
     * Classification of gender, including the default one "Any"
     */
    public static final String[] genders = new String[]{"All", "Male", "Female"};
    /**
     * Classification of track range
     */
    public static final String[] trackRanges = new String[]{"Last 24 Hours", "Last Week", "Last Month"};
}
