package com.example.myshoppingapp.seller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myshoppingapp.AdminKidsCategoryDisplay;
import com.example.myshoppingapp.AdminMenClothing;
import com.example.myshoppingapp.AdminPanelinterface;
import com.example.myshoppingapp.AdminmaintainskidsproudctActivity;
import com.example.myshoppingapp.CheckNewWomenProducts;
import com.example.myshoppingapp.MainActivity;
import com.example.myshoppingapp.Model.Products;
import com.example.myshoppingapp.R;
import com.example.myshoppingapp.ViewHolder.ItemViewHolder;
import com.example.myshoppingapp.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.nio.file.attribute.AclEntry;

public class SellerHomeActivity extends AppCompatActivity {
    private TextView mTextMesssage;
    Button navhome,navadd,navlogout;
    private RecyclerView recyclerView,recyclerView1,recyclerView2,recyclerView3;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference unverifiedWProductRef;
    private DatabaseReference unverifiedWProductRef1;
    private DatabaseReference unverifiedWProductRef2;
    private DatabaseReference unverifiedWProductRef3;
    public boolean onNavigationItemSelected(MenuItem items){
        switch (items.getItemId()){

            case R.id.navigation_home:
                Intent intentHome = new Intent(Intent.ACTION_VIEW);
                startActivity(intentHome);
                return true;

            case R.id.navigation_add:
                Intent intentCate = new Intent(SellerHomeActivity.this, AdminPanelinterface.class);
                startActivity(intentCate);
                finish();
                return true;

            case R.id.navigation_logout:
                final FirebaseAuth mAuth;
                mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                Intent intentMain = new Intent(SellerHomeActivity.this, MainActivity.class);
                intentMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
                startActivity(intentMain);

        }
        return false;
    };






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_add, R.id.navigation_logout)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_home:
                        Intent intentHome = new Intent(SellerHomeActivity.this, SellerHomeActivity.class);
                        startActivity(intentHome);
                        return true;

                    case R.id.navigation_add:
                        //         mTextMesssage.setText(R.string.title_dashboard);
                        Intent intentCate = new Intent(SellerHomeActivity.this, AdminPanelinterface.class);
                        startActivity(intentCate);
                        finish();
                        return true;

                    case R.id.navigation_logout:
                        final FirebaseAuth mAuth;
                        mAuth = FirebaseAuth.getInstance();
                        mAuth.signOut();
                        Intent intentMain = new Intent(SellerHomeActivity.this, MainActivity.class);
                        intentMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        finish();
                        startActivity(intentMain);

                }
                return false;
            }

        });

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
                .setQuery(unverifiedWProductRef.orderByChild("sid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid()),Products.class).build();



        FirebaseRecyclerAdapter<Products, ItemViewHolder> adapter=
                new FirebaseRecyclerAdapter<Products, ItemViewHolder>(options) {
                    @NonNull
                    @Override
                    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_item_view, parent, false);
                        ItemViewHolder holder = new ItemViewHolder(view);
                        return holder;
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull ItemViewHolder holder, int i, @NonNull final Products model) {
                        holder.txtProductName.setText(model.getPname());
                        holder.txtProductDescription.setText(model.getDescription());
                        holder.txtProductState.setText("State :"+model.getProductStatus());
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

                                AlertDialog.Builder builder=new AlertDialog.Builder(SellerHomeActivity.this);
                                builder.setTitle("Do you want to Delete this Product.Are you Sure");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if(i==0)
                                        {
                                            deleteProduct(productID);

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


        LoadMenProduct();
        LoadKidsProduct();
        LoadAccessoriesproduct();

    }


    private void LoadMenProduct(){

        FirebaseRecyclerOptions<Products> options1 = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(unverifiedWProductRef1.orderByChild("sid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid()),Products.class).build();



        FirebaseRecyclerAdapter<Products, ItemViewHolder> adapter1=
                new FirebaseRecyclerAdapter<Products, ItemViewHolder>(options1) {
                    @NonNull
                    @Override
                    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_item_view, parent, false);
                        ItemViewHolder holder1 = new ItemViewHolder(view1);
                        return holder1;
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull ItemViewHolder holder1, int i, @NonNull final Products model1) {

                        holder1.txtProductName.setText(model1.getPname());
                        holder1.txtProductDescription.setText(model1.getDescription());
                        holder1.txtProductState.setText("State :" + model1.getProductStatus());
                        holder1.txtProductPrice.setText("Price = " + model1.getPrice() + "$");
                        Picasso.get().load(model1.getImage()).into(holder1.imageView);

                        holder1.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final String productID = model1.getPid();
                                CharSequence options[] = new CharSequence[]{
                                        "Yes",
                                        "No"
                                };

                                AlertDialog.Builder builder = new AlertDialog.Builder(SellerHomeActivity.this);
                                builder.setTitle("Do you want to Delete this Product.Are you Sure");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (i == 0) {
                                            deletemProduct(productID);

                                        }
                                        if (i == 1) {

                                        }
                                    }
                                });
                                builder.show();
                            }
                        });

                    }
                };

        recyclerView1.setAdapter(adapter1);
        adapter1.startListening();

    }

    private void LoadKidsProduct(){

        FirebaseRecyclerOptions<Products> options3 = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(unverifiedWProductRef2.orderByChild("sid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid()),Products.class).build();



        FirebaseRecyclerAdapter<Products, ItemViewHolder> adapter2=
                new FirebaseRecyclerAdapter<Products, ItemViewHolder>(options3) {
                    @NonNull
                    @Override
                    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_item_view, parent, false);
                        ItemViewHolder holder2 = new ItemViewHolder(view2);
                        return holder2;
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull ItemViewHolder holder2, int i, @NonNull final Products model2) {

                        holder2.txtProductName.setText(model2.getPname());
                        holder2.txtProductDescription.setText(model2.getDescription());
                        holder2.txtProductState.setText("State :" + model2.getProductStatus());
                        holder2.txtProductPrice.setText("Price = " + model2.getPrice() + "$");
                        Picasso.get().load(model2.getImage()).into(holder2.imageView);

                        holder2.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final String productID = model2.getPid();
                                CharSequence options[] = new CharSequence[]{
                                        "Yes",
                                        "No"
                                };

                                AlertDialog.Builder builder = new AlertDialog.Builder(SellerHomeActivity.this);
                                builder.setTitle("Do you want to Delete this Product.Are you Sure");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (i == 0) {
                                            deletekProduct(productID);

                                        }
                                        if (i == 1) {

                                        }
                                    }
                                });
                                builder.show();
                            }
                        });

                    }
                };

        recyclerView2.setAdapter(adapter2);
        adapter2.startListening();

    }
    private void LoadAccessoriesproduct(){

        FirebaseRecyclerOptions<Products> options3 = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(unverifiedWProductRef3.orderByChild("sid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid()),Products.class).build();



        FirebaseRecyclerAdapter<Products, ItemViewHolder> adapter2=
                new FirebaseRecyclerAdapter<Products, ItemViewHolder>(options3) {
                    @NonNull
                    @Override
                    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_item_view, parent, false);
                        ItemViewHolder holder2 = new ItemViewHolder(view2);
                        return holder2;
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull ItemViewHolder holder2, int i, @NonNull final Products model2) {

                        holder2.txtProductName.setText(model2.getPname());
                        holder2.txtProductDescription.setText(model2.getDescription());
                        holder2.txtProductState.setText("State :" + model2.getProductStatus());
                        holder2.txtProductPrice.setText("Price = " + model2.getPrice() + "$");
                        Picasso.get().load(model2.getImage()).into(holder2.imageView);

                        holder2.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final String productID = model2.getPid();
                                CharSequence options[] = new CharSequence[]{
                                        "Yes",
                                        "No"
                                };

                                AlertDialog.Builder builder = new AlertDialog.Builder(SellerHomeActivity.this);
                                builder.setTitle("Do you want to Delete this Product.Are you Sure");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (i == 0) {
                                            deleteaProduct(productID);

                                        }
                                        if (i == 1) {

                                        }
                                    }
                                });
                                builder.show();
                            }
                        });

                    }
                };

        recyclerView3.setAdapter(adapter2);
        adapter2.startListening();

    }


    private void deleteProduct(String productID) {
        unverifiedWProductRef.child(productID)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(SellerHomeActivity.this,"That item has been Deleted Successfully",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(SellerHomeActivity.this, SellerHomeActivity.class);
                        startActivity(intent);
                        finish();

                    }
                });
    }
    private void deletemProduct(String productID) {
        unverifiedWProductRef1.child(productID)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(SellerHomeActivity.this,"That item has been Deleted Successfully",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(SellerHomeActivity.this, SellerHomeActivity.class);
                        startActivity(intent);
                        finish();

                    }
                });
    }
    private void deletekProduct(String productID) {
        unverifiedWProductRef2.child(productID)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(SellerHomeActivity.this,"That item has been Deleted Successfully",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(SellerHomeActivity.this, SellerHomeActivity.class);
                        startActivity(intent);
                        finish();

                    }
                });
    }
    private void deleteaProduct(String productID) {
        unverifiedWProductRef3.child(productID)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(SellerHomeActivity.this,"That item has been Deleted Successfully",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(SellerHomeActivity.this, SellerHomeActivity.class);
                        startActivity(intent);
                        finish();

                    }
                });
    }
}