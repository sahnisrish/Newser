package com.newser.Fragments;


import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.newser.Data.Constants;
import com.newser.Data.News;
import com.newser.R;

import java.io.ByteArrayInputStream;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {

    private String newsId;
    private FirebaseFirestore firestore;

    private ImageView image;
    private TextView title;
    private TextView content;
    private ScrollView containerLayout;
    private ProgressBar progressBar;

    public NewsFragment() {
        // Required empty public constructor
    }
    @SuppressLint("ValidFragment")
    public NewsFragment(String newsId){
        this.newsId = newsId;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        image = rootView.findViewById(R.id.image);
        title = rootView.findViewById(R.id.title);
        content = rootView.findViewById(R.id.content);
        containerLayout = rootView.findViewById(R.id.container);
        progressBar = rootView.findViewById(R.id.progress);

        firestore = FirebaseFirestore.getInstance();

        FetchData();
        return rootView;
    }

    private void FetchData() {
        ToggleProgress();
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... strings) {
                firestore.collection(Constants.NEWS)
                        .document(strings[0])
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                News newsItem = documentSnapshot.toObject(News.class);
                                updateViews(newsItem);
                            }
                        });
                return null;
            }
        }.doInBackground(newsId);
    }

    private void ToggleProgress() {
        if(progressBar.getVisibility()==View.GONE){
            progressBar.setVisibility(View.VISIBLE);
            containerLayout.setAlpha(.3f);
        }
        else {
            progressBar.setVisibility(View.GONE);
            containerLayout.setAlpha(1f);
        }
    }

    private void updateViews(News news) {
        title.setText(news.getTitle());
        content.setText(news.getContent());
        StorageReference imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(news.getImg_link());
        imageRef.getBytes(Constants.ONE_MEGABYTE).addOnCompleteListener(new OnCompleteListener<byte[]>() {
            @Override
            public void onComplete(@NonNull Task<byte[]> task) {
                if(task.isSuccessful()){
                    byte[] bytes=task.getResult();
                    ByteArrayInputStream inputStream= new ByteArrayInputStream(bytes);
                    Bitmap bmp= BitmapFactory.decodeStream(inputStream);
                    image.setImageBitmap(bmp);
                }
                ToggleProgress();
            }
        });
    }

}
