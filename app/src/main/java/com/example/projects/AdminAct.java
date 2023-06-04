package com.example.projects;

import static java.lang.Integer.parseInt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.projects.databinding.ActivityAdminBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AdminAct extends AppCompatActivity {
    ActivityAdminBinding binding4;
    RecyclerView recyclerView;
    DatabaseReference database;
    FirebaseAuth AdminU;
    Adapter adapter;
    ArrayList<User> list;
    FirebaseFirestore data;
    FirebaseUser stu;
    ProgressDialog ProDia;
    int CountC = 0;
    int CountE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding4 = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding4.getRoot());


        AdminU = FirebaseAuth.getInstance();
        data = FirebaseFirestore.getInstance();
        ProDia = new ProgressDialog(this);

        database = FirebaseDatabase.getInstance().getReference("Student");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new Adapter(this, list);
        recyclerView.setAdapter(adapter);


        data.collection("Students")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot ds : task.getResult()) {
                            User model = new User(
                                    ds.getString("id"),
                                    ds.getString("Uid"),
                                    ds.getString("chickenCount"),
                                    ds.getString("eggCount"),
                                    ds.getString("week"),
                                    ds.getString("time"));
                            int ChickenCount = Integer.parseInt(ds.getString("chickenCount"));
                            int EggCount = Integer.parseInt(ds.getString("eggCount"));
                            CountC += ChickenCount;
                            CountE += EggCount;

                            list.add(model);
                        }
                        binding4.cc.setText(String.valueOf(CountC));
                        binding4.ec.setText(String.valueOf(CountE));

                        adapter = new Adapter(AdminAct.this, list);
                        binding4.recyclerView.setAdapter(adapter);


                        AdminU.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
                            @Override
                            public void onAuthStateChanged(@NonNull FirebaseAuth out) {
                                if (out.getCurrentUser() == null) {
                                    startActivity(new Intent(getApplicationContext(), Start.class));
                                    finish();

                                }
                            }
                        });

                        binding4.logoutBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                sendToMain();

                            }
                        });

                        binding4.refreshBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    startActivity(new Intent(getApplicationContext(), AdminAct.class));
                                } catch (Exception e) {
                                    Toast.makeText(AdminAct.this, "Something Wrong Try Again", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

                        binding4.btQ.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Double kg = 0.25, Total, cq;
                                cq = Double.parseDouble(binding4.cc.getText().toString());
                                Total = kg * cq;
                                binding4.CQ.setText(Double.toString(Total).concat("KG"));
                            }
                        });


                    }


                    private void sendToMain() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(AdminAct.this);
                        builder.setTitle("LogOut").setMessage("Are You Want To LogOut This?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                AdminU.signOut();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        builder.create().show();


                    }
                });
    }
}
