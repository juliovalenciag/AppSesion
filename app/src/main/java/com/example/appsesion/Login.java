package com.example.appsesion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appsesion.BD.DbUsuarios;
import com.example.appsesion.MyDesUtil.MyDesUtil;
import com.example.appsesion.json.MyInfo;

import java.util.List;

public class Login extends AppCompatActivity {


    public static final String KEY = "+4xij6jQRSBdCymMxweza/uMYo+o0EUg";
    private String testClaro = "Hola mundo";
    private String testDesCifrado;

    public String correo;
    public String mensaje;

    public  static List<MyInfo> list;
    public static String TAG = "mensaje";
    public static String TOG = "error";
    public static String json = null;
    public static String usr,pswd;

    private Button buttonRegistro, buttonLogin, buttonOlvide;
    public MyDesUtil myDesUtil= new MyDesUtil().addStringKeyBase64(KEY);



    EditText contra;
    boolean contraVisible;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
/*
        contra = findViewById(R.id.contrasenalogin);

        contra.setOnTouchListener(new View.OnTouchListener() {
            @Override

            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right=2;
                if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if (motionEvent.getRawX()>=contra.getRight()-contra.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = contra.getSelectionEnd();
                        if (contraVisible){
                            contra.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_off_24,0);
                            contra.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            contraVisible=false;

                        }
                        else {
                            contra.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_24,0);
                            contra.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            contraVisible=true;
                        }
                        contra.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
*/
        buttonRegistro = findViewById(R.id.buttonEd);
        buttonLogin = findViewById(R.id.buttonpr);
        buttonOlvide = findViewById(R.id.buttonEl);
        EditText usuario = findViewById(R.id.editTextUsr);
        EditText contra = findViewById(R.id.editTextContra);

      /*

        Read();
        json2List(json);

       */

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usr = String.valueOf(usuario.getText());
                pswd = String.valueOf(contra.getText());
                ingresar(usr , pswd);
            }
        });

        buttonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Registro.class);
                startActivity(intent);
            }
        });

        buttonOlvide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Olvide.class);
                startActivity(intent);
            }
        });

    }
    /*
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
            json = new String(bytes);
            json = myDesUtil.desCifrar(json);
            if(json != null) {
                Log.d(TAG, json);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void json2List( String json )
    {
        Gson gson = null;
        String mensaje = null;
        if (json == null || json.length() == 0)
        {
            Toast.makeText(getApplicationContext(), "Error json null or empty", Toast.LENGTH_LONG).show();
            return;
        }
        gson = new Gson();
        Type listType = new TypeToken<ArrayList<MyInfo>>(){}.getType();
        list = gson.fromJson(json, listType);
        if (list == null || list.size() == 0 )
        {
            Toast.makeText(getApplicationContext(), "Error list is null or empty", Toast.LENGTH_LONG).show();
            return;
        }
    }
    private File getFile( )
    {
        return new File( getDataDir() , Registro.archivo );
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

     */
    public void ingresar(String usr , String pswd){
        /*
        int i=0;
        for(MyInfo myInfo : list){
            if(myInfo.getUsuario().equals(usr)&&myInfo.getPassword().equals(pswd)){
                Intent intent = new Intent(Login.this, Principal.class);
                intent.putExtra("Objeto", myInfo);
                startActivity(intent);
                i=1;
            }
        }
        if(i==0){
            Toast.makeText(getApplicationContext(), "El usuario o contraseña son incorrectos", Toast.LENGTH_LONG).show();
        }

         */
        if(usr.equals("")||pswd.equals("")){
            Toast.makeText(getApplicationContext(), "Completa los campos", Toast.LENGTH_LONG).show();
        }else{
            DbUsuarios dbUsuarios = new DbUsuarios(Login.this);
            MyInfo myInfo = dbUsuarios.GetUsuario(usr);
            if(myInfo!=null){
                if(myInfo.getPassword().equals(pswd)){
                    Toast.makeText(getApplicationContext(), "Bienvenido", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Login.this, Principal.class);
                    intent.putExtra("Objeto", myInfo);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Contraseña incorrecta", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(getApplicationContext(), "No se ha encontró el usuario", Toast.LENGTH_LONG).show();
            }
        }
    }
}
