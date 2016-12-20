package view;

import controller.OpenScreenController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Jack on 07/03/2016.
 */

/**
 * class is a frame that opens when program is run
 */
public class OpenScreen extends JFrame{
    private JTextField userNameInput;
    private JButton submit;
    private JButton favourites;

    /**
     * constructor calls internal methods to initialise view
     * @param usernames names of created users
     * @param controller controller for buttons and user election on the view
     */
    public OpenScreen(ArrayList<String> usernames, OpenScreenController controller){
        super("Shark Tracker");
        setLayout(new GridLayout(2,1));
        setSize(300, 500);

        add(getLogoLabel());
        add(getButtonPanel(usernames, controller));

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * creates a label with the shark logo
     * @return label with shark logo
     */
    private JLabel getLogoLabel(){
        ImageIcon logo = new ImageIcon("./src/resources/shark.png");
        return new JLabel(logo);
    }

    /**
     * creates a panel conatining the search and favourites button as well as the user selection
     * @param userNames names of created userd
     * @param controller controller to controll buttons
     * @return created panel
     */
    private JPanel getButtonPanel(ArrayList<String> userNames, OpenScreenController controller){
        JPanel options = new JPanel(new BorderLayout());
        JPanel buttons = new JPanel(new GridLayout(2,1));
        JPanel userPanel = new JPanel(new GridLayout(2,2));

        JComboBox<String> users = new JComboBox<>();

        for(String username : userNames){
            users.addItem(username);
        }
        users.addItemListener(controller);

        userNameInput = new JTextField();
        userNameInput.setVisible(false);
        submit = getButtonAttachedToController("Submit", controller);
        submit.setVisible(false);

        buttons.add(getButtonAttachedToController("Search", controller));

        favourites = getButtonAttachedToController("Favourites", controller);
        buttons.add(favourites);

        userPanel.add(users);
        userPanel.add(getButtonAttachedToController("New User", controller));
        userPanel.add(userNameInput);
        userPanel.add(submit);
        options.add(buttons,BorderLayout.CENTER);
        options.add(userPanel,BorderLayout.SOUTH);
        return options;
    }

    /**
     * creates a button with a listener attatched to it
     * @param buttonLabel writing on button
     * @param actionListener listener to attach
     * @return returns created button
     */
    private JButton getButtonAttachedToController(String buttonLabel, ActionListener actionListener){
        JButton button = new JButton(buttonLabel);
        button.addActionListener(actionListener);
        return button;

    }

    /**
     * enables favourites view
     * @param value boolean with desired value to pass to the favourites view
     */
    public void enableFavouritesWindow(boolean value){
        favourites.setEnabled(value);
    }

    /**
     * gets the text the user types for creating a new user
     * @return the text entered
     */
    public JTextField getUserNameInput(){
        return userNameInput;
    }

    /**
     * gets the submit button
     * @return submit button
     */
    public JButton getSubmit(){
        return submit;
    }
}
