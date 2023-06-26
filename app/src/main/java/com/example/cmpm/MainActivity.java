package com.example.cmpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.cmpm.Fragment.FavouriteFragment;
import com.example.cmpm.Fragment.GioHangFragment;
import com.example.cmpm.Fragment.HomeFragment;
import com.example.cmpm.Fragment.InfoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView mnBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mnBottom = findViewById(R.id.mnBottom);
        LoadFragment(new HomeFragment());

        mnBottom.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.mnHome:
                        LoadFragment(new HomeFragment());
                        return true;

                    case R.id.mnSave:
                        LoadFragment(new FavouriteFragment());
                        return true;

                    case R.id.mnHistory:
                        LoadFragment(new GioHangFragment());
                        return true;

                    case R.id.mnInfo:
                        LoadFragment(new InfoFragment());
                        return true;
                }
                return true;
            }
        });
    }

    void LoadFragment(Fragment fmNew){
        FragmentTransaction fmTranform = getSupportFragmentManager().beginTransaction();
        fmTranform.replace(R.id.main_fragment,fmNew);
        fmTranform.addToBackStack(null);
        fmTranform.commit();
    }

}