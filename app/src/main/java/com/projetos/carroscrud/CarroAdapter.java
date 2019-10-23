package com.projetos.carroscrud;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CarroAdapter extends ArrayAdapter<Carros> {


     private final Context context;
     private final ArrayList<Carros> elementos;

     public CarroAdapter(Context context, ArrayList<Carros> elementos){
         super(context, R.layout.listacarros, elementos);
         this.context = context;
         this.elementos = elementos;
     }

     public View getView(int position, View convertView, ViewGroup parent){
         LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         View rowView = inflater.inflate(R.layout.listacarros, parent, false);

         TextView modelo = rowView.findViewById(R.id.txtListaModelo);
         TextView placa = rowView.findViewById(R.id.txtListaPlaca);
         TextView Tmarca = rowView.findViewById(R.id.txtListaMarca);

         //CHAMANDO A LOGO
         ImageView imgLogo = rowView.findViewById(R.id.imgListaLogo);

         //SETANDO IMAGENS NA LISTA
         String marca = elementos.get(position).getMarca();
         if (marca.equals("Fiat")) {
             imgLogo.setImageResource(R.drawable.fiat);
         } else if (marca.equals("Ford")) {
             imgLogo.setImageResource(R.drawable.ford);
         } else if (marca.equals("GM") || marca.equals("Chevrolet")) {
             imgLogo.setImageResource(R.drawable.chevrolet);
         } else if (marca.equals("Volkswagen") || marca.equals("VW")) {
             imgLogo.setImageResource(R.drawable.volkswagen);
         } else if (marca.equals("Toyota")) {
             imgLogo.setImageResource(R.drawable.toyota);
         } else if (marca.equals("Mitsubishi")) {
             imgLogo.setImageResource(R.drawable.mitsubish);
         } else if (marca.equals("Jeep")) {
             imgLogo.setImageResource(R.drawable.jeep);
         } else if (marca.equals("Hyundai")) {
             imgLogo.setImageResource(R.drawable.hyundai);
         } else if (marca.equals("Dodge")) {
             imgLogo.setImageResource(R.drawable.dodge);
         } else if (marca.equals("Audi")) {
             imgLogo.setImageResource(R.drawable.audi);
         } else if (marca.equals("BMW") || marca.equals("bmw")) {
             imgLogo.setImageResource(R.drawable.bmw);
         } else if (marca.equals("Cherry")) {
             imgLogo.setImageResource(R.drawable.chery);
         } else if (marca.equals("Citroen")) {
             imgLogo.setImageResource(R.drawable.citroen);
         } else if (marca.equals("Ferrari")) {
             imgLogo.setImageResource(R.drawable.ferrari);
         } else if (marca.equals("Jac")) {
             imgLogo.setImageResource(R.drawable.jac);
         } else if (marca.equals("Peugeot")) {
             imgLogo.setImageResource(R.drawable.peugeot);
         } else if (marca.equals("Suzuki")) {
             imgLogo.setImageResource(R.drawable.suzuki);
         } else {
             imgLogo.setImageResource(R.drawable.desconhecido);
         }

         Tmarca.setText("Marca: " + elementos.get(position).getMarca());
         modelo.setText("Modelo: " + elementos.get(position).getModelo());
         placa.setText("Placa: " + elementos.get(position).getPlaca());

         return rowView;
     }
}
