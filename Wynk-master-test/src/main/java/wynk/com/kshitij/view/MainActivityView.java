package wynk.com.kshitij.view;

import android.graphics.Bitmap;

import java.util.List;

import wynk.com.kshitij.model.Photo;


public interface MainActivityView {

    void updateImageUrls(List<Photo> photoList);

    void updateBitmap(Bitmap bitmap);

    void showLoader();

    void hideLoader();
}
