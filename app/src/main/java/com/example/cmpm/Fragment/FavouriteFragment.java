package com.example.cmpm.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cmpm.Activity.InfoBookActivity;
import com.example.cmpm.Adapter.BookAdapter;
import com.example.cmpm.Model.Book;
import com.example.cmpm.R;
import com.example.cmpm.ui.LoginActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends Fragment implements BookAdapter.CallBack{
    RecyclerView rcView;
    BookAdapter bookAdapter;
    ArrayList<Book> listBook;
    CollectionReference ref;


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Trang Yêu Thích");
        View v =  inflater.inflate(R.layout.fragment_favourite, container, false);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String idUser = LoginActivity.auth.getUid();

        listBook = new ArrayList<>();

        bookAdapter = new BookAdapter(getContext(),listBook,this);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.rcFaBook);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(bookAdapter);


        db.collection("User").document(idUser).collection("KhoSach")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot d :list)
                {
                    double yeuThich = d.getDouble("YeuThich");
                    if(yeuThich == 1)
                    {

                        Book book = d.toObject(Book.class);
                        listBook.add(book);
                    }
                }
                bookAdapter.notifyDataSetChanged();
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
        i.putExtra("tacgia",book.getTacGia());
        i.putExtra("mota",book.getMoTa());
        startActivity(i);
    }

    @Override
    public void setFilteredList(ArrayList<Book> filteredList) {

    }
}