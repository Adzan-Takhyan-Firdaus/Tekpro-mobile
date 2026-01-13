package com.adzan.tekpro;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {

    // Deklarasi variabel untuk komponen UI
    private TextView btnLogin;
    private EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // 1. Inisialisasi View dari XML
        btnLogin = findViewById(R.id.btnLogin);
        etSearch = findViewById(R.id.etSearch);

        // 2. Klik Tombol Login
        // Mengarahkan user ke halaman LoginActivity
        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SearchActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        // 3. Logika Input Pencarian
        // Menangani aksi saat user menekan tombol 'Search' atau 'Enter' di keyboard
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Mengecek apakah user menekan tombol pencarian di keyboard
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {

                    String query = etSearch.getText().toString().trim();

                    if (!query.isEmpty()) {
                        // PINDAH KE HALAMAN HASIL (SearchResultActivity)
                        // Kita kirim kata kuncinya lewat Intent (putExtra)
                        Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                        intent.putExtra("QUERY_KEY", query);
                        startActivity(intent);
                    } else {
                        // Jika input kosong, kasih peringatan
                        Toast.makeText(SearchActivity.this, "Masukkan kata kunci!", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });
    }
}