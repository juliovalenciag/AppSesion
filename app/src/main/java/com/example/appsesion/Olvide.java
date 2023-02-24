package com.example.appsesion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appsesion.BD.DbUsuarios;
import com.example.appsesion.MyDesUtil.MyDesUtil;
import com.example.appsesion.json.MyInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Olvide extends AppCompatActivity {

    public static List<MyInfo> list;
    public static String json = null;
    public static String TAG = "mensaje";
    public static String TOG = "error";
    public static String cadena = null;
    public MyDesUtil myDesUtil = new MyDesUtil().addStringKeyBase64(Registro.KEY);
    public String usr = null;
    public String correo,mensaje;
    EditText usuario,email;
    Button buttonre, buttonlog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvide);

        usuario = findViewById(R.id.usr);
        email = findViewById(R.id.mail);
        buttonlog = findViewById(R.id.buttonlogin);
        buttonre = findViewById(R.id.buttonpr);

        DbUsuarios dbUsuarios = new DbUsuarios(Olvide.this);

        buttonlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Olvide.this, Login.class);
                startActivity(intent);
            }
        });

        buttonre.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                usr = String.valueOf(usuario.getText());
                correo = String.valueOf(email.getText());
                MyInfo User = dbUsuarios.GetUsuario(usr,correo);
                if(usr.equals("")&&email.equals("")){
                    Toast.makeText(getApplicationContext(), "Llene los campos", Toast.LENGTH_LONG).show();
                }else{
                    if (User == null){
                        Toast.makeText(getApplicationContext(), "El registro no existe, intente de nuevo", Toast.LENGTH_SHORT).show();
                    } else {
                            correo = User.getCorreo();
                            String contra = User.getPassword();
                            String nueva = String.format("%d",(int)(Math.random()*1000));
                            mensaje="<html><body><h1>Solicitud de recuperación de contraseña</h1><br><h2>Su contraseña pasada: "+ contra +"</h2><br><h2> Su <b>nueva</b> contraseña es: "+ nueva +"</h2></body></html>";
                            correo = myDesUtil.cifrar(correo);
                            mensaje = myDesUtil.cifrar(mensaje);

                            boolean f = dbUsuarios.AlterUser(usr, nueva);

                            if (f){
                                if (sendInfo(correo,mensaje)){
                                    Toast.makeText(getApplicationContext(), "Se envió su contraseña", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Error al enviar", Toast.LENGTH_SHORT).show();
                            }

                        }

                    }

                }
            });

    }
    public boolean sendInfo( String correo ,String mensaje)
    {
        JsonObjectRequest jsonObjectRequest = null;
        JSONObject jsonObject = null;
        String url = "https://us-central1-nemidesarrollo.cloudfunctions.net/envio_correo";
        RequestQueue requestQueue = null;
        if( correo == null || correo.length() == 0 )
        {
            return false;
        }
        jsonObject = new JSONObject( );
        try
        {
            jsonObject.put("correo" , correo );
            jsonObject.put("mensaje", mensaje);
            String hola = jsonObject.toString();
            Log.i(TAG,hola);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                Log.i(TAG, response.toString());
            }
        } , new  Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e  (TOG, error.toString());
            }
        } );
        requestQueue = Volley.newRequestQueue( getBaseContext() );
        requestQueue.add(jsonObjectRequest);

        return true;
    }
}