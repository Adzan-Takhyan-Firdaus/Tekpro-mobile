package com.adzan.tekpro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailArticleActivity extends AppCompatActivity {

    private TextView tvTitle, tvAuthor, tvContent, btnBack, btnLogin;
    private ImageView iconUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_article);

        // 1. Inisialisasi View
        tvTitle = findViewById(R.id.tvDetailTitle);
        tvAuthor = findViewById(R.id.tvDetailAuthor);
        tvContent = findViewById(R.id.tvDetailContent);
        btnBack = findViewById(R.id.btnBackDetail);
        iconUser = findViewById(R.id.iconUserDetail);
        btnLogin = findViewById(R.id.btnLoginDetail);

        // 2. Ambil data dari Intent (Dikirim dari ArticleAdapter)
        String title = getIntent().getStringExtra("EXTRA_TITLE");
        String content = getIntent().getStringExtra("EXTRA_CONTENT");
        String author = getIntent().getStringExtra("EXTRA_AUTHOR"); // Ini kuncinya!
        boolean isLoggedIn = getIntent().getBooleanExtra("IS_LOGGED_IN", false);

        // 3. Set data ke tampilan
        tvTitle.setText(title);
        tvContent.setText(content);

        // Cek kalau author null, kasih default
        if (author != null) {
            tvAuthor.setText("By: " + author);
        } else {
            tvAuthor.setText("By: Unknown");
        }

        // 4. Logika Header (Status Login)
        if (isLoggedIn) {
            iconUser.setVisibility(View.VISIBLE);
            btnLogin.setVisibility(View.GONE);
        } else {
            iconUser.setVisibility(View.GONE);
            btnLogin.setVisibility(View.VISIBLE);

            btnLogin.setOnClickListener(v -> {
                startActivity(new Intent(DetailArticleActivity.this, LoginActivity.class));
            });
        }

        btnBack.setOnClickListener(v -> finish());
    }
}