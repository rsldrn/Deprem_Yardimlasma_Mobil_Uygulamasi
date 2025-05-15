package com.example.ceng106_oop;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

//import spes.myapplication.model.modelIhtiyacItem;
//import spes.myapplication.model.ToplanmaAlani;

public interface ApiService {

    // ihtiyac_listesi tablosundaki tüm verileri al
    @GET("needs")
    Call<List<modelIhtiyacItem>> getNeedsList();

    // toplanma_alanlari tablosundaki tüm verileri al
    @GET("toplanma_alanlari")
    Call<List<ToplanmaAlani>> getToplanmaAlanlari();

    @GET("ihtiyac_listesi")
    Call<List<modelIhtiyacItem>> getIhtiyacListesiByKategori();

}
