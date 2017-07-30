package ru.itword.replica.fileuploader.http;

import okhttp3.Response;

/**
 * Created by Itword on 30.07.2017.
 */
public interface ResponseResolver<T> {
    void onSuccess(Response response, T object);
    void onFailure(Response response, T object);
}
