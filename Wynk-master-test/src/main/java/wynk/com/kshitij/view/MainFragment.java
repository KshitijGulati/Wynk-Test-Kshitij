package wynk.com.kshitij.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import wynk.com.kshitij.R;
import wynk.com.kshitij.model.Photo;
import wynk.com.kshitij.presenter.ImagesPresenter;
import wynk.com.kshitij.utils.Utility;


public class MainFragment extends Fragment implements MainActivityView {

    private Button previous, next;
    private ProgressBar pBar;
    private ImageView imv;
    private List<Photo> photoList = new ArrayList<>();
    private int currentIndex = -1;
    private int pageCount = 1;
    private ImagesPresenter imagesPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // for retaining instances over configuration change
        setRetainInstance(true);
        imagesPresenter = new ImagesPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_view, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        pBar = (ProgressBar) view.findViewById(R.id.progress);
        previous = (Button) view.findViewById(R.id.previous);
        next = (Button) view.findViewById(R.id.next);
        imv = (ImageView) view.findViewById(R.id.imageview);
        // initially if currentIndex is less than 0 hide previous
        if (currentIndex <= 0) {
            previous.setVisibility(View.GONE);
        } else {
            previous.setVisibility(View.VISIBLE);
        }
        //onclick of previous button should navigate the user to previous image
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex--;
                // on the basis of currentIndex, access the photo url and fetch bitmap
                if (photoList != null && photoList.size() > 0 && currentIndex < photoList.size()) {
                    imagesPresenter.fetchBitmap(Utility.getImageUrl(photoList.get(currentIndex)));
                }
                if (currentIndex > 0) {
                    previous.setVisibility(View.VISIBLE);
                } else {
                    previous.setVisibility(View.GONE);
                }
            }
        });
        //onclick of next button should navigate the user to next image
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex++;
                if (photoList != null && photoList.size() > 0 && currentIndex < photoList.size()) {
                    imagesPresenter.fetchBitmap(Utility.getImageUrl(photoList.get(currentIndex)));
                }
                if (currentIndex > 0) {
                    previous.setVisibility(View.VISIBLE);
                } else {
                    previous.setVisibility(View.GONE);
                }
                if (currentIndex >= photoList.size()) {
                    imagesPresenter.fetchImagesUrl(++pageCount);
                }
            }
        });
        // initially if photoList is empty
        if (photoList == null || photoList.size() == 0) {
            imagesPresenter.fetchImagesUrl(pageCount);
        } else {
            imagesPresenter.fetchBitmap(Utility.getImageUrl(photoList.get(currentIndex)));
        }
    }

    @Override
    public void updateImageUrls(List<Photo> photoList) {
        this.photoList.addAll(photoList);
        // for first case, show first image, without clicking next button
        if (currentIndex < 0 && photoList.size() > 0) {
            currentIndex++;
            imagesPresenter.fetchBitmap(Utility.getImageUrl(photoList.get(0)));
        }
    }

    @Override
    public void updateBitmap(Bitmap bitmap) {
        imv.setImageBitmap(bitmap);
    }

    @Override
    public void showLoader() {
        pBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoader() {
        pBar.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        imagesPresenter.clearCache();
        super.onDestroy();
    }
}
