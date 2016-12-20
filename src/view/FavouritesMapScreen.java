package view;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.*;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;
import view.widgets.SharkMarker;

import javax.swing.*;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Favourites Map Screen for showing a map with all the favourited sharks on for a given
 * user
 *
 * Created by Adam on 15/03/2016.
 */
public class FavouritesMapScreen {

    private HashMap<Location, String> sharks;

    /**
     * Constructs a new favourites map screen from a provided HashMap of Locations and shark
     * names as strings
     *
     * @see de.fhpotsdam.unfolding.geo.Location
     * @param favourites - A hashmap of locations to strings
     */
    public FavouritesMapScreen(HashMap<Location, String> favourites) {
        sharks = favourites;
        FavouritesMapApplet();
    }

    /**
     * Method constructs a new Processing applet and runs it as a Processing Sketch
     */
    private void FavouritesMapApplet(){
        MapProcessingApplet mapApplet = new MapProcessingApplet();
        PApplet.runSketch(new String[]{"view.FavouritesMapScreen.MapProcessingApplet"}, mapApplet);
    }

    /**
     * An internal class that extends a Processing applet to allow us to render a new
     * Unfolding map using processing for Java
     */
    private class MapProcessingApplet extends PApplet{

        //The unfolding map we're rendering
        private UnfoldingMap map;
        //boolean indicating if the CloseWindowListeners have been removed from the PApplet
        private boolean clearedListeners = false;

        /**
         * Processing Defined method that sets up the applet with a defined size,
         * constructs a new map object and binds event listeners to it. It then reaches
         * into the class it is nested within and configures markers
         */
        @Override
        public void setup() {
            frame.setTitle("Favourites Map");
            size(1000, 750);
            setSize(1000,750);
            map = new UnfoldingMap(this, new Google.GoogleMapProvider());
            MapUtils.createDefaultEventDispatcher(this, map);
            configureMarkers();
        }

        /**
         * A looped method that continually updates what is being shown in the processing app
         * this draw method clears whats currently in the app and redraws the map
         *
         * It also checks to see if event listeners have been cleared, if they haven't then
         * it clears the event listeners and reports this fact
         */
        @Override
        public void draw() {
            this.clear();
            map.draw();
            if(!clearedListeners){
                clearListeners();
                clearedListeners = true;
            }
        }

        /**
         * Configure the markers on the map by constructing a new Shark marker object
         *
         * @see SharkMarker
         */
        private void configureMarkers(){
            for(Location l : sharks.keySet()){
                map.addMarker(new SharkMarker(l, sharks.get(l)));
            }

            map.zoomAndPanToFit(new ArrayList<>(sharks.keySet()));

        }

        /**
         * gets all the listeners on the frame that encapsulates the PApplet
         * removes all the window listeners. This prevents the applet from calling
         * System.exit(0) which terminates the JVM and thus prevents the program from continuing
         * to run after the map has been closed
         */
        private void clearListeners(){
            JFrame currentFrame = (JFrame)this.frame;
            WindowListener[] listeners = currentFrame.getWindowListeners();
            for(WindowListener l : listeners){
                currentFrame.removeWindowListener(l);
            }
        }

    }
}
