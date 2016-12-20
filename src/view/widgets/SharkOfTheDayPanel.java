package view.widgets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by Jack on 24/03/2016.
 */

/**
 * this class is a panel representing shark of the day
 */
public class SharkOfTheDayPanel extends JPanel {

    private JButton followButton;

    /**
     * constructor call the internal methods to make the top and bottom parts of the panel
     * @param sharkName name of shark
     * @param sharkGender gender of shark
     * @param sharkStageOfLife stage of sharks life
     * @param sharkSpecies species of shark
     * @param sharkLength length of shark
     * @param sharkWeight weight of shark
     * @param sharkDescription description of shark
     * @param isVideoAvailable boolean for if video is available
     * @param listener listener for the panel
     */
    public SharkOfTheDayPanel(String sharkName, String sharkGender,
                              String sharkStageOfLife, String sharkSpecies,
                              String sharkLength, String sharkWeight,
                              String sharkDescription, boolean isVideoAvailable,
                              ActionListener listener){
        setLayout(new GridLayout(2,1));
        constructTopPanel(sharkName, sharkGender, sharkStageOfLife, sharkSpecies, sharkLength, sharkWeight);
        constructBottomPanel(sharkDescription, isVideoAvailable, listener);
    }

    /**
     * creates a panel containing all the info of the shark
     * @param sharkName name of shark
     * @param sharkGender gender of shark
     * @param sharkStageOfLife stage of sharks life
     * @param sharkSpecies species of shark
     * @param sharkLength length of shark
     * @param sharkWeight weight of shark
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

    /**
     * creates a panel containing the sharks description and a follow option, also adds a see video button if video is available
     * @param sharkDescription description of shark
     * @param isVideoAvailable true if video is available
     * @param listener listener for buttins
     */
    private void constructBottomPanel(String sharkDescription, boolean isVideoAvailable, ActionListener listener){
        JPanel bottomPanel = new JPanel(new BorderLayout());

        JLabel descriptionTitle = new JLabel("Description:");
        bottomPanel.add(descriptionTitle, BorderLayout.NORTH);
        JTextArea description = new JTextArea(sharkDescription);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setEditable(false);
        bottomPanel.add(description, BorderLayout.CENTER);

        bottomPanel.add(getFollowPanel(isVideoAvailable, listener), BorderLayout.SOUTH);


        add(bottomPanel);
    }

    /**
     * makes a panel containing a follow button and a see video button if one is available
     * @param isVideoAvailable true if video is available
     * @param listener listener for buttons
     * @return
     */
    private JPanel getFollowPanel(boolean isVideoAvailable, ActionListener listener){
        JPanel followPanel = new JPanel(new FlowLayout());


        followButton = new JButton("Follow");
        followButton.addActionListener(listener);
        followPanel.add(followButton);
        JButton linkButton = new JButton("See Video");
        linkButton.addActionListener(listener);
        linkButton.setEnabled(isVideoAvailable);
        followPanel.add(linkButton);

        return followPanel;
    }

    /**
     * changes the text on the following button depending on if user is following shark or not
     * @param isFollowing true if person is following shark
     */
    public void setFollowing(boolean isFollowing){
        if(isFollowing){
            followButton.setText("Following");
        } else {
            followButton.setText("Follow");
        }
    }
}
