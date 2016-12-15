package io.keepcoding.madridguide.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.keepcoding.madridguide.R;
import io.keepcoding.madridguide.adapters.ShopsAdapter;
import io.keepcoding.madridguide.model.Shop;
import io.keepcoding.madridguide.model.Shops;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShopsFragment extends Fragment {
    private RecyclerView notesRecyclerView;
    private ShopsAdapter adapter;

    public ShopsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shops, container, false);

        notesRecyclerView = (RecyclerView) view.findViewById(R.id.shops_recycler_view);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;

    }

    private void updateUI() {
        Shops shops = Shops.build(getShops());

        adapter = new ShopsAdapter(shops, getActivity());
        notesRecyclerView.setAdapter(adapter);
    }

    @NonNull
    private List<Shop> getShops() {
        List<Shop> data = new ArrayList<>();
        data.add(new Shop(1, "1").setAddress("AD 1"));
        data.add(new Shop(2, "2").setAddress("AD 2"));
        return data;
    }


}
