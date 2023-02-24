package com.example.appsesion.contract;

import static com.example.appsesion.BD.UsuariosDBService.TABLE_PASS;
import static com.example.appsesion.BD.UsuariosDBService.TABLE_USRS;

import android.content.ContentValues;
import android.provider.BaseColumns;

import com.example.appsesion.json.MyData;
import com.example.appsesion.json.MyInfo;

import java.io.Serializable;

public class UsuariosContract implements Serializable {
    public static final String TAG = "UsuariosContract";

    public static abstract class UsuarioEntry implements BaseColumns{
        public static final String USUARIO = "usuario";

        public static final String getCreateTable( ){
            String table = "CREATE TABLE " + TABLE_USRS + "(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "usuario TEXT NOT NULL UNIQUE," +
                    "psswd TEXT NOT NULL," +
                    "email TEXT NOT NULL," +
                    "box1 TEXT," +
                    "box2 TEXT," +
                    "box3 TEXT," +
                    "gen INTEGER," +
                    "fecha TEXT," +
                    "cel TEXT," +
                    "lug TEXT" +
                    ")";
            return table;
        }
        public static ContentValues toContentValues(MyInfo info){
            ContentValues values = new ContentValues();
            values.put("usuario", info.getUsuario());
            values.put("psswd", info.getPassword());
            values.put("email", info.getCorreo());
            values.put("box1", info.getBox1());
            values.put("box2", info.getBox2());
            values.put("box3", info.getBox3());
            values.put("gen", info.getGen());
            values.put("fecha", info.getFecha());
            values.put("cel", info.getCel());
            values.put("lug", info.getLugar());
            return values;
        }
    }

    public abstract static class MyDataEntry implements BaseColumns{
        public static final String getCreateTable( )
        {
            String table ="CREATE TABLE "+TABLE_PASS+"(" +
                    "id_pass INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "pass TEXT NOT NULL," +
                    "user_p TEXT NOT NULL," +
                    "id INTEGER NOT NULL)";
            return table;
        }
        public static ContentValues toContentValues(MyData myData)
        {
            ContentValues values = new ContentValues();
            values.put("pass", myData.getPass());
            values.put("user_p", myData.getUsuario());
            values.put("id", myData.getId_usr());

            return values;
        }
    }
}
