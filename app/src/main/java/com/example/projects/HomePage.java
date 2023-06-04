package com.example.projects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projects.databinding.ActivityHomePageBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class HomePage extends AppCompatActivity {
    ActivityHomePageBinding binding1;
    ProgressDialog ProDia;
    FirebaseFirestore UP;
    RecyclerView recyclerView;
    DatabaseReference Edit;
    ArrayList<User> userArrayList;
    User user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding1=ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding1.getRoot());

        UP=FirebaseFirestore.getInstance();
        String docId=UP.collection("Students").document().getId();
        System.out.println(docId);
        String Coll = UP.collection("Students").document(docId).getPath();
        System.out.println(Coll);


        String UID=getIntent().getStringExtra("Uid");
        String id=getIntent().getStringExtra("id");
        String chickenCount=getIntent().getStringExtra("chickenCount");
        String eggCount=getIntent().getStringExtra("eggCount");
        String week=getIntent().getStringExtra("week");

        binding1.ed1.setText(id);
        binding1.edh1.setText(chickenCount);
        binding1.edh2.setText(eggCount);
        binding1.edh3.setText(week);

        binding1.Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Uid=UID;
                String Id=binding1.ed1.getText().toString();
                String ChickenC=binding1.edh1.getText().toString();
                String EggC=binding1.edh2.getText().toString();
                String Week=binding1.edh3.getText().toString();

                updateCount(Uid,Id,ChickenC,EggC,Week);

            }
        });


    }

    private void updateCount(String uid, String id, String chickenC, String eggC, String week) {

        UP.collection("Students").document(uid).update("id",id,"chickenCount",chickenC,"eggCount",eggC,"week",week)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(HomePage.this, "Updated", Toast.LENGTH_SHORT).show();
                        onBackPressed();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(HomePage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                });
    }
}