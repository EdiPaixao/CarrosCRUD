package com.projetos.carroscrud;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class TelaPesquisa extends Activity {


    private TextView Tmodelo;
    private TextView Tmarca;
    private TextView Tano;
    private Button Bdeletar;
    private Button Balterar;
    private Button Bsair;
    private TextView Tplaca;
    private ImageView imgMarca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_pesquisa);

        Tmarca = findViewById(R.id.txtMarca);
        Tmodelo = findViewById(R.id.txtModelo);
        Tano = findViewById(R.id.txtAno);
        Tplaca = findViewById(R.id.txtPlaca);
        Balterar = findViewById(R.id.btnAlterar);
        Bdeletar = findViewById(R.id.btnDeletar);
        imgMarca = findViewById(R.id.imgMarca);
        Bsair = findViewById(R.id.btnSair);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        final Carros c = (Carros) b.getSerializable("dados");

        Tplaca.setText("Placa: " + c.getPlaca());
        Tmarca.setText("Marca: " + c.getMarca());
        logoMarcaCarro(c.getMarca());
        Tmodelo.setText("Modelo: " + c.getModelo());
        Tano.setText("Ano: " + c.getAno());


        Bdeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //CRIANDO OU ACESSANDO O BANCO DE DADOS
                    SQLiteDatabase bancodedados = openOrCreateDatabase("carros", MODE_PRIVATE, null);


                    bancodedados.execSQL("DELETE FROM carros WHERE placa = '" + c.getPlaca() + "' ");
                    Toast.makeText(getApplicationContext(), "Veiculo excluido", Toast.LENGTH_LONG).show();
                    finish();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("Erro", e.getMessage());
                }
            }
        });

        Balterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("placaCarro", c.getPlaca());
                Intent intent = new Intent(getApplicationContext(), Tela_Alterar.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        Bsair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void logoMarcaCarro(String marca){
        if (marca.equals("Fiat")) {
            imgMarca.setImageResource(R.drawable.fiat);
        } else if (marca.equals("Ford")) {
            imgMarca.setImageResource(R.drawable.ford);
        } else if (marca.equals("GM") || marca.equals("Chevrolet")) {
            imgMarca.setImageResource(R.drawable.chevrolet);
        } else if (marca.equals("Volkswagen") || marca.equals("VW")) {
            imgMarca.setImageResource(R.drawable.volkswagen);
        } else if (marca.equals("Toyota")) {
            imgMarca.setImageResource(R.drawable.toyota);
        } else if (marca.equals("Mitsubishi")) {
            imgMarca.setImageResource(R.drawable.mitsubish);
        } else if (marca.equals("Jeep")) {
            imgMarca.setImageResource(R.drawable.jeep);
        } else if (marca.equals("Hyundai")) {
            imgMarca.setImageResource(R.drawable.hyundai);
        } else if (marca.equals("Dodge")) {
            imgMarca.setImageResource(R.drawable.dodge);
        } else if (marca.equals("Audi")) {
            imgMarca.setImageResource(R.drawable.audi);
        } else if (marca.equals("BMW") || marca.equals("bmw")) {
            imgMarca.setImageResource(R.drawable.bmw);
        } else if (marca.equals("Cherry")) {
            imgMarca.setImageResource(R.drawable.chery);
        } else if (marca.equals("Citroen")) {
            imgMarca.setImageResource(R.drawable.citroen);
        } else if (marca.equals("Ferrari")) {
            imgMarca.setImageResource(R.drawable.ferrari);
        } else if (marca.equals("Jac")) {
            imgMarca.setImageResource(R.drawable.jac);
        } else if (marca.equals("Peugeot")) {
            imgMarca.setImageResource(R.drawable.peugeot);
        } else if (marca.equals("Suzuki")) {
            imgMarca.setImageResource(R.drawable.suzuki);
        } else {
            imgMarca.setImageResource(R.drawable.desconhecido);
        }
    }

}
