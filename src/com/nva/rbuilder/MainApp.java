package com.nva.rbuilder;

import com.nva.rbuilder.model.ReportConfig;
import com.nva.rbuilder.utils.ConfigsList;
import com.nva.rbuilder.utils.ReadConfig;
import com.nva.rbuilder.view.ConfigEditDialogController;
import com.nva.rbuilder.view.ConfigOverviewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    public static final String CONFIGS_DIR= "DATA/";

    //наблюдаемый список конфигов
    private static ObservableList<ReportConfig> reportConfigList = FXCollections.observableArrayList();

    public MainApp() {
        refreshConfigList();
    }

    public void refreshConfigList(){
        if(reportConfigList.size() != 0){reportConfigList.clear();}
        //получаем список конфигов из папки DATA
        List<String> configsList = ConfigsList.get(CONFIGS_DIR);
        for (String configName : configsList){
            ReportConfig reportConfig = ReadConfig.read(CONFIGS_DIR + configName);
            if(reportConfig == null) return;
            reportConfig.setName(configName);
            reportConfigList.add(reportConfig);
        }
    }

    public ObservableList<ReportConfig> getReportConfigList() {
        return reportConfigList;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Report Builder");

        //устанавливаем иконку приложения
        this.primaryStage.getIcons().add(
                new Image(MainApp.class.getResourceAsStream("resources/builder_64.png")));

        initRootLayout();
        showConfigOverview();
    }

    /**
     * инициализация корневого макета.
     */
    public void initRootLayout(){
        try {
            //загружаем корневой макет
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            //отображаем сцену, содержащую корневой макет.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    /**
     * показываем в корневом макете информацию о конфигах
     */
    public void showConfigOverview(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ConfigOverview.fxml"));
            AnchorPane configOverview = (AnchorPane) loader.load();

            //помещаем инфу о конфигах в центр корневого макета
            rootLayout.setCenter(configOverview);

            ConfigOverviewController controller = loader.getController();
            controller.setMainApp(this);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public boolean showConfigEditDialog(ReportConfig config){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ConfigEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            //создание диалогового окна Stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Config");

            if(config == null){dialogStage.setTitle("New Config");}

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.getIcons().add(
                    new Image(MainApp.class.getResourceAsStream("resources/edit_64.png")));
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            //передаем config
            ConfigEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setConfig(config);

            //показываем диалоговое окно и ждем пока пользователь его закроет
            dialogStage.showAndWait();

            return controller.isSaveClicked();
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
