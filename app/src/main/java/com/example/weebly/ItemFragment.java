package com.example.weebly;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weebly.helpers.FavoritesDbHelper;
import com.example.weebly.placeholder.Content;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;

public class ItemFragment extends Fragment {

    private String mData = "Nothing to see here.";
    private String mSearchString = "";

    private static final String ARG_DATA = "data";

    public static ItemFragment newInstance(String day) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DATA, day);

        fragment.setArguments(args);
        return fragment;
    }

    public ItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mData = getArguments().getString(ARG_DATA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new GridLayoutManager(context, 1));

            MainActivity main  =(MainActivity) getActivity();

            List<Content.AnimeSched> items = Content.getItemByDay(mData,main.searchText);
            ArrayList<String> favorites = FavoritesDbHelper.getAllFavorites(context);

            ArrayList<Content.AnimeSched> finalList= new ArrayList<>();
            ArrayList<Content.AnimeSched> notFavorites= new ArrayList<>();
            items.forEach(animeSched -> {
                if(favorites.contains(animeSched.id)){
                    finalList.add(animeSched);
                }else{
                    notFavorites.add(animeSched);
                }
            });

            finalList.addAll(notFavorites);


            MyItemRecyclerViewAdapter theAdapter= new MyItemRecyclerViewAdapter(finalList, getActivity());

            recyclerView.setAdapter(theAdapter);
        }
        return view;
    }
}