package com.example.haircutscheduling.classes.CustomAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haircutscheduling.R;
import com.example.haircutscheduling.activities.MainActivity;
import com.example.haircutscheduling.classes.DataModels.HairStyleDataModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AvailabilityCustomAdapter extends RecyclerView.Adapter<AvailabilityCustomAdapter.MyViewHolder>  {

    private final ArrayList<String> dataSet;
    private HairStyleDataModel hairStyleDataModel;
    private FirebaseDatabase database;
    private FirebaseAuth mAuto;
    private MainActivity mainActivity;

    public AvailabilityCustomAdapter(ArrayList<String> data, HairStyleDataModel hairStyleAppointment, MainActivity main) {
        this.dataSet = data;
        this.hairStyleDataModel = hairStyleAppointment;
        database = FirebaseDatabase.getInstance();
        mAuto = FirebaseAuth.getInstance();
        this.mainActivity = main;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardViewAvailable;
        TextView textViewHour;
        Button set;

        public MyViewHolder(View itemView) {
            super(itemView);

            this.set = (Button) itemView.findViewById(R.id.buttonSet);
            this.cardViewAvailable = (CardView) itemView.findViewById(R.id.available_card_view);
            this.textViewHour = (TextView) itemView.findViewById(R.id.textViewHour);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.available_card, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        TextView textViewHour = holder.textViewHour;
        CardView cardViewAvailable = holder.cardViewAvailable;

        Button btnSet = holder.set;

        String hour = dataSet.get(position).toString();
        textViewHour.setText(hour);

        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hairStyleDataModel.setHour(hour);
                hairStyleDataModel.setUserId(mAuto.getCurrentUser().getUid());

                DatabaseReference myRef = database.getReference("appointments").child("appointmentsList");
                myRef.child(hairStyleDataModel.getDate()).child(hairStyleDataModel.getHour()).setValue(hairStyleDataModel);
                Toast.makeText(mainActivity,"\n" +
                        "Appointment booked successfully",Toast.LENGTH_LONG).show();
                mainActivity.setMainFragment();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
