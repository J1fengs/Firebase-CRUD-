package com.example.firebase;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DialogForm extends DialogFragment {
    String nama, matkul, key, pilih;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public DialogForm(String nama, String matkul, String key, String pilih) {
        this.nama = nama;
        this.matkul = matkul;
        this.key = key;
        this.pilih = pilih;
    }
    TextView tnama, tmatkul;
    Button simpan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_tambah, container, false);
        tnama = view.findViewById(R.id.editTextNamaMahasiswa);
        tmatkul = view.findViewById(R.id.editTextNamaMatkul);
        simpan = view.findViewById(R.id.buttonSaveData);

        tnama.setText(nama);
        tmatkul.setText(matkul);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getNama = tnama.getText().toString();
                String getMatkul = tmatkul.getText().toString();
                if(pilih.equals("ubah")){
                    databaseReference.child("Mahasiswa").child(key).setValue(new ModelMahasiswa(getNama,getMatkul )).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(view.getContext(), "Berhasil Melakukan Update", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(view.getContext(), "Gagal Melakukan Update", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if(dialog != null){
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }
}
