/**
 * Created by Itword on 23.07.2017.
 */

import com.sun.javafx.scene.SceneUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.itword.replica.fileuploader.controller.*;

import java.io.IOException;

public class Main extends Application implements SceneChanger{


    private Stage rootStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        rootStage = primaryStage;
        changeScene(Frames.AUTH, false);
        primaryStage.show();


    }



    @Override
    public void changeScene(Frames frame, boolean resizable) {
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource(frame.getPath()));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ResizableByRootPane controller = loader.getController();
        controller.setSceneChanger(this);
        double prefHeight = controller.getRootPane().getPrefHeight();
        double prefWidth = controller.getRootPane().getPrefWidth();
        Scene scene = new Scene(parent);
        rootStage.setWidth(prefWidth);
        rootStage.setHeight(prefHeight);
        rootStage.setResizable(resizable);
        rootStage.setScene(scene);
    }
}
