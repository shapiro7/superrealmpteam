package com.example.newmemeo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.view.ContextThemeWrapper;

import java.util.ArrayList;

public class SQLiteHelper {

    private static final String dbName= "myMemotest2";
    private static final String table1 = "MemoTable";
    private static final int dbVersion = 6;

    private OpenHelper opener;
    private SQLiteDatabase db;

    private Context context;

    public SQLiteHelper(Context context){
        this.context= context;
        this.opener =new OpenHelper(context, dbName,null,dbVersion);
        db= opener.getWritableDatabase();
    }

    private class OpenHelper extends SQLiteOpenHelper{
        public OpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            String create = "CREATE TABLE " + table1 +"(" +
                    "seq integer PRIMARY KEY AUTOINCREMENT," +
                    "maintext text," +
                    "subtext text," +
                    "isdone integer)";
            sqLiteDatabase.execSQL(create);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+table1);
            onCreate(sqLiteDatabase);

        }
    }

    public void insertMemo(Memo memo){
        String sql = "INSERT INTO " + table1 + " VALUES(NULL, '"+memo.miantext+"', '"+memo.subetxt+"',  "+ memo.getIsdone()+");";
        db.execSQL(sql);
    }

    public void deleteMemo(int position){
        String sql = "DELETE FROM "+table1+" WHERE seq = "+position+";";
        db.execSQL(sql);
    }

    public ArrayList<Memo> selectAll(){
        String sql = "SELECT * FROM "+table1;

        ArrayList<Memo> list = new ArrayList<>();

        Cursor results = db.rawQuery(sql,null);
        results.moveToFirst();

        while(!results.isAfterLast()){

            Memo memo = new Memo(results.getInt(0),results.getString(1), results.getString(2),results.getInt(3));
            list.add(memo);
            results.moveToNext();
        }

        results.close();
        return list;
    }

}
