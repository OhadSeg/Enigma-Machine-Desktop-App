package components.firstTab;

import components.currConfiguration.CurrConfiComponentController;
import enigmaDtos.TestedMachineDetailsDTO;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import components.primary.AppController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.util.Pair;
import utils.RomanNumbers;

import java.util.*;

public class FirstTabComponentController {

    private class plubBoardColor {
        private String color;
        private Button first = null;
        private Button second = null;

        public plubBoardColor(String color) {
            this.color = color;
        }

        public Button getOtherButton(Button button) {
            if (this.first == button) {
                return this.second;
            }
            return this.first;
        }

        public void addButtonToPair(Button Letter) {
            if (first == null) {
                first = Letter;
            } else if (second == null) {
                second = Letter;
            }
        }

        public Boolean isPairComplete() {
            return first != null && second != null;
        }

        public String getColor() {
            return color;
        }

        public void resetPair() {
            first = null;
            second = null;
        }

        public Pair getAsPair() {
            if (first != null && second != null) {
                return new Pair(first.getText().charAt(0), second.getText().charAt(0));
            }
            return null;
        }

        public boolean isEmptyPair() {
            return first == null && second == null;
        }
    }

    private AppController mainController;
    @FXML
    private CurrConfiComponentController currConfiComponentController;

    @FXML
    private FlowPane setRotorsFlowPane;

    @FXML
    private FlowPane setPlugBoardFlowPane;

    @FXML
    private ComboBox<String> reflectorComb;
    @FXML
    private Label machineDetailsLabel;

    private SimpleStringProperty machineDetailsProperty;

    private ArrayList<ComboBox<Integer>> rotorsInUseCBArray = new ArrayList<>();

    private ArrayList<ComboBox<Character>> rotorStartingPosCBArray = new ArrayList<>();

    private ArrayList<plubBoardColor> plugColors;

    private Map<Button, String> plugBoardMap;

    public FirstTabComponentController() {
        machineDetailsProperty = new SimpleStringProperty();
    }

    public void setFirstTabComponent(){
        rotorsInUseCBArray = new ArrayList<>();
        rotorStartingPosCBArray = new ArrayList<>();

        plugColors = new ArrayList<>();
        plugBoardMap = new HashMap<>();
        reflectorComb.valueProperty().set(null);

        plugColors.add(new plubBoardColor("#FF0000"));
        plugColors.add(new plubBoardColor("#0000FF"));
        plugColors.add(new plubBoardColor("#008000"));
        plugColors.add(new plubBoardColor("#800080"));
        plugColors.add(new plubBoardColor("#00FFFF"));
        plugColors.add(new plubBoardColor("#FFA500"));
        plugColors.add(new plubBoardColor("#D2691E"));
        plugColors.add(new plubBoardColor("#FFFACD"));
        plugColors.add(new plubBoardColor("#FF69B4"));
        plugColors.add(new plubBoardColor("#778899"));
        plugColors.add(new plubBoardColor("#FFFFE0"));
        plugColors.add(new plubBoardColor("#FFFF00"));
        plugColors.add(new plubBoardColor("#008080"));
    }


    public void initialize() {
        if (currConfiComponentController != null) {
            currConfiComponentController.setFirstTabComponentController(this);
        }
        machineDetailsLabel.textProperty().bind(machineDetailsProperty);
    }
    public void setMachineDetailsLabel(String machineDetails){
        machineDetailsProperty.set(machineDetails);
    }

    // לאחד את הפרמטרים שהפונקציה מקבלת לDTO
    public void fileLoadedEnablingButtons(int amountOfAllReflectors, String abc, int amountOfAllRotors, int amountOfRotorsInUse) {
        setRotorsFlowPane.getChildren().clear();
        ArrayList<String> reflectorsList = new ArrayList<>();
        ArrayList<Character> abcList = new ArrayList<>();
        ArrayList<Integer> rotorsIdList = new ArrayList<>();

        for (int i = 0; i < amountOfAllReflectors; i++) {
            reflectorsList.add(RomanNumbers.values()[i].name());
        }
        ObservableList<String> reflectorsObservList = FXCollections.observableList(reflectorsList);
        reflectorComb.setItems(reflectorsObservList);

        for (int i = 1; i <= amountOfAllRotors; i++) {
            rotorsIdList.add(i);
        }
        ObservableList<Integer> rotorsIdObservList = FXCollections.observableList(rotorsIdList);

        for (int i = 0; i < abc.length(); i++) {
            abcList.add(abc.charAt(i));
        }
        ObservableList<Character> abcObservList = FXCollections.observableList(abcList);


        for (int i = 0; i < amountOfRotorsInUse; i++) {

            ComboBox<Integer> rotorsIdComboBox = new ComboBox<>(rotorsIdObservList);
            rotorsInUseCBArray.add(rotorsIdComboBox);

            ComboBox<Character> rotorStartPosComboBox = new ComboBox<>(abcObservList);
            rotorStartingPosCBArray.add(rotorStartPosComboBox);

            Label rotorIndexLabel = new Label();
            rotorIndexLabel.setText(i + 1 + "st Rotor");

            setRotorsFlowPane.getChildren().add(rotorIndexLabel);
            setRotorsFlowPane.setHgap(30);
            setRotorsFlowPane.getChildren().add(rotorsIdComboBox);
            setRotorsFlowPane.setHgap(40);
            setRotorsFlowPane.getChildren().add(rotorStartPosComboBox);
            setRotorsFlowPane.setVgap(10);
        }
        CreatePlugBoard(abc);
    }

