package ru.itword.replica.fileuploader.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Window;
import okhttp3.*;
import ru.itword.replica.fileuploader.http.RequestExecutor;
import ru.itword.replica.fileuploader.http.ResponseResolver;
import ru.itword.replica.fileuploader.http.SecurityContextHolder;
import ru.itword.replica.fileuploader.utils.ControlResultResolver;
import ru.itword.replica.fileuploader.model.MessageDto;
import ru.itword.replica.fileuploader.model.UserDto;
import ru.itword.replica.fileuploader.utils.PopupCreateUtils;
import sun.applet.Main;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created by Itword on 29.07.2017.
 */
public class AuthorizationWindow implements Initializable, ResizableByRootPane {

    private static final String profilePath = "profile";
    private static final String signupPath = "signup";

    private SceneChanger sceneChanger;

    @FXML
    public Button loginButton;
    @FXML
    public Button registrationButton;
    @FXML
    public TextField usernameInput;
    @FXML
    public PasswordField passwordInput;
    @FXML
    public AnchorPane mainPane;

    @FXML
    private Window window;

    private ObjectMapper mapper = new ObjectMapper();


    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void onLoginButtonClick() throws IOException {
        window = mainPane.getScene().getWindow();
        SecurityContextHolder.setCredentials(usernameInput.getText(), passwordInput.getText());
        RequestExecutor.executeRequest(RequestExecutor.formGetRequest(profilePath), window, new ResponseResolver<MessageDto>() {
            @Override
            public void onSuccess(Response response, MessageDto object) {
                SecurityContextHolder.setProfile(mapper.convertValue(object.getContent(), UserDto.class));
                PopupCreateUtils.createPopup("Вы успешно вошли! \n" +
                        "Ваш логин: " + SecurityContextHolder.getProfile().getUsername()).show(window);
                FXMLLoader loader = new FXMLLoader(getClass()
                        .getResource(Frames.MAIN.getPath()));
                sceneChanger.changeScene(Frames.MAIN, true);
            }

            @Override
            public void onFailure(Response response, MessageDto object) {
                SecurityContextHolder.resetCredentials();
                StringBuilder showedString = new StringBuilder();
                for (String s : object.getMessages()) {
                    showedString.append(s).append("\n");
                }
                PopupCreateUtils.createPopup(showedString.toString()).show(window);
            }
        });
    }

    public void onRegisterButtonClick() throws IOException {
        window = mainPane.getScene().getWindow();
        final UserDto userDto = new UserDto();
        userDto.setUsername(usernameInput.getText());
        userDto.setPassword(passwordInput.getText());
        final Map<String, Control> fields = new HashMap<String, Control>();
        fields.put("username", usernameInput);
        fields.put("password", passwordInput);
        for (String s : fields.keySet()) {
            fields.get(s).setStyle("-fx-border-color: #00d800");
        }
        RequestExecutor.executeRequest(RequestExecutor.formPostRequest(signupPath, userDto), window, new ResponseResolver<MessageDto>() {
            @Override
            public void onSuccess(Response response, MessageDto object) {
                UserDto user = mapper.convertValue(object.getContent(), UserDto.class);
                SecurityContextHolder.setCredentials(userDto.getUsername(), userDto.getPassword());
                SecurityContextHolder.setProfile(user);
                PopupCreateUtils.createPopup("Вы успешно зарегистрировались! \n" +
                        "Ваш логин: " + user.getUsername()).show(window);
                sceneChanger.changeScene(Frames.MAIN, true);
            }
            @Override
            public void onFailure(Response response, MessageDto object) {
                ControlResultResolver.resolveErrors(fields, object);
            }
        });

    }


    @Override
    public Pane getRootPane() {
        return mainPane;
    }

    @Override
    public void setSceneChanger(SceneChanger changer) {
        this.sceneChanger = changer;
    }
}
