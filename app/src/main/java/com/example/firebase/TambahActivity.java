package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TambahActivity extends AppCompatActivity {
    EditText edNama, edMatkul;
    Button saveData;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        edNama = findViewById(R.id.editTextNamaMahasiswa);
        edMatkul = findViewById(R.id.editTextNamaMatkul);
        saveData = findViewById(R.id.buttonSaveData);

        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getNama = edNama.getText().toString();
                String getMatkul = edMatkul.getText().toString();

                if(getNama.isEmpty() || getMatkul.isEmpty()){
                    Toast.makeText(TambahActivity.this, "form nama atau matkul kosong", Toast.LENGTH_SHORT).show();
                }else{
                    databaseReference.child("Mahasiswa").push().setValue(new ModelMahasiswa(getNama, getMatkul)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(TambahActivity.this, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(TambahActivity.this, MainActivity.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(TambahActivity.this, "Gagal Menambahkan Data", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });
    }
}