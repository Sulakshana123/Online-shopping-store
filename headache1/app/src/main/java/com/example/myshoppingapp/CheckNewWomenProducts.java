package com.example.myshoppingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myshoppingapp.Model.Products;
import com.example.myshoppingapp.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class CheckNewWomenProducts extends AppCompatActivity {

    private RecyclerView recyclerView,recyclerView1,recyclerView2,recyclerView3;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference unverifiedWProductRef;
    private DatabaseReference unverifiedWProductRef1;
    private DatabaseReference unverifiedWProductRef2;
    private DatabaseReference unverifiedWProductRef3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_new_women_products);

        unverifiedWProductRef = FirebaseDatabase.getInstance().getReference().child("Women Products");
        unverifiedWProductRef1 = FirebaseDatabase.getInstance().getReference().child("Mens Products");
        unverifiedWProductRef2 = FirebaseDatabase.getInstance().getReference().child("Kids Products");
        unverifiedWProductRef3 = FirebaseDatabase.getInstance().getReference().child("Accessories Products");


        recyclerView = findViewById(R.id.seller_home_recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView1 = findViewById(R.id.seller_home_recyclerview1);
        recyclerView1.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView1.setLayoutManager(layoutManager);

        recyclerView2 = findViewById(R.id.seller_home_recyclerview2);
        recyclerView2.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView2.setLayoutManager(layoutManager);

        recyclerView3 = findViewById(R.id.seller_home_recyclerview3);
        recyclerView3.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView3.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(unverifiedWProductRef.orderByChild("productStatus").equalTo("Not Approved"),Products.class).build();

        FirebaseRecyclerAdapter<Products, ProductViewHolder>adapter=
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int i, @NonNull final Products model) {
                        holder.txtProductName.setText(model.getPname());
                        holder.txtProductDescription.setText(model.getDescription());
                        holder.txtProductPrice.setText("Price = " + model.getPrice() + "$");
                        Picasso.get().load(model.getImage()).into(holder.imageView);

                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String productID =model.getPid();
                            CharSequence options[]=new CharSequence[]{
                                    "Yes",
                                    "No"
                            };

                            AlertDialog.Builder builder=new AlertDialog.Builder(CheckNewWomenProducts.this);
                            builder.setTitle("Do you want to Approved this Product.Are you Sure");
                            builder.setItems(options, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if(i==0)
                                    {
                                        ChangeProductState(productID);

                                    }
                                    if(i==1){

                                    }
                                }
                            });
                            builder.show();
                        }
                    });



                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

        checkMen();
        checkids();
        checkAccessory();
    }


    private void checkMen()
    {
        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(unverifiedWProductRef1.orderByChild("productStatus").equalTo("Not Approved"),Products.class).build();

        FirebaseRecyclerAdapter<Products, ProductViewHolder>adapter=
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int i, @NonNull final Products model) {
                        holder.txtProductName.setText(model.getPname());
                        holder.txtProductDescription.setText(model.getDescription());
                        holder.txtProductPrice.setText("Price = " + model.getPrice() + "$");
                        Picasso.get().load(model.getImage()).into(holder.imageView);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final String productID =model.getPid();
                                CharSequence options[]=new CharSequence[]{
                                        "Yes",
                                        "No"
                                };

                                AlertDialog.Builder builder=new AlertDialog.Builder(CheckNewWomenProducts.this);
                                builder.setTitle("Do you want to Approved this Product.Are you Sure");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if(i==0)
                                        {
                                            ChangeProductState(productID);

                                        }
                                        if(i==1){

                                        }
                                    }
                                });
                                builder.show();
                            }
                        });



                    }
                };
        recyclerView1.setAdapter(adapter);
        adapter.startListening();


}


    private void checkids()
    {
        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(unverifiedWProductRef2.orderByChild("productStatus").equalTo("Not Approved"),Products.class).build();

        FirebaseRecyclerAdapter<Products, ProductViewHolder>adapter=
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int i, @NonNull final Products model) {
                        holder.txtProductName.setText(model.getPname());
                        holder.txtProductDescription.setText(model.getDescription());
                        holder.txtProductPrice.setText("Price = " + model.getPrice() + "$");
                        Picasso.get().load(model.getImage()).into(holder.imageView);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final String productID =model.getPid();
                                CharSequence options[]=new CharSequence[]{
                                        "Yes",
                                        "No"
                                };

                                AlertDialog.Builder builder=new AlertDialog.Builder(CheckNewWomenProducts.this);
                                builder.setTitle("Do you want to Approved this Product.Are you Sure");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if(i==0)
                                        {
                                            ChangeProductState(productID);

                                        }
                                        if(i==1){

                                        }
                                    }
                                });
                                builder.show();
                            }
                        });



                    }
                };
        recyclerView2.setAdapter(adapter);
        adapter.startListening();


    }


    private void checkAccessory()
    {
        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(unverifiedWProductRef3.orderByChild("productStatus").equalTo("Not Approved"),Products.class).build();

        FirebaseRecyclerAdapter<Products, ProductViewHolder>adapter=
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int i, @NonNull final Products model) {
                        holder.txtProductName.setText(model.getPname());
                        holder.txtProductDescription.setText(model.getDescription());
                        holder.txtProductPrice.setText("Price = " + model.getPrice() + "$");
                        Picasso.get().load(model.getImage()).into(holder.imageView);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final String productID =model.getPid();
                                CharSequence options[]=new CharSequence[]{
                                        "Yes",
                                        "No"
                                };

                                AlertDialog.Builder builder=new AlertDialog.Builder(CheckNewWomenProducts.this);
                                builder.setTitle("Do you want to Approved this Product.Are you Sure");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if(i==0)
                                        {
                                            ChangeProductState(productID);

                                        }
                                        if(i==1){

                                        }
                                    }
                                });
                                builder.show();
                            }
                        });



                    }
                };
        recyclerView3.setAdapter(adapter);
        adapter.startListening();


    }

    private void ChangeProductState(String productID) {
       unverifiedWProductRef.child(productID)
               .child("productStatus")
               .setValue("Approved").addOnCompleteListener(new OnCompleteListener<Void>() {
           @Override
           public void onComplete(@NonNull Task<Void> task) {
               Toast.makeText(CheckNewWomenProducts.this,"That item has been approved and it is now available for sale from the seller",Toast.LENGTH_SHORT).show();
           }
       });
    }
}