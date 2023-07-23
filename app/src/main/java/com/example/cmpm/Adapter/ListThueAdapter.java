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

public class ListThueAdapter extends RecyclerView.Adapter<ListThueAdapter.MyViewHolder> {

    Context context;
    ArrayList<Book> book;
    CallBack callBack;


    public ListThueAdapter(Context context, ArrayList<Book> book,CallBack callBack) {
        this.context = context;
        this.book = book;
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public ListThueAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_hd_sach,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Book book1 = book.get(position);
        holder.tvTenSach.setText(book.get(position).getTenSach());
        holder.tvGiaSach.setText(String.valueOf(book.get(position).getGia()));
        Picasso.get().load(book.get(position).getImage()).into(holder.ivSachGioHang);
        holder.btnChonSach.setOnClickListener(view -> callBack.onClick(position,book1));

        if(book.get(position).getTinhTrang() == 1){
            holder.tvXacNhan.setText("Đã xác nhận");
        }else {
            holder.tvXacNhan.setText("Chưa Xác nhận");
        }

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
            ivSachGioHang = itemView.findViewById(R.id.ivHD);
            tvTenSach = itemView.findViewById(R.id.tvTenSachHD);
            tvGiaSach = itemView.findViewById(R.id.tvGiaSachHD);
            btnChonSach = itemView.findViewById(R.id.btnChonSach);
            tvXacNhan= itemView.findViewById(R.id.cbDaXN);
        }
    }

    public interface CallBack{
        void onClick(int position, Book book);
    }
}

