package controller;

import api.jaws.Shark;
import controller.Sharks.SharkController;
import controller.Sharks.SharkInfoWidgetController;
import view.CompatibilityScreen;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class collects the shark data from the API, calculates the compatibility and updates the view
 */
public class CompatibilityScreenController implements ItemListener{
    private static HashMap<String, Shark> males;
    private static HashMap<String, Shark> females;

    private CompatibilityScreen view;
    private String selectedMaleShark = "Male";
    private String selectedFemaleShark = "Female";

    /**
     * Retrieves sharks by gender
     */
    static {
        males = SharkController.getAllSharksOfGender("Male");
        females = SharkController.getAllSharksOfGender("Female");
        males.put("Male", null);
        females.put("Female", null);
    }

    /**
     * Contructs an instance of the compatibility screen controller
     */
    public CompatibilityScreenController() {
        ArrayList<String> maleNames = new ArrayList<>(males.keySet());
        maleNames.add(0, "Male");
        ArrayList<String> femaleNames = new ArrayList<>(females.keySet());
        femaleNames.add(0, "Female");
        view = new CompatibilityScreen(maleNames, femaleNames, this);
    }

    /**
     * Updates the details of the male shark in the view
     * @param shark - the shark model to display in the male details panel
     */
    public void displayDetailsOfMale(String shark) {
        if (shark.equals("Male")) {
            view.setNoMaleInfo();
        } else {
            view.setMaleSharkInfo(
                    SharkInfoWidgetController.getViewWithoutAdding(males.get(shark)));
        }
    }

    /**
     * Updates the details of the male shark in the view
     * @param shark - the shark model to display in the female details panel
     */
    public void displayDetailsOfFemale(String shark) {
        if (shark.equals("Female")) {
            view.setNoFemaleInfo();
        } else {
            view.setFemaleSharkInfo(
                    SharkInfoWidgetController.getViewWithoutAdding(females.get(shark)));
        }
    }

    /**
     * Updates the compatibility value
     * @param male - male shark
     * @param female - female shark
     */
    public void updateCompatibility(String male, String female){
        if(!selectedFemaleShark.equals("Female") && !selectedMaleShark.equals("Male")){
            view.setCompatibilityNumber(
                    calculateCompatibility(males.get(male), females.get(female)));
        }
    }

    /**
     * Calculates the compatibility between the two selected sharks
     * @param male - male shark
     * @param female - female shark
     * @return int
     */
    public int calculateCompatibility(Shark male, Shark female) {
        double value = (differentiateSpecies(male, female) + differentiateStageOfLife(male, female)) / 2;
        return (int)Math.round(value*100);
    }

    /**
     * Checks if the two sharks are from the same species
     * @param male - male shark
     * @param female - female shark
     * @return double
     */
    public double differentiateSpecies(Shark male, Shark female) {
        if (!male.getSpecies().equals(female.getSpecies())) {
            return 0;
        }
        return 1;
    }

    /**
     * Calculates the probability of matching the two sharks depending on their stage of life
     * @param male - male shark
     * @param female - female shark
     * @return double
     */
    public double differentiateStageOfLife(Shark male, Shark female) {
        double difference = 0;
        switch (male.getStageOfLife()) {
            case "Mature": {
                switch (female.getStageOfLife()) {
                    case "Mature":
                        difference = 1;
                        break;
                    case "Immature":
                        difference = 0;
                        break;
                    case "Undetermined":
                        difference = 0.5;
                        break;
                }
                break;
            }
            case "Immature":
                difference = 0;
                break;
            case "Undetermined": {
                switch (female.getStageOfLife()) {
                    case "Mature":
                        difference = 0.5;
                        break;
                    case "Immature":
                        difference = 0;
                        break;
                    case "Undetermined":
                        difference = 0.25;
                        break;
                }
                break;
            }
        }
        return difference;
    }

    /**
     * Detects the change in value of the compatibility
     *
     * @param e - change event
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        String changedInput = (String)e.getItem();
        if(males.containsKey(changedInput)){
            selectedMaleShark = changedInput;
            displayDetailsOfMale(changedInput);
        }else if(females.containsKey(changedInput)){
            selectedFemaleShark = changedInput;
            displayDetailsOfFemale(changedInput);
        }
        updateCompatibility(selectedMaleShark, selectedFemaleShark);
    }


}
