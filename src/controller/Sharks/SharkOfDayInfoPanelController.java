package controller.Sharks;

import api.jaws.Shark;
import controller.Favourites.FavouritesController;
import view.widgets.SharkOfTheDayPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Controller for handling events on the sharkOfTheDay Info Panel creates
 * a new shark of the day info panel for holding the given sharks data
 * 
 * Created by Adam on 25/03/2016.
 */
public class SharkOfDayInfoPanelController implements ActionListener {

    private SharkOfTheDayPanel view;
    private URL sharkUrl;

    /**
     * Method constructs a new shark of the day info panel controller
     * and creates a a shark of the day info panel with it
     *
     * if the video URL provided is not valid, it will catch this exception
     * and print a witty exception to the console
     *
     * It will also disable the button for show video
     */
    private SharkOfDayInfoPanelController() {
        //get the shark of the day from the SharkOfDay controller and get
        //details on said shark from the SharkController
        Shark sharkOfTheDay = SharkController.getSharkByName(
                SharkOfTheDayController.getSharkName());
        try{
            String url = SharkOfTheDayController.getSharkUrl();
            sharkUrl = new URL(url);
        } catch (MalformedURLException e){
            System.out.println("This exception is being thrown because ain't nobody got time for videos");
        }

        //Construct a new view with the details provided
        view = new SharkOfTheDayPanel(sharkOfTheDay.getName(), sharkOfTheDay.getGender(),
                sharkOfTheDay.getStageOfLife(), sharkOfTheDay.getSpecies(),
                sharkOfTheDay.getLength(), sharkOfTheDay.getWeight(),
                sharkOfTheDay.getDescription(), (sharkUrl != null), this);

        //Sets the value of the follow button depending on if the shark
        //is already being followed by the user
        view.setFollowing(FavouritesController.isAFavourite(
                FavouritesController.getCurrentUser(), sharkOfTheDay.getName()));
    }

    /**
     * Static constructor for building a new hidden SotD controller and
     * just getting the view from said controller. This view is returned
     * to the calling method to be placed in a window
     * @return - Shark of the day panel
     */
    public static SharkOfTheDayPanel getSharkOfTheDayPanel(){
        return new SharkOfDayInfoPanelController().view;
    }

    /**
     * Handles the follow button and See video button click actions
     * if the follow/unfollow button is clicked will make the necessary
     * changes in the favourite controller
     *
     * if the see video button is clicked (and it can only be clicked if a
     * valid url is available) then it'll open the URL in a new browser window
     * @param e - the event of the ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //if is a button
        if(e.getSource() instanceof JButton){
            //cast to button
            JButton clickedButton = (JButton)e.getSource();
            //get text from button and switch case
            switch (clickedButton.getText()){
                //if text is follow, then follow sharkoftheday
                case "Follow":
                    FavouritesController.addSharkNames(FavouritesController.getCurrentUser(),
                            SharkOfTheDayController.getSharkName());
                    view.setFollowing(true);
                    break;
                //if text is following, then unfollow sharkoftheday
                case "Following":
                    FavouritesController.removeFavouriteShark(FavouritesController.getCurrentUser(),
                            SharkOfTheDayController.getSharkName());
                    view.setFollowing(false);
                    break;
                //if text is see video, then open video in browser
                case "See Video":
                    try {
                        Desktop.getDesktop().browse(sharkUrl.toURI());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    break;
            }
        }
    }
}
