package com.nva.rbuilder.view;

import com.nva.rbuilder.MainApp;
import com.nva.rbuilder.model.ReportConfig;
import com.nva.rbuilder.model.builder.ReportBuilder;
import com.nva.rbuilder.utils.Assets;
import com.nva.rbuilder.utils.FileWriter;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class ConfigOverviewController {

    @FXML
    private TableView<ReportConfig> configTableView;
    @FXML
    private TableColumn<ReportConfig, String> configNameColumn;

    @FXML
    private Label gameNameLabel;
    @FXML
    private Label issueLabel;
    @FXML
    private CheckBox conferenceModeCheckBox;
    @FXML
    private Label dateEndLabel;
    @FXML
    private Label loginLabel;
    @FXML
    private CheckBox autoAuthorisationModeCheckBox;
    @FXML
    private Button buttonBuild;
    @FXML
    private Text progressLabel;
    @FXML
    private ProgressBar progressBar;

    //ссылка на главное приложение
    private MainApp mainApp;

    public ConfigOverviewController(){
    }

    @FXML
    private void initialize(){
        //инициализация списка конфигов
        configNameColumn.setCellValueFactory(cellData -> cellData.getValue().configName());

        //очистка информации
        showConfigDetails(null);

        //добавляем слушателя выбора конфига
        configTableView.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> showConfigDetails(newValue)));

        //добавляем евент, при двойном клике по выбранному конфигу - открываем окно редактирования
        configTableView.setRowFactory( event -> {
            TableRow<ReportConfig> row = new TableRow<>();
                    row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                                if ((mouseEvent.getClickCount() == 2) && (! row.isEmpty())) {
                                    handleEditConfig();
                                }
                            }
                        }
                    });
                    return row;
                });

        //добавляем экшн к чекбоксу conferenceMode
        conferenceModeCheckBox.setOnAction((event -> {
            handleCheckConferenceMode(configTableView.getSelectionModel().getSelectedItem());
        }));

    }

    /**
     * получаем ссылку на главное приложение и заполняем таблицу
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        configTableView.setItems(mainApp.getReportConfigList());

        //выбираем первый конфиг из таблицы
        configTableView.getSelectionModel().selectFirst();
    }

    /**
     * заполняем поля из выбранного конфига
     * если конфиг не выбран, то очищаем все
     * @param config
     */
    private void showConfigDetails(ReportConfig config){
        if(config != null){
            //заполняем инфу из конфига
            gameNameLabel.setText(config.getReport().getGameName());
            issueLabel.setText(config.getSettings().getIssue());
            conferenceModeCheckBox.setSelected(config.getSettings().isConferenceMode());
            dateEndLabel.setText(config.getSettings().getDateEnd());
            loginLabel.setText(config.getSettings().getLogin());
            autoAuthorisationModeCheckBox.setSelected(config.getSettings().isAutoAuthorisationMode());


            progressBar.progressProperty().unbind();
            progressBar.progressProperty().bind(configTableView.getSelectionModel().getSelectedItem().progress());

            progressLabel.textProperty().unbind();
            progressLabel.textProperty().bind(configTableView.getSelectionModel().getSelectedItem().progressLabel());
            if((progressBar.progressProperty().get() > 0)&&
                    (progressBar.progressProperty().get() < 1)) {
                buttonBuild.setDisable(true);
                progressBar.setDisable(false);
            }else {
                buttonBuild.setDisable(false);
                progressBar.setDisable(true);
            }
        }else {
            gameNameLabel.setText("");
            issueLabel.setText("");
            conferenceModeCheckBox.setSelected(false);
            dateEndLabel.setText("");
            loginLabel.setText("");
            autoAuthorisationModeCheckBox.setSelected(false);
            progressBar.progressProperty().unbind();
            progressBar.setProgress(0);
            progressBar.setDisable(true);
            progressLabel.textProperty().unbind();
            progressLabel.setText("");
        }
    }

    @FXML
    private void handleCheckConferenceMode(ReportConfig config){
        if(config != null){
        config.getSettings().setConferenceMode(conferenceModeCheckBox.isSelected());
    }}

    /**
     * вызывается, когда пользователь жмакает по New...
     * открывется окно для внесения изменений в выбраном конфиге
     */
    @FXML
    private void handleNewConfig(){
        boolean saveClicked = mainApp.showConfigEditDialog(null);
        if (saveClicked){
            //обновляем список доступных конфигов
            mainApp.refreshConfigList();
//            //очищаем и заполняем таблицу обновленными данными
//            configTableView.setItems(null);
//            configTableView.setItems(mainApp.getReportConfigList());
            //выбираем первый элемент таблицы
            configTableView.getSelectionModel().selectFirst();
            }
    }

    /**
     * вызывается, когда пользователь жмакает по Delete
     * открывется окно для внесения изменений в выбраном конфиге
     */
    @FXML
    private void handleDelete() {
        ReportConfig selectedConfig = configTableView.getSelectionModel().getSelectedItem();

        if(selectedConfig != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting...");
            alert.setHeaderText(selectedConfig.configName().get() + " will be deleted");
            alert.setContentText("Are you sure?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                //удаляем выбранный файл конфига
                delete(mainApp.CONFIGS_DIR + selectedConfig.configName().get());
                //обновляем список доступных конфигов
                mainApp.refreshConfigList();
                configTableView.getSelectionModel().selectFirst();
            } else {

            }
        }else {
            //ничего не выбрано
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Config Selected");
            alert.setContentText("Please select a config in the table.");

            alert.showAndWait();
        }
    }

    private void delete(String path){
        try {
            Files.delete(Paths.get(path));
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Deleting...");
            alert.setHeaderText("Delete failed!");
            alert.setContentText("Please close a config file and try again.");

            alert.showAndWait();
        }
    }

    /**
     * вызывается, когда пользователь жмакает по Edit...
     * открывется окно для внесения изменений в выбраном конфиге
     */
    @FXML
    private void handleEditConfig(){
        ReportConfig selectedConfig = configTableView.getSelectionModel().getSelectedItem();
        if(selectedConfig != null){
            boolean saveClicked = mainApp.showConfigEditDialog(selectedConfig);
            if (saveClicked){
                showConfigDetails(selectedConfig);
            }
        }else {
            //ничего не выбрано
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Config Selected");
            alert.setContentText("Please select a config in the table.");

            alert.showAndWait();
        }
    }

    /**
     * вызывается, когда пользователь жмакает на BUILD REPORT
     */
    @FXML
    private void handleBuildReport() {
        ReportConfig selectedConfig = configTableView.getSelectionModel().getSelectedItem();
        if (selectedConfig != null) {
            buttonBuild.setDisable(true);
            progressBar.setDisable(false);

            progressBar.progressProperty().unbind();
            selectedConfig.setProgress(0);
            progressBar.progressProperty().bind(selectedConfig.progress());

            progressLabel.textProperty().unbind();
            selectedConfig.setProgressLabel("");
            progressLabel.textProperty().bind(selectedConfig.progressLabel());


            /**
             * ******************************************************************************************
             * процесс сборки отчета в отдельном потоке
             */
            Task<String[]> task = new Task<String[]>() {
                @Override
                public String[] call() {
                    ReportConfig selectedConfig = configTableView.getSelectionModel().getSelectedItem();
                    ReportBuilder reportBuilder = new ReportBuilder(selectedConfig);
                    reportBuilder.build();
                    String[] info = reportBuilder.getInfo();
                    return info;
                }
            };

            task.setOnSucceeded(e -> {
                try {
                    String reportName = task.get()[0];
                    String errors = task.get()[1];
                    if(errors.equals("")){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.initOwner(mainApp.getPrimaryStage());
                        alert.setTitle("Done!");
                        alert.setHeaderText("Report "+ reportName + " Complete!");
                        alert.setContentText("Please press OK for continue...");
                        alert.showAndWait();
                    }else {
                        if(errors.contains("change")){
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.initOwner(mainApp.getPrimaryStage());
                            alert.setTitle("Warning!");
                            alert.setHeaderText("Report "+ reportName + " Complete!");
                            alert.setContentText(errors);
                            alert.showAndWait();
                        }else{
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.initOwner(mainApp.getPrimaryStage());
                            alert.setTitle("Error!");
                            alert.setHeaderText("Report "+ reportName + " Don't Complete!");
                            alert.setContentText(errors);
                            alert.showAndWait();
                        }
                    }
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } catch (ExecutionException e2) {
                    e2.printStackTrace();
                }finally {
                    progressBar.setDisable(true);
                    buttonBuild.setDisable(false);
                    selectedConfig.setProgress(0);
                    selectedConfig.setProgressLabel("");
                }
            });

            task.setOnFailed(e ->{
                Stage dialog = new Stage();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(dialog);
                alert.setTitle("Error!");
                alert.setHeaderText("Unknown Error!");
                alert.setContentText("Thread unexpectedly ended!");
                alert.showAndWait();
            });

            new Thread(task).start();

            /**
             * ************************************************************************************************
             */

        } else {
            //ничего не выбрано
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Config Selected");
            alert.setContentText("Please select a config in the table.");

            alert.showAndWait();
        }
    }

    /**
     * вызывается, когда пользователь жмакает на Generate Configs
     */
    @FXML
    private void handleGenerateConfigs(){
        FileWriter.write("defaultGame.xml", Assets.CONFIG_GAME_DEFAULT);
        FileWriter.write("defaultLottery.xml", Assets.CONFIG_LOTTERY_DEFAULT);
        FileWriter.write("defaultHTML.xml", Assets.CONFIG_HTML_DEFAULT);
        //обновляем список доступных конфигов
        mainApp.refreshConfigList();
        //очищаем и заполняем таблицу обновленными данными
        configTableView.setItems(null);
        configTableView.setItems(mainApp.getReportConfigList());
        //выбираем первый элемент таблицы
        configTableView.getSelectionModel().selectFirst();
    }

}
