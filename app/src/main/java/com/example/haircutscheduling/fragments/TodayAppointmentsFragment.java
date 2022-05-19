package com.example.haircutscheduling.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haircutscheduling.R;
import com.example.haircutscheduling.classes.CustomAdapters.TodayAppointmentsCustomAdapter;
import com.example.haircutscheduling.classes.Data.AppointmentsData;
import com.example.haircutscheduling.classes.DataModels.HairStyleDataModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TodayAppointmentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodayAppointmentsFragment extends Fragment {

    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<HairStyleDataModel> todayBooked;
    private static TodayAppointmentsCustomAdapter adapter;
    private FirebaseDatabase database;

    public TodayAppointmentsFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AllAppointmentsFragment.
     */
    public static TodayAppointmentsFragment newInstance() {
        TodayAppointmentsFragment fragment = new TodayAppointmentsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_today_appointments, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.todayRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
        String currentDate = format.format(new Date());

        DatabaseReference myRef = database.getReference("appointments").child("appointmentsList").child(currentDate);
        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if (!task.isSuccessful()) {
                }
                else {
                    todayBooked = new ArrayList<HairStyleDataModel>();

                    AppointmentsData appointmentDay = new AppointmentsData((HashMap) task.getResult().getValue());

//                    Object objData = task.getResult().getValue(Object.class);
//                    HashMap<String, HairStyleDataModel> appointmentDay = (HashMap<String, HairStyleDataModel>) objData;
                    /*if (appointmentDay != null)
                    {
                        for (HairStyleDataModel dataModel :appointmentDay.values()) {
                            todayBooked.add(dataModel);
                        }
                    }*/
                    if (appointmentDay.appointmentsList != null)
                    {
                        todayBooked.addAll(appointmentDay.appointmentsList.values());
                    }

                    adapter = new TodayAppointmentsCustomAdapter(todayBooked);
                    recyclerView.setAdapter(adapter);
                }
            }
        });

        return view;
    }
}