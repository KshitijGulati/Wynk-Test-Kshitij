package wynk.com.kshitij.presenter;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import wynk.com.kshitij.utils.ImagesCache;
import wynk.com.kshitij.utils.Utility;


public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    private String imageUrl;
    private ImagesCache cache;
    private ImageFetchListener listener;

    public DownloadImageTask(ImagesCache cache, ImageFetchListener listener) {
        this.cache = cache;
        this.listener = listener;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        imageUrl = params[0];
        return Utility.getBitmapFromURL(imageUrl);
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        if (result != null) {
            cache.addImageToWarehouse(imageUrl, result);
            if (listener != null) {
                listener.updateImageBitmap(result);
            }
        }
    }
}