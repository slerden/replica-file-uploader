package ru.itword.replica.fileuploader.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * Created by Itword on 30.07.2017.
 */
public class MainWindow implements ResizableByRootPane {

    private SceneChanger sceneChanger;

    @FXML
    public Label welcomeLabel;
    @FXML
    public AnchorPane mainPane;
    @FXML
    public ListView dirs;
    @FXML
    public Button addBtn;
    @FXML
    public Label timerLabel;
    @FXML
    public Button delBtn;


    @Override
    public Pane getRootPane() {
        return mainPane;
    }

    @Override
    public void setSceneChanger(SceneChanger changer) {
        this.sceneChanger = changer;
    }
}
