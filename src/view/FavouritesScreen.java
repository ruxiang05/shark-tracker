package view;

import view.widgets.FavouriteSharkInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * screen showing all the favourite sharks
 */
public class FavouritesScreen extends JFrame {

    private JPanel favouriteSharks;

    /**
     * constuctor makes the frame and calls internal method to create widgets,initialising the screen
     * @param listener action listener for each widget
     */
    public FavouritesScreen(ActionListener listener){
        super("Favourites");
        setLayout(new BorderLayout());
        createWidgets(listener);
        setSize(500, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.setVisible(true);
    }

    /**
     * calls intenal methods to create the dispaly of users favourite sharks
     * @param listener listener for the button
     */
    private void createWidgets(ActionListener listener){
        JLabel description = new JLabel("Your favourite sharks are this far away from you right now:");
        description.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(description,BorderLayout.NORTH);
        createMainSharksView();
        createNavigationPanel(listener);
    }

    /**
     * creates a panel with the back and map buttons
     * @param listener actionlistener for the buttons
     */
    private void createNavigationPanel(ActionListener listener){
        JPanel bottomBar = new JPanel(new GridLayout(1, 2));
        JButton backButton = new JButton("Back");

        backButton.addActionListener(listener);
        bottomBar.add(backButton);
        JButton mapButton = new JButton("Map");
        mapButton.addActionListener(listener);
        bottomBar.add(mapButton);

        add(bottomBar, BorderLayout.SOUTH);
    }

    /**
     * creates a panel that contains panels containing each shark
     */
    private void createMainSharksView(){
        favouriteSharks = new JPanel();
        favouriteSharks.setLayout(new BoxLayout(favouriteSharks, BoxLayout.Y_AXIS));
        JScrollPane favouritesScrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        favouritesScrollPane.setViewportView(favouriteSharks);
        add(favouritesScrollPane, BorderLayout.CENTER);
    }

    /**
     * adds a favourite shark to the main sharks panel
     * @param sharkInfo info of shark
     */
    public void addFavouriteShark(FavouriteSharkInfo sharkInfo){
        favouriteSharks.add(sharkInfo);
        repaint();
        revalidate();
    }

    /**
     * removes all sharks from the view
     */
    public void clearAllSharks(){
        favouriteSharks.removeAll();
        repaint();
        revalidate();
    }

}