package com.example.appsesion.BD;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.appsesion.contract.UsuariosContract;
import com.example.appsesion.json.MyInfo;

import java.util.ArrayList;
import java.util.List;

public class DbUsuarios extends UsuariosDBService{
    Context context;

    public DbUsuarios(@Nullable Context context){
        super(context);
        this.context = context;
    }

    public long saveUsr(MyInfo info){
        long id = 0;
        try{
            UsuariosDBService usuariosDBService = new UsuariosDBService(context);
            SQLiteDatabase db = usuariosDBService.getWritableDatabase();

            ContentValues values = new ContentValues();
            id = db.insert(TABLE_USRS,null, UsuariosContract.UsuarioEntry.toContentValues(info));

        }catch(Exception ex){
            ex.toString();
        }
        finally{
            return id;
        }
    }

    public List<MyInfo> getUsuarios( )
    {
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        List<MyInfo>usuarios = null;
        MyInfo usuario = null;

        sqLiteDatabase = getReadableDatabase();
        cursor = sqLiteDatabase.rawQuery("SELECT*FROM "+TABLE_USRS,null);
        if( cursor == null )
        {
            return null;
        }
        if( cursor.getCount() < 1)
        {
            return null;
        }
        if( !cursor.moveToFirst() )
        {
            return null;
        }
        Log.d(TAG, "" + cursor.getCount());
        usuarios = new ArrayList<MyInfo>( );
        for( int i = 0; i < cursor.getCount(); i++)
        {
            usuario = new MyInfo( );
            usuario.setUsuario(cursor.getString( 0 ));
            usuario.setPassword(cursor.getString(1));
            usuario.setCorreo(cursor.getString(2));
            usuario.setBox1(cursor.getString(3));
            usuario.setBox2(cursor.getString(4));
            usuario.setBox3(cursor.getString(5));
            usuario.setGen(cursor.getInt(6));
            usuario.setFecha(cursor.getString(7));
            usuario.setCel(cursor.getString(8));
            usuario.setLugar(cursor.getString(9));
            usuarios.add(usuario);
            cursor.moveToNext( );
        }
        return usuarios;
    }

    public MyInfo GetUsuario(String usr){
        MyInfo info = new MyInfo();
        UsuariosDBService usuariosDBService = new UsuariosDBService(context);
        SQLiteDatabase db = usuariosDBService.getReadableDatabase();
        Cursor cursor=null;
        String query = "SELECT * FROM table_usuarios WHERE usuario = ?";
        String[] args = {usr};

        cursor = db.rawQuery(query,args);
        if (cursor.moveToFirst()) {
            info.setId_usr(cursor.getInt(0));
            info.setUsuario( cursor.getString( 1 ) );
            info.setPassword(cursor.getString(2));
            info.setCorreo(cursor.getString(3));
            info.setBox1(cursor.getString(4));
            info.setBox2(cursor.getString(5));
            info.setBox3(cursor.getString(6));
            info.setGen(cursor.getInt(7));
            info.setFecha(cursor.getString(8));
            info.setCel(cursor.getString(9));
            info.setLugar(cursor.getString(10));
            return info;
        }

        cursor.close();
        return null;
    }

    public MyInfo GetUsuario(String usr,String Email){
        MyInfo info = new MyInfo();
        UsuariosDBService usuariosDBService = new UsuariosDBService(context);
        SQLiteDatabase db = usuariosDBService.getReadableDatabase();
        Cursor cursor = null;
        String query = "SELECT * FROM table_usuarios WHERE usuario = ? AND email = ?";
        String[] args = {usr,Email};

        cursor = db.rawQuery(query,args);
        if (cursor.moveToFirst()) {
            info.setId_usr(cursor.getInt(0));
            info.setUsuario( cursor.getString( 1 ) );
            info.setPassword(cursor.getString(2));
            info.setCorreo(cursor.getString(3));
            info.setBox1(cursor.getString(4));
            info.setBox2(cursor.getString(5));
            info.setBox3(cursor.getString(6));
            info.setGen(cursor.getInt(7));
            info.setFecha(cursor.getString(8));
            info.setCel(cursor.getString(9));
            info.setLugar(cursor.getString(10));
            return info;
        }

        cursor.close();
        return null;
    }

    public boolean AlterUser(String usr,String pass){
        boolean ok = false;
        UsuariosDBService usuariosDBService = new UsuariosDBService(context);
        SQLiteDatabase db =usuariosDBService.getWritableDatabase();
        try{
            db.execSQL("UPDATE " + TABLE_USRS + " SET psswd = '" + pass + "' WHERE usuario='" + usr + "'");
            ok = true;
        }catch(Exception ex){
            ex.toString();
        }
        return ok;
    }
}
