package com.example.ceng106_oop;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//import spes.myapplication.model.modelIhtiyacItem;
//import spes.myapplication.network.ApiService;
//import spes.myapplication.network.RetrofitClient;

public class ListActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private EditText aramaEditText;
    private Button araButton;
    private Button yardimGonderButton;
    private String seciliIhtiyac = null;

    private List<String> veriAdiListesi = new ArrayList<>();
    private List<modelIhtiyacItem> ihtiyaclar = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavigationBar.setupNavigationBar(this, R.id.nav_profile);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, veriAdiListesi);
        listView.setAdapter(adapter);

        yardimGonderButton = findViewById(R.id.yardimGonderButton);
        yardimGonderButton.setEnabled(false);
        yardimGonderButton.setOnClickListener(v -> {
            if (seciliIhtiyac != null) {
                Toast.makeText(ListActivity.this, "Yardım siz tarafından gönderiliyor", Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            seciliIhtiyac = veriAdiListesi.get(position);
            yardimGonderButton.setEnabled(true);
        });

        aramaEditText = findViewById(R.id.aramaEditText);
        araButton = findViewById(R.id.araButton);
        araButton.setEnabled(false); // Başta pasif

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.getNeedsList().enqueue(new Callback<List<modelIhtiyacItem>>() {
            @Override
            public void onResponse(Call<List<modelIhtiyacItem>> call, Response<List<modelIhtiyacItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ihtiyaclar = response.body();
                    araButton.setEnabled(true);

                    for (modelIhtiyacItem item : ihtiyaclar) {
                        if (item.item != null) {
                            String adres = item.neighborhood + " mahallesi, " +
                                    item.street + " caddesi, No:" +
                                    item.building_info + ", " +
                                    item.province + "/" + item.district;

                            veriAdiListesi.add(item.item + " - " + adres);
                        }
                    }

                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<modelIhtiyacItem>> call, Throwable t) {
                Log.e("API Error", "Needs listesi alınamadı: " + t.getMessage());
            }
        });

        // Arama işlemi
        araButton.setOnClickListener(v -> {
            String aramaTerimi = aramaEditText.getText().toString().trim().toLowerCase();
            veriAdiListesi.clear();

            for (modelIhtiyacItem item : ihtiyaclar) {
                if (item.item != null && item.item.toLowerCase().contains(aramaTerimi)) {
                    String adres = item.neighborhood + " mahallesi, " +
                            item.street + " caddesi, No:" +
                            item.building_info + ", " +
                            item.province + "/" + item.district;

                    veriAdiListesi.add(item.item + " - " + adres);
                }
            }

            adapter.notifyDataSetChanged();
        });
    }
}
