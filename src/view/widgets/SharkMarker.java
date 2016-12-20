package view.widgets;

import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import processing.core.PGraphics;

/**
 * Created by Adam on 20/03/2016.
 */

/**
 * class for pointer on the shark map
 */
public class SharkMarker extends SimplePointMarker {
    private String sharkName;

    /**
     * constructor that initialises sharkName field and location of shark
     * @param location Location of shark
     * @param sharkName Name of shark
     */
    public SharkMarker(Location location, String sharkName) {
        super(location);
        this.sharkName = sharkName;
    }

    /**
     * method to draw marker onto map
     * @param pg object to draw graphics
     * @param x x location
     * @param y y location
     */
    public void draw(PGraphics pg, float x, float y) {
        pg.pushStyle();
        pg.noStroke();

        pg.fill(128, 128, 179, 100);
        //set size of circle here
        pg.ellipse(x, y, 90, 90);

        pg.fill(26, 26, 89, 100);
        //set size of circle here
        pg.ellipse(x, y, 70, 70);

        //set text size here
        pg.textSize(90);
        pg.fill(255, 255, 255, 180);

        pg.text(sharkName, x - pg.textWidth(sharkName) / 2, y + 4);
        pg.popStyle();
    }
}
