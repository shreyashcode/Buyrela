package com.example.firestore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firestore.Modal.Modal_Search;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class Suggestion_Adapter extends FirestoreRecyclerAdapter<Modal_Search, Suggestion_Adapter.MyViewHolder> {

    public Suggestion_Adapter.OnItemClickListener onItem;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Suggestion_Adapter(@NonNull FirestoreRecyclerOptions<Modal_Search> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i, @NonNull Modal_Search modal_search) {
        myViewHolder.name.setText(modal_search.getName());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Suggestion_Adapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item1,parent,false));
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_textview1);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    onItem.onItemClick(name.getText().toString(),position);
                }
            });
        }
    }

    public interface OnItemClickListener
    {
        void onItemClick(String string , int position);

    }
    public void setItemClickListener(Suggestion_Adapter.OnItemClickListener listener)
    {
        onItem = listener;
    }

}