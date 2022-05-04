package com.example.haircutscheduling.classes.CustomAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haircutscheduling.R;

import java.util.ArrayList;
import java.util.HashMap;

public class HistoryCustomAdapter extends RecyclerView.Adapter<HistoryCustomAdapter.MyViewHolder> {

    private final ArrayList<HashMap<String, String>> dataSet;

    public HistoryCustomAdapter(ArrayList<HashMap<String, String>> data) {
        this.dataSet = data;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardViewHistory;
        TextView textViewHairStyleHistory;
        TextView textViewDateHistory;
        TextView textViewHourHistory;

        public MyViewHolder(View itemView) {
            super(itemView);

            this.cardViewHistory = (CardView) itemView.findViewById(R.id.card_view_history);
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
        CardView cardViewHistory = holder.cardViewHistory;

//        textViewHairStyleHistory.setText(dataSet.get(position).getHairStyle());
//        textViewDateHistory.setText(dataSet.get(position).getDate());
//        textViewHourHistory.setText(dataSet.get(position).getHour());

        textViewHairStyleHistory.setText(dataSet.get(position).get("hairStyle"));
        textViewDateHistory.setText(dataSet.get(position).get("date"));
        textViewHourHistory.setText(dataSet.get(position).get("hour"));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}