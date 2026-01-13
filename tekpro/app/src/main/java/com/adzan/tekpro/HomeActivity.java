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
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView rvArticles;
    private DatabaseHelper dbHelper;
    private ArticleAdapter adapter;
    private TextView btnCreate;
    private EditText etSearchHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // 1. Inisialisasi View
        dbHelper = new DatabaseHelper(this);
        rvArticles = findViewById(R.id.rvArticles);
        btnCreate = findViewById(R.id.btnMenuCreate);
        etSearchHome = findViewById(R.id.etSearchHome);

        // 2. Setup RecyclerView
        rvArticles.setLayoutManager(new LinearLayoutManager(this));

        // Panggil fungsi untuk memuat data awal
        loadArticles();

        // 3. Logika Tombol Create
        btnCreate.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AddArticleActivity.class);
            startActivity(intent);
        });

        // 4. Logika Pencarian (Search)
        etSearchHome.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String query = etSearchHome.getText().toString().trim();
                performSearchHome(query);
                v.clearFocus(); // Menurunkan keyboard setelah search
                return true;
            }
            return false;
        });
    }

    private void loadArticles() {
        Cursor cursor = dbHelper.getAllArticles();
        updateRecyclerView(cursor);
    }

    private void performSearchHome(String keyword) {
        Cursor cursor;
        if (keyword.isEmpty()) {
            cursor = dbHelper.getAllArticles();
        } else {
            cursor = dbHelper.searchArticles(keyword);
        }
        updateRecyclerView(cursor);
    }

    // Fungsi pembantu agar kode lebih rapi
    private void updateRecyclerView(Cursor cursor) {
        if (cursor != null && cursor.getCount() > 0) {
            if (adapter == null) {
                adapter = new ArticleAdapter(this, cursor);
                rvArticles.setAdapter(adapter);
            } else {
                adapter.swapCursor(cursor);
            }
        } else {
            Toast.makeText(this, "Artikel tidak ditemukan.", Toast.LENGTH_SHORT).show();
            if (adapter != null) adapter.swapCursor(null);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadArticles();
    }
}