package com.example.haircutscheduling.classes.CustomAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haircutscheduling.R;
import com.example.haircutscheduling.classes.DataModels.HairStyleDataModel;

import java.util.ArrayList;
import java.util.HashMap;

public class TodayAppointmentsCustomAdapter extends RecyclerView.Adapter<TodayAppointmentsCustomAdapter.MyViewHolder>{

    private final ArrayList<HairStyleDataModel> dataSet;

    public TodayAppointmentsCustomAdapter(ArrayList<HairStyleDataModel> data) {
        this.dataSet = data;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardViewTodaysBooked;
        TextView textViewHairStyleHistory;
        TextView textViewDateHistory;
        TextView textViewHourHistory;

        public MyViewHolder(View itemView) {
            super(itemView);

            this.cardViewTodaysBooked = (CardView) itemView.findViewById(R.id.card_view_history);
            this.textViewHairStyleHistory = (TextView) itemView.findViewById(R.id.textViewHairStyleHistory);
            this.textViewDateHistory = (TextView) itemView.findViewById(R.id.textViewHistoryDate);
            this.textViewHourHistory = (TextView) itemView.findViewById(R.id.textViewHistoryHour);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_cards, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        TextView textViewHairStyleHistory = holder.textViewHairStyleHistory;
        TextView textViewDateHistory = holder.textViewDateHistory;
        TextView textViewHourHistory = holder.textViewHourHistory;
        CardView cardViewHistory = holder.cardViewTodaysBooked;

        Object model = dataSet.get(position);
        HashMap<String,String> hashModel = (HashMap<String, String>) model;

        textViewDateHistory.setText(hashModel.get("date"));
        textViewHourHistory.setText(hashModel.get("hour"));
        textViewHairStyleHistory.setText(hashModel.get("hairStyle"));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
