package com.example.appsesion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.appsesion.json.MyInfo;

public class Principal extends AppCompatActivity {
    String aux;
    MyInfo myInfo = null;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Object object = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        textView= findViewById(R.id.textViewPrincipal);
        Intent intent = getIntent();
        if(intent != null) {
            if (intent.getExtras() != null) {
                object = intent.getExtras().get("Objeto");
                if (object != null) {
                    if (object instanceof MyInfo) {
                        myInfo = (MyInfo) object;
                        textView.setText("Bienvenid@ a la app, " + myInfo.getUsuario());
                    }
                }
            }
        }
        Button buttonvolverlogin = findViewById(R.id.buttonvolver);

        buttonvolverlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Principal.this, Login.class);
                startActivity(intent);
            }
        });
    }
}