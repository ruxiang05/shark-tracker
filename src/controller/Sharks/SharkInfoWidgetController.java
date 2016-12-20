package controller.Sharks;

import api.jaws.Shark;
import controller.Favourites.FavouritesController;
import model.SearchResult;
import view.SearchScreen;
import view.widgets.SharkInfo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller for the widget that shows shark info. Handles click events on the
 * favourites button
 *
 * Created by Adam on 21/03/2016.
 */
public class SharkInfoWidgetController implements ActionListener {

    private SharkInfo view;
    private String sharkName;


    /**
     * Creates a new sharkInfoWidgetController and sharkInfo view and return the JPanel
     * as an object. The controller is attached but not returned and therefore in
     * accessible to the calling method.
     *
     * @param shark - the shark to create the moreInfo JPanel for
     * @return - a JPanel (with a controller attached) to show the shark info
     */
    public static SharkInfo getViewWithoutAdding(Shark shark){
        //create a new SharkInfoWidgetController
        SharkInfoWidgetController controller =  new SharkInfoWidgetController(shark);
        //return the view within it
        return controller.view;
    }

    /**
     * Construct a sharkInfo controller with the shark model passed to it and
     * no SearchScreen view to add it to
     * @param shark - the shark to show in the sharkInfo JPanel
     */
    private SharkInfoWidgetController(Shark shark) {
        //creates a new SharkInfo view
        view = new SharkInfo(shark.getName(),
                shark.getGender(), shark.getStageOfLife(),
                shark.getSpecies(), shark.getLength(),
                shark.getWeight(), shark.getDescription(),
                "", this);

        //sets the shark name in the controller
        sharkName = shark.getName();

        //sets whether or not the shark is being followed
        view.setFollowing(
                FavouritesController.isAFavourite(
                        FavouritesController.getCurrentUser(),
                        sharkName)
        );
    }

    /**
     * Creates a new SharkInfoWidgetController and adds the view contained with
     * to the SearchView specified in the parameters
     * @param searchView - the search view to add the SharkInfo JPanel to
     * @param sharkResult - the sharkResult to use for the info in the sharkInfo JPanel
     */
    public SharkInfoWidgetController(SearchScreen searchView, SearchResult sharkResult) {
        Shark shark = sharkResult.getShark();
        view = new SharkInfo(shark.getName(),
                shark.getGender(), shark.getStageOfLife(),
                shark.getSpecies(), shark.getLength(),
                shark.getWeight(), shark.getDescription(),
                sharkResult.getTimeOfPing().toString("EEEE d MMMM yyyy HH:mm:ss"), this);

        sharkName = shark.getName();

        view.setFollowing(
                FavouritesController.isAFavourite(
                    FavouritesController.getCurrentUser(),
                    sharkName)
        );

        searchView.addNewSharkToSearch(view);
    }

    /**
     * Handles the button click on the follow/unfollow button on the MoreInfo
     * view, passes instructions on follow/unfollow to the favouritescontroller
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //checks if instance of a Jbutton
        if(e.getSource() instanceof JButton){
            //casts to a JButton
            JButton followUnfollowButton = (JButton)e.getSource();
            //switches between behaviour depending on if the button shows follow or unfollow
            switch (followUnfollowButton.getText()){
                case "Follow":
                    //sets the shark to follow in favourite controller, and the button
                    //to show unfollow
                    FavouritesController.addSharkNames(
                            FavouritesController.getCurrentUser(),
                            sharkName);
                    view.setFollowing(true);
                    break;
                case "Following":
                    //sets the shark to unfollow in the favourites controller and the button
                    //to show follow
                    FavouritesController.removeFavouriteShark(
                            FavouritesController.getCurrentUser(),
                            sharkName);
                    view.setFollowing(false);
                    break;
            }
        }
    }
}
