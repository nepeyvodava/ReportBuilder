package com.nva.rbuilder.view;

import com.nva.rbuilder.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class RootLayoutController {

    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ReportBuilder");
        alert.setHeaderText("ReportBuilder v1.3.0");
        alert.setContentText("Author: [N:V:A]\n" +
                "QA Manager: Dimka\n" +
                "Website: https://github.com/nepeyvodava");

        alert.showAndWait();
    }

    @FXML
    public void handleExit(){
        System.exit(0);
    }

}
