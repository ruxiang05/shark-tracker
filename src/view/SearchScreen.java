package view;

import controller.Sharks.SharkController;
import controller.SearchScreenController;
import model.SharkConstants;
import view.widgets.SharkInfo;
import view.widgets.SharkOfTheDayPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Jack on 14/03/2016.
 */

/**
 * class is a panel that displays all search results and shark of the day
 */
public class SearchScreen extends JFrame{

    private JPanel mainPane;
    private SearchScreenController controller;


    /**
     * sets controller for window and calls initialise frame internal method
     * @param controller controller for window
     * @param sharkOfTheDayPanel Panel representing shark of the day
     */
    public SearchScreen(SearchScreenController controller, SharkOfTheDayPanel sharkOfTheDayPanel){
        super("Search");
        this.controller = controller;
        setLayout(new BorderLayout());
        initialiseFrame(sharkOfTheDayPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1200, 1000);
        setVisible(true);
    }

    /**
     * takes a shark info panel and adds it to the view
     * @param sharkInfo panel with sharks info
     */
    public void addNewSharkToSearch(SharkInfo sharkInfo){
        mainPane.add(sharkInfo);
        repaint();
        revalidate();
    }

    /**
     * Adds a no results found message for if no resukts are found
     */
    public void addNoResults(){
        mainPane.add(new JLabel("No Results found...\n Try different criteria"));
        repaint();
        revalidate();
    }

    /**
     * removes all shark info panels
     */
    public void clearSharkResults(){
       mainPane.removeAll();
    }

    /**
     * creates all the panels that a=make up the search screen
     * @param sharkOfTheDayPanel panel containing info for the shark of the day
     */
    private void initialiseFrame(SharkOfTheDayPanel sharkOfTheDayPanel){
        add(createLeftPane(sharkOfTheDayPanel), BorderLayout.WEST);
        createSearchResultsPanel();
        createBottomBar();
    }

    /**
     * creates a panel to contain shark of the day and the search parameters
     * @param sharkOfTheDayPanel Panel containg info on shark of the day
     * @return returns the created panel
     */
    private JPanel createLeftPane(SharkOfTheDayPanel sharkOfTheDayPanel){
        JPanel leftPane = new JPanel();
        leftPane.setLayout(new GridLayout(3,1));
        leftPane.add(sharkOfTheDayPanel);
        leftPane.add(createSearchFieldPane());
        leftPane.add(createLogoSearchPane());
        return leftPane;
    }

    /**
     * creates a pane with the logo and search panel
     * @return created panel
     */
    private JPanel createLogoSearchPane(){
        JPanel buttonLogoPanel = new JPanel(new BorderLayout());
        buttonLogoPanel.add(getSearchStatsButtonPanel(), BorderLayout.NORTH);

        JLabel logo = new JLabel(new ImageIcon("./src/resources/shark.png"));
        buttonLogoPanel.add(logo, BorderLayout.CENTER);

        return buttonLogoPanel;
    }

    /**
     * creates a panel with three buttons, search statistics and compatatbility
     * @return returns created panel
     */
    private JPanel getSearchStatsButtonPanel(){
        JPanel buttonsPanel = new JPanel(new GridLayout(3, 1));

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(controller);
        buttonsPanel.add(searchButton);

        JButton statisticsButton = new JButton("Statistics");
        statisticsButton.addActionListener(controller);
        buttonsPanel.add(statisticsButton);

        JButton compatibilityButton = new JButton("Compatibility");
        compatibilityButton.addActionListener(controller);
        buttonsPanel.add(compatibilityButton);

        return buttonsPanel;
    }

    /**
     * creates a panel with combo boxes allowing user to specify the parameters for a search
     * @return returns created panel
     */
    private JPanel createSearchFieldPane(){
        JPanel searchFieldPanel = new JPanel(new GridLayout(5, 1));
        searchFieldPanel.add(new JLabel("Shark Tracker"));

        JComboBox<String> trackingRangeSelector = createComboBox(
                SharkConstants.trackRanges, "trackingRange");
        searchFieldPanel.add(createComboField("Tracking Range:", trackingRangeSelector));

        JComboBox<String> genderSelector = createComboBox(
                SharkConstants.genders, "gender");
        searchFieldPanel.add(createComboField("Gender:", genderSelector));

        JComboBox<String> stageSelector = createComboBox(
                SharkConstants.stageOfLives, "stageOfLife");
        searchFieldPanel.add(createComboField("Stage of Life:", stageSelector));

        JComboBox<String> tagLocationSelector = createComboBox(
                SharkController.tagLocations, "tagLocation");
        searchFieldPanel.add(createComboField("Tag Locations:", tagLocationSelector));

        return searchFieldPanel;
    }

    /**
     * creates a panel to contain each combo box
     * @param title title of combobox
     * @param comboBox the combobox
     * @return created panel
     */
    private JPanel createComboField(String title, JComboBox<String> comboBox){
        JPanel comboField = new JPanel(new GridLayout(2, 1));
        comboField.add(new JLabel(title));
        comboField.add(comboBox);
        return comboField;
    }

    /**
     * creates a combo box with the correct options
     * @param searchOptions contains the appropriate options for the combobox
     * @param name name of box
     * @return
     */
    private JComboBox<String> createComboBox(String[] searchOptions, String name){
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setName(name);
        for(String s : searchOptions){
            comboBox.addItem(s);
        }
        comboBox.addItemListener(controller);
        return comboBox;
    }

    /**
     * creates a panel to contain the results of the search
     */
    private void createSearchResultsPanel(){
        mainPane = new JPanel();
        mainPane.setLayout(new BoxLayout(mainPane,BoxLayout.Y_AXIS));
        JScrollPane searchScrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        searchScrollPane.getViewport().add(mainPane);
        getContentPane().add(searchScrollPane);
    }

    /**
     * creates a bar with the aknowledgement, back button and refresh button
     */
    private void createBottomBar(){
        JPanel bottomBar = new JPanel(new FlowLayout());
        bottomBar.add(new JLabel(SharkController.acknowledgement));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(controller);
        bottomBar.add(backButton);
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(controller);
        bottomBar.add(refreshButton);
        add(bottomBar, BorderLayout.SOUTH);
    }
}
