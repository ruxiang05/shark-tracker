package view.widgets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by Adam on 22/03/2016.
 */

/**
 * This class is for a Panel that displays a users favourite sharks
 */
public class FavouriteSharkInfo extends JPanel {

    /**
     * Constructor makes a grid that contains labels for sharks name, distance and info as well as a button to unfollow or request more info
     * @param sharkName String containing sharks name
     * @param sharkDistance String containing the distance from the user
     * @param additionalInfo String containing info about the shark
     * @param listener Action listener for the buttons
     */
    public FavouriteSharkInfo(String sharkName, String sharkDistance,
                              String additionalInfo, ActionListener listener) {
        super(new GridLayout(1, 5));

        setMaximumSize(new Dimension(500, 30));

        add(new JLabel(sharkName));
        add(new JLabel(sharkDistance));
        add(new JLabel(additionalInfo));

        JButton unfollowButton = new JButton("Following");
        unfollowButton.addActionListener(listener);

        JButton infoButton = new JButton("More Info");
        infoButton.addActionListener(listener);

        add(unfollowButton);
        add(infoButton);
    }
}
