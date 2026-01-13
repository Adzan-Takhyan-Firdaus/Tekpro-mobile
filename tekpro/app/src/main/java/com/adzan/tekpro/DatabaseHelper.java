package com.adzan.tekpro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TekproDB.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "users";
    public static final String COL_USER_ID = "user_id";
    public static final String COL_USERNAME = "username";
    private static final String COL_PASSWORD = "password";

    private static final String TABLE_ARTICLES = "articles";
    public static final String COL_ARTICLE_ID = "article_id";
    public static final String COL_TITLE = "title";
    public static final String COL_CONTENT = "content";
    public static final String COL_IMAGE_PATH = "image_path";
    public static final String COL_AUTHOR_ID = "author_id";
    public static final String COL_CREATED_AT = "created_at";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_USERS + " (" +
                COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT UNIQUE, " +
                COL_PASSWORD + " TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_ARTICLES + " (" +
                COL_ARTICLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TITLE + " TEXT, " +
                COL_CONTENT + " TEXT, " +
                COL_IMAGE_PATH + " TEXT, " +
                COL_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                COL_AUTHOR_ID + " INTEGER, " +
                "FOREIGN KEY(" + COL_AUTHOR_ID + ") REFERENCES " + TABLE_USERS + "(" + COL_USER_ID + "))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    public boolean registerUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_USERNAME, username);
        cv.put(COL_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, cv);
        return result != -1;
    }

    public long checkUserLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        long userId = -1;
        Cursor cursor = db.rawQuery("SELECT " + COL_USER_ID + " FROM " + TABLE_USERS +
                " WHERE " + COL_USERNAME + " = ? AND " + COL_PASSWORD + " = ?", new String[]{username, password});
        if (cursor.moveToFirst()) {
            userId = cursor.getLong(0);
        }
        cursor.close();
        return userId;
    }

    public boolean addArticle(String title, String content, String imagePath, long authorId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_TITLE, title);
        cv.put(COL_CONTENT, content);
        cv.put(COL_IMAGE_PATH, imagePath);
        cv.put(COL_AUTHOR_ID, authorId);
        long result = db.insert(TABLE_ARTICLES, null, cv);
        return result != -1;
    }

    public Cursor getAllArticles() {
        SQLiteDatabase db = this.getReadableDatabase();
        // JOIN UNTUK AMBIL USERNAME
        String query = "SELECT T1.*, T2." + COL_USERNAME +
                " FROM " + TABLE_ARTICLES + " T1 " +
                " INNER JOIN " + TABLE_USERS + " T2 ON T1." + COL_AUTHOR_ID + " = T2." + COL_USER_ID +
                " ORDER BY T1." + COL_CREATED_AT + " DESC";
        return db.rawQuery(query, null);
    }

    public Cursor searchArticles(String title) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT T1.*, T2." + COL_USERNAME +
                " FROM " + TABLE_ARTICLES + " T1 " +
                " INNER JOIN " + TABLE_USERS + " T2 ON T1." + COL_AUTHOR_ID + " = T2." + COL_USER_ID +
                " WHERE T1." + COL_TITLE + " LIKE ? ORDER BY T1." + COL_CREATED_AT + " DESC";
        return db.rawQuery(query, new String[]{"%" + title + "%"});
    }
}