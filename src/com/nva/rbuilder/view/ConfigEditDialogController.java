package com.nva.rbuilder.view;

import com.nva.rbuilder.MainApp;
import com.nva.rbuilder.model.ItemOfTree;
import com.nva.rbuilder.model.ReportConfig;
import com.nva.rbuilder.utils.ConfigsList;
import com.nva.rbuilder.utils.CustomTextFieldTreeTableCell;
import com.nva.rbuilder.utils.TreeTableUtil;
import com.nva.rbuilder.utils.WriteConfig;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;

public class ConfigEditDialogController {

    @FXML
    private TextField issueField;
    @FXML
    private CheckBox conferenceModeCheckBox;
    @FXML
    private TextField dateEndField;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passField;
    @FXML
    private TextField jiraURlField;
    @FXML
    private CheckBox autoAuthorisationModeCheckBox;
    @FXML
    private TextField gameNameField;
    @FXML
    private TreeTableView<ItemOfTree> treeTableView;
    @FXML
    private TreeTableColumn<ItemOfTree, String> treeTableColumnSection;
    @FXML
    private TreeTableColumn<ItemOfTree, String> treeTableColumnSectionName;
    @FXML
    private TreeTableColumn<ItemOfTree, String> treeTableColumnSectionLabel;
    @FXML
    private TreeTableColumn<ItemOfTree, String> treeTableColumnUnit;
    @FXML
    private TreeTableColumn<ItemOfTree, String> treeTableColumnUnitName;
    @FXML
    private TreeTableColumn<ItemOfTree, String> treeTableColumnUnitLabel;
    @FXML
    private TreeTableColumn<ItemOfTree, String> treeTableColumnPercent;

    private Stage dialogStage;
    private ReportConfig config;
    private boolean saveClicked = false;

    @FXML
    private void initialize(){
        treeTableColumnSection.setCellValueFactory(CellData -> CellData.getValue().getValue().nameSectionProperty());
        treeTableColumnSectionName.setCellValueFactory(CellData -> CellData.getValue().getValue().nameSectionProperty());
        treeTableColumnSectionLabel.setCellValueFactory(CellData -> CellData.getValue().getValue().labelSectionProperty());
        treeTableColumnSectionName.setCellFactory(CustomTextFieldTreeTableCell.<ItemOfTree>forTreeTableColumn());
        treeTableColumnSectionLabel.setCellFactory(CustomTextFieldTreeTableCell.<ItemOfTree>forTreeTableColumn());

        treeTableColumnUnit.setCellValueFactory(CellData -> CellData.getValue().getValue().nameUnitProperty());
        treeTableColumnUnitName.setCellValueFactory(CellData -> CellData.getValue().getValue().nameUnitProperty());
        treeTableColumnUnitLabel.setCellValueFactory(CellData -> CellData.getValue().getValue().labelUnitProperty());
        treeTableColumnUnitName.setCellFactory(CustomTextFieldTreeTableCell.<ItemOfTree>forTreeTableColumn());
        treeTableColumnUnitLabel.setCellFactory(CustomTextFieldTreeTableCell.<ItemOfTree>forTreeTableColumn());

        treeTableColumnPercent.setCellValueFactory(CellData -> CellData.getValue().getValue().percentProperty());
        treeTableColumnPercent.setCellFactory(CustomTextFieldTreeTableCell.<ItemOfTree>forTreeTableColumn());
    }

    /**
     * Устанавливаем сцену для этого окна.
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }

    /**
     * принемаем конфиг, который будем редактировать
     * @param config
     */
    public void setConfig(ReportConfig config){
        if(config == null){config = new ReportConfig();}
        this.config = config;

        issueField.setText(config.getSettings().getIssue());
        issueField.setPromptText("GOXBKOFRA-2");
        conferenceModeCheckBox.setSelected(config.getSettings().isConferenceMode());
        dateEndField.setText(config.getSettings().getDateEnd());
        dateEndField.setPromptText("Friday on the current week");
        loginField.setText(config.getSettings().getLogin());
        loginField.setPromptText("i.ivanov");
        passField.setText(config.getSettings().getPass());
        jiraURlField.setText(config.getSettings().getJiraURL());
        jiraURlField.setPromptText("https://jira-srv.octavian.ru");
        autoAuthorisationModeCheckBox.setSelected(config.getSettings().isAutoAuthorisationMode());
        gameNameField.setText(config.getReport().getGameName());

        treeTableView.setRoot(null);
        TreeItem<ItemOfTree> rootTree = TreeTableUtil.getRoot(config);
        treeTableView.setRoot(rootTree);
        treeTableView.getSelectionModel().selectFirst();
    }

    /**
     * Returns true, если пользователь кликнул Save, иначе false.
     * @return
     */
    public boolean isSaveClicked(){
        return saveClicked;
    }

