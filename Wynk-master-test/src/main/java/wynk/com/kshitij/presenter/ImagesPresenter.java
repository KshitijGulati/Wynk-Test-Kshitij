package wynk.com.kshitij.presenter;

import android.graphics.Bitmap;

import java.util.List;

import wynk.com.kshitij.model.Photo;
import wynk.com.kshitij.utils.ImagesCache;
import wynk.com.kshitij.utils.Utility;
import wynk.com.kshitij.view.MainActivityView;


public class ImagesPresenter implements UrlsListListener, ImageFetchListener {

    private ImagesCache cache;
    private MainActivityView activityView;

    public ImagesPresenter(MainActivityView activityView) {
        this.activityView = activityView;
        cache = ImagesCache.getInstance();
        cache.initializeCache();
    }

    public void fetchImagesUrl(int pageCount) {
        // fetch images list from api
        if (activityView != null) {
            activityView.showLoader();
        }
        FetchImageUrlsTask fetchImagesTask = new FetchImageUrlsTask(this);
        Utility.execute(fetchImagesTask, String.valueOf(pageCount));
    }

    public void fetchBitmap(String imgUrl) {
        Bitmap bm = cache.getImageFromWarehouse(imgUrl);
        if (bm != null) {
            activityView.updateBitmap(bm);
        } else {
            if (activityView != null) {
                activityView.showLoader();
            }
            DownloadImageTask imgTask = new DownloadImageTask(cache, this);
            Utility.execute(imgTask, imgUrl);
        }
    }

    @Override
    public void updateImages(List<Photo> photoList) {
        if (activityView != null) {
            activityView.hideLoader();
            activityView.updateImageUrls(photoList);
        }
    }

    @Override
    public void updateImageBitmap(Bitmap bitmap) {
        if (bitmap != null && activityView != null) {
            activityView.hideLoader();
            activityView.updateBitmap(bitmap);
        }
    }

    public void clearCache() {
        cache.clearCache();
    }
}
