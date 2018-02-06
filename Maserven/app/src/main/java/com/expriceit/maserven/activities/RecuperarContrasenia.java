package com.expriceit.maserven.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.expriceit.maserven.LoginActivity;
import com.expriceit.maserven.R;
import com.expriceit.maserven.utils.Utils;

import mehdi.sakout.fancybuttons.FancyButton;

public class RecuperarContrasenia extends Activity {

    FancyButton btn_validar_email;
    ProgressBar load_progreesbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_contrasenia);
        btn_validar_email = (FancyButton) findViewById(R.id.btnInicar);

        btn_validar_email.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
              //  Utils.generarAlerta(LoginActivity.this, "ALERTA!", "Email no válido");

                /* provisional*/
                //load_progreesbar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(RecuperarContrasenia.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}
