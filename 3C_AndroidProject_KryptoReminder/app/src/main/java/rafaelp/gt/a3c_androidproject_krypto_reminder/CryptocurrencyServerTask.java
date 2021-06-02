package rafaelp.gt.a3c_androidproject_krypto_reminder;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class CryptocurrencyServerTask extends AsyncTask<String, Integer, String> {

    private final String TAG = CryptocurrencyServerTask.class.getSimpleName();

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
