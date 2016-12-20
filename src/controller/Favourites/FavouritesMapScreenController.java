package controller.Favourites;

import controller.Sharks.SharkController;
import de.fhpotsdam.unfolding.geo.Location;
import view.FavouritesMapScreen;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A controller that handles the favourites map screen, constructs a new
 * screen and renders the locations of favourited sharks upon said screen
 *
 * Created by Adam on 20/03/2016.
 */
public class FavouritesMapScreenController {
    private FavouritesMapScreen view;


    /**
     * Constructs a new Favourites Map Screen and passes it a list of the favourite
     * sharks for a given user
     */
    public FavouritesMapScreenController(ArrayList<String> sharks) {
        //constructs a new hashmap of sharks and their locations
        HashMap<Location, String> positionsByShark = new HashMap<>();
        //loops through each shark name
        for(String sharkName : sharks){
            //retrieves their location
            api.jaws.Location sharkJawsLoc = SharkController.getLastLocationOfShark(sharkName);
            Location sharkLocation = new Location(sharkJawsLoc.getLatitude(), sharkJawsLoc.getLongitude());
            //adds the shark name to the hashmap
            positionsByShark.put(sharkLocation, sharkName);
        }
        //pass the hashmap to the favouritesMapScreen view for rendering
        view = new FavouritesMapScreen(positionsByShark);
    }

}
