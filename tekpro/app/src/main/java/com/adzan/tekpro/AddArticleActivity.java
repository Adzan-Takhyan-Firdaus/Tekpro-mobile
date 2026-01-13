package com.adzan.tekpro;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddArticleActivity extends AppCompatActivity {

    private EditText etTitle, etContent;
    private TextView btnSave, btnCancel;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);

        dbHelper = new DatabaseHelper(this);
        etTitle = findViewById(R.id.etArticleTitle);
        etContent = findViewById(R.id.etArticleContent);
        btnSave = findViewById(R.id.btnSaveArticle);
        btnCancel = findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(v -> saveArticle());
    }

    private void saveArticle() {
        String title = etTitle.getText().toString().trim();
        String content = etContent.getText().toString().trim();

        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Judul dan Isi tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            return;
        }

        // --- AMBIL ID USER YANG LAGI LOGIN ---
        SharedPreferences sharedPref = getSharedPreferences("UserSession", MODE_PRIVATE);
        long userId = sharedPref.getLong("current_user_id", -1);

        if (userId != -1) {
            boolean success = dbHelper.addArticle(title, content, "", userId);
            if (success) {
                Toast.makeText(this, "Artikel Berhasil Dibuat!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Gagal simpan ke database!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Sesi habis, silakan login ulang!", Toast.LENGTH_SHORT).show();
        }
    }
}