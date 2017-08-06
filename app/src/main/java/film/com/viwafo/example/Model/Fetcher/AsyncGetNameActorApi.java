package film.com.viwafo.example.Model.Fetcher;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import film.com.viwafo.example.Listener.OnDatabaseCreated;
import film.com.viwafo.example.Model.Entity.Actor;
import film.com.viwafo.example.Util.IOUtil;

/**
 * Created by minhl on 06/08/2017.
 */

public class AsyncGetNameActorApi extends AsyncTask<String, Void, List> {

    private OnDatabaseCreated listenner;

    public AsyncGetNameActorApi(OnDatabaseCreated listenner) {
        this.listenner = listenner;
    }

    @Override
    protected List doInBackground(String... params) {
        String jsonStr = getStringFromURL(params[0]);
        return createNameActor(jsonStr);
    }

    private List createNameActor(String jsonStr) {
        try {
            List<Actor> list = new ArrayList<>();
            JSONObject jsonRootObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonRootObject.optJSONArray("cast");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Actor actor = new Actor();
                actor.setName(jsonObject.optString("name"));
                actor.setUrlImage(jsonObject.optString("profile_path"));
                list.add(actor);
                if (list.size() == 20) {
                    break;
                }
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getStringFromURL(String urlApi) {
        InputStream in = null;
        BufferedReader br = null;
        try {
            URL url = new URL(urlApi);
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
    protected void onPostExecute(List list) {
        super.onPostExecute(list);
        listenner.onCreated(list);
    }
}
