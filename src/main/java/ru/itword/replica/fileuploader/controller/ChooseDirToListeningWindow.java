package ru.itword.replica.fileuploader.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;


import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Itword on 23.07.2017.
 */
public class ChooseDirToListeningWindow implements Initializable, ResizableByRootPane{

    private SceneChanger sceneChanger;

    @FXML
    AnchorPane mainPane;

    @FXML
    TextField fieldContainer;

    @FXML
    Button selectDirButton;


    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void onActionMethod(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Node target = (Node) actionEvent.getTarget();
        File file = directoryChooser.showDialog(target.getScene().getWindow());
        fieldContainer.setText(file.getAbsolutePath());
    }

    @Override
    public Pane getRootPane() {
        return mainPane;
    }

    @Override
    public void setSceneChanger(SceneChanger changer) {
        this.sceneChanger = sceneChanger;
    }
}
