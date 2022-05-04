package com.example.haircutscheduling.classes.CustomAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haircutscheduling.R;
import com.example.haircutscheduling.classes.DataModels.UpdateDataModel;

import java.util.ArrayList;

public class UpdatesBoardCustomAdapter extends RecyclerView.Adapter<UpdatesBoardCustomAdapter.MyViewHolder> {

    private final ArrayList<UpdateDataModel> dataSet;

    public UpdatesBoardCustomAdapter(ArrayList<UpdateDataModel> data) {
        this.dataSet = data;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardViewUpdates;
        EditText editTextUpdate;
        TextView textViewUpdateDate;

        public MyViewHolder(View itemView) {
            super(itemView);

            this.cardViewUpdates = (CardView) itemView.findViewById(R.id.card_view_updates);
            this.editTextUpdate = (EditText) itemView.findViewById(R.id.editTextTextMultiLineUpdate);
            this.textViewUpdateDate = (TextView) itemView.findViewById(R.id.textViewUpdateDate);
        }
    }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.updates_cards, parent, false);

            MyViewHolder myViewHolder = new MyViewHolder(view);

            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            EditText editTextUpdate = holder.editTextUpdate;
            TextView textViewUpdateDate = holder.textViewUpdateDate;

            UpdateDataModel upd = dataSet.get(position);
            editTextUpdate.setText(upd.getUpdate());
            textViewUpdateDate.setText(upd.getDate());
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }

}
