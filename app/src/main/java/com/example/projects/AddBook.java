package com.example.projects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projects.databinding.ActivityAddbookBinding;
import com.example.projects.databinding.ActivityAddbookBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class AddBook extends AppCompatActivity {
    ActivityAddbookBinding binding2;
    FirebaseFirestore CFC;
    DatabaseReference database;
    FirebaseAuth Verify;
    FirebaseUser Stud;
    ProgressDialog ProDia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding2=ActivityAddbookBinding.inflate(getLayoutInflater());
        setContentView(binding2.getRoot());

        CFC=FirebaseFirestore.getInstance();
        Verify=FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance().getReference().child("Student");
        Stud=Verify.getCurrentUser();
        ProDia=new ProgressDialog(this);

        Verify.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth out) {
                if (out.getCurrentUser()==null){
                    startActivity(new Intent(getApplicationContext(),Start.class));
                    finish();
                }
            }
        });

        binding2.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToMain();

            }
        });

        binding2.bh2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertCount();
            }
        });




    }



    public void sendToMain(){
        AlertDialog.Builder builder=new AlertDialog.Builder(AddBook.this);
        builder.setTitle("LogOut").setMessage("Are You Want To LogOut This?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Verify.signOut();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.create().show();
    }
    private void insertCount() {
        String ID = binding2.ed1.getText().toString();
        String ChickenCount = binding2.edh1.getText().toString();
        String EggCount = binding2.edh2.getText().toString();
        String Week = binding2.edh3.getText().toString();
        if (ID.length()<=0){
            return;
        }
        if (ChickenCount.length()<=0){
            return;
        }if (EggCount.length()<=0){
            return;
        }if (Week.length()<=0){
            return;
        }
        SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        String Time = SDF.format(new Date());
        String Uid=UUID.randomUUID().toString();

        Map<String,Object> Students=new HashMap<>();
        Students.put("id",ID);
        Students.put("Uid",Uid);
        Students.put("chickenCount",ChickenCount);
        Students.put("eggCount",EggCount);
        Students.put("week",Week);
        Students.put("time",Time);
        CFC.collection("Students").document(Uid).set(Students)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                Toast.makeText(AddBook.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                                binding2.ed1.setText("");
                                binding2.edh1.setText("");
                                binding2.edh2.setText("");
                                binding2.edh3.setText("");

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddBook.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });


    }
}