    /**
     * вызывается, когда пользователь жмакнул по кнопке Save
     */
    @FXML
    private void handleSave(){
        if(isInputValid()){
            config.getSettings().setIssue(issueField.getText());
            config.getSettings().setConferenceMode(conferenceModeCheckBox.isSelected());
            config.getSettings().setDateEnd(dateEndField.getText());
            config.getSettings().setLogin(loginField.getText());
            config.getSettings().setPass(passField.getText());
            config.getSettings().setJiraURL(jiraURlField.getText());
            config.getSettings().setAutoAuthorisationMode(autoAuthorisationModeCheckBox.isSelected());
            config.getReport().setGameName(gameNameField.getText());

            //записываем изменения в объект config
            TreeTableUtil.saveChangesToConfig(treeTableView.getRoot(), config);

            /**
             * если конфиг новый, то задаем ему имя
             */
            if(config.configName().get() == null){
                String newConfigName = config.getReport().getGameName() + ".xml";
                //если конфиг с таким именем уже есть, прибавляем к названию "_"
                List<String> configsList = ConfigsList.get(MainApp.CONFIGS_DIR);
                if(configsList.contains(newConfigName)){newConfigName = "_" + newConfigName;}
                config.setName(newConfigName);}

            //TODO
            //записываем конфиг в файл
            WriteConfig.write(config,config.configName().get());

            saveClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Вызывается, когда пользователь жмакнул по кнопке Close
     */
    @FXML
    private void handleClose(){
        dialogStage.close();
    }

    /**
     * проверка заполнения полей на корректность
     * @return
     */
    private boolean isInputValid(){
        String errorMessage = "";
        if((issueField.getText() == null) || (issueField.getText().length() == 0)){
            errorMessage += "No valid IssueCode\n";
        }else if(issueField.getText().contains(" ")){
            errorMessage += "IssueCode contains space\n";
        }
        if((jiraURlField.getText() == null) || (jiraURlField.getText().length() == 0)){
            errorMessage += "No valid Jira's URL\n";
        }else if(jiraURlField.getText().contains(" ")){
            errorMessage += "Jira's URL contains space\n";
        }
        if((loginField.getText() == null) || (loginField.getText().length() == 0)){
            errorMessage += "Empty login\n";
        }

        if(errorMessage.length() == 0){
            return true;
        }else {
            //Show error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Don't stupid, correct field!");
            alert.setContentText(errorMessage);

            alert.showAndWait();
            return false;
        }
    }

    /**
     * Вызывается, когда пользователь жмакнул по кнопке Add Section
     */
    @FXML
    private void handleAddSection(){
        ItemOfTree section = new ItemOfTree("EmptyName", "EmptyL");
        ItemOfTree unit = new ItemOfTree("EmptyName", "EmptyL", "0");
        TreeItem<ItemOfTree> sectionTree = new TreeItem<>(section);
        sectionTree.getChildren().add(new TreeItem<>(unit));
        treeTableView.getRoot().getChildren().add(sectionTree);
        treeTableView.getRoot().setExpanded(true);
        treeTableView.getSelectionModel().selectLast();
    }

    /**
     * Вызывается, когда пользователь жмакнул по кнопке Add Unit
     */
    @FXML
    private void handleAddUnit(){
        int selectedIndex = treeTableView.getSelectionModel().getSelectedIndex();
        //если выбран корень, то ничего не делаем
        if(selectedIndex != 0){
            TreeItem selectedObject = treeTableView.getSelectionModel().getSelectedItem();

            //если выбранный объект не последний в ветке(не Unit), то открываем ветку(Section) и выбираем первый объект
            //обновляем selectedObject
            if(!selectedObject.isLeaf()){
                selectedObject.setExpanded(true);
                treeTableView.getSelectionModel().selectNext();
                selectedObject = treeTableView.getSelectionModel().getSelectedItem();
            }

            //создаем новый Unit, и записываем его в текущую ветку
            ItemOfTree unit = new ItemOfTree("EmptyName", "EmptyL", "0");
            selectedObject.getParent().getChildren().add(new TreeItem<>(unit));
            treeTableView.getSelectionModel().selectLast();
        }
    }

    /**
     * Вызывается, когда пользователь жмакнул по кнопке DeleteObject
     */
    @FXML
    private void handleDeleteObject(){
        int selectedIndex = treeTableView.getSelectionModel().getSelectedIndex();

        if(selectedIndex != 0){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting...");
            alert.setHeaderText("Selected units will be deleted");
            alert.setContentText("Are you sure?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                TreeItem selectedObject = treeTableView.getSelectionModel().getSelectedItem();
                selectedObject.getParent().getChildren().remove(selectedObject);
            } else {}
        }
    }

}
