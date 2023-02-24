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
import android.widget.Switch;
import android.widget.Toast;

import com.example.appsesion.BD.DbUsuarios;
import com.example.appsesion.MyDesUtil.MyDesUtil;
import com.example.appsesion.json.MyData;
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

public class Registro extends AppCompatActivity  {

    private Button registrarbttn;
    private CheckBox box1, box2, box3;
    private Spinner spinner;
    private RadioButton r1,r2;
    private static final String TAG = "MainActivity";
    public static final String archivo = "archivo.json";
    public static String usr,password,email,numero,fecha,lugar;

    public static String[] box = new String[4];
    EditText contra;
    boolean contraVisible;

    private EditText usuario,pswd,mail,num,fec;
    public static String boxes1;
    public static String boxes2;
    public static String boxes3;

    String json = null;

    public static int on;


    public static List<MyInfo> list =new ArrayList<MyInfo>();


    public static List<MyData> lista;
    public static final String KEY = "+4xij6jQRSBdCymMxweza/uMYo+o0EUg";
    public MyDesUtil myDesUtil= new MyDesUtil().addStringKeyBase64(KEY);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        lista= new ArrayList<>();
        MyData myData=null;

        Spinner spinner = findViewById(R.id.spinnerlugares);
        String [] lugares = {"Despliegue","Estado de México","Ciudad de México","Otro estado"};


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, lugares);
        spinner.setAdapter(adapter);
        registrarbttn = findViewById(R.id.registrarbttn);
        Button loginbttn = findViewById(R.id.loginbttn);
        usuario = findViewById(R.id.usuario);
        pswd = findViewById(R.id.contrasena);
        mail = findViewById(R.id.email);
        num = findViewById(R.id.numtel);
        fec = findViewById(R.id.fechanac);
        r1 = findViewById(R.id.radioButton1);
        r2 = findViewById(R.id.radioButton2);
        box1 = findViewById(R.id.checkBox1);
        box2 = findViewById(R.id.checkBox2);
        box3 = findViewById(R.id.checkBox3);

        /*
        Read();
        json2List(json);

         */
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
                password = String.valueOf(pswd.getText());
                email = String.valueOf(mail.getText());
                numero = String.valueOf(num.getText());
                fecha = String.valueOf(fec.getText());
                lugar = spinner.getSelectedItem().toString();

                String[] box = new String[3];

                if(box1.isChecked()==true){
                    boxes1="Email";
                }else{
                    boxes1="No";
                }
                if(box2.isChecked()==true){
                    boxes2="Teléfono";
                }else{
                    boxes2="No";
                }
                if(box3.isChecked()==true){
                    boxes3="Redes Sociales";
                }else{
                    boxes3="no";
                }
                if(r1.isChecked()==true){
                    on=1;
                }
                if(r2.isChecked()==true){
                    on=1;
                }


                if(usr.equals("")||password.equals("")||email.equals("")){
                    Log.d(TAG,"Campos vacios");
                    Log.d(TAG,usr);
                    Log.d(TAG,password);
                    Log.d(TAG,email);
                    Toast.makeText(getApplicationContext(), "Completa los campos", Toast.LENGTH_LONG).show();

                }else{
                    if(Digest.validarEmail(email)){
                        Digest.fillInfo(info);
                        DbUsuarios dbUsuarios = new DbUsuarios(Registro.this);
                        long id = dbUsuarios.saveUsr(info);
                        /*
                        if(list.isEmpty()){
                            Log.d(TAG,"Lleno");
                            Digest.fillInfo(info);
                            List2Json(info,list);
                        }else{
                            if(Digest.usuarios(list,usr,email)){
                                Log.d(TAG,"Campos ocupados");
                                Toast.makeText(getApplicationContext(), "El usuario o correo ya existe", Toast.LENGTH_LONG).show();
                            }else{
                                Digest.fillInfo(info);

                                info.setContras(lista);
                               List2Json(info,list);
                            }
                        }
                         */
                        if (id > 0){
                            Toast.makeText(Registro.this, "Guardado",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(Registro.this, "Error al guardar",Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Introduzca un correo válido", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

    }
    /*
    public void List2Json(MyInfo info,List<MyInfo> list){
        Gson gson = null;
        String json = null;
        gson = new Gson();
        list.add(info);
        json =gson.toJson(list, ArrayList.class);
        if (json == null)
        {
            Log.d(TAG, "Error json");
        }
        else
        {
            Log.d(TAG, json);
            json=myDesUtil.cifrar(json);
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
            Log.d(TAG, "Hola");
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
            json= myDesUtil.desCifrar(json);
            if(json != null) {
                Log.d(TAG, json);
            }
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
        String mensaje = null;
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
*/

}
