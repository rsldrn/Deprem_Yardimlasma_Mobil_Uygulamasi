package com.example.myapp.supabase;

import com.example.myapp.models.Talep;
import com.example.myapp.models.Gonderi;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Query;

public interface SupabaseServiceforTakip {

    @GET("needs?select=*")
    Call<List<Talep>> getTalepler();

//     ----- Talepler: PATCH -----

    @PATCH("needs")
    Call<Void> updateTalepDurum(@Query("id") String idQuery, @Body Map<String, Object> body);


//     ----- Gönderiler: GET -----

    @GET("needs?select=*")
    Call<List<Gonderi>> getGonderiler();

    // ----- Gönderiler: PATCH -----

    @PATCH("needs")
    Call<Void> updateGonderiDurum(@Query("id") String idQuery, @Body Map<String, Object> body);

}
