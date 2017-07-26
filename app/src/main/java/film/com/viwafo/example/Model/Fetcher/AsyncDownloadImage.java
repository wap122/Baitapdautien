package film.com.viwafo.example.Model.Fetcher;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import film.com.viwafo.example.Adapter.CustomAdapter;
import film.com.viwafo.example.Util.IOUtil;

/**
 * Created by minhl on 26/07/2017.
 */

public class AsyncDownloadImage extends AsyncTask<String, Void, Bitmap> {

    private ImageView imageView;

    public AsyncDownloadImage(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String urlImage = params[0];
        InputStream in = null;
        try {
            URL url = new URL(urlImage);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setAllowUserInteraction(false);
            httpURLConnection.setInstanceFollowRedirects(true);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            int resCode = httpURLConnection.getResponseCode();
            if (resCode != HttpURLConnection.HTTP_OK) {
                return null;
            }
            in = httpURLConnection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            return bitmap;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtil.closeQuiety(in);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        imageView.setImageBitmap(bitmap);
    }
}
