package evoliris.com.hamid_masterdetails.task;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by temp on 8/09/2016.
 */
public class AsynctaskShowOnlyOneBook extends AsyncTask<String, Void, String> {


    private GetAsyncTaskCreateOnlyOneBookCallback callback;

    public AsynctaskShowOnlyOneBook(GetAsyncTaskCreateOnlyOneBookCallback callback) {
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        if (params.length != 1) {
            throw new IllegalArgumentException("GetAsyncTask can only be executed with a string");
        }

        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream inputStream = connection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            return sb.toString();
        } catch (IOException e) {
            return "false";
        }

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        callback.onPostGetCreateBook(s);
    }

    public interface GetAsyncTaskCreateOnlyOneBookCallback {

        void onPostGetCreateBook(String s);

    }
}
