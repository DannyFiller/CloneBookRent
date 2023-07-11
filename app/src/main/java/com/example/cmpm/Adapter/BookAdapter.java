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

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder>{

    Context context;

    public static ArrayList<Book> list;

    CallBack bookCall;

//    public BookAdapter(Context context, ArrayList<Book> list) {
//        this.context = context;
//        this.list = list;
//    }

    public BookAdapter(Context context,ArrayList<Book> list, CallBack bookCall) {
        this.context = context;
        this.list = list;
        this.bookCall = bookCall;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.itemlayout,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Book book = list.get(position);
        holder.tv.setText(list.get(position).getTenSach());
        Picasso.get().load(list.get(position).getImage()).into(holder.iv);

        holder.itemView.setOnClickListener(view -> bookCall.onClick(position,book));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        TextView tv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            iv = itemView.findViewById(R.id.ivItem);
            tv = itemView.findViewById(R.id.tvItem);
        }
    }

    public interface CallBack{
        void onClick(int position, Book book);
        void setFilteredList(ArrayList<Book> filteredList);
    }
}