package model;

import api.jaws.Shark;
import org.joda.time.DateTime;

import java.util.*;

/**
 * This class compares the pings of two sharks or two pings of the same shark
 * @see controller.SearchScreenController
 * @see controller.StatisticsScreenController
 * @see controller.Sharks.SharkInfoWidgetController
 */
public class SearchResult implements Comparator<SearchResult>, Comparable<SearchResult>{

    private Shark shark;
    private DateTime timeOfPing;

    /**
     * The constructor takes a shark and the time of its ping
     * @param shark - given shark
     * @param timeOfPing - given time of the shark's ping
     */
    public SearchResult(Shark shark, DateTime timeOfPing){
        this.shark = shark;
        this.timeOfPing = timeOfPing;
    }

    /**
     * This method returns the shark
     * @return Shark
     */
    public Shark getShark() {
        return shark;
    }

    /**
     * This method returns the time of the ping
     * @return DateTime
     */
    public DateTime getTimeOfPing() {
        return timeOfPing;
    }

    /**
     * This method compares the time of the same search result
     * @param o - search result
     * @return the most recent time
     */
    @Override
    public int compareTo(SearchResult o) {
        return this.timeOfPing.compareTo(o.timeOfPing);
    }

    /**
     * This method compares the time of two different search results
     * @param o1 - search result
     * @param o2 - search result
     * @return the most recent search result
     */
    @Override
    public int compare(SearchResult o1, SearchResult o2) {
        return o1.timeOfPing.compareTo(o2.timeOfPing);
    }
}
