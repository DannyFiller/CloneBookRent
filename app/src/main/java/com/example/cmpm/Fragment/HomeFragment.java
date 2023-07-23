package com.example.cmpm.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cmpm.Activity.InfoBookActivity;
import com.example.cmpm.Adapter.BookAdapter;
import com.example.cmpm.Model.Book;
import com.example.cmpm.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements BookAdapter.CallBack{

    RecyclerView rcView;
    BookAdapter bookAdapter;
    ArrayList<Book> listBook;
    CollectionReference ref;
    private SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        ref = db.collection("Sach");

        listBook = new ArrayList<>();

        bookAdapter = new BookAdapter(getContext(),listBook,this);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.rcBook);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(bookAdapter);

        db.collection("DauSach").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot d :list)
                {
                    Book book = d.toObject(Book.class);
                    book.setId(d.getId());
                    listBook.add(book);
                }
                bookAdapter.notifyDataSetChanged();
            }

        });

        // tạo thanh tìm kiếm
        searchView = v.findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });
        return v;
    }


    @Override
    public void onClick(int position, Book book) {
        Intent i = new Intent(getContext(), InfoBookActivity.class);
        i.putExtra("ten",book.getTenSach());
        i.putExtra("image",book.getImage());
        i.putExtra("id",book.getId());
        i.putExtra("phanloai",book.getLoai());
        i.putExtra("gia",book.getGia());
        i.putExtra("giaThue",book.getGiaThue());
        i.putExtra("tacgia",book.getTacGia());
        i.putExtra("mota",book.getMoTa());
        startActivity(i);
    }

    private void filterList(String text) {
        ArrayList<Book> filterList = new ArrayList<>();
        for (Book b : listBook){
            if(b.getTenSach().toLowerCase().contains(text.toLowerCase())){
                filterList.add(b);
            }
        }

        if(filterList.isEmpty()){

        }
        else {
            setFilteredList(filterList);
        }
    }

    @Override
    public void setFilteredList(ArrayList<Book> filteredList) {
        BookAdapter.list =  filteredList;
        bookAdapter.notifyDataSetChanged();
    }
}