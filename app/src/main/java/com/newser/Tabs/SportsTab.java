package com.newser.Tabs;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newser.R;

public class SportsTab extends Fragment {

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_sports, container, false);


        return rootView;
    }
}

