package com.example.cmpm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cmpm.Model.Book;
import com.example.cmpm.Model.HoaDon;
import com.example.cmpm.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TraSachAdapter extends RecyclerView.Adapter<TraSachAdapter.MyViewHolder> {

    Context context;
    ArrayList<Book> book;
    CallBack callBack;


    public TraSachAdapter(Context context, ArrayList<Book> book,CallBack callBack) {
        this.context = context;
        this.book = book;
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public TraSachAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_tra_sach,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Book book1 = book.get(position);
        holder.tvTenSach.setText(book.get(position).getTenSach());
        holder.tvGiaSach.setText(String.valueOf(book.get(position).getIdSachThue()));
        Picasso.get().load(book.get(position).getImage()).into(holder.ivSachGioHang);



    }

    @Override
    public int getItemCount() {
        return book.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{
        ImageView ivSachGioHang;
        TextView tvTenSach,tvGiaSach;
        Button btnChonSach;
        TextView tvXacNhan;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivSachGioHang = itemView.findViewById(R.id.ivHDTS);
            tvTenSach = itemView.findViewById(R.id.tvTenSachHDTS);
            tvGiaSach = itemView.findViewById(R.id.tvMaSachHDTS);
//            btnChonSach = itemView.findViewById(R.id.btnChonSachTS);
//            tvXacNhan= itemView.findViewById(R.id.cbDaXNTS);
        }
    }

    public interface CallBack{
        void onClick(int position, Book book);
    }
}

