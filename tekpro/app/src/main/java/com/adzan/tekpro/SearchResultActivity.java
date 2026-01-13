package com.adzan.tekpro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class SearchResultActivity extends AppCompatActivity {

    private TextView btnLogin, tvLogo;
    private EditText etSearchAgain;
    private RecyclerView rvSearchResult;
    private DatabaseHelper dbHelper;
    private ArticleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result); // Pastikan nama layout benar

        // 1. Inisialisasi View
        dbHelper = new DatabaseHelper(this);
        btnLogin = findViewById(R.id.btnLoginResult);
        tvLogo = findViewById(R.id.tvLogoResult);
        etSearchAgain = findViewById(R.id.etSearchAgain);
        rvSearchResult = findViewById(R.id.rvSearchResult);

        // 2. Setup RecyclerView
        rvSearchResult.setLayoutManager(new LinearLayoutManager(this));

        // 3. Ambil data 'QUERY_KEY' dari Intent (dikirim oleh SearchActivity)
        String query = getIntent().getStringExtra("QUERY_KEY");

        // Tampilkan kata kunci di kolom search biar user tahu apa yang lagi dicari
        if (query != null) {
            etSearchAgain.setText(query);
            performSearch(query);
        }

        // 4. Tombol Login (Tetap ada karena belum login)
        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SearchResultActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        // 5. Bisa Search Lagi (Real-time atau tekan Enter)
        etSearchAgain.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(etSearchAgain.getText().toString().trim());
                return true;
            }
            return false;
        });
    }

    // Fungsi untuk ambil data dari database dan pasang ke Adapter
    private void performSearch(String keyword) {
        Cursor cursor = dbHelper.searchArticles(keyword);

        if (adapter == null) {
            // Jika adapter belum ada, buat baru
            adapter = new ArticleAdapter(this, cursor);
            rvSearchResult.setAdapter(adapter);
        } else {
            // Jika sudah ada, cukup ganti datanya saja (refresh)
            adapter.swapCursor(cursor);
        }
    }
}