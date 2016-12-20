package view;

import view.widgets.SharkInfo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ItemListener;
import java.util.ArrayList;

/**
 * This class represents the compatibility screen
 * @see controller.SearchScreenController
 */
public class CompatibilityScreen extends JFrame {

    private  JLabel compatibilityNumber;
    private JPanel maleArea;
    private JPanel femaleArea;

    /**
     * Constructs an instance of the compatibility screen, having given the list of all male and female sharks
     * @param males  - list of male names
     * @param females  - list of female names
     * @param listener  - the listener of the comboboxes
     *
     */
    public CompatibilityScreen(ArrayList<String> males, ArrayList<String> females, ItemListener listener){
        setLayout(new BorderLayout());
        setSize(1000,600);
        createWidgets(males, females, listener);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Creates all the widgets inside the compatibility screen
     * @param males - list of male names
     * @param females - list of female names
     * @param listener - the listener of the comboboxes
     */
    public void createWidgets(ArrayList<String> males, ArrayList<String> females, ItemListener listener){
        compatibilityNumber = new JLabel("0");
        JPanel percentagePanel = new JPanel(new FlowLayout());
        percentagePanel.add(compatibilityNumber, RIGHT_ALIGNMENT);
        percentagePanel.add(new JLabel("%"), LEFT_ALIGNMENT);

        add(percentagePanel,BorderLayout.SOUTH);

        createComboBoxes(males, females, listener);

        createDetailsPanels();
    }

    /**
     * Creates the two comboboxes, one for males and one for females
     * @param males  - list of male names
     * @param females  - list of female names
     * @param listener  - the listener of the comboboxes
     */
    public void createComboBoxes(ArrayList<String> males, ArrayList<String> females, ItemListener listener){
        JPanel comboBoxes = new JPanel();
        comboBoxes.setLayout(new GridLayout(1,2));

        JComboBox<String> malesComboBox = createComboBox(males, listener);
        comboBoxes.add(malesComboBox);

        JComboBox<String> femalesComboBox = createComboBox(females, listener);
        comboBoxes.add(femalesComboBox);

        add(comboBoxes,BorderLayout.NORTH);
    }

    /**
     * Adds data to and returns the combobox
     * @param names - list of names
     * @param listener - combobox listener
     * @return  JCombobox
     */
    public JComboBox<String> createComboBox(ArrayList<String> names, ItemListener listener){
        JComboBox<String> comboBox = new JComboBox<>();
        for(String s : names){
            comboBox.addItem(s);
        }
        comboBox.addItemListener(listener);
        comboBox.setBorder(new EmptyBorder(10,10,10,10));
        return comboBox;
    }

    /**
     * Creates the two panels for the male and female shark and adds them to the compatibility screen
     */
    public void createDetailsPanels(){
        maleArea = new JPanel();
        maleArea.setBorder(new EmptyBorder(10,10,10,10));
        femaleArea = new JPanel();
        femaleArea.setBorder(new EmptyBorder(10,10,10,10));
        JPanel details = new JPanel(new GridLayout(1,2));
        details.add(maleArea,CENTER_ALIGNMENT);
        details.add(femaleArea,CENTER_ALIGNMENT);

        add(details,BorderLayout.CENTER);
    }

    /**
     * Sets the value of the compatibilityNumber label
     * @param compatibilityNumber - compatibility value
     */
    public void setCompatibilityNumber(int compatibilityNumber) {
        this.compatibilityNumber.setText("" + compatibilityNumber);
    }

    /**
     * Removes the male details
     */
    public void setNoMaleInfo(){
        maleArea.removeAll();
        redraw();
    }

    /**
     * Removes the female details
     */
    public void setNoFemaleInfo(){
        femaleArea.removeAll();
        redraw();
    }

    /**
     * Sets the male details
     * @param sharkInfo  - details panel
     */
    public void setMaleSharkInfo(SharkInfo sharkInfo){
        maleArea.removeAll();
        if(sharkInfo != null) {
            maleArea.add(sharkInfo);
        }
        redraw();
    }

    /**
     * Sets the femail details
     * @param sharkInfo - details panel
     * Sets the female details
     * @param sharkInfo
     */
    public void setFemaleSharkInfo(SharkInfo sharkInfo){
        femaleArea.removeAll();
        if(sharkInfo != null) {
            femaleArea.add(sharkInfo);
        }
        redraw();
    }

    /**
     * Refreshes the widget
     */
    private void redraw(){
        pack();
        repaint();
        revalidate();
    }
}
