package com.t3h.buoi13;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.t3h.buoi13.adapter.SongAdapter;
import com.t3h.buoi13.databinding.ActivityMainBinding;
import com.t3h.buoi13.model.Song;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements  SongAdapter.ItemClickListerner {

    private SystemData systemData;
    private ArrayList<Song> arrSong;

    private SongAdapter adapter;

    private ActivityMainBinding binding;

    private MediaManager manager;


    private final String[] PERMISSION_LIST = {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_main);
        adapter = new SongAdapter(this);
        adapter.setListerner(this);
        binding.lvSong.setAdapter(adapter);


//        checkAndRequestPermissions();

        if (checkPermission()) {
            readData();
        }
    }

    private void readData() {
        systemData = new SystemData(this);
        arrSong = systemData.getData();
//        arrSong = systemData.getData3();
        //int a=7;
        adapter.setData(arrSong);

        manager = new MediaManager(arrSong,this);

    }


    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String p : PERMISSION_LIST) {
                if (checkSelfPermission(p) !=
                        PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(PERMISSION_LIST, 0);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (checkPermission()) {
            readData();
        } else {
            finish();
        }
    }


    @Override
    public void onItemClicked(int position) {
        manager.create(position);
    }
}
