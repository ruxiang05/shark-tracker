package controller;

import controller.Favourites.FavouritesController;
import controller.Favourites.FavouritesScreenController;
import view.OpenScreen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 *
 * Controller for managing the open screen and routing the user to the correct window
 * in the program
 *
 * Created by Adam on 15/03/2016.
 */
public class OpenScreenController implements ActionListener, ItemListener {

    //The view the controller controls
    private OpenScreen view;

    /**
     * This method constructs a new Open Screen Controller which in turn constructs
     * a new open Screen with itself (the controller) as the parameter
     */
    public OpenScreenController() {
        view = new OpenScreen(FavouritesController.getAllUsernames(), this);
        if(FavouritesController.getAllUsernames().size() > 0){
            view.enableFavouritesWindow(true);
        } else {
            view.enableFavouritesWindow(false);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        FavouritesController.setCurrentUser(e.getItem().toString());
    }

    /**
     * Method for handling button click events on the openScreen, when fired
     * it identifies which button has been clicked by the text of the button
     * and then it navigates the user to the screen the selected
     * @param e - The action event (primarily a button click event)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //Check the eventSource is in fact a button, just in case the controller is
        //accidentally added to any other actionlistening method
        if(e.getSource() instanceof JButton){
            //cast to JButton
            JButton clickedButton = (JButton)e.getSource();
            switch (clickedButton.getText()){
                //if favourites button clicked, load favourites screen
                case "Favourites":
                    new FavouritesScreenController();
                    //hide and close open screen
                    view.setVisible(false);
                    view.dispose();
                    break;
                //if Search button clicked then load search screen
                case "Search":
                    new SearchScreenController();
                    //hide and close search screen
                    view.setVisible(false);
                    view.dispose();
                    break;
                case "New User":
                    view.getUserNameInput().setVisible(true);
                    view.getSubmit().setVisible(true);
                    break;
                case "Submit":
                    view.getUserNameInput().setVisible(false);
                    view.getSubmit().setVisible(false);
                    FavouritesController.setCurrentUser(view.getUserNameInput().getText());
                    view.getUserNameInput().setText("");
                    break;

            }
        }


    }
}
