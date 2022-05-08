package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class Login extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText email = findViewById(R.id.loginEmail);
        final EditText password = findViewById(R.id.loginPassword);
        final Button loginbtn = findViewById(R.id.buttonLogin);
        final Button registerbtn = findViewById(R.id.buttonRegister);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailtxt = email.getText().toString();
                final String passwordtxt = password.getText().toString();

                if(emailtxt.isEmpty() || passwordtxt.isEmpty()){
                    Toast.makeText(Login.this, "enter your email or password", Toast.LENGTH_SHORT).show();
                }else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(emailtxt)){
                                final String getPassword = snapshot.child(emailtxt).child("password").getValue(String.class);

                                if(getPassword.equals(passwordtxt)){
                                    Toast.makeText(Login.this, "Login Success", Toast.LENGTH_SHORT).show();

                                    //Opening Main Activity
                                    startActivity(new Intent(Login.this, MainActivity.class));
                                    finish();

                                }else{
                                    Toast.makeText(Login.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(Login.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });
    }
}