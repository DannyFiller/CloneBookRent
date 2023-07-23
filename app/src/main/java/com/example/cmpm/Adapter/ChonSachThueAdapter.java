package com.example.cmpm.Adapter;

import android.content.Context;
import android.content.pm.PackageManager;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kotlin.experimental.BitwiseOperationsKt;

public class ChonSachThueAdapter extends RecyclerView.Adapter<ChonSachThueAdapter.MyViewHolder>{

    Context context;

    ArrayList<Book> list;

    CallBack bookCall;

//    public BookAdapter(Context context, ArrayList<Book> list) {
//        this.context = context;
//        this.list = list;
//    }

    public ChonSachThueAdapter(Context context,ArrayList<Book> list, CallBack bookCall) {
        this.context = context;
        this.list = list;
        this.bookCall = bookCall;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_sach_thue,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Book book = list.get(position);
        holder.tv.setText(list.get(position).getId());
        if (list.get(position).getTinhTrang() == 0)
        {
            holder.tvTinhTrang.setText("Sách còn tồn kho");
        }else {
            holder.tvTinhTrang.setText("Đã cho thuê");
        }



        holder.itemView.setOnClickListener(view -> bookCall.onClick(position,book));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv,tvTinhTrang;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tvMasach);
            tvTinhTrang = itemView.findViewById(R.id.tvTinhTrangST);
        }
    }

    public interface CallBack{
        void onClick(int position, Book book);
    }
}