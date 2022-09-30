package components.secondTab;

import components.currConfiguration.CurrConfiComponentController;
import enigmaDtos.TestedMachineDetailsDTO;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import components.primary.AppController;
import javafx.scene.layout.FlowPane;
import javafx.util.Duration;
import javafx.util.Pair;
import utils.EncryptionMode;

import java.util.HashMap;
import java.util.Map;

public class SecondTabComponentController {

    private AppController mainController;
    @FXML
    private CurrConfiComponentController currConfiComponentController;

    @FXML
    private TextField inputTF;

    @FXML
    private TextArea outputTA;

    @FXML
    private FlowPane abcKeyBoardFP;

    @FXML
    private FlowPane codedKeyBoardFP;

    @FXML
    private TextArea historyAndStatsTA;

    private Map<Character, Label> codedKeyBoardLabels;


    public SecondTabComponentController() {
        codedKeyBoardLabels = new HashMap<>();
    }

    public void initialize() {
        if (currConfiComponentController != null) {
            currConfiComponentController.setSecondTabComponentController(this);
        }
        historyAndStatsTA.setEditable(false);
        outputTA.setEditable(false);
    }

    @FXML
    public void processCodeButtonActionListener(ActionEvent event) {
        String textTtoCode = inputTF.getText();
        if (textTtoCode != null) {
            String textAfterCode = mainController.doRunCode(textTtoCode, EncryptionMode.WHOLEWORD);
            outputTA.setText(outputTA.getText() + "\n" + textAfterCode);
        }
    }


    public void fileLoadedEnablingButtons(String abc) {

//        resetCurrConfiguration();
        //בנייה של המקלדת המאירה
        codedKeyBoardLabels.clear();
        codedKeyBoardFP.setAlignment(Pos.TOP_CENTER);
        codedKeyBoardFP.getChildren().clear();
        for (int i = 0; i < abc.length(); i++) {
            Label letterFromAbcLabel = new Label(abc.charAt(i) + "");
            letterFromAbcLabel.setMinWidth(25);
            letterFromAbcLabel.setAlignment(Pos.CENTER);
            letterFromAbcLabel.setStyle("-fx-border-color: gray;");
            codedKeyBoardFP.getChildren().add(letterFromAbcLabel);
            codedKeyBoardFP.setHgap(25);
            codedKeyBoardFP.setVgap(10);
            codedKeyBoardLabels.put(abc.charAt(i), letterFromAbcLabel);
        }
        //בנייה של מקלדת תו תו
        abcKeyBoardFP.setAlignment(Pos.TOP_CENTER);
        abcKeyBoardFP.getChildren().clear();
        for (int i = 0; i < abc.length(); i++) {
            Button letterFromAbcButton = new Button();
            letterFromAbcButton.setText(abc.charAt(i) + "");
            EventHandler<ActionEvent> actionEventHandler = event -> {
                Character LetterAfterCode = mainController.doRunCode(letterFromAbcButton.getText() + "", EncryptionMode.LETTERAFTERLETTER).charAt(0);
                //אנימציה שמאירה את האות
                codedKeyBoardLabels.get(LetterAfterCode).setStyle("-fx-background-color: gold");
                PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.5));
                pauseTransition.setOnFinished(e -> {
                    codedKeyBoardLabels.get(LetterAfterCode).setStyle(null);
                    codedKeyBoardLabels.get(LetterAfterCode).setStyle("-fx-border-color: gray;");
                });
                pauseTransition.play();
                //להציג את התו לפני ואחרי ההצפנה כל אחד במקומו
                inputTF.setText(inputTF.getText() + letterFromAbcButton.getText());
                outputTA.setText(outputTA.getText() + codedKeyBoardLabels.get(LetterAfterCode).getText());
            };
            letterFromAbcButton.setOnAction(actionEventHandler);
            abcKeyBoardFP.getChildren().add(letterFromAbcButton);
            abcKeyBoardFP.setHgap(25);
            abcKeyBoardFP.setVgap(10);
        }
    }


    @FXML
    public void resetMachineButtonActionListener(ActionEvent event) {
        mainController.doReset();
    }

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    @FXML
    void clearButtonActionListener(ActionEvent event) {
        inputTF.clear();
        outputTA.clear();
    }

    @FXML
    void doneButtonActionListener(ActionEvent event) {
        addNewEncryptionTextToHistoryAndStats(new Pair<>(new Pair<>(outputTA.getText(), new Long(0)), inputTF.getText()));
    }

    public void buildCurrConfiguration(TestedMachineDetailsDTO machineDetails) {
        currConfiComponentController.buildNewCurrConfiguration(machineDetails);
    }

    public void cleanCurrConfiguration() {
        currConfiComponentController.cleanCurrConfiguration();
    }

    public void stepCurrConfiguration(TestedMachineDetailsDTO machineDetails) {
        currConfiComponentController.stepCurrConfiguration(machineDetails);
    }

    public void clearHistoryAndStats() {
        historyAndStatsTA.clear();
    }

    public void addNewConfigurationToHistoryAndStats(String dataToAdd) {
        if (dataToAdd != null) {
            historyAndStatsTA.appendText(dataToAdd + "\n");
        }
    }

    public void setConfigurationMode(TestedMachineDetailsDTO newConfiguration, String consoleConfiguration) {
        buildCurrConfiguration(newConfiguration);
        addNewConfigurationToHistoryAndStats(consoleConfiguration);
    }

    public void loadFileMode(String abc){
        fileLoadedEnablingButtons(abc);
        clearHistoryAndStats();
    }
    public void addNewEncryptionTextToHistoryAndStats(Pair<Pair<String, Long>, String> codedTextAndTime) {
        StringBuilder dataToAdd = new StringBuilder();
        dataToAdd.append("     ");
        dataToAdd.append("<" + codedTextAndTime.getValue() + ">");
        dataToAdd.append(" --> ");
        dataToAdd.append("<" + codedTextAndTime.getKey().getKey() + ">");
        dataToAdd.append(" (" + codedTextAndTime.getKey().getValue() + " ns)");
        if (dataToAdd != null) {
            historyAndStatsTA.appendText(dataToAdd + "\n");
        }
    }
}
