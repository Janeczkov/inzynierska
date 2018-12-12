package com.apka.inzynierska;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final Button mainguestb=(Button) findViewById(R.id.mainguestb);
        final Button mainloginb=(Button) findViewById(R.id.mainloginb);
        final Button mainregisterb=(Button) findViewById(R.id.mainregisterb);
        /*
        final EditText imie=(EditText) findViewById(R.id.imie);
        final EditText nazwisko=(EditText) findViewById(R.id.nazwisko);
        final EditText oceny=(EditText) findViewById(R.id.oceny);
        final TextView avgEdit=(TextView) findViewById(R.id.AvgTextViev);
        final TextView avgText=(TextView) findViewById(R.id.avgTextViev);*/
        Intent intent=getIntent();

        final Intent atguest=new Intent(MainActivity.this,typszkoly.class);
        final Intent atlogin=new Intent(MainActivity.this,logowanie.class);
        final Intent atregister=new Intent(MainActivity.this,rejestracja.class);

        /*avgEdit.setVisibility(View.INVISIBLE);
        avgText.setVisibility(View.INVISIBLE);
        String avg=intent.getStringExtra("avg");
        if(avg!=null){
            avgEdit.setVisibility(View.VISIBLE);
            avgText.setVisibility(View.VISIBLE);
            avgEdit.setText(avg);
        }
        bocen.setVisibility(View.INVISIBLE);





        TextWatcher obsluga_tekstu= new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String sImie=imie.getText().toString();
                String sNazwisko=nazwisko.getText().toString();
                String sIlosc=oceny.getText().toString();
                if(sImie.isEmpty()||sIlosc.isEmpty()||sNazwisko.isEmpty()){
                    bocen.setVisibility(View.INVISIBLE);
                }
                else
                if(Integer.parseInt(sIlosc)>=5&&Integer.parseInt(sIlosc)<=15) bocen.setVisibility(View.VISIBLE);
                else bocen.setVisibility(View.INVISIBLE);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        oceny.addTextChangedListener(obsluga_tekstu);
        imie.addTextChangedListener(obsluga_tekstu);
        nazwisko.addTextChangedListener(obsluga_tekstu);
        */
        mainguestb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //target.putExtra("ilosc",oceny.getText().toString());
                MainActivity.this.startActivity(atguest);
            }


        /*final Toast grzanka=Toast.makeText(this, //kontekst-zazwyczaj referencja do Activity
                "heck", //napis do wyświetlenia
                Toast.LENGTH_SHORT);



        this.przycisk = (Button) this.findViewById(R.id.bocen);

        przycisk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //przycisk.setText("ha");
                grzanka.show();
            }
            */

        });

        mainloginb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //target.putExtra("ilosc",oceny.getText().toString());
                MainActivity.this.startActivity(atlogin);
            }
        });
        mainregisterb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //target.putExtra("ilosc",oceny.getText().toString());
                MainActivity.this.startActivity(atregister);
            }
        });
    }



    /*View.OnClickListener obsluga_klikniecia = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            przycisk.setText("Hejka");
        }
    };*/

    //na poczatku dodac odwolania do buttonow i tekstow aby bylo latwiej wpisywac zamiast this.findviewbyid, dalej trzeba dodac sprawdzanie tekstu ifem
    //czy np pole jest puste i czy ma znaki, jak za malo to ukrywac przycisk setVisibility i to robie w 2 funkcji text watchera
}