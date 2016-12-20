package view.widgets;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by Jack on 16/03/2016.
 */

/**
 * This class is a Panel that holds the sharks info
 */
public class SharkInfo extends JPanel {

    private JButton followButton;

    /**
     * constructor calls internal methods to create top and bottom panel for shark info
     * @param sharkName String containing name of shark
     * @param sharkGender String containing gender of shark
     * @param sharkStageOfLife String containing stage of sharks life
     * @param sharkSpecies String containing species of shark
     * @param sharkLength String containing length of shark
     * @param sharkWeight String containing weight of shark
     * @param sharkDescription String containing description of shark
     * @param pingDate String containing ping date of shark
     * @param listener Action Listener for buttons in panel
     */
    public SharkInfo(String sharkName,String sharkGender,
                     String sharkStageOfLife,String sharkSpecies,
                     String sharkLength,String sharkWeight,
                     String sharkDescription, String pingDate,
                     ActionListener listener){

        setLayout(new GridLayout(2,1));
        setBorder(new LineBorder(Color.BLACK, 1));

        constructTopPanel(sharkName, sharkGender, sharkStageOfLife, sharkSpecies, sharkLength, sharkWeight);

        constructBottomPanel(sharkDescription, pingDate, listener);

    }

    /**
     * Method to change the follow button to Unfollow and vice versa when approporiate
     * @param isFollowing a boolean representing whether or not user is folowing this shark
     */
    public void setFollowing(boolean isFollowing){
        if(isFollowing){
            followButton.setText("Following");
        } else {
            followButton.setText("Follow");
        }
    }

    /**
     * method to construct bottom panel containing shark description the date shark was pinged and a listener for any actions
     * @param sharkDescription String containing description of shark
     * @param pingDate String containing date shark was pinged
     * @param listener Sction listener for any events
     */
    private void constructBottomPanel(String sharkDescription, String pingDate, ActionListener listener){
        JPanel bottomPanel = new JPanel(new BorderLayout());

        JLabel descriptionTitle = new JLabel("Description:");
        bottomPanel.add(descriptionTitle, BorderLayout.NORTH);
        JTextArea description = new JTextArea(sharkDescription);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setEditable(false);
        bottomPanel.add(description, BorderLayout.CENTER);

        bottomPanel.add(getFollowPanel(pingDate, listener), BorderLayout.SOUTH);

        add(bottomPanel);
    }

    /**
     * creates a panel containing a follow button and the date the shark was pinged
     * @param pingDate date shark was pinged
     * @param listener listener for follow button
     * @return returns the follow panel
     */
    private JPanel getFollowPanel(String pingDate, ActionListener listener){
        JPanel followPanel = new JPanel(new FlowLayout());

        followPanel.add(new JLabel(pingDate));

        followButton = new JButton("Follow");
        followButton.addActionListener(listener);
        followPanel.add(followButton);

        return followPanel;
    }

    /**
     * creates the top pane; for the shark info panel consisting of the name, gender, stage of life , species,length and weight of the shark
     * @param sharkName String containing name of shark
     * @param sharkGender String containing gender of shark
     * @param sharkStageOfLife String containing stage of sharks life
     * @param sharkSpecies String containing species of shark
     * @param sharkLength String containing length of shark
     * @param sharkWeight String containing weight of shark
     */
    private void constructTopPanel(String sharkName,String sharkGender,String sharkStageOfLife,String sharkSpecies,String sharkLength,String sharkWeight){
        JPanel topPanel = new JPanel(new GridLayout(6,2));

        topPanel.add(new JLabel("Name:"));
        topPanel.add(new JLabel(sharkName));

        topPanel.add(new JLabel("Gender:"));
        topPanel.add(new JLabel(sharkGender));

        topPanel.add(new JLabel("Stage of Life:"));
        topPanel.add(new JLabel(sharkStageOfLife));

        topPanel.add(new JLabel("Species:"));
        topPanel.add(new JLabel(sharkSpecies));

        topPanel.add(new JLabel("Length:"));
        topPanel.add(new JLabel(sharkLength));

        topPanel.add(new JLabel("Weight"));
        topPanel.add(new JLabel(sharkWeight));

        add(topPanel);

    }
}
