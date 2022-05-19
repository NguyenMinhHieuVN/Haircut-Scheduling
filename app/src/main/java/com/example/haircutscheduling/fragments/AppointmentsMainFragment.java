package com.example.haircutscheduling.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haircutscheduling.R;
import com.example.haircutscheduling.activities.MainActivity;
import com.example.haircutscheduling.classes.CustomAdapters.HairStylesMenuCustomAdapter;
import com.example.haircutscheduling.classes.Data.HairStylesMenuData;
import com.example.haircutscheduling.classes.DataModels.HairStyleDataModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppointmentsMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppointmentsMainFragment extends Fragment {

    MainActivity mainActivity;

    public AppointmentsMainFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AppointmentsMainFragment.
     */
    public static AppointmentsMainFragment newInstance() {
        AppointmentsMainFragment fragment = new AppointmentsMainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_appointments_main, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.hairstyleRecyclerView);
        recyclerView.setHasFixedSize(true);
        //RecyclerView la mot ViewGroup no duoc dung de chuanbi va hien thi cac View tuong ung nhau
        //RecyclerView duoc cho la su ke thua cua ListView va GridView

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        ArrayList<HairStyleDataModel> hairstyleData = new ArrayList<HairStyleDataModel>();
        for (int i = 0; i < HairStylesMenuData.hairStyleArray.length; i++) {
            hairstyleData.add(new HairStyleDataModel(
                    HairStylesMenuData.hairStyleArray[i],
                    HairStylesMenuData.priceArray[i],
                    HairStylesMenuData.drawableArray[i]
            ));
        }
        
        mainActivity = (MainActivity) getActivity();
        HairStylesMenuCustomAdapter adapter = new HairStylesMenuCustomAdapter(hairstyleData, mainActivity);
        recyclerView.setAdapter(adapter);

        return view;
    }
}