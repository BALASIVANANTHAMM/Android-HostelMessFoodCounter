package com.example.projects;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.projects.databinding.ActivityStartBinding;

public class Start extends AppCompatActivity {
    ActivityStartBinding binding12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding12=ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding12.getRoot());

        binding12.bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent ad = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(ad);
                }catch (Exception e){
                    Toast.makeText(Start.this, "Not Working", Toast.LENGTH_SHORT).show();
                }

            }
        });
        binding12.bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent st = new Intent(getApplicationContext(), StuLogin.class);
                    startActivity(st);
                }catch (Exception e){
                    Toast.makeText(Start.this, "Not Working", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}