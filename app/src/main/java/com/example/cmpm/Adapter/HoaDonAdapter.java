package com.example.cmpm.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cmpm.Activity.InfoTraSachActivity;
import com.example.cmpm.Model.Book;
import com.example.cmpm.Model.HoaDon;
import com.example.cmpm.R;
import com.example.cmpm.ui.LoginActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.MyViewHolder> {

    Context context;
    ArrayList<HoaDon> hoaDon;
    CallBack call;


    public HoaDonAdapter(Context context, ArrayList<HoaDon> hoaDon,CallBack call) {
        this.context = context;
        this.hoaDon = hoaDon;
        this.call = call;
    }

    @NonNull
    @Override
    public HoaDonAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.hoadon_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonAdapter.MyViewHolder holder, int position) {
        HoaDon hd = hoaDon.get(position);
        holder.mahd.setText(hoaDon.get(position).getMaHoaDon());
        KiemTra();
        holder.tvXacNhan.setText(MyViewHolder.tinhTrang);
        holder.tvTenKH.setText(hoaDon.get(position).getTenKh());
        holder.itemView.setOnClickListener(view -> call.onClick(position,hd));
    }

    @Override
    public int getItemCount() {
        return hoaDon.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView mahd,tvXacNhan,tvTenKH;
        public static String tinhTrang;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mahd = itemView.findViewById(R.id.tvHoaDon);
            tvXacNhan = itemView.findViewById(R.id.tvXacNhanHD);
            tvTenKH= itemView.findViewById(R.id.tvTenKHHD);
        }
    }

    public interface CallBack{
        void onClick(int position, HoaDon hd);
    }

    void KiemTra(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String id = LoginActivity.auth.getUid();

        db.collection("HoaDon").document(id).collection("danhSach").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d :list)
                    {

                        Book book1 = d.toObject(Book.class);
                        if(book1.getTinhTrang() == 0){
                            MyViewHolder.tinhTrang = "chưa xác nhận";

                        }
                        else
                        {
                            MyViewHolder.tinhTrang = "Đã xác nhận";
                        }
                    }
                }
            }
        });
    }
}
