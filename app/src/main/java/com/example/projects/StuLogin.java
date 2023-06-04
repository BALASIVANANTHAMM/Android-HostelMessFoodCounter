package com.example.projects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.projects.databinding.ActivityStuLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class StuLogin extends AppCompatActivity {
    ActivityStuLoginBinding binding13;
    FirebaseAuth Student;
    FirebaseUser user1;
    FirebaseFirestore data;
    String emailPattern = "[student0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog ProDia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding13=ActivityStuLoginBinding.inflate(getLayoutInflater());
        setContentView(binding13.getRoot());

        ProDia=new ProgressDialog(this);
        Student=FirebaseAuth.getInstance();
        user1=Student.getCurrentUser();
        data=FirebaseFirestore.getInstance();

        binding13.AdB1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Auth();
            }
        });
    }
    private void Auth() {
        String email=binding13.Ed1.getText().toString();
        String password=binding13.Ed2.getText().toString();

        if (!email.matches(emailPattern))
        {
            binding13.Ed1.setError("Enter Valid Email");
        }
        else if(password.isEmpty())
        {
            binding13.Ed2.setError("Enter Correct Password");
        }
        else{
            ProDia.setMessage("Please Wait For Login");
            ProDia.setTitle("Login");
            ProDia.setCanceledOnTouchOutside(false);
            ProDia.show();

            Student.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        ProDia.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(StuLogin.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                    }else{
                        ProDia.dismiss();
                        Toast.makeText(StuLogin.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserToNextActivity() {
        Intent mth = new Intent(getApplicationContext(),AddBook.class);
        mth.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mth);
    }
}