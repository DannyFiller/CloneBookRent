package com.example.cmpm.Adapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cmpm.Model.Book;
import com.example.cmpm.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.MyViewHolder>{

    Context context;

    ArrayList<Book> list;
    CallBack deleteCall;



//    public BookAdapter(Context context, ArrayList<Book> list) {
//        this.context = context;
//        this.list = list;
//    }

    public GioHangAdapter(Context context,ArrayList<Book> list,CallBack delete) {
        this.context = context;
        this.list = list;
        this.deleteCall = delete;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.giohang_item_layout,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Book book = list.get(position);

        holder.tvTenSach.setText(list.get(position).getTenSach());
        holder.tvGiaSach.setText(String.valueOf(list.get(position).getGia()));
        Picasso.get().load(list.get(position).getImage()).into(holder.ivSachGioHang);

        holder.tvbtnDelete.setOnClickListener(view -> deleteCall.onClick(position,book));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView ivSachGioHang;
        TextView tvTenSach,tvGiaSach;

        ImageView tvbtnDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivSachGioHang = itemView.findViewById(R.id.ivGioHang);
            tvTenSach = itemView.findViewById(R.id.tvTenSachGH);
            tvGiaSach = itemView.findViewById(R.id.tvGiaSachGH);
            tvbtnDelete = itemView.findViewById(R.id.ivbtnDelete);

        }
    }

    public interface CallBack{

        void onClick(int position, Book book);
    }
}