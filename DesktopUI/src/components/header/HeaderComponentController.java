package components.header;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import components.primary.AppController;
import javafx.stage.FileChooser;

import java.io.File;

public class HeaderComponentController {

    @FXML
    private Label filePathLabel;

    @FXML
    private Label fileErrorMessageLabel;

    @FXML
    private Button loadFileButton;

    private AppController mainController;

    private SimpleStringProperty filePathProperty;

    private SimpleStringProperty fileErrorMessageProperty;


    public void initialize() {
        filePathLabel.textProperty().bind(filePathProperty);
        fileErrorMessageLabel.textProperty().bind(fileErrorMessageProperty);
    }

    public HeaderComponentController() {
        filePathProperty = new SimpleStringProperty();
        fileErrorMessageProperty = new SimpleStringProperty();

    }

    public void setFilePathProperty(String selectedFile) {
        this.filePathProperty.setValue(selectedFile);
    }

    public void setFileErrorMessageProperty(String errorMessage) {
        this.fileErrorMessageProperty.setValue(errorMessage);
    }

    @FXML
    public void loadFileButtonActionListener(ActionEvent event) {
        mainController.doLoadFile();
    }

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }
}