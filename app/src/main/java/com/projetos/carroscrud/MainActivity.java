package com.projetos.carroscrud;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private Button adicionar;
    private EditText marca;
    private EditText modelo;
    private EditText ano;
    private Button pesquisar;
    private EditText placa;
    private Button listar;
    private ArrayList<Carros> listaCarros = new ArrayList<>();
    SQLiteDatabase bancodedados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //COLOCANDO AS IDS DOS COMPONENTES DA ACTIVITY
        adicionar = findViewById(R.id.btnAdicionar);
        ano = findViewById(R.id.idAno);
        marca = findViewById(R.id.idMarca);
        modelo = findViewById(R.id.idModelo);
        placa = findViewById(R.id.idPlaca);
        listar = findViewById(R.id.btnListar);

        //CRIANDO O BANCO DE DADOS
        bancodedados = openOrCreateDatabase("carros", MODE_PRIVATE, null);
        //CRIANDO A TABELA
        bancodedados.execSQL("CREATE TABLE IF NOT EXISTS carros (id INTEGER PRIMARY KEY AUTOINCREMENT, ano VARCHAR, modelo VARCHAR, marca VARCHAR, placa VARCHAR) ");


        placa.setFilters( new InputFilter[] {new InputFilter.AllCaps ()});
        SimpleMaskFormatter simpleMaskPlaca= new SimpleMaskFormatter("AAA-NNNN");
        MaskTextWatcher maskMatricula = new MaskTextWatcher(placa, simpleMaskPlaca);
        placa.addTextChangedListener(maskMatricula);


        adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    String Ano = ano.getText().toString();
                    String Modelo = modelo.getText().toString();
                    String Marca = marca.getText().toString();
                    String Placa = placa.getText().toString();


                    //CONDIÇÃO PARA VERIFICAR SE HÁ CAMPOS NÃO PREENCHIDOS
                    if(Ano.isEmpty() || Modelo.isEmpty() || Marca.isEmpty() || Placa.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Preencha todos os campos", Toast.LENGTH_LONG).show();
                    } else{

                        // COLETANDO DADOS
                        ContentValues values = new ContentValues();
                        values.put("marca", marca.getText().toString());
                        values.put("modelo", modelo.getText().toString());
                        values.put("ano", ano.getText().toString());
                        values.put("placa", placa.getText().toString());

                        //INSERINDO DADOS NO BANCO DE DADOS
                        bancodedados.insert("carros", null, values);

                        //APRESENTANDO MENSAGEM DE SUCESSO
                        // Limpa os campos
                        limpaCampos();
                        showMessage("Successo", "Veículo incluido com sucesso!");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Ocorreu algum erro com o banco de dados...", Toast.LENGTH_LONG).show();
                }
            }
        });

        listar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CARREGANDO LISTA COM METODO DE CARREGAR DADOS
                carregarDados();

                Intent i = new Intent(getApplicationContext(), TelaListar.class);
                Bundle b = new Bundle();
                //CRIANDO UMA SERIALIZABLE PARA OUTRA TELA
                b.putSerializable("listaCarros", listaCarros);
                i.putExtras(b);


                //INICIANDO A NOVA TELA
                startActivity(i);
            }
        });

    }


    //LIMPA CAMPOS
    public void limpaCampos(){
        marca.setText("");
        modelo.setText("");
        ano.setText("");
        placa.setText("");

        // fecha o teclado virtual
        ((InputMethodManager) MainActivity.this.getSystemService(
                Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                getCurrentFocus().getWindowToken(), 0);
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("Fechar", null);
        //builder.setIcon(R.drawable.dizzi);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void carregarDados(){
        listaCarros.clear();
        Cursor c = bancodedados.rawQuery("SELECT * FROM carros ORDER BY modelo ASC", null);
        while (c.moveToNext()){
            listaCarros.add(new Carros(c.getString(c.getColumnIndex("modelo")),
                    c.getString(c.getColumnIndex("marca")),
                    c.getString(c.getColumnIndex("placa")),
                    c.getString(c.getColumnIndex("ano"))));
        }
    }
}
