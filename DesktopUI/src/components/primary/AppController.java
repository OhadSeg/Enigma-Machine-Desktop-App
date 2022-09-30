package components.primary;

import components.firstTab.FirstTabComponentController;
import components.header.HeaderComponentController;
import components.secondTab.SecondTabComponentController;
import components.thirdTab.ThirdTabComponentController;
import enigmaDtos.BruteForceDetailsDTO;
import enigmaDtos.CandidateDTO;
import enigmaDtos.TestedMachineDetailsDTO;
import exceptions.MachineDetailsFromUserException;
import exceptions.UserInputException;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import operation.Operator;
import utils.EncryptionMode;

import java.io.File;

public class AppController {

    @FXML
    private ScrollPane headerComponent;
    @FXML
    private HeaderComponentController headerComponentController;
    @FXML
    private ScrollPane firstTabComponent;
    @FXML
    private FirstTabComponentController firstTabComponentController;
    @FXML
    private ScrollPane secondTabComponent;
    @FXML
    private SecondTabComponentController secondTabComponentController;
    @FXML
    private ScrollPane thirdTabComponent;
    @FXML
    private ThirdTabComponentController thirdTabComponentController;
    @FXML
    private Tab machineTab;
    @FXML
    private Tab encryptionTab;
    @FXML
    private Tab bruteForceTab;

    private SimpleBooleanProperty ifFileSelected;

    private Stage primaryStage;

    private Operator operator;

    public AppController() {
        ifFileSelected = new SimpleBooleanProperty();
        operator = new Operator();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }


    @FXML
    public void initialize() {
        if (headerComponentController != null && firstTabComponentController != null && secondTabComponentController != null && thirdTabComponentController != null) {
            headerComponentController.setMainController(this);
            firstTabComponentController.setMainController(this);
            secondTabComponentController.setMainController(this);
            thirdTabComponentController.setMainController(this);
            machineTab.setDisable(true);
            encryptionTab.setDisable(true);
            bruteForceTab.setDisable(true);

        }
    }

    public String doRunCode(String textToCode, EncryptionMode mode) {
        try {
            Pair codedTextAndTime = operator.setTextToCode(textToCode, mode);
            operator.updateDynamicCurrConfiguration();
            firstTabComponentController.stepCurrConfiguration(operator.getNewCurrConfiguration());
            firstTabComponentController.setMachineDetailsLabel(operator.getMachineDetails());
            secondTabComponentController.stepCurrConfiguration(operator.getNewCurrConfiguration());
            thirdTabComponentController.stepCurrConfiguration(operator.getNewCurrConfiguration());

            if (mode == EncryptionMode.WHOLEWORD) {
                secondTabComponentController.addNewEncryptionTextToHistoryAndStats(new Pair<>(codedTextAndTime, textToCode));
            }

            return codedTextAndTime.getKey().toString();
        } catch (UserInputException e) {
            showMessage("Code error", Alert.AlertType.WARNING, e.getMessage());
        }
        return null;
    }

    public void doRandomCode() {
        operator.randomMode();
        encryptionTab.setDisable(false);
        bruteForceTab.setDisable(false);
        firstTabComponentController.setConfigurationMode(operator.getMachineDetails(), operator.getNewCurrConfiguration());
        secondTabComponentController.setConfigurationMode(operator.getNewCurrConfiguration(), operator.getConsoleCurrConfiguration());
        thirdTabComponentController.buildCurrConfiguration(operator.getNewCurrConfiguration());
        thirdTabComponentController.clearAllComponents();
        thirdTabComponentController.machineDetailsLoaded();
    }

    public void doEnterMachineDetailsManually(TestedMachineDetailsDTO machineDetails) {
        try {
            operator.setMachineDetails(machineDetails);
            firstTabComponentController.setConfigurationMode(operator.getMachineDetails(), operator.getNewCurrConfiguration());
            secondTabComponentController.setConfigurationMode(operator.getNewCurrConfiguration(), operator.getConsoleCurrConfiguration());
            thirdTabComponentController.buildCurrConfiguration(operator.getNewCurrConfiguration());
            thirdTabComponentController.clearAllComponents();
            thirdTabComponentController.machineDetailsLoaded();
            encryptionTab.setDisable(false);
            bruteForceTab.setDisable(false);
        } catch (MachineDetailsFromUserException e) {
            showMessage("File error", Alert.AlertType.ERROR, e.getMessage());
        }
    }

    public void doLoadFile() {
        operator = new Operator();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select words file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null) {
            return;
        }

        String absolutePath = selectedFile.getAbsolutePath();
        //שולחים את הנתיב לבדיקה
        try {
            operator.setXmlPath(absolutePath);
            //אם הקובץ טוב
            machineTab.setDisable(false);
            encryptionTab.setDisable(true);
            bruteForceTab.setDisable(true);
            //
            firstTabComponentController.setFirstTabComponent();
            // להקטין לDTO
            firstTabComponentController.cleanCurrConfiguration();
            secondTabComponentController.cleanCurrConfiguration();
            thirdTabComponentController.cleanCurrConfiguration();
            firstTabComponentController.fileLoadedEnablingButtons(operator.getNumOfReflectorsFromEnigma(), operator.getAbcFromEnigma(), operator.getAmountOfAllRotors(), operator.getAmountOfAllRotorsInUse());
            firstTabComponentController.setMachineDetailsLabel(operator.getMachineDetails());
            secondTabComponentController.loadFileMode(operator.getAbcFromEnigma());
            thirdTabComponentController.setAgentsComb(operator.getMaxAmountOfAgents());
            thirdTabComponentController.setWordsToDictionary(operator.getDictionary());
            thirdTabComponentController.clearAllComponents();
            headerComponentController.setFilePathProperty(absolutePath);
            headerComponentController.setFileErrorMessageProperty("File is loaded successfully");

        } catch (Exception e) {
            //אם הקובץ פגום
            showMessage("File error", Alert.AlertType.ERROR, e.getMessage());
        }
    }

    public void showMessage(String title, Alert.AlertType alertType, String content) {
        Alert alert = new Alert(alertType);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.setTitle(title);
        alert.setAlertType(alertType);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void doReset() {
        operator.resetEnigma();
        firstTabComponentController.stepCurrConfiguration(operator.getResetedConfiguration());
        secondTabComponentController.stepCurrConfiguration(operator.getResetedConfiguration());
        thirdTabComponentController.stepCurrConfiguration(operator.getNewCurrConfiguration());
    }


    public void doBruteForce(BruteForceDetailsDTO bfDetails) {
        try {
            operator.startBruteForce(bfDetails);
        }catch (UserInputException e) {
            showMessage("Code error", Alert.AlertType.WARNING, e.getMessage());
            return;
        }

        Thread listenerThread = new Thread(() -> {
            try {
                while (true) {
                    if (operator.isBruteForceStillRunning()) {
                        CandidateDTO candidate = operator.getCandidateFromDM();
                        thirdTabComponentController.updateCandidateList(candidate);
                        thirdTabComponentController.updateCandidateProgressBar(operator.getProgressFromDM());
                    } else {
                        System.out.println("finish");
                        break;
                    }
                }
            } catch (Exception e) {
                showMessage("Error", Alert.AlertType.ERROR, e.getMessage());
            }
        });
        listenerThread.start();
    }

    public void doPause(){
        operator.doPause();
    }
    public void doResume(){
        operator.doResume();
    }
    public void doStop(){
        operator.doStop();
    }
}
