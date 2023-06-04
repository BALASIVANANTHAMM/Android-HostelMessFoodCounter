package com.example.projects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.projects.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseAuth admin;
    FirebaseUser user;
    String emailPattern = "admin123@gmail.com";
    ProgressDialog ProDia;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ProDia=new ProgressDialog(this);
        admin=FirebaseAuth.getInstance();
        user=admin.getCurrentUser();

        binding.AdB1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Auth();
            }
        });
    }

    private void Auth() {
        String email=binding.Ed1.getText().toString();
        String password=binding.Ed2.getText().toString();

        if (!email.matches(emailPattern))
        {
          binding.Ed1.setError("Enter Valid Email");
        }
        else if(password.isEmpty())
        {
            binding.Ed2.setError("Enter Your Password");
        }
        else{
            ProDia.setMessage("Please Wait For Login");
            ProDia.setTitle("Login");
            ProDia.setCanceledOnTouchOutside(false);
            ProDia.show();

            admin.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        ProDia.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                    }else{
                        ProDia.dismiss();
                        Toast.makeText(MainActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserToNextActivity() {
        Intent mth = new Intent(getApplicationContext(),AdminAct.class);
        mth.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mth);
    }
}