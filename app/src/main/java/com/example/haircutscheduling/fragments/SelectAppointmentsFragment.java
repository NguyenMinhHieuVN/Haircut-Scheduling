package com.example.haircutscheduling.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haircutscheduling.R;
import com.example.haircutscheduling.activities.MainActivity;
import com.example.haircutscheduling.classes.CustomAdapters.AvailabilityCustomAdapter;
import com.example.haircutscheduling.classes.Data.AppointmentsData;
import com.example.haircutscheduling.classes.DataModels.HairStyleDataModel;
import com.example.haircutscheduling.classes.Day;
import com.example.haircutscheduling.classes.Settings;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectAppointmentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectAppointmentsFragment extends Fragment {

    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static AvailabilityCustomAdapter adapter;

    private FirebaseDatabase database;
    private MainActivity mainActivity;
    private HairStyleDataModel hairStyleAppointment;
    private String date;
    private static Settings settings;
    private Day day;


    public SelectAppointmentsFragment() {
    }

    public SelectAppointmentsFragment(HairStyleDataModel hairStyle) {
        this.hairStyleAppointment = hairStyle;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SelectAppointmentsFragment.
     */
    public static SelectAppointmentsFragment newInstance(HairStyleDataModel hairStyle) {
        SelectAppointmentsFragment fragment = new SelectAppointmentsFragment(hairStyle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_select_appointments, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerAvailbility);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mainActivity.VerifyOperationTimeExist();

        CalendarView dayOffCalender = view.findViewById(R.id.calendarViewSelectAppointment);
        dayOffCalender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");

                date = format.format(new Date(year, month, dayOfMonth));
                String currentDate = format.format(new Date());

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                int dayOfWeek = calendar.get(calendar.DAY_OF_WEEK);
                Date selectedDate = null;
                Date current = null;

                try {
                    selectedDate = format.parse(date);
                    current = format.parse(currentDate);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if(selectedDate.before(current)) {
                    Toast.makeText(mainActivity, "\n" +
                            "Please select a date in the future", Toast.LENGTH_LONG).show();
                }
                else {
                    hairStyleAppointment.setDate(date);
                    DatabaseReference settingRef = database.getReference("settings");
                    settingRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> settingTtask) {
                            if (!settingTtask.isSuccessful()) {
                            } else {
                                if (settingTtask.getResult().hasChildren()) {
                                    settings = settingTtask.getResult().getValue(Settings.class);
                                    day = settings.OperationTime.get(String.valueOf(dayOfWeek));
                                }
                            }
                        }
                    });


                    DatabaseReference myRef = database.getReference("appointments").child("appointmentsList").child(date);
                    myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {

                            if (!task.isSuccessful()) {
                            } else {
                                ArrayList<String> hours = new ArrayList<>();
                                if (task.getResult().hasChildren()) {
                                    AppointmentsData appointmentDay = new AppointmentsData((HashMap) task.getResult().getValue());
                                    hours = getAvailableHours(appointmentDay, day);
                                } else {
                                    hours = setNewDate();
                                }
                                adapter = new AvailabilityCustomAdapter(hours, hairStyleAppointment, mainActivity);
                                recyclerView.setAdapter(adapter);
                            }
                        }
                    });
                }
            }
        });

        return view;
    }

    private ArrayList<String> setNewDate()
    {
        if (settings.DayOffList.containsValue(date)) {
            mainActivity = (MainActivity) getActivity();
            Toast.makeText(mainActivity, "There are no appointments available", Toast.LENGTH_LONG).show();
            return new ArrayList<>();
        } else {
            return getAvailableHours(new AppointmentsData(), day);
        }
    }

    private ArrayList<String> getAvailableHours(AppointmentsData appointmentDay, Day day)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");

        ArrayList<String> availableHours = new ArrayList<String>();

        if (!day.getDayOff()) {

            String startHour = day.getStartHour();
            String endHour = day.getEndHour();

            Set<String> booked = appointmentDay.appointmentsList.keySet();

            Date start = null;
            Date end = null;

            try {
                start = dateFormat.parse(startHour);
                end = dateFormat.parse(endHour);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Date hour = start;

            while (hour.before(end))
            {
                String hourFormat = dateFormat.format(hour.getTime());

                if (!booked.contains(hourFormat)) {
                    availableHours.add(hourFormat);
                }

                hour.setMinutes(hour.getMinutes() + 30);
            }
        }
        else {
            mainActivity = (MainActivity) getActivity();
            Toast.makeText(mainActivity, "Close", Toast.LENGTH_LONG).show();
        }

        return availableHours;
    }
}