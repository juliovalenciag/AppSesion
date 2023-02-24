package com.example.appsesion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.widget.ListView;
import android.widget.Toast;

import com.example.appsesion.BD.DbPass;
import com.example.appsesion.MyAdapter.MyAdapter;
import com.example.appsesion.MyDesUtil.MyDesUtil;
import com.example.appsesion.json.MyData;
import com.example.appsesion.json.MyInfo;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Principal extends AppCompatActivity {
    public MyDesUtil myDesUtil = new MyDesUtil().addStringKeyBase64(Registro.KEY);
    private List<MyInfo> list;
    public static String TAG = "mensaje";
    public static String json = null;
    private ListView listView;
    private List<MyData> listo;
    String aux;
    public boolean bandera = false;
    public int pos = 0;
    public static MyInfo myInfo = null;
    EditText editText, editText1;
    Button button, button1, button2;

    MyData data = new MyData();
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Object object = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.getExtras() != null) {
                object = intent.getExtras().get("Objeto");
                if (object != null) {
                    if (object instanceof MyInfo) {
                        myInfo = (MyInfo) object;
                    }
                }
            }
        }
/*
        list = new ArrayList<>();
        list = Login.list;

 */
        editText = findViewById(R.id.editTextUsr);
        editText1 = findViewById(R.id.editTextContra);
        button = findViewById(R.id.buttonEl);
        button1 = findViewById(R.id.buttonEd);
        button2 = findViewById(R.id.buttonAd);
        listView = (ListView) findViewById(R.id.listViewId);

        DbPass dbContras = new DbPass(Principal.this);

        listo = dbContras.getPass(myInfo.getId_usr());

        /*
        listo = new ArrayList<MyData>();
        listo = myInfo.getContras();

         */
        MyAdapter myAdapter = new MyAdapter(listo, getBaseContext());
        listView.setAdapter(myAdapter);
        button.setEnabled(false);
        button1.setEnabled(false);
        if (listo==null) {
            Toast.makeText(getApplicationContext(), "Para agregar contraseñas, de clic en el menú o en el (+)", Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "Llene los campos", Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), String.valueOf(myInfo.getId_usr()), Toast.LENGTH_LONG).show();
        }
        Toast.makeText(getApplicationContext(), "Da click en las contraseñas para editar o eliminar", Toast.LENGTH_LONG).show();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                data = listo.get(i);
                editText.setText(data.getUsuario());
                editText1.setText(data.getPass());
                pos = i;
                button.setEnabled(true);
                button1.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Guarde Cambios", Toast.LENGTH_LONG).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbPass dbPass = new DbPass(Principal.this);
                boolean id = dbPass.deletePass(myInfo.getId_usr(),data.getUsuario(),data.getPass());
                if(id){
                    listo = dbPass.getPass(myInfo.getId_usr());
                    MyAdapter myAdapter = new MyAdapter(listo, getBaseContext());
                    listView.setAdapter(myAdapter);
                    editText.setText("");
                    editText1.setText("");
                    Toast.makeText(getApplicationContext(), "Eliminado", Toast.LENGTH_LONG).show();
                    button.setEnabled(false);
                    button1.setEnabled(false);
                } else{
                    Toast.makeText(getApplicationContext(), "Error al eliminar", Toast.LENGTH_LONG).show();
                }
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usr = String.valueOf(editText.getText());
                String pass = String.valueOf(editText1.getText());
                if (usr.equals("") || pass.equals("")) {
                    Toast.makeText(getApplicationContext(), "Complete los campos", Toast.LENGTH_LONG).show();
                } else {
                    DbPass dbPass = new DbPass(Principal.this);
                    boolean id=dbPass.AlterPass(usr,pass,myInfo.getId_usr(),data.getId_pass());

                    if(id) {
                        listo = dbPass.getPass(myInfo.getId_usr());
                        /*
                        listo.get(pos).setUsuario(usr);
                        listo.get(pos).setPass(pass);

                        myInfo.setContras(listo);
                         */
                        MyAdapter myAdapter = new MyAdapter(listo, getBaseContext());
                        listView.setAdapter(myAdapter);
                        editText.setText("");
                        editText1.setText("");
                        Toast.makeText(getApplicationContext(), "Se editó la contraseña", Toast.LENGTH_LONG).show();
                        button.setEnabled(false);
                        button1.setEnabled(false);
                    } else {
                        Toast.makeText(getApplicationContext(), "Error, intentelo de nuevo", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usr = String.valueOf(editText.getText());
                String pass = String.valueOf(editText1.getText());
                if (usr.equals("") || pass.equals("")) {
                    Toast.makeText(getApplicationContext(), "Completa los campos", Toast.LENGTH_LONG).show();
                } else {
                    MyData myData = new MyData();
                    myData.setPass(pass);
                    myData.setUsuario(usr);
                    myData.setId_usr(myInfo.getId_usr());
                    Toast.makeText(getApplicationContext(), String.valueOf(myInfo.getId_usr()), Toast.LENGTH_LONG).show();
                    DbPass dbPass = new DbPass(Principal.this);
                    long id=dbContras.savePass(myData);
                    if (id > 0) {
                    /*
                    listo.add(myData);

                     */
                        listo=dbPass.getPass(myInfo.getId_usr());
                        MyAdapter myAdapter = new MyAdapter(listo, getBaseContext());
                        listView.setAdapter(myAdapter);
                        editText.setText("");
                        editText1.setText("");
                        Toast.makeText(getApplicationContext(), myData.getUsuario() + " " + myData.getPass(), Toast.LENGTH_LONG).show();
                        Toast.makeText(Principal.this, "Guardado",Toast.LENGTH_LONG).show();
                    } else{
                        Toast.makeText(Principal.this, "Error al guardar",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu Principal) {
        boolean flag = false;
        flag = super.onCreateOptionsMenu(Principal);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, Principal);
        return flag;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item1) {
            String usr = String.valueOf(editText.getText());
            String pass = String.valueOf(editText1.getText());
            if (usr.equals("") || pass.equals("")) {
                Toast.makeText(getApplicationContext(), "Complete los campos", Toast.LENGTH_LONG).show();
            } else {
                MyData myData = new MyData();
                myData.setPass(pass);
                myData.setUsuario(usr);
                myData.setId_usr(myInfo.getId_usr());
                Toast.makeText(getApplicationContext(), String.valueOf(myInfo.getId_usr()), Toast.LENGTH_LONG).show();
                DbPass dbPass = new DbPass(Principal.this);
                    long p = dbPass.savePass(myData);
                if ( p > 0 ) {
                    /*
                    listo.add(myData);

                     */
                    listo=dbPass.getPass(myInfo.getId_usr());
                    MyAdapter myAdapter = new MyAdapter(listo, getBaseContext());
                    listView.setAdapter(myAdapter);
                    editText.setText("");
                    editText1.setText("");
                    Toast.makeText(getApplicationContext(), myData.getUsuario()+" "+myData.getPass(), Toast.LENGTH_LONG).show();
                    Toast.makeText(Principal.this, "Guardado",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(Principal.this, "Error al guardar, puede que ya este registrado el usuario",Toast.LENGTH_LONG).show();
                }
            }
            return true;
        }
        if (id == R.id.item2) {
            int i = 0;
            for (MyInfo inf : list) {
                if (myInfo.getUsuario().equals(inf.getUsuario())) {
                    list.get(i).setContras(listo);
                }
                i++;
            }
            /*List2Json(myInfo, list);*/
            return true;
        }
        if (id == R.id.item3) {
            Intent intent = new Intent(Principal.this, Login.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.itemF){
            Intent intent = new Intent(Principal.this,FootBallApi.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    public void List2Json(MyInfo info, List<MyInfo> list) {
        Gson gson = null;
        String json = null;
        gson = new Gson();
        json = gson.toJson(list, ArrayList.class);
        if (json == null) {
            Log.d(TAG, "Error json");
        } else {
            Log.d(TAG, json);
            json = myDesUtil.cifrar(json);
            Log.d(TAG, json);
            writeFile(json);
        }
        Toast.makeText(getApplicationContext(), "Guardado", Toast.LENGTH_LONG).show();
    }

    private boolean writeFile(String text) {
        File file = null;
        FileOutputStream fileOutputStream = null;
        try {
            file = getFile();
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(text.getBytes(StandardCharsets.UTF_8));
            fileOutputStream.close();
            Log.d(TAG, "Hola");
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private File getFile() {
        return new File(getDataDir(), Registro.archivo);
    }

     */
}




