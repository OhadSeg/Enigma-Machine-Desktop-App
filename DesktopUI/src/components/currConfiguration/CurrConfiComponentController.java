package components.currConfiguration;

import components.firstTab.FirstTabComponentController;
import components.secondTab.SecondTabComponentController;
import components.thirdTab.ThirdTabComponentController;
import enigmaDtos.TestedMachineDetailsDTO;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;

public class CurrConfiComponentController {
    @FXML
    private FlowPane currRotorsFP;
    @FXML
    private FlowPane currWindowPosFP;
    @FXML
    private FlowPane currNotchFP;
    @FXML
    private FlowPane currPlugBoardFP;
    @FXML
    private Label reflectorLabel;
    private FirstTabComponentController firstTabComponentController;
    private SecondTabComponentController secondTabComponentController;
    private ThirdTabComponentController thirdTabComponentController;


    private ArrayList<Label> rotorsIdLabels = new ArrayList<>();
    private ArrayList<Label> windowPosLabels = new ArrayList<>();
    private ArrayList<Label> notchPosLabels = new ArrayList<>();
    private ArrayList<Label> plugBoardLabel = new ArrayList<>();

    public void initialize() {
        currRotorsFP.setAlignment(Pos.TOP_CENTER);
        currWindowPosFP.setAlignment(Pos.TOP_CENTER);
        currNotchFP.setAlignment(Pos.TOP_CENTER);
        reflectorLabel.setAlignment(Pos.TOP_CENTER);
        currPlugBoardFP.setAlignment(Pos.TOP_CENTER);
    }

    public void buildNewCurrConfiguration(TestedMachineDetailsDTO currConfiguration) {
        cleanCurrConfiguration();
        currConfiguration.getChosenValidInUseRotors().forEach((rotor) -> {
            Label rotorLabel = new Label(rotor + "");
            rotorsIdLabels.add(rotorLabel);
            currRotorsFP.getChildren().add(rotorLabel);
            currRotorsFP.setHgap(10);
        });

        currConfiguration.getRotorsWindowDynamicPosition().forEach((windowPos) -> {
            Label windowPosLabel = new Label(windowPos + "");
            windowPosLabels.add(windowPosLabel);
            currWindowPosFP.getChildren().add(windowPosLabel);
            currWindowPosFP.setHgap(10);
        });

        currConfiguration.getNotchesDynamicPosition().forEach((notchPos) -> {
            Label notchPosLabel = new Label(notchPos + "");
            notchPosLabels.add(notchPosLabel);
            currNotchFP.getChildren().add(notchPosLabel);
            currNotchFP.setHgap(10);
        });

        reflectorLabel.setText(currConfiguration.getChosenValidReflector() + "");

        currConfiguration.getChosenValidPlugs().forEach((plugPair) -> {
            Label plugPairLabel = new Label(plugPair.getKey() + " <-> " + plugPair.getValue());
            plugBoardLabel.add(plugPairLabel);
            currPlugBoardFP.getChildren().add(plugPairLabel);
            currPlugBoardFP.setHgap(10);
        });
    }

    public void updateNewCurrConfiguration(TestedMachineDetailsDTO currConfiguration) {
        for (int i = 0; i < rotorsIdLabels.size(); i++) {
            rotorsIdLabels.get(i).setText(currConfiguration.getChosenValidInUseRotors().get(i) + "");
            windowPosLabels.get(i).setText(currConfiguration.getRotorsWindowDynamicPosition().get(i)+"");
            notchPosLabels.get(i).setText(currConfiguration.getNotchesDynamicPosition().get(i)+"");
        }
        reflectorLabel.setText(currConfiguration.getChosenValidReflector()+"");

        plugBoardLabel.clear();
        currConfiguration.getChosenValidPlugs().forEach((plugPair) -> {
            Label plugPairLabel = new Label(plugPair.getKey() + " <-> " + plugPair.getValue());
            plugBoardLabel.add(plugPairLabel);
            currPlugBoardFP.getChildren().add(plugPairLabel);
            currPlugBoardFP.setHgap(10);
        });
    }

    public void stepCurrConfiguration(TestedMachineDetailsDTO currConfiguration) {
        for (int i = 0; i < windowPosLabels.size(); i++) {
            windowPosLabels.get(i).setText(currConfiguration.getRotorsWindowDynamicPosition().get(i) + "");
            notchPosLabels.get(i).setText(currConfiguration.getNotchesDynamicPosition().get(i) + "");
        }
    }


    public void cleanCurrConfiguration() {
        currRotorsFP.getChildren().clear();
        currWindowPosFP.getChildren().clear();
        reflectorLabel.setText("");
        currNotchFP.getChildren().clear();
        currPlugBoardFP.getChildren().clear();

        rotorsIdLabels.clear();
        windowPosLabels.clear();
        notchPosLabels.clear();
        plugBoardLabel.clear();
    }

    public void setFirstTabComponentController(FirstTabComponentController firstTabComponentController) {
        this.firstTabComponentController = firstTabComponentController;
    }

    public void setSecondTabComponentController(SecondTabComponentController secondTabComponentController) {
        this.secondTabComponentController = secondTabComponentController;
    }
    public void setThirdTabComponentController(ThirdTabComponentController thirdTabComponentController) {
        this.thirdTabComponentController = thirdTabComponentController;
    }
}