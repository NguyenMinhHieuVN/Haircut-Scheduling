package com.example.haircutscheduling.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.haircutscheduling.R;
import com.example.haircutscheduling.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class AdminFragment extends Fragment {

    MainActivity mainActivity;

    public AdminFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AdminFragment.
     */
    public static AdminFragment newInstance() {
        AdminFragment fragment = new AdminFragment();
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
        //LayoutInflater su dung de khoi tao noi dung cua cac tac XML bo cuc vao cac doi tuong View tuong ung
        //ViewGroup la tap hop cac che do xem (TextView, EditText, ListView, v.v.), giống như một vùng chứa.
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin, container, false);

        Button editDaysoff = view.findViewById(R.id.buttonEditNotAvailableDays);
        editDaysoff.setOnClickListener(v -> {
            mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.setFragment(new EditDaysOffFragment());
        });

        Button editOpeningHour = view.findViewById(R.id.buttonOpeningHour);
        editOpeningHour.setOnClickListener(v -> {
            mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.setFragment(new EditOpeningHour());
        });

        Button editContactDetails = view.findViewById(R.id.buttonUpdateContactDetails);
        editContactDetails.setOnClickListener(v -> {
            mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.setFragment(new EditAdminContactDetailsFragment());
        });

        Button todaysAppointments = view.findViewById(R.id.buttonTodayAppointment);
        todaysAppointments.setOnClickListener(v -> {
            mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.setFragment(new TodayAppointmentsFragment());
        });

        return view;
    }
}