package com.example.cmpm.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cmpm.Model.Book;
import com.example.cmpm.Model.HoaDon;
import com.example.cmpm.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class XacNhanTraSachAdapter extends RecyclerView.Adapter<XacNhanTraSachAdapter.MyViewHolder> {

    Context context;
    public static ArrayList<HoaDon> hoaDon;
    CallBack call;


    public XacNhanTraSachAdapter(Context context, ArrayList<HoaDon> hoaDon,CallBack call) {
        this.context = context;
        this.hoaDon = hoaDon;
        this.call = call;
    }

    @NonNull
    @Override
    public XacNhanTraSachAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.hoadon_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HoaDon hd = hoaDon.get(position);
        holder.mahd.setText(hoaDon.get(position).getMaHoaDon());
        holder.itemView.setOnClickListener(view -> call.onClick(position,hd));
        holder.tenkh.setText(hoaDon.get(position).getTenKh());

        FirebaseFirestore db = FirebaseFirestore.getInstance();

    }


    @Override
    public int getItemCount() {
        return hoaDon.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView mahd,tenkh,trasach;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mahd = itemView.findViewById(R.id.tvHoaDon);
            tenkh = itemView.findViewById(R.id.tvTenKHHD);
            trasach = itemView.findViewById(R.id.tvDaTra);
        }
    }

    public interface CallBack{
        void onClick(int position, HoaDon hd);

        void setFilteredList(ArrayList<HoaDon> filteredList);
    }
}
