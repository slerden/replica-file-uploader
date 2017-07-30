package ru.itword.replica.fileuploader.controller;

import javafx.scene.layout.Pane;

/**
 * Created by Itword on 30.07.2017.
 */
public interface ResizableByRootPane {
    Pane getRootPane();
    void setSceneChanger(SceneChanger changer);
}
