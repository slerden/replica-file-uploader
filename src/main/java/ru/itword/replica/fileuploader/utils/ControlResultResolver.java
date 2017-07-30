package ru.itword.replica.fileuploader.utils;

import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Font;
import javafx.stage.WindowEvent;
import ru.itword.replica.fileuploader.model.MessageDto;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Itword on 30.07.2017.
 */
public class ControlResultResolver {
    public static void resolveErrors(Map<String, Control> controlMap, MessageDto messageDto){
        Font font = new Font(12);
        for (String fieldName : messageDto.getFieldErrors().keySet()) {
            for (String error : messageDto.getFieldErrors().get(fieldName)) {
                for (String controlKey : controlMap.keySet()) {
                    if(fieldName.startsWith(controlKey)){
                        final Control control = controlMap.get(controlKey);
                        if(control.getTooltip() == null){
                            control.setTooltip(new Tooltip(""));
                            control.getTooltip().setFont(new Font(12));
                            control.getTooltip().setMaxWidth(300);
                            control.getTooltip().setWrapText(true);
                            control.setStyle("-fx-border-color: red");
                        }
                        control.getTooltip().setText(control.getTooltip().getText() + error + "\n");
                    }
                }
            }
        }
    }
}
