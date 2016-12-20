package controller.Sharks;

import view.widgets.MoreInfoScreen;
import view.widgets.SharkInfo;

/**
 * This is the controller for the more info screen. This is a generic screen
 * that can be used to show exactly one widget of type SharkInfo. This window essentiallu
 * allows the display of additional details relating one shark.
 *
 * It is currently only used on the Favourites screen to offer more info about a shark
 * but can be used elsewhere as well
 *
 * Created by Adam on 22/03/2016.
 */
public class MoreInfoScreenController {
    /**
     * Constructer for the moreInfo screen controller. This takes the name of the
     * shark to show more info on, and creates a new view containing said info
     * @param sharkName - the name of the shark to show more info on
     */
    public MoreInfoScreenController(String sharkName) {
        //use a static constructor to build the view with specifying
        //which frame it should be added to
        SharkInfo info = SharkInfoWidgetController.getViewWithoutAdding(SharkController.getSharkByName(sharkName));
        new MoreInfoScreen(info);
    }
}
