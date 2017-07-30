package ru.itword.replica.fileuploader.model;

import java.util.List;
import java.util.Map;

/**
 * Created by Itword on 23.07.2017.
 */
public class MessageDto {

    public MessageDto() {
    }

    public MessageDto(Object content) {
        this.content = content;
    }

    private Object content;
    private Map<String, List<String>> fieldErrors;
    private List<String> errors;
    private List<String> messages;

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public Map<String, List<String>> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(Map<String, List<String>> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}
