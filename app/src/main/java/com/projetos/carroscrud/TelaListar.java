package com.projetos.carroscrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class TelaListar extends AppCompatActivity {

    private Button home;
    private String modelo;
    private String marca;
    private String ano;
    private String carro;
    private String placa;
    private ListView lista;
    ArrayList<Carros> listaCarros = new ArrayList<>();
    ArrayAdapter<Carros> adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_listar);


        home = findViewById(R.id.btnHome);
        lista = findViewById(R.id.lstLista);

        Intent i = getIntent();
        Bundle b = i.getExtras();

        listaCarros = (ArrayList<Carros>) b.getSerializable("listaCarros");

        //adaptador = new ArrayAdapter<>(getApplication(), android.R.layout.simple_list_item_1, listaCarros);
        adaptador = new CarroAdapter(getApplicationContext(), listaCarros);

        lista.setAdapter(adaptador);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Carros c = (Carros) lista.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), TelaPesquisa.class);

                Bundle b = new Bundle();

                b.putSerializable("dados", c);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

}
