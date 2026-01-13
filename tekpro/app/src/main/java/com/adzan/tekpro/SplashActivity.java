package com.adzan.tekpro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log; // Tambahkan Log

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SplashActivity extends AppCompatActivity {

    // Log Tag untuk debugging
    private static final String TAG = "SplashActivity";
    private static final int LOCATION_PERMISSION_CODE = 100;

    ImageView flag;
    TextView welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        flag = findViewById(R.id.imgFlag);
        welcome = findViewById(R.id.tvWelcome);

        // CEK PERMISSION
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_CODE);
        } else {
            getLocationUpdates();
        }

        // Auto pindah ke SearchActivity
        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            Intent intent = new Intent(SplashActivity.this, SearchActivity.class);
            startActivity(intent);
            finish();
        }, 25000);
    }

    // ===============================
    //       AMBIL UPDATE LOKASI
    // ===============================
    private void getLocationUpdates() {
        LocationManager locationManager =
                (LocationManager) getSystemService(LOCATION_SERVICE);

        try {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0,
                    0,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            // Panggil method baru
                            String locationDetail = getLocationDetails(
                                    location.getLatitude(),
                                    location.getLongitude()
                            );
                            setFlagAndWelcome(locationDetail);

                            // Hentikan update lokasi setelah dapat sekali
                            locationManager.removeUpdates(this);
                        }
                    }
            );
        } catch (SecurityException e) {
            Log.e(TAG, "Security Exception saat requestLocationUpdates: " + e.getMessage());
            e.printStackTrace();
            // Jika gagal, set default Indonesia
            setFlagAndWelcome("ID");
        }
    }

    // ===============================
    //    AMBIL DETAIL LOKASI (NEGARA/PROVINSI)
    // ===============================
    private String getLocationDetails(double lat, double lon) {
        // Gunakan Locale.ENGLISH untuk Geocoder agar hasil nama Area Administratif lebih konsisten
        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);

        try {
            List<Address> addressList = geocoder.getFromLocation(lat, lon, 1);
            if (!addressList.isEmpty()) {
                Address address = addressList.get(0);
                String countryCode = address.getCountryCode();

                // Cek jika NEGARA-nya adalah Indonesia (ID)
                if ("ID".equalsIgnoreCase(countryCode)) {
                    // Jika di Indonesia, ambil NAMA ADMINISTRATIVE AREA (Provinsi)
                    String adminArea = address.getAdminArea();
                    if (adminArea != null && !adminArea.isEmpty()) {
                        Log.d(TAG, "Lokasi di Indonesia. Provinsi: " + adminArea);
                        // Kembalikan nama provinsi (misal: "West Java")
                        return adminArea;
                    } else {
                        // Jika provinsi null, kembalikan kode negara ID
                        return "ID";
                    }
                } else {
                    // Jika BUKAN di Indonesia, kembalikan kode negara (misal: "US", "JP")
                    Log.d(TAG, "Lokasi di luar Indonesia. Kode Negara: " + countryCode);
                    return countryCode;
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Geocoder gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return "ID"; // fallback default Indonesia
    }

    // ===============================
    //     ATUR FLAG & UCAPAN
    // ===============================
    private void setFlagAndWelcome(String locationDetail) {

        String lowerCaseDetail = locationDetail.toLowerCase(Locale.ROOT);

        // 1. Cek dulu untuk PROVINSI di Indonesia
        if (lowerCaseDetail.contains("west java") || lowerCaseDetail.contains("jawa barat")) {
            // Asumsi kamu punya logo provinsi Jabar di drawable
            flag.setImageResource(R.drawable.jabar);
            welcome.setText("Wilujeng Sumping di tanah anu pinuh carita, Tekpro hadir jadi dulur satia nu siap mantuan anjeun kalayan tulus tur tanpa wates.");

        }
        // Tambahkan provinsi lain di sini jika perlu, misal:
        // --- JAWA TENGAH (Jateng) ---
        else if (lowerCaseDetail.contains("central java") || lowerCaseDetail.contains("jawa tengah")) {
            flag.setImageResource(R.drawable.jateng);
            welcome.setText("Sugeng rawuh! Tekpro siap mbantu."); // Jawa
        }
        // --- JAWA TIMUR (Jatim) ---
        else if (lowerCaseDetail.contains("east java") || lowerCaseDetail.contains("jawa timur")) {
            flag.setImageResource(R.drawable.jatim);
            welcome.setText("Sugeng rawuh rek! Tekpro siap nulungi."); // Jawa Suroboyoan
        }
        // --- DKI JAKARTA ---
        else if (lowerCaseDetail.contains("jakarta") || lowerCaseDetail.contains("dki")) {
            flag.setImageResource(R.drawable.jakarta);
            welcome.setText("Assalammu'alaikum! Tekpro siap bantuin, boss."); // Betawi/Indonesia
        }
        // --- BANTEN ---
        else if (lowerCaseDetail.contains("banten")) {
            flag.setImageResource(R.drawable.banten);
            welcome.setText("Wilujeng sumping di Banten! Tekpro siap ngabantu."); // Sunda/Indonesia
        }
        // --- SUMATERA BARAT (Sumbar) ---
        else if (lowerCaseDetail.contains("west sumatra") || lowerCaseDetail.contains("sumatera barat") || lowerCaseDetail.contains("sumbar")) {
            flag.setImageResource(R.drawable.sumbar);
            welcome.setText("Assalamu'alaikum. Salamik ka Tekpro, siaok!"); // Minang/Indonesia
        }
        // --- BALI ---
        else if (lowerCaseDetail.contains("bali")) {
            flag.setImageResource(R.drawable.bali);
            welcome.setText("Rahajeng rauh! Tekpro sampun siyaga."); // Bali
        }
        // 2. Jika bukan provinsi spesifik yang didukung, cek KODE NEGARA
        else {
            switch (locationDetail) {
                case "JP":
                    flag.setImageResource(R.drawable.flag_jp);
                    welcome.setText("こんにちは、Tekproがお手伝いします");
                    break;

                case "US":
                    flag.setImageResource(R.drawable.flag_us);
                    welcome.setText("Hello, Tekpro is here to help you");
                    break;

                case "ID": // Ini akan jadi default fallback kalau di Indonesia tapi provinsi ga terdeteksi/ga didukung
                default:
                    // Jika tidak terdeteksi atau default ID
                    flag.setImageResource(R.drawable.flag_indo);
                    welcome.setText("Halo, Tekpro siap membantu");
                    break;
            }
        }
    }

    // ===============================
    //    PERMISSION CALLBACK
    // ===============================
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_CODE) {

            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                getLocationUpdates();
            }
        }
    }
}