package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FiatCurrencyServerTask extends AsyncTask<String, Integer, String> {

    private final String TAG = FiatCurrencyServerTask.class.getSimpleName();

    @Override
    protected String doInBackground(String... strings) {
        String sJson = "";
        String URL = strings[0];
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(URL).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                sJson = readResponseStream(reader);
            }
        } catch (IOException e) {
            Log.d(TAG, e.getLocalizedMessage());
        }
        return sJson;
    }

    private String readResponseStream(BufferedReader reader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }


}
