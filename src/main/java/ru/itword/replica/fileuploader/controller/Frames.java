package ru.itword.replica.fileuploader.controller;

/**
 * Created by Itword on 30.07.2017.
 */
public enum Frames {
    AUTH("AuthorizationWindow.fxml"), MAIN("MainWindow.fxml"), CHOOSE_DIR("ChooseDirToListeningWindow.fxml");
    private String path;

    Frames(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
