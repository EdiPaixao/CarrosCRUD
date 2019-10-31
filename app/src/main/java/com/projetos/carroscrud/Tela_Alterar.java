package com.projetos.carroscrud;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


public class Tela_Alterar extends Activity {

    private String placaCarro;
    private Button cancelar;
    private Button alterar;
    private EditText modelo;
    private EditText ano;
    private Spinner marcas;
    private ArrayAdapter<String> adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela__alterar);

        cancelar = findViewById(R.id.btnCancelar);
        alterar = findViewById(R.id.btnTAtualizar);
        modelo = findViewById(R.id.idModelo);
        ano = findViewById(R.id.idAno);
        marcas = findViewById(R.id.spnAtualizaMarca);

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
        String Marca = marcas.getSelectedItem().toString();
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
        int posicao = 0;
        String textoMarca = "";
        try {
            //CRIANDO OU ACESSANDO O BANCO DE DADOS
            SQLiteDatabase bancodedados = openOrCreateDatabase("carros", MODE_PRIVATE, null);

            //RECUPERANDO OS DADOS
            Cursor cursor = bancodedados.rawQuery("SELECT * FROM carros WHERE placa = '" + placaCarro + "' ", null);

            //CONFIGURAÇÃO DA SPINNER
            adaptador = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_text, listagemMarcas());
            adaptador.setDropDownViewResource(R.layout.simple_spinner_dropdown);
            marcas.setAdapter(adaptador);


            int indiceColunaAno = cursor.getColumnIndex("ano");
            int indiceColunaModelo = cursor.getColumnIndex("modelo");
            int indiceColunaMarca = cursor.getColumnIndex("marca");

            cursor.moveToFirst();

            while (cursor != null) {
                textoMarca = cursor.getString(indiceColunaMarca);
                //PESQUISANDO A POSIÇÃO DA ARRAY PARA SETAR POSITION NO SPINNER
                for(int i=0; (i <= listagemMarcas().size()-1) ; i++){

                    if(listagemMarcas().get(i).equals(textoMarca)){

                        posicao = i;
                        break;
                    }else{
                        posicao = 0;
                    }
                }
                marcas.setSelection(posicao);
                modelo.setText(cursor.getString(indiceColunaModelo));
                ano.setText(cursor.getString(indiceColunaAno));
                cursor.moveToNext();
            }
            cursor.close();

        } catch (Exception e) {
        }
    }

    public ArrayList<String> listagemMarcas(){
        ArrayList<String> lista = new ArrayList<>();

        lista.add("Audi");
        lista.add("BMW");
        lista.add("Cherry");
        lista.add("Chevrolet");
        lista.add("Citroen");
        lista.add("Dodge");
        lista.add("Ferrari");
        lista.add("Fiat");
        lista.add("Ford");
        lista.add("Hyundai");
        lista.add("Jac");
        lista.add("Jeep");
        lista.add("Peugeot");
        lista.add("Renault");
        lista.add("Mitsubish");
        lista.add("Suzuki");
        lista.add("Toyota");
        lista.add("Volkswagem");

        return lista;
    }
}