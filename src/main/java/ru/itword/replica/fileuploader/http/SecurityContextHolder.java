package ru.itword.replica.fileuploader.http;

import okhttp3.Credentials;
import ru.itword.replica.fileuploader.model.UserDto;

/**
 * Created by Itword on 30.07.2017.
 */
public class SecurityContextHolder {
    private static String credentials;
    private static UserDto profile;

    public static UserDto getProfile() {
        return profile;
    }

    public static void setProfile(UserDto profile) {
        SecurityContextHolder.profile = profile;
    }

    public static void setCredentials(String username, String password){
        credentials = Credentials.basic(username, password);
    }
    public static void resetCredentials(){
        credentials = null;
    }
    public static String getCredentials(){
        return credentials;
    }
}
