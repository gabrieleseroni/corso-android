package it.elbuild.corso.intentserviceexample;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by lu on 22/01/16.
 */

public class WGetService extends IntentService {

    private int result = Activity.RESULT_CANCELED;
    public static final String URL = "urlpath";
    public static final String FILENAME = "filename";
    public static final String FILEPATH = "filepath";
    public static final String RESULT = "result";
    public static final String NOTIFICATION = "it.elbuild.corso.intentserviceexample";
    private RequestQueue mRequestQueue;
    public WGetService() {
        super("DownloadService");
    }
    private String fileName;
    
    @Override
    protected void onHandleIntent(Intent intent) {
        String urlPath = intent.getStringExtra(URL);
         fileName = intent.getStringExtra(FILENAME);

        ImageRequest request = new ImageRequest(urlPath,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        result = Activity.RESULT_OK;
                        saveBitmap(fileName,bitmap);
                        publishResults(result);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR ", error.toString());
                    }
                });

        addToVolleyQueueWithout(request);

    }

    private void publishResults(int result) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(FILEPATH, Environment.getExternalStorageDirectory().getPath().toString() + "/" + fileName);
        intent.putExtra(RESULT, result);
        sendBroadcast(intent);
    }

    public  <T> void addToVolleyQueueWithout(Request<T> req) {
        req.setShouldCache(false);
        req.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(15),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        getRequestQueue().add(req);
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public static void saveBitmap(String fileName, Bitmap bmp) {
        FileOutputStream out = null;
        File file = new File(Environment.getExternalStorageDirectory().getPath().toString() + "/" + fileName );
        try {

            out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}