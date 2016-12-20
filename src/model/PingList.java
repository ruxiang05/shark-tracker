package model;

import api.jaws.Ping;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class is represented by the array list of pings
 * @see controller.Sharks.SharkController
 * @see PingData
 */
public class PingList extends ArrayList<Ping> {

    private String serverSideUpdate;
    private DateTime timeUpdated;

    /**
     * The constructor takes the last time the API was updated and sets the record time of this and the current time to when the app was updated
     * @param serverSideUpdate - last time the server was updated
     */
    public PingList(String serverSideUpdate) {
        this.serverSideUpdate = serverSideUpdate;
        timeUpdated = DateTime.now();
    }

    /**
     * This method returns the updated time of the app
     * @return DateTime
     */
    public DateTime getTimeUpdated() {
        return timeUpdated;
    }

    /**
     * This method returns the record time of the updated time of the API
     * @return String
     */
    public String getServerSideUpdate() {
        return serverSideUpdate;
    }

    /**
     * This method sets the record of the updated time of the API
     * @param serverSideUpdate - last time the server was updated
     */
    public void setServerSideUpdate(String serverSideUpdate) {
        this.serverSideUpdate = serverSideUpdate;
    }

    /**
     * This method adds a ping to the list and records the time the data was updated
     * @param ping - ping from the API
     * @return boolean
     */
    @Override
    public boolean add(Ping ping) {
        timeUpdated = DateTime.now();
        return super.add(ping);
    }

    /**
     * This method adds a ping to the list with the a specific index and records the time the data was updated
     * @param index - given index
     * @param element - given ping
     */
    @Override
    public void add(int index, Ping element) {
        timeUpdated = DateTime.now();
        super.add(index, element);
    }

    /**
     * This method adds all pings received to the list
     * @param c - collection of Pings
     * @return boolean
     */
    @Override
    public boolean addAll(Collection<? extends Ping> c) {
        timeUpdated = DateTime.now();
        return super.addAll(c);
    }

    /**
     * This method adds all pings received to the list to specific indexes and returns the updated list
     * @param index - given index
     * @param c - collection of Pings
     * @return
     */
    @Override
    public boolean addAll(int index, Collection<? extends Ping> c) {
        timeUpdated = DateTime.now();
        return super.addAll(index, c);
    }
}
