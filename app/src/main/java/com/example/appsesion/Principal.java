package com.example.appsesion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.appsesion.json.MyInfo;

import java.util.ArrayList;
import java.util.List;

public class Principal extends AppCompatActivity {

    private ListView listView;
    private List<String> list;

    String aux;
    public static MyInfo myInfo = null;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Object object = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);


        Intent intent = getIntent();
        if(intent != null) {
            if (intent.getExtras() != null) {
                object = intent.getExtras().get("Objeto");
                if (object != null) {
                    if (object instanceof MyInfo) {
                        myInfo = (MyInfo) object;
                    }
                }
            }
        }

        listView = (ListView) findViewById(R.id.listViewId);
        list = new ArrayList<String>();
        for( int i = 0; i < 100; i++)
        {
            list.add( String.format( "Contraseña %d" , i + 1 ) );
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.activity_list_view,R.id.editTextContra, list );
        listView.setAdapter(arrayAdapter);






    }
}