package services;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.TextView;

import com.example.stefan.textup.R;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpPostService extends AsyncTask<String, Void, String>{
    private String localServer = "http://10.0.2.2:3000/api/";
    private  AsyncResponse delegate;
    private  String sendData;

    public HttpPostService(String data){
        this.sendData = data;
        this.delegate=null;
    }

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
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            //write data into server
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(sendData.getBytes());
            outputStream.flush();


            //read response
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                result.append(line).append("\n");
            }
            inputStream.close();
            bufferedReader.close();
            outputStream.close();
            connection.disconnect();
        }
        catch (MalformedURLException e) {
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