package com.example.cmpm.AdminPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cmpm.Activity.InfoBookActivity;
import com.example.cmpm.Adapter.BookAdapter;
//import com.example.cmpm.Adapter.XacNhanTraSachAdapter;
import com.example.cmpm.Model.Book;
import com.example.cmpm.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListBookAdminActivity extends AppCompatActivity implements BookAdapter.CallBack{

    BookAdapter bookAdapter;
    private ArrayList<Book> listBook;
    ExtendedFloatingActionButton btnAdd;
    CollectionReference ref;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_book_admin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Admin");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        btnAdd = findViewById(R.id.btnAdd);

//        ref = db.collection("Sach");

        listBook = new ArrayList<>();

        bookAdapter = new BookAdapter(ListBookAdminActivity.this,listBook,this);
        RecyclerView recyclerView = findViewById(R.id.rcAdmin);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ListBookAdminActivity.this,2);
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


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListBookAdminActivity.this,AddBookActivity.class);
                startActivity(i);
            }
        });

        // tạo thanh tìm kiếm
        searchView = findViewById(R.id.searchView);
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
    }

    @Override
    public void onClick(int position, Book book) {

        Intent i = new Intent(ListBookAdminActivity.this, EditBookActivity.class);
        i.putExtra("ten",book.getTenSach());
        i.putExtra("image",book.getImage());
        i.putExtra("id",book.getId());
        i.putExtra("phanloai",book.getLoai());
        i.putExtra("giaThue",book.getGiaThue());
        i.putExtra("gia",book.getGia());
        i.putExtra("tacgia",book.getTacGia());
        i.putExtra("soLuong",book.getSoLuong());
        i.putExtra("moTa",book.getMoTa());

        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.mnReturnBook:
                startActivity(new Intent(ListBookAdminActivity.this, XNTraSachActivity .class));
                break;
            case R.id.mnReceipt:
                startActivity(new Intent(ListBookAdminActivity.this,HoaDonActivity.class));
                break;
            case R.id.mnDoanhThu:
                startActivity(new Intent(ListBookAdminActivity.this,DoanhThuActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void filterList(String text) {
        ArrayList<Book> filterList = new ArrayList<>();
        for (Book b : listBook){
            if(b.getId().toLowerCase().contains(text.toLowerCase())){
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

