package com.example.doispontosnaprova;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CreateDatabase extends SQLiteOpenHelper {

    public static final String DB_NAME = "contatos.db";
    public static final String ID = "_id";
    public static final String TABLE = "contato";
    public static final String NOME = "nome";
    public static final String EMAIL = "email";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE =  "longitude";

    private static final int VERSION = 1;

    public CreateDatabase(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE + " ( "
                + "_id integer primary key autoincrement, " +
                "nome text,"+
                "email text," +
                "latitude double,"+
                "longitude double"+
        ")";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }
}
