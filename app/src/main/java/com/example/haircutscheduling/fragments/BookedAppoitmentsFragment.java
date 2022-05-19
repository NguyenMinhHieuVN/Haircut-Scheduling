package com.example.haircutscheduling.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haircutscheduling.R;
import com.example.haircutscheduling.activities.MainActivity;
import com.example.haircutscheduling.classes.CustomAdapters.BookedCustomAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookedAppoitmentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookedAppoitmentsFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<HashMap<String, String>> bookedAppointmentData;
    private static BookedCustomAdapter adapter;
    private FirebaseDatabase database;
    private FirebaseAuth mAuto;
    MainActivity mainActivity;

    public BookedAppoitmentsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BookedAppoitmentsFragment.
     */
    public static BookedAppoitmentsFragment newInstance() {
        BookedAppoitmentsFragment fragment = new BookedAppoitmentsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        mAuto = FirebaseAuth.getInstance();
        mainActivity = (MainActivity) getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booked_appoitments, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerBooked);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        DatabaseReference myRef = database.getReference("appointments").child("appointmentsList");
        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                bookedAppointmentData = new ArrayList<HashMap<String, String>>();

                String currentUserId = mAuto.getCurrentUser().getUid();
                if (!task.isSuccessful()) {
                } else {
                    if (task.getResult().hasChildren()) {

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
                        String today = dateFormat.format(new Date());
                        Date currentDay = null;
                        try {
                            currentDay = dateFormat.parse(today);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        Object objData = task.getResult().getValue(Object.class);
                        HashMap<String, HashMap<String,HashMap<String, String>>> appointmentMap = (HashMap<String, HashMap<String,HashMap<String, String>>>) objData;
                        for (HashMap<String,HashMap<String, String>> map : appointmentMap.values()) {
                            for (HashMap<String,String> val: map.values()) {
                                Date date = null;
                                try {
                                    date = dateFormat.parse(val.get("date"));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                if (val.get("userId").equals(currentUserId) && (date.after(currentDay) || date.equals(currentDay))) {
                                    bookedAppointmentData.add(val);
                                }
                            }
                        }
                    }

                    if(bookedAppointmentData.isEmpty())
                        Toast.makeText(mainActivity,"\n" +
                                "No appointments booked",Toast.LENGTH_LONG).show();
                    adapter = new BookedCustomAdapter(bookedAppointmentData, mainActivity);
                    recyclerView.setAdapter(adapter);
                }
            }
        });

        return view;
    }
}