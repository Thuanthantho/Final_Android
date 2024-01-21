package com.example.bikerer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class RequestAdapter extends ArrayAdapter{

    private Activity mContext;
    List<Trip> requestList;
    public RequestAdapter(Activity mContext, List<Trip> requestList) {
        super(mContext, R.layout.trip_request_list, requestList);
        this.mContext = mContext;
        this.requestList = requestList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.trip_request_list,null,true);

        TextView tvDestination = listItemView.findViewById(R.id.destinationRequest);
        TextView tvPrice = listItemView.findViewById(R.id.priceRequest);
        TextView tvVehicle = listItemView.findViewById(R.id.vehicleRequest);
        TextView tvDistance = listItemView.findViewById(R.id.distanceRequest);
        Button acceptRequestBtn = listItemView.findViewById(R.id.acceptBtn);

        Trip trips = requestList.get(position);

        tvDestination.setText(trips.getDestination());
        tvPrice.setText(trips.getPrice());
        tvVehicle.setText(trips.getVehicle());
        tvDistance.setText(trips.getDistance());

        return listItemView;
    }

}
