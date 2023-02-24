package com.example.appsesion.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.appsesion.contract.UsuariosContract;
import com.example.appsesion.json.MyData;

import java.util.ArrayList;
import java.util.List;

public class DbPass extends UsuariosDBService {
    Context context;
    public DbPass(@Nullable Context context) {
        super(context);
        this.context=context;
    }

    public long savePass(MyData myData){
        long id = 0;
        try{
            UsuariosDBService usuariosDBService = new UsuariosDBService(context);
            SQLiteDatabase db =usuariosDBService.getWritableDatabase();

            ContentValues values= new ContentValues();
            id = db.insert(TABLE_PASS,null, UsuariosContract.MyDataEntry.toContentValues(myData));

        }catch(Exception ex){
            ex.toString();
        }
        finally{
            return id;
        }

    }

    public List<MyData> getPass(int id )
    {
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        List<MyData>contras = null;
        MyData myData = null;
        sqLiteDatabase = getReadableDatabase();
        cursor = sqLiteDatabase.rawQuery("SELECT*FROM " + TABLE_PASS +" WHERE id = "+id,null);
        if( cursor == null )
        {
            return new ArrayList<MyData>();
        }
        if( cursor.getCount() < 1)
        {
            return new ArrayList<MyData>();
        }
        if( !cursor.moveToFirst() )
        {
            return new ArrayList<MyData>();
        }
        Log.d(TAG, "" + cursor.getCount());
        contras = new ArrayList<MyData>( );
        for( int i = 0; i < cursor.getCount(); i++)
        {
            myData = new MyData( );
            myData.setId_pass(cursor.getInt(0));
            myData.setPass(cursor.getString(1));
            myData.setUsuario(cursor.getString(2));
            myData.setId_usr(cursor.getInt(3));
            contras.add(myData);
            cursor.moveToNext( );
        }
        Log.d("Contraseñas",contras.toString());
        return contras;
    }
    public boolean AlterPass(String up,String pass,int id,int id_pass){
        boolean ok = false;
        UsuariosDBService usuariosDBService = new UsuariosDBService(context);
        SQLiteDatabase db =usuariosDBService.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("pass", pass);
        values.put("user_p", up);

        try{
            db.execSQL("UPDATE " + TABLE_PASS + " SET pass = '" + pass + "', user_p = '" + up + "' WHERE id = '" + id + "' AND id_pass = '" +id_pass+ "'");
            ok = true;
        }catch(Exception ex){
            ex.toString();
            ok=false;
        } finally {
            db.close();
        }
        return ok;
    }

    public boolean deletePass(int id,String up,String pass) {

        boolean ok = false;

        UsuariosDBService dbHelper = new UsuariosDBService(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_PASS + " WHERE id = '" + id + "' AND pass ='" + pass + "' AND user_p = '" + up + "'");
            ok = true;
        } catch (Exception ex) {
            ex.toString();
            ok = false;
        } finally {
            db.close();
        }
        return ok;
    }

    public void AlterPassW(String up,String pass,int id,int id_pass){
        UsuariosDBService usuariosDBService = new UsuariosDBService(context);
        SQLiteDatabase db =usuariosDBService.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("pass",pass);
        values.put("user_p",up);
    }
}
