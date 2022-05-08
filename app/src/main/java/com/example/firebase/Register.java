package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText registEmail = findViewById(R.id.registerEmail);
        final EditText registPassword = findViewById(R.id.registerPassword);

        final Button registbtn = findViewById(R.id.registerUserBtn);
        final Button loginnowbtn = findViewById(R.id.toLoginForm);

        loginnowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        registbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String registEmailtxt = registEmail.getText().toString();
                final String registPasswordtxt = registPassword.getText().toString();

                if(registEmailtxt.isEmpty() || registPasswordtxt.isEmpty()){
                    Toast.makeText(Register.this, "please fill the register form", Toast.LENGTH_SHORT).show();
                }else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(registEmailtxt)){
                                Toast.makeText(Register.this, "email already registered", Toast.LENGTH_SHORT).show();
                            }else{
                                databaseReference.child("users").child(registEmailtxt).child("password").setValue(registPasswordtxt);

                                Toast.makeText(Register.this, "register successful", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });
    }
}