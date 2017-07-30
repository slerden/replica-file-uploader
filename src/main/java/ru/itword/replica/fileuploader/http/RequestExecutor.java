package ru.itword.replica.fileuploader.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.PopupControl;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import okhttp3.*;
import ru.itword.replica.fileuploader.model.MessageDto;
import ru.itword.replica.fileuploader.utils.PopupCreateUtils;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Itword on 30.07.2017.
 */
public class RequestExecutor {

    private static final OkHttpClient client = new OkHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    public static void executeRequest(Request request, Window owner, ResponseResolver<MessageDto> resolver){
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            PopupCreateUtils.createPopup("Запрос не увенчался успехом :(").show(owner);
        }
        if(response != null){
            MessageDto messageDto = null;
            if(response.body() != null && response.body().contentType() != null && response.body().contentType().equals(MediaType.parse("application/json;charset=UTF-8"))){
                try {
                    messageDto = mapper.readValue(response.body().string(), MessageDto.class);
                } catch (IOException e) {
                    PopupCreateUtils.createPopup("Ошибка при чтении ответа :(").show(owner);
                }
                if(response.isSuccessful()){
                    resolver.onSuccess(response, messageDto);
                }
                else {
                    resolver.onFailure(response, messageDto);
                }
            }
            else if (response.code() == 403){
                PopupCreateUtils.createPopup("Доступ запрещен").show(owner);
            }
        }
    }

    public static Request formPostRequest(String url, Object object) throws JsonProcessingException {
        return formPostRequest(url, object, null);
    }
    public static Request formPostRequest(String path, Object object, Map<String, String> requestParams) throws JsonProcessingException {
        Request.Builder builder = new Request.Builder();
        if(SecurityContextHolder.getCredentials() != null) builder.addHeader("Authentication", SecurityContextHolder.getCredentials());
        builder.url(getURLBuilder(path, requestParams).build());
        String requestBody = mapper.writeValueAsString(object);
        builder.post(RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), requestBody));
        return builder.build();
    }

    public static Request formGetRequest(String path, Map<String, String> requestParams){
        HttpUrl.Builder urlBuilder = getURLBuilder(path, requestParams);
        Request.Builder builder = new Request.Builder();
        if(SecurityContextHolder.getCredentials() != null) builder.addHeader("Authorization", SecurityContextHolder.getCredentials());
        builder.get().url(urlBuilder.build());
        return builder.build();
    }

    public static Request formGetRequest(String path){
        return formGetRequest(path, null);
    }

    private static HttpUrl.Builder getURLBuilder(String path){
        return getURLBuilder(path, null);
    }

    private static HttpUrl.Builder getURLBuilder(String path, Map<String, String> requestParams){
        HttpUrl.Builder urlBuilder = new HttpUrl.Builder();
        urlBuilder.host(HOST).port(PORT).addPathSegment(path).scheme("http");
        if(requestParams != null){
            for (String requestParam : requestParams.keySet()) {
                urlBuilder.addQueryParameter(requestParam, requestParams.get(requestParam));
            }
        }
        return urlBuilder;
    }

}
