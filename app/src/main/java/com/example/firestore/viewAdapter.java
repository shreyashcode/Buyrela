package com.example.firestore;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class viewAdapter extends FirebaseRecyclerAdapter<vegetable, viewAdapter.viewHolder>
{
    public viewAdapter(@NonNull FirebaseRecyclerOptions<vegetable> options)
    {

        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewAdapter.viewHolder holder, int position, @NonNull vegetable model) {

    }

    @NonNull
    @Override
    public viewAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        return null;
    }

    public class viewHolder extends RecyclerView.ViewHolder
    {

        public viewHolder(@NonNull View itemView)
        {
            super(itemView);
        }
    }
}