    private void CreatePlugBoard(String abc) {
        setPlugBoardFlowPane.setAlignment(Pos.TOP_CENTER);
        setPlugBoardFlowPane.getChildren().clear();
        for (int i = 0; i < abc.length(); i++) {
            Button letterFromAbcButton = new Button();
            plugBoardMap.put(letterFromAbcButton, null);
                EventHandler<ActionEvent> actionEventEventHandler = event -> {
                if (plugBoardMap.get(letterFromAbcButton) == null) {
                    for (int j = 0; j < abc.length() / 2; j++) {
                        if (!plugColors.get(j).isPairComplete()) {
                            letterFromAbcButton.setStyle("-fx-background-color: " + plugColors.get(j).getColor());
                            plugColors.get(j).addButtonToPair(letterFromAbcButton);
                            plugBoardMap.put(letterFromAbcButton, plugColors.get(j).getColor());
                            break;
                        }
                    }
                } else {
                    for (int j = 0; j < abc.length() / 2; j++) {
                        if (plugColors.get(j).getColor() == plugBoardMap.get(letterFromAbcButton)) {
                            if (plugColors.get(j).isPairComplete()) {
                                plugColors.get(j).getOtherButton(letterFromAbcButton).setStyle(null);
                                plugBoardMap.put(plugColors.get(j).getOtherButton(letterFromAbcButton), null);
                            }
                            letterFromAbcButton.setStyle(null);
                            plugBoardMap.put(letterFromAbcButton, null);
                            plugColors.get(j).resetPair();
                            break;
                        }
                    }
                }
            };
            letterFromAbcButton.setOnAction(actionEventEventHandler);
            letterFromAbcButton.setMinWidth(28);
            letterFromAbcButton.setText(abc.charAt(i) + "");

            setPlugBoardFlowPane.getChildren().add(letterFromAbcButton);
            setPlugBoardFlowPane.setHgap(25);
            setPlugBoardFlowPane.setVgap(10);
        }
    }


    @FXML
    public void randomCodeButtonActionListener(ActionEvent event) {
        mainController.doRandomCode();
    }

    @FXML
    public void setCodeButtonActionListener(ActionEvent event) {
        TestedMachineDetailsDTO machineDetails = new TestedMachineDetailsDTO();
        rotorsInUseCBArray.forEach((rotorID) -> machineDetails.addToRotorsInUse(rotorID.getValue()));
        rotorStartingPosCBArray.forEach((StartingPos) -> machineDetails.addToStartingPos(StartingPos.getValue()));
        if (reflectorComb.getValue() != null) {
            machineDetails.setChosenValidReflector(RomanNumbers.valueOf(reflectorComb.getValue()));
        }
        plugColors.forEach((plugPair) -> {
            if (!plugPair.isEmptyPair()) machineDetails.addToPlugBoard(plugPair.getAsPair());
        });
        int x = 5;
        mainController.doEnterMachineDetailsManually(machineDetails);
    }

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    public void buildCurrConfiguration(TestedMachineDetailsDTO machineDetails){
        currConfiComponentController.buildNewCurrConfiguration(machineDetails);
    }

    public void cleanCurrConfiguration(){
        currConfiComponentController.cleanCurrConfiguration();
    }

    public void stepCurrConfiguration(TestedMachineDetailsDTO machineDetails){
        currConfiComponentController.stepCurrConfiguration(machineDetails);
    }

    public void setConfigurationMode(String machineDetails, TestedMachineDetailsDTO newConfiguration){
        setMachineDetailsLabel(machineDetails);
        buildCurrConfiguration(newConfiguration);
    }
}
