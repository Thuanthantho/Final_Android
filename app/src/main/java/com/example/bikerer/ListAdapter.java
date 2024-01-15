package com.example.bikerer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListAdapter extends ArrayAdapter {
    private Activity mContext;
    List<Trip> tripList;
    public ListAdapter(Activity mContext, List<Trip> tripList) {
        super(mContext, R.layout.trip_history_list, tripList);
        this.mContext = mContext;
        this.tripList = tripList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.trip_history_list,null,true);

        TextView tvDestination = listItemView.findViewById(R.id.tvDestination);
        TextView tvPrice = listItemView.findViewById(R.id.tvPrice);
        TextView tvVehicle = listItemView.findViewById(R.id.tvVehicle);
        TextView tvDistance = listItemView.findViewById(R.id.tvDistance);

        Trip trips = tripList.get(position);

        tvDestination.setText(trips.getDestination());
        tvPrice.setText(trips.getPrice());
        tvVehicle.setText(trips.getVehicle());
        tvDistance.setText(trips.getDistance());

        return listItemView;
    }
}
