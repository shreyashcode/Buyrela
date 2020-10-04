package com.example.firestore.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firestore.Modal.UserSlots;
import com.example.firestore.Modal.Useraddress;
import com.example.firestore.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class SlotsAdapter extends FirestoreRecyclerAdapter<UserSlots, SlotsAdapter.SlotsAdapterViewHolder> {
    public int index = -1;
    public SlotsAdapter(@NonNull FirestoreRecyclerOptions<UserSlots> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull SlotsAdapterViewHolder slotsAdapterViewHolder, int i, @NonNull UserSlots userSlots)
    {
        slotsAdapterViewHolder.slots.setText(userSlots.getUserslots());
        Log.d("onBind", userSlots.getStatus());
        if(userSlots.getStatus().equals("NOTAVAILABLE"))
        {
       //     slotsAdapterViewHolder.banner.setBackgroundColor(Color.parseColor("#E15B58"));
            slotsAdapterViewHolder.banner_banner.setBackgroundColor(Color.parseColor("#E15B58"));
            slotsAdapterViewHolder.banner_banner.setText("Not available");
        }
        else
        {
        //    slotsAdapterViewHolder.banner.setBackgroundColor(Color.parseColor("#62BA24"));
            slotsAdapterViewHolder.banner_banner.setBackgroundColor(Color.parseColor("#62BA24"));
            slotsAdapterViewHolder.banner_banner.setText("Available");
        }
    }

    @NonNull
    @Override
    public SlotsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_slots, parent, false);
        return new SlotsAdapterViewHolder(view);
    }

    public class SlotsAdapterViewHolder extends RecyclerView.ViewHolder
    {
        TextView slots;
        TextView banner_banner;
      //  ImageView banner;
        public SlotsAdapterViewHolder(@NonNull View itemView)
        {
            super(itemView);
            slots = itemView.findViewById(R.id.availability);
            banner_banner = itemView.findViewById(R.id.banner_banner);
           // banner = itemView.findViewById(R.id.banner);
        }
    }
}

