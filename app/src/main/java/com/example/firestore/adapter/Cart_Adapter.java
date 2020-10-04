package com.example.firestore.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.firestore.Modal.Cart;
import com.example.firestore.Modal.Common;
import com.example.firestore.My_basket;
import com.example.firestore.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart_Adapter extends FirestoreRecyclerAdapter<Cart, Cart_Adapter.CartViewHolder> {

    public double rs;
    public double x;
    public double diff;
    public double quantity_kg;
    public Button button;
    public double my_rupee;
    public double my_double;
    public CollectionReference savedRef;
    public DocumentReference documentReference;
    public FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public Cart_Adapter(@NonNull FirestoreRecyclerOptions<Cart> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final CartViewHolder cartViewHolder, int i, @NonNull final Cart cart) {
        cartViewHolder.name.setText(cart.getName());
        cartViewHolder.quantity.setText("Quantity:  "+cart.getQuantity()+" Kg");
        cartViewHolder.price.setText("Price: Rs. "+cart.getPrice());
        Picasso.get().load(cart.getLink()).into(cartViewHolder.thumbnail);
        my_rupee = Double.parseDouble(cart.getPrice());
        my_double = Double.parseDouble(cart.getQuantity());
        my_rupee = my_rupee/my_double;
        my_double = my_double*4;
        Log.d("Double", "double"+my_double);
        int kuch_bhi = (int)Math.round(my_double);
            cartViewHolder.elegantNumberButton.setNumber(String.valueOf(kuch_bhi));
        cartViewHolder.elegantNumberButton.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String num = cartViewHolder.elegantNumberButton.getNumber();
                diff = Double.parseDouble(cart.getPrice());
                Log.d("ONCLICKONCLICK", "ononclickhere"+my_rupee+" "+cart.getPrice()+" "+cart.getQuantity());
                my_rupee = Double.parseDouble(cart.getPrice());
                my_double = Double.parseDouble(cart.getQuantity());
                my_rupee = my_rupee/my_double;
                Log.d("Double", "num"+num);
                if(Integer.parseInt(num) == 0)
                {
                    delete(cartViewHolder.getAdapterPosition());
                }
                else
                {
                    Double quantity_in_kg = Double.parseDouble(num) * 0.25;
                    Double rs = my_rupee * quantity_in_kg;
                    documentReference = firebaseFirestore.collection("users").document(Common.user.getName() + Common.user.getPhone()).collection("orders").document(cart.getName());
                    Map<String, Object> map = new HashMap<>();
                    map.put("quantity", quantity_in_kg.toString());
                    map.put("price", rs.toString());
                    documentReference.update(map);
                    Log.d("document_rs", rs+"");
                    Log.d("priceof", ""+Common.ddouble);
                  //  Common.textView.setText(Common.ddouble.toString());
                    Map<String, Object> map1 = new HashMap<>();
                    map1.put("price", Common.ddouble);
                    firebaseFirestore.collection("users").document(Common.user.getName()+Common.user.getPhone()).collection("order_price").document("Item_price").update(map1);
                    notifyDataSetChanged();
                    setItemPrice();
                }
            }
        });

        cartViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(cartViewHolder.view.getContext());
                ad.setTitle("Remove item from cart");
                ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete(cartViewHolder.getAdapterPosition());
                    }
                });
                ad.show();
            }
        });

    }

    public void setItemPrice()
    {
        firebaseFirestore.collection("users").document(Common.user.getName()+Common.user.getPhone()).collection("orders").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d("MESSAGEE", "messagee1");
                        Common.ddouble = 0.0;
                        List<DocumentSnapshot> my_list = new ArrayList<>();
                        my_list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot document : my_list) {
                            Common.ddouble = Common.ddouble + Double.parseDouble(document.get("price").toString());
                        }
                        Common.textView.setText(Common.ddouble.toString());
                        //Common.textView = cart_value;
                        Map<String, Object> map = new HashMap<>();
                        map.put("price", Common.ddouble);
                        firebaseFirestore = FirebaseFirestore.getInstance();
                        savedRef = firebaseFirestore.collection("users").document(Common.user.getName() + Common.user.getPhone()).collection("order_price");
                        savedRef.document("Item_price").set(map);
                        Log.d("MESSAGEE", "messagee");
                    }
                });

    }

    public void delete(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
        setItemPrice();
    }


    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_constraint, parent, false);
        return new CartViewHolder(view);
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{
        TextView name ;
        TextView quantity;
        TextView price;
        ElegantNumberButton elegantNumberButton;
        ImageView thumbnail;
        View view ;
        ImageView imageView;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.vege_name);
            price = itemView.findViewById(R.id.price);
            imageView = itemView.findViewById(R.id.bin);
            elegantNumberButton = itemView.findViewById(R.id.enb);
            quantity = itemView.findViewById(R.id.quantity);
            thumbnail = itemView.findViewById(R.id.imageView2);
            view = itemView;

        }
    }
}
