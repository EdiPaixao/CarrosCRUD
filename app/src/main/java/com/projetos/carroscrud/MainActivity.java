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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private Button adicionar;
    private EditText modelo;
    private EditText ano;
    private Spinner marcas;
    private EditText placa;
    private Button listar;

    private ArrayList<Carros> listaCarros = new ArrayList<>();
    private ArrayAdapter<String> adaptador;
    SQLiteDatabase bancodedados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //COLOCANDO AS IDS DOS COMPONENTES DA ACTIVITY
        adicionar = findViewById(R.id.btnAdicionar);
        ano = findViewById(R.id.idAno);
        modelo = findViewById(R.id.idModelo);
        placa = findViewById(R.id.idPlaca);
        listar = findViewById(R.id.btnListar);
        marcas = findViewById(R.id.spnMarcas);

        //CRIANDO O BANCO DE DADOS
        bancodedados = openOrCreateDatabase("carros", MODE_PRIVATE, null);
        //CRIANDO A TABELA
        bancodedados.execSQL("CREATE TABLE IF NOT EXISTS carros (id INTEGER PRIMARY KEY AUTOINCREMENT, ano VARCHAR, modelo VARCHAR, marca VARCHAR, placa VARCHAR) ");

        //SETAR DADOS NA SPINNER
        adaptador = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_text, listagemMarcas());
        adaptador.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        marcas.setAdapter(adaptador);


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
                    String Marca = marcas.getSelectedItem().toString();
                    String Placa = placa.getText().toString();


                    //CONDIÇÃO PARA VERIFICAR SE HÁ CAMPOS NÃO PREENCHIDOS
                    if(Ano.isEmpty() || Modelo.isEmpty() || Marca.isEmpty() || Placa.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Preencha todos os campos", Toast.LENGTH_LONG).show();
                    } else{

                        // COLETANDO DADOS
                        ContentValues values = new ContentValues();
                        values.put("marca", marcas.getSelectedItem().toString());
                        values.put("modelo", modelo.getText().toString());
                        values.put("ano", ano.getText().toString());
                        values.put("placa", placa.getText().toString());

                        //INSERINDO DADOS NO BANCO DE DADOS
                        bancodedados.insert("carros", null, values);

                        //APRESENTANDO MENSAGEM DE SUCESSO
                        // Limpa os campos
                        limpaCampos();
                        showMessage("Sucesso", "Veículo incluido com sucesso!");
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
