package com.projetos.carroscrud;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Tela_Alterar extends Activity {

    private String placaCarro;
    private Button cancelar;
    private Button alterar;
    private EditText marca;
    private EditText modelo;
    private EditText ano;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela__alterar);

        cancelar = findViewById(R.id.btnCancelar);
        alterar = findViewById(R.id.btnTAtualizar);
        marca = findViewById(R.id.idMarca);
        modelo = findViewById(R.id.idModelo);
        ano = findViewById(R.id.idAno);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        placaCarro = b.getString("placaCarro");

        preenchimentoCarro();


        alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alterarCarro();
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void alterarCarro() {
        String Marca = marca.getText().toString();
        String Modelo = modelo.getText().toString();
        String Ano = ano.getText().toString();

        try {
            //CRIANDO OU ACESSANDO O BANCO DE DADOS
            SQLiteDatabase bancodedados = openOrCreateDatabase("carros", MODE_PRIVATE, null);

            bancodedados.execSQL("UPDATE carros SET marca = '" + Marca + "', modelo = '" + Modelo + "', ano = '" + Ano + "' WHERE placa = '" + placaCarro + "' ");
            Toast.makeText(getApplicationContext(), "Cadastro do veículo atualizado", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } catch (Exception e) {

        }
    }

    public void preenchimentoCarro() {
        try {
            //CRIANDO OU ACESSANDO O BANCO DE DADOS
            SQLiteDatabase bancodedados = openOrCreateDatabase("carros", MODE_PRIVATE, null);

            //RECUPERANDO OS DADOS
            Cursor cursor = bancodedados.rawQuery("SELECT * FROM carros WHERE placa = '" + placaCarro + "' ", null);


            int indiceColunaAno = cursor.getColumnIndex("ano");
            int indiceColunaModelo = cursor.getColumnIndex("modelo");
            int indiceColunaMarca = cursor.getColumnIndex("marca");

            cursor.moveToFirst();

            while (cursor != null) {
                marca.setText(cursor.getString(indiceColunaMarca));
                modelo.setText(cursor.getString(indiceColunaModelo));
                ano.setText(cursor.getString(indiceColunaAno));
                cursor.moveToNext();
            }
            cursor.close();

        } catch (Exception e) {
        }
    }
}