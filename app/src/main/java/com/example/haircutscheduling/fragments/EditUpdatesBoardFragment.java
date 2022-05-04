package com.example.haircutscheduling.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.haircutscheduling.R;
import com.example.haircutscheduling.activities.MainActivity;
import com.example.haircutscheduling.classes.DataModels.UpdateDataModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;


public class EditUpdatesBoardFragment extends Fragment {

    MainActivity mainActivity;
    public FirebaseDatabase database;

    public EditUpdatesBoardFragment() {
        // Required empty public constructor
    }

    public static EditUpdatesBoardFragment newInstance() {
        EditUpdatesBoardFragment fragment = new EditUpdatesBoardFragment();
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
        View view = inflater.inflate(R.layout.fragment_edit_updates_board, container, false);

        Button addUpdate = view.findViewById(R.id.buttonAddUpdate);
        addUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText updateInput = view.findViewById(R.id.editTextTextMultiLineUpdateInput);
                String update = updateInput.getText().toString();

                mainActivity = (MainActivity) getActivity();
                addToUpdatesBoard(update);
            }
        });

        return view;
    }

    public void addToUpdatesBoard(String update) {
        if (update.isEmpty()) {
            Toast.makeText(mainActivity, "\n" +
                    "Please write your updates", Toast.LENGTH_SHORT).show();
        } else {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
            String currentDate = format.format(new Date());

            UpdateDataModel adminUpdate = new UpdateDataModel(update, currentDate);
            DatabaseReference myRef = database.getReference("updates");
            myRef.child("updatesList").push().setValue(adminUpdate);

            Toast.makeText(mainActivity, "Update added to the board", Toast.LENGTH_SHORT).show();
        }
    }
}