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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class BookedCustomAdapter extends RecyclerView.Adapter<BookedCustomAdapter.MyViewHolder> {

    private final ArrayList<HashMap<String, String>> dataSet;
    private FirebaseDatabase database;
    private MainActivity mainActivity;

    public BookedCustomAdapter(ArrayList<HashMap<String, String>> data, MainActivity main) {
        this.dataSet = data;
        database = FirebaseDatabase.getInstance();
        this.mainActivity = main;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardViewBooked;
        TextView textViewHairStyleBooked;
        TextView textViewPriceBooked;
        TextView textViewDateBooked;
        TextView textViewHourBooked;
        Button cancelBtn;

        public MyViewHolder(View itemView) {
            super(itemView);

            this.cardViewBooked = (CardView) itemView.findViewById(R.id.card_view_booked);
            this.textViewHairStyleBooked = (TextView) itemView.findViewById(R.id.textViewHairStyleBooked);
            this.textViewPriceBooked = (TextView) itemView.findViewById(R.id.textViewPriceBooked);
            this.textViewDateBooked = (TextView) itemView.findViewById(R.id.textViewBookedDate);
            this.textViewHourBooked = (TextView) itemView.findViewById(R.id.textViewHourBooked);
            this.cancelBtn = (Button) itemView.findViewById(R.id.buttonCancel);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.booked_cards, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        TextView textViewHairStyleBooked = holder.textViewHairStyleBooked;
        TextView textViewPriceBooked = holder.textViewPriceBooked;
        CardView cardViewBooked = holder.cardViewBooked;
        TextView textViewDateBooked = holder.textViewDateBooked;
        TextView textViewHourBooked = holder.textViewHourBooked;

        String date = dataSet.get(position).get("date");
        String hour = dataSet.get(position).get("hour");
        textViewHairStyleBooked.setText(dataSet.get(position).get("hairStyle"));
        textViewPriceBooked.setText(dataSet.get(position).get("price"));
        textViewDateBooked.setText(date);
        textViewHourBooked.setText(hour);
        DatabaseReference myRef = database.getReference("appointments").child("appointmentsList");

        Button cancel = holder.cancelBtn;
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference myRef = database.getReference("appointments").child("appointmentsList").child(date).child(hour);
                myRef.removeValue();
                mainActivity.setMainFragment();
                Toast.makeText(mainActivity, "Appointment has been cancelled",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}