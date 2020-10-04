package com.example.firestore.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.firestore.MapAddress;
import com.example.firestore.Modal.Cart;
import com.example.firestore.Modal.Common;
import com.example.firestore.Modal.Useraddress;
import com.example.firestore.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class address_adapter extends FirestoreRecyclerAdapter<Useraddress, address_adapter.addressViewHolder> {
        public int index = -1;
        public address_adapter(@NonNull FirestoreRecyclerOptions<Useraddress> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull final addressViewHolder address, int i, @NonNull final Useraddress useraddress) {
            address.Name.setText(useraddress.getName());
              address.PlotNumber.setText(useraddress.getPlotNumber()+","+useraddress.getResidentName()+","+useraddress.getArea()+","+"Nagpur");
           // address.ResidentName.setText("Resident name"+useraddress.getResidentName());
             address.LandMark.setText("Land mark: "+useraddress.getLandMark());
               //     address.area.setText("Area         "+useraddress.getArea());
                 address.Pincode.setText("Pincode: "+useraddress.getPinCode());
            address.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    index = address.getAdapterPosition();
                    Common.useraddress = useraddress;
                    MapAddress mapAddress = new MapAddress(null, useraddress.getPlotNumber()+","+useraddress.getResidentName()+","+useraddress.getArea()+","+"Nagpur"+","+useraddress.getPinCode());
                    Common.MAP = mapAddress;
                    notifyDataSetChanged();
                    Common.my_int = -1;
                    Log.d("user", Common.useraddress.getName());
                }
            });
            address.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(address.itemView.getContext());
                    ad.setTitle("Want to delete");
                    ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            delete(address.getAdapterPosition());
                        }
                    })      ;
                    ad.show();
                    return true;
                }
            });

            if(index == address.getAdapterPosition())
            {
                address.id.setBackgroundColor(Color.parseColor("#F4C0B0"));
                address.itemView.setBackgroundColor(Color.parseColor("#F4C0B0"));
                address.Name.setTextColor(Color.parseColor("#000000"));
            }
            else
            {
                address.id.setBackgroundColor(Color.parseColor("#FFFDFC"));
                address.itemView.setBackgroundColor(Color.parseColor("#FFFDFC"));
                address.Name.setTextColor(Color.parseColor("#000000"));
            }
        }

        @NonNull
        @Override
        public addressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_addresses, parent, false);
            return new addressViewHolder(view);
        }

        public class addressViewHolder extends RecyclerView.ViewHolder
        {
            TextView Name;
            TextView PlotNumber;
            TextView ResidentName;
            TextView LandMark;
            TextView Pincode;
            TextView area;
            CardView id;
            int index = -1;
            public addressViewHolder(@NonNull View itemView) {
                super(itemView);

                Name = itemView.findViewById(R.id.name_id);
                PlotNumber = itemView.findViewById(R.id.plot_number_id);
                //ResidentName = itemView.findViewById(R.id.resident_name_id);
                LandMark = itemView.findViewById(R.id.landMark_id);
                Pincode = itemView.findViewById(R.id.pincode_id);
                //area = itemView.findViewById(R.id.area_id);
                 id = itemView.findViewById(R.id.id);
            }
        }
    public void delete(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }
    }
