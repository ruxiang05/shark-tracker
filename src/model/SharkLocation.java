package model;

import api.jaws.Location;
import javafx.beans.NamedArg;
import javafx.util.Pair;
import org.joda.time.DateTime;

/**
 * This class represents a pair of the latest location of the shark and the time when it was recorded
 * @see controller.Sharks.SharkController
 * @see SharkLocations
 */
public class SharkLocation extends Pair<Location, DateTime> {
    public SharkLocation(@NamedArg("key") Location key, @NamedArg("value") DateTime value) {
        super(key, value);
    }
}
