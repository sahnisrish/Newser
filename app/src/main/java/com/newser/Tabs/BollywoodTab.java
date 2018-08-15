package com.newser.Tabs;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.newser.Data.Categories;
import com.newser.Data.Constants;
import com.newser.Data.News;
import com.newser.NewsAdapter;
import com.newser.R;

import java.util.ArrayList;

public class BollywoodTab extends Fragment {

    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private ArrayList<News> news;
    private FirebaseFirestore firestore;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_bollywood, container, false);

        recyclerView = rootView.findViewById(R.id.recycler_view);
        news = new ArrayList<>();
        adapter = new NewsAdapter(getContext(),news);
        firestore = FirebaseFirestore.getInstance();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        FetchNews();

        return rootView;
    }

    private void FetchNews() {
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... strings) {
                firestore.collection(Constants.CATEGORIES)
                        .document(strings[0])
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Categories category = documentSnapshot.toObject(Categories.class);
                                for(int i=0;i<category.getNews().size();i++){
                                    firestore.collection(Constants.NEWS)
                                            .document(category.getNews().get(i))
                                            .get()
                                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    News newsItem = documentSnapshot.toObject(News.class);
                                                    news.add(newsItem);
                                                    adapter.notifyDataSetChanged();
                                                }
                                            });
                                }
                            }
                        });
                return null;
            }
        }.execute(Constants.BOLLYWOOD);
    }
}

