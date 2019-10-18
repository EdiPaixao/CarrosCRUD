package com.projetos.carroscrud;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CarroAdapter extends ArrayAdapter<Carros> {


     private final Context context;
     private final ArrayList<Carros> elementos;

     public CarroAdapter(Context context, ArrayList<Carros> elementos){
         super(context, R.layout.lista, elementos);
         this.context = context;
         this.elementos = elementos;
     }

     public View getView(int position, View convertView, ViewGroup parent){
         LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         View rowView = inflater.inflate(R.layout.listacarros, parent, false);

         TextView modelo = rowView.findViewById(R.id.txtListaModelo);
         TextView placa = rowView.findViewById(R.id.txtListaPlaca);

         modelo.setText("Modelo: " + elementos.get(position).getModelo());
         placa.setText("Placa: " + elementos.get(position).getPlaca());

         return rowView;
     }
}
