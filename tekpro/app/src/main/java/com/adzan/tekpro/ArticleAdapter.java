package com.adzan.tekpro;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private final Context context;
    private Cursor cursor;

    public ArticleAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvSnippet;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvArticleTitle);
            tvSnippet = itemView.findViewById(R.id.tvArticleSnippet);
        }
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        if (!cursor.moveToPosition(position)) {
            return;
        }

        // 1. Ambil data dari Cursor
        String title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TITLE));
        String content = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CONTENT));
        String username = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_USERNAME));

        // 2. Tampilkan data ke View List
        holder.tvTitle.setText(title);
        String snippetText = content + " - By: " + username;
        holder.tvSnippet.setText(snippetText);

        // 3. Logika Klik Judul
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailArticleActivity.class);

                // KIRIM DATA KE DETAIL
                intent.putExtra("EXTRA_TITLE", title);
                intent.putExtra("EXTRA_CONTENT", content);


                intent.putExtra("EXTRA_AUTHOR", username);
                // --------------------------------

                // Cek status login untuk header di Detail
                if (context instanceof HomeActivity) {
                    intent.putExtra("IS_LOGGED_IN", true);
                } else {
                    intent.putExtra("IS_LOGGED_IN", false);
                }

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (cursor == null) ? 0 : cursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (cursor != null) {
            cursor.close();
        }
        cursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
}