package spes.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationBar {

    public static void setupNavigationBar(Activity activity) {
        BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottom_navigation);
        if (bottomNavigationView == null) {
            Log.e("NavigationBar", "bottom_navigation bulunamadı!");
            return;
        }

            int currentActivityId = activity.getClass().getSimpleName().hashCode(); // benzersiz kimlik gibi

            // Örnek olarak aktif sekmeyi kontrol için farklı bir mantık kurulabilir
            if (activity instanceof ListActivity) {
                bottomNavigationView.setSelectedItemId(R.id.nav_sender);
            }

        /*bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_sender) {
                if (!(activity instanceof ListActivity)) {
                    activity.startActivity(new Intent(activity, ListActivity.class));
                }
                return true;
            } else if (id == R.id.nav_new_request) {
                if (!(activity instanceof NeedsFormActivity)) {
                    activity.startActivity(new Intent(activity, NeedsFormActivity.class));
                }
                return true;
            } else if (id == R.id.nav_my_requests) {
                if (!(activity instanceof TakipSayafasiActivity)) {
                    activity.startActivity(new Intent(activity, TakipSayafasiActivity.class));
                }
                return true;
            } else if (id == R.id.nav_profile) {
                if (!(activity instanceof ProfileActivity)) {
                    activity.startActivity(new Intent(activity, ProfileActivity.class));
                }
                return true;
            }
            return false;
        });

         */
            bottomNavigationView.setOnItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.nav_sender) {
                    Toast.makeText(activity, "Gönderici sekmesine tıklandı", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.nav_new_request) {
                    Toast.makeText(activity, "Yeni Talep sekmesine tıklandı", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.nav_my_requests) {
                    Toast.makeText(activity, "Taleplerim sekmesine tıklandı", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.nav_profile) {
                    Toast.makeText(activity, "Profil sekmesine tıklandı", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            });
        }
    }
