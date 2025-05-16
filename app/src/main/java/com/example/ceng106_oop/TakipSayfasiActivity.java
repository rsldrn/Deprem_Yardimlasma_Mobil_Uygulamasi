package com.example.ceng106_oop;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ceng106_oop.R;
import com.example.ceng106_oop.GonderilerAdapter;
import com.example.ceng106_oop.TaleplerAdapter;
import com.example.ceng106_oop.Gonderi;
import com.example.ceng106_oop.Talep;
import com.example.ceng106_oop.SupabaseClient;
import com.example.ceng106_oop.SupabaseServiceforTakip;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class TakipSayfasiActivity extends AppCompatActivity {

    private RecyclerView taleplerRecyclerView;
    private RecyclerView gonderilerRecyclerView;

    private TaleplerAdapter taleplerAdapter;
    private GonderilerAdapter gonderilerAdapter;

    private final List<Talep> talepList = new ArrayList<>();
    private final List<Gonderi> gonderiList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.takip_sayfasi);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(" ");


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
//
//
//        }
//        else {
//            getWindow().getDecorView().setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        }


        // Talepler RecyclerView
        taleplerRecyclerView = findViewById(R.id.taleplerRecyclerView);
        taleplerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        taleplerAdapter = new TaleplerAdapter(this,talepList);
        taleplerRecyclerView.setAdapter(taleplerAdapter);

        // Gönderiler RecyclerView
        gonderilerRecyclerView = findViewById(R.id.gonderilerRecyclerView);
        gonderilerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        gonderilerAdapter = new GonderilerAdapter(gonderiList, new GonderilerAdapter.OnGonderiActionListener() {
            @Override
            public void onGonderiTeslimEttim(Gonderi gonderi) {
                Log.d("Teslim", "Gönderi teslim edildi: " + gonderi.getUser_id());
                // Buraya teslim edildiğinde yapılacak işlemler yazılabilir
            }
        });
        gonderilerRecyclerView.setAdapter(gonderilerAdapter);

        // Supabase Servisi
        SupabaseServiceforTakip service = SupabaseClient.getClient().create(SupabaseServiceforTakip.class);

        // Talepleri çek
        service.getTalepler().enqueue(new Callback<List<Talep>>() {
            @Override
            public void onResponse(Call<List<Talep>> call, Response<List<Talep>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    talepList.clear();
                    talepList.addAll(response.body());
                    taleplerAdapter.notifyDataSetChanged();
                } else {
                    Log.e("TakipActivity", "Talepler başarısız: " + response.code());
                    try {
                        if (response.errorBody() != null) {
                            Log.e("TakipActivity", "Hata içeriği: " + response.errorBody().string());
                        }
                    } catch (IOException e) {
                        Log.e("TakipActivity", "Talepler error body okunamadı", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Talep>> call, Throwable t) {
                Log.e("TakipActivity", "Talepler ağ hatası", t);
            }
        });

        // Gönderileri çek
        service.getGonderiler().enqueue(new Callback<List<Gonderi>>() {
            @Override
            public void onResponse(Call<List<Gonderi>> call, Response<List<Gonderi>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    gonderiList.clear();
                    gonderiList.addAll(response.body());
                    gonderilerAdapter.notifyDataSetChanged();
                } else {
                    Log.e("TakipActivity", "Gönderiler başarısız: " + response.code());
                    try {
                        if (response.errorBody() != null) {
                            Log.e("TakipActivity", "Gönderi hata içeriği: " + response.errorBody().string());
                        }
                    } catch (IOException e) {
                        Log.e("TakipActivity", "Gönderi error body okunamadı", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Gonderi>> call, Throwable t) {
                Log.e("TakipActivity", "Gönderi ağ hatası", t);
            }
        });
    }
}
