package com.example.appsesion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.PatternsCompat;

import android.content.Intent;
import android.media.metrics.Event;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appsesion.json.MyInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Registro extends AppCompatActivity {

    private Button registrarbttn;

    private static final String TAG = "MainActivity";
    public static final String archivo = "archivo.json";

    EditText contra;
    boolean contraVisible;

    String json = null;
    String usr = null;
    String password=null;
    String email = null;


    public static boolean activado;
    public static List<MyInfo> list =new ArrayList<MyInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Spinner spinner = findViewById(R.id.spinnerlugares);
        String [] lugares = {"Despliegue","Estado de México","Ciudad de México","Otro estado"};




        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, lugares);
        spinner.setAdapter(adapter);
        registrarbttn = findViewById(R.id.registrarbttn);
        Button loginbttn = findViewById(R.id.loginbttn);
        EditText usuario = findViewById(R.id.usuario);
        EditText contrasena = findViewById(R.id.contrasena);
        EditText mail = findViewById(R.id.email);
        EditText num = findViewById(R.id.numtel);
        EditText fechanac = findViewById(R.id.fechanac);
        RadioButton r1 = findViewById(R.id.radioButton1);
        RadioButton r2 = findViewById(R.id.radioButton2);
        CheckBox box1 = findViewById(R.id.checkBox1);
        CheckBox box2 = findViewById(R.id.checkBox2);
        CheckBox box3 = findViewById(R.id.checkBox3);
        Read();
        json2List(json);
        loginbttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registro.this, Login.class);
                startActivity(intent);
            }
        });
        registrarbttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyInfo info= new MyInfo();

                usr = String.valueOf(usuario.getText());
                password = String.valueOf(contrasena.getText());
                email = String.valueOf(mail.getText());

                String[] box = new String[3];

                if(box1.isChecked()==true){
                    box[0]="Email";
                }else{
                    box[0]="no";
                }
                if(box2.isChecked()==true){
                    box[1]="Teléfono";
                }else{
                    box[1]="no";
                }
                if(box3.isChecked()==true){
                    box[2]="Redes Sociales";
                }else{
                    box[2]="no";
                }
                if(r1.isChecked()==true){
                    activado=true;
                }
                if(r2.isChecked()==true){
                    activado=false;
                }

                if(usr.equals("")||password.equals("")||email.equals("")){
                    Log.d(TAG,"Campos vacios");
                    Log.d(TAG,usr);
                    Log.d(TAG,password);
                    Log.d(TAG,email);
                    Toast.makeText(getApplicationContext(), "Completa los campos", Toast.LENGTH_LONG).show();

                }else{
                    if(validarEmail(email)){
                        if(list.isEmpty()){
                            Log.d(TAG,"Lleno");
                            info.setUsuario(usr);
                            info.setPassword(Digest.bytesToHex(Digest.createSha1(String.valueOf(contrasena.getText()))));
                            info.setCel(String.valueOf(num.getText()));
                            info.setFecha(String.valueOf(fechanac.getText()));
                            info.setMedios(box);
                            info.setCorreo(String.valueOf(mail.getText()));
                            info.setLugar(spinner.getSelectedItem().toString());
                            info.setGenero(activado);
                            List2Json(info,list);
                        }else{
                            if(usuarios(list,usr)){
                                Log.d(TAG,"Usuario ya existente");
                                Toast.makeText(getApplicationContext(), "El nombre de usuario ya existe", Toast.LENGTH_LONG).show();
                            }else{
                                info.setUsuario(usr);
                                info.setPassword(Digest.bytesToHex(Digest.createSha1(String.valueOf(contrasena.getText()))));
                                info.setCel(String.valueOf(num.getText()));
                                info.setFecha(String.valueOf(fechanac.getText()));
                                info.setMedios(box);
                                info.setCorreo(String.valueOf(mail.getText()));
                                info.setLugar(spinner.getSelectedItem().toString());
                                info.setGenero(activado);
                                List2Json(info,list);
                            }
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Introduzca un correo válido", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

    }
    public boolean validarEmail(String email){
        boolean bandera;
        if(email.isEmpty()){
            bandera=false;
        }else{
            if(PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()){
                bandera=true;
            }else{
                bandera=false;
            }
        }
        return bandera;
    }
    public boolean usuarios(List<MyInfo> list,String usr){
        boolean bandera = false;
        for(MyInfo informacion : list){
            if(informacion.getUsuario().equals(usr)){
                bandera=true;
            }
        }
        return bandera;
    }
    public void Objet2Json(MyInfo info){
        Gson gson =null;
        String json= null;
        String mensaje = null;
        gson =new Gson();
        json = gson.toJson(info);
        if (json != null) {
            Log.d(TAG, json);
            mensaje = "object2Json OK";
        } else {
            mensaje = "Error object2Json";
        }
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
    }
    public void List2Json(MyInfo info,List<MyInfo> list){
        Gson gson =null;
        String json= null;
        gson =new Gson();
        list.add(info);
        json =gson.toJson(list, ArrayList.class);
        if (json == null)
        {
            Log.d(TAG, "Error json");
        }
        else
        {
            Log.d(TAG, json);
            writeFile(json);
        }
        Toast.makeText(getApplicationContext(), "Guardado", Toast.LENGTH_LONG).show();
    }
    private boolean writeFile(String text){
        File file =null;
        FileOutputStream fileOutputStream =null;
        try{
            file=getFile();
            fileOutputStream = new FileOutputStream( file );
            fileOutputStream.write( text.getBytes(StandardCharsets.UTF_8) );
            fileOutputStream.close();
            Log.d(TAG, "Datos:");
            return true;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }
    private File getFile(){
        return new File(getDataDir(),archivo);
    }
    public boolean Read(){
        if(!isFileExits()){
            return false;
        }
        File file = getFile();
        FileInputStream fileInputStream = null;
        byte[] bytes = null;
        bytes = new byte[(int)file.length()];
        try {
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);
            json=new String(bytes);
            Log.d(TAG,json);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
    private boolean isFileExits( )
    {
        File file = getFile( );
        if( file == null )
        {
            return false;
        }
        return file.isFile() && file.exists();
    }
    public void json2List( String json )
    {
        Gson gson = null;
        String datos = null;
        if (json == null || json.length() == 0)
        {
            Toast.makeText(getApplicationContext(), "Error, json nulo o vacío", Toast.LENGTH_LONG).show();
            return;
        }
        gson = new Gson();
        Type listType = new TypeToken<ArrayList<MyInfo>>(){}.getType();
        list = gson.fromJson(json, listType);
        if (list == null || list.size() == 0 )
        {
            Toast.makeText(getApplicationContext(), "Error, json nulo o vacío", Toast.LENGTH_LONG).show();
            return;
        }
    }

}
