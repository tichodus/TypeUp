package services;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.TextView;

import com.example.stefan.textup.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpGetService extends AsyncTask<String, Void, String>{

    String resultJson;
    ProgressDialog progressDialog;
    String localServer = "http://10.0.2.2:3000/api/";
    AsyncResponse delegate = null;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(this.localServer + strings[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            //read response
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                result.append(line).append("\n");
            }

            inputStream.close();
            bufferedReader.close();
            connection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        this.delegate.processResponse(s);
    }

    public void setAsyncResponse(AsyncResponse response){
        this.delegate = response;
    }


}