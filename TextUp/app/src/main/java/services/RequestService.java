package services;

import com.google.gson.Gson;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Observable;

/**
 * Created by Stefan on 3/15/2018.
 */

public final class RequestService {
    private final static String localserver = "http://172.22.128:3000/api/";
    public static void createGetRequest(Object data, String action){
        Gson gson = new Gson();
        String json = gson.toJson(data);

    }
    public static void createPostRequest(Object data, String action){
        Gson gson = new Gson();
        String dataJson = gson.toJson(data);
        try {
            URL url = new URL(RequestService.localserver + action );
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/json");
            connection.connect();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static Observable createPutRequest(Object data, String action){
        return new Observable();
    }
    public static Observable createDeleteRequest(Object data, String action){
        return new Observable();
    }
}
