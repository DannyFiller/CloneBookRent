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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SachTonKhoAdapter extends RecyclerView.Adapter<SachTonKhoAdapter.MyViewHolder>{

    Context context;

    ArrayList<Book> list;
    CallBack deleteCall;



//    public BookAdapter(Context context, ArrayList<Book> list) {
//        this.context = context;
//        this.list = list;
//    }

    public SachTonKhoAdapter(Context context,ArrayList<Book> list,CallBack delete) {
        this.context = context;
        this.list = list;
        this.deleteCall = delete;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_item_status,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Book book = list.get(position);

        holder.tvIdSach.setText(list.get(position).getId());

        if (list.get(position).getTinhTrang() == 0)
        {
            holder.tvTinhTrang.setText("Còn");
        }else {
            holder.tvTinhTrang.setText("đã được mượn");
        }

        Picasso.get().load(list.get(position).getImage()).into(holder.ivSachGioHang);

        holder.tvbtnDelete.setOnClickListener(view -> deleteCall.onClick(position,book));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView ivSachGioHang;
        TextView tvIdSach,tvTinhTrang;

        ImageView tvbtnDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivSachGioHang = itemView.findViewById(R.id.ivGioHang);
            tvIdSach = itemView.findViewById(R.id.idSachEdit);
            tvTinhTrang = itemView.findViewById(R.id.tvTinhTrang);
            tvbtnDelete = itemView.findViewById(R.id.ivbtnDelete);
        }
    }

    public interface CallBack{

        void onClick(int position, Book book);
    }
}
