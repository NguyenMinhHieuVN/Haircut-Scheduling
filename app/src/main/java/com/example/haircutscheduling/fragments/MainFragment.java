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
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    MainActivity mainActivity;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainFragment.
     */
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
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
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        Button getAppointment = view.findViewById(R.id.buttonScheduleAppointment);
        getAppointment.setOnClickListener(v -> {
            mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.setFragment(new AppointmentsMainFragment());
        });

        Button bookedAppointments = view.findViewById(R.id.buttonShowBookedAppointment);
        bookedAppointments.setOnClickListener(v -> {
            mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.setFragment(new BookedAppoitmentsFragment());
        });

        Button switchUser = view.findViewById(R.id.buttonSwitchUser);
        switchUser.setOnClickListener(v -> {
            mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.logOut();
            mainActivity.setLoginFragment();
        });

        Button getUserHistory = view.findViewById(R.id.buttonGetApoointmentHistory);
        getUserHistory.setOnClickListener(v -> {
            mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.setFragment(new UserHistoryFragment());
        });

        Button contactUs = view.findViewById(R.id.buttonContactUs);
        contactUs.setOnClickListener(v -> {
            mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.setFragment(new ContactUsFragment());
        });

        return view;
    }
}