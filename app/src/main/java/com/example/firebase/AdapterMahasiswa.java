package com.example.firebase;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AdapterMahasiswa extends RecyclerView.Adapter<AdapterMahasiswa.MyViewHolder> {
    private List<ModelMahasiswa> mList;
    private Activity activity;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public AdapterMahasiswa(List<ModelMahasiswa> mList, Activity activity ){
        this.mList = mList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.layout_item, parent, false);
        return new MyViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ModelMahasiswa data = mList.get(position);
        holder.tvNama.setText("Nama : " + data.getNama());
        holder.tvMatkul.setText("Mata Kuliah : " + data.getMatkul());
        holder.btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setPositiveButton("iya ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        databaseReference.child("Mahasiswa").child(data.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(activity, "Berhasil Menghapus Data", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(activity, "Gagal Menghapus Data", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setMessage("Yakin Mau Menghapus? " + data.getNama());
                builder.show();
            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                FragmentManager manager = ((AppCompatActivity)activity).getSupportFragmentManager();
                DialogForm dialog = new DialogForm(
                        data.getNama(), data.getMatkul(), data.getKey(), "ubah"
                );
                dialog.show(manager, "form");
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvMatkul;
        CardView cardView;
        ImageView btnHapus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.textviewNama);
            tvMatkul = itemView.findViewById(R.id.textviewMatkul);
            cardView = itemView.findViewById(R.id.cardview);
            btnHapus = itemView.findViewById(R.id.iconHapus);

        }
    }
}
