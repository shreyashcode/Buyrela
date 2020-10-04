package com.example.firestore;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

public class FoodAdapter extends FirestoreRecyclerAdapter<Food, FoodAdapter.FoodViewHolder> {

    OnItemClickListener listener;


    public FoodAdapter(@NonNull FirestoreRecyclerOptions<Food> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FoodViewHolder holder, int position, @NonNull Food model) {
        holder.name.setText(model.getName());
        holder.key.setText("Rs. "+String.valueOf(model.getKey()));
        Picasso.get().load(model.getLink()).into(holder.link);
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vegetable_constraint_item,parent,false);
        return new FoodViewHolder(view);
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView link;
        public TextView key;
        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.id_2);
            link = itemView.findViewById(R.id.image);
            key = itemView.findViewById(R.id.id_1);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    listener.onItemClick(getSnapshots().getSnapshot(position),position);
                }
            });
        }
    }

    public interface OnItemClickListener
    {
        void onItemClick(DocumentSnapshot documentSnapshot , int position);

    }
    public void setItemClickListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }

}
