package film.com.viwafo.example.Model.Fetcher;

import android.os.AsyncTask;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import film.com.viwafo.example.Model.ParseData;
import film.com.viwafo.example.Util.IOUtil;


/**
 * Created by macintoshhd on 7/23/17.
 */
public class AsyncApi extends AsyncTask<String, Void, String> {
    public String jsonStr;

    @Override
    protected String doInBackground(String... params) {
        return getStringFromURL(params[0]);

    }

    private String getStringFromURL(String urlApi) {
        String urlText = urlApi;
        InputStream in = null;
        BufferedReader br = null;
        try {
            URL url = new URL(urlText);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setAllowUserInteraction(false);
            httpURLConnection.setInstanceFollowRedirects(true);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            int resCode = httpURLConnection.getResponseCode();
            if (resCode == HttpURLConnection.HTTP_OK) {
                in = httpURLConnection.getInputStream();
                br = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String s;
                while ((s = br.readLine()) != null) {
                    sb.append(s);
                    sb.append("\n");
                }
                return sb.toString();
            } else {
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtil.closeQuiety(in);
            IOUtil.closeQuiety(br);
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        jsonStr = result;
        ParseData.createJson(jsonStr);
    }
}
