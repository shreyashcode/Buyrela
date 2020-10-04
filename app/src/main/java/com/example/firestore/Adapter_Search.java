package com.example.firestore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Search extends RecyclerView.Adapter<Adapter_Search.SearchHolder> {
    List<String> list = new ArrayList<>();
    OnItemClickListener listener;


    public Adapter_Search(List<String> list) {
        this.list = list;
    }

    public void filter(ArrayList<String> filterList) {
        this.list = filterList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHolder holder, final int position) {
        holder.name.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SearchHolder extends RecyclerView.ViewHolder{
        TextView name;
        public SearchHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_textview);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    listener.onItemClick(name.getText().toString(),position);
                }
            });
        }
    }

    public interface OnItemClickListener
    {
        void onItemClick(String string , int position);

    }
    public void setItemClickListener(Adapter_Search.OnItemClickListener listener)
    {
        this.listener = listener;
    }

}