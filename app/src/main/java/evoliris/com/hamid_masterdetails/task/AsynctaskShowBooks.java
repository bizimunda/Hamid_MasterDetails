package evoliris.com.hamid_masterdetails.task;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by temp on 8/09/2016.
 */
public class AsynctaskShowBooks extends AsyncTask<String, Void, String> {


    private GetAsyncTaskCallback callback;

    public AsynctaskShowBooks(GetAsyncTaskCallback callback) {
        this.callback = callback;
    }



    @Override
    protected String doInBackground(String... s) {

        if (s.length != 1) {
            throw new IllegalArgumentException("GetAsyncTask can only be executed with a string");
        }

        BufferedReader reader = null;
        InputStream is = null;

        try {

            URL url = new URL(s[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            is = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            return sb.toString();
        } catch (IOException e) {
            return e.getMessage();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("GetAsyncTask", e.getMessage());
                }
            }

            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("GetAsyncTask", e.getMessage());
                }
            }
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        callback.onPostGet(s);
    }

    public interface GetAsyncTaskCallback {

        void onPostGet(String s);

    }
}
