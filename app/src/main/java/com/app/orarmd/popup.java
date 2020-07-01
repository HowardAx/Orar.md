package com.app.orarmd;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import java.util.Calendar;
import java.util.Date;

import android.util.Log;
import android.widget.TextView;

public class popup extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        setContentView(R.layout.popup);

        DBaccess dBaccess = DBaccess.getInstance(getApplicationContext());
        dBaccess.open();

        Calendar rightNow = Calendar.getInstance();
        int currentHourIn24Format = rightNow.get(Calendar.HOUR_OF_DAY);
        int currentMinutes = rightNow.get(Calendar.MINUTE);
        String currTime = "";
        currTime += currentHourIn24Format;

        if(currentMinutes<10){
            currTime += "0" + currentMinutes;
        }else{
            currTime += currentMinutes;
        }


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);


        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        Intent intent = getIntent();

        String titlu = intent.getStringExtra("Statia");
        String aux = intent.getStringExtra("Rute");

        String trol = "";
        String auto = "";
        String micro = "";
        String statia = "";
        String tr  = "Tr_";
        String ora ="";
        String aut = "Aut_";
        Boolean check = false;

        TextView infotitle = findViewById(R.id.titleinfo);
        TextView troleibus = findViewById(R.id.trol);
        TextView autobus = findViewById(R.id.auto);
        TextView microbus = findViewById(R.id.micro);
        if(!titlu.equals("")){
            infotitle.setText(titlu);
        }
        if(!(aux == null)) {
            char[] aux_arr = aux.toCharArray();
            int i = 0;

            for (int j = 0; j < aux_arr.length; j++) {
                if (aux_arr[j] == '+') {
                    i++; /*alexandrul1*/
                } else if (i == 0) {
                    statia = "L_";
                    statia = statia + aux_arr[j];
                } else if (i == 1) {
                    if(aux_arr[j] != ',' && aux_arr[j] != ' '){
                        tr += aux_arr[j];
                    } else {
                        check = true;
                        Log.e("INFORMATIE", currTime + "\n" + tr + "\n" + statia);
                        ora = dBaccess.getCloserTransport(currTime, tr, statia);
                        dBaccess.close();
                        tr = "Tr_";
                        int ora2 =  Integer.parseInt(ora);

                        int hh = 0, mm = 0;

                        hh = ora2 / 100;
                        mm = ora2 % 100;

                        if(hh < 10){
                            ora = "0" + hh + ":";
                        }
                        else {
                            ora = hh + ":";
                        }

                        if(mm < 10){
                            ora += "0" + mm;
                        }
                        else {
                            ora+=mm;
                        }
                        trol+= " " + ora;
                    }




                    trol = trol + " ";
                    if(check){
                        trol += "\n";
                        check=false;
                    }


                } else if (i == 2) {
                    dBaccess.open();
                    if(aux_arr[j] != ',' && aux_arr[j] != ' '){
                        aut += aux_arr[j];
                    } else {
                        check = true;
                        Log.e("INFORMATIE", currTime + "\n" + aut + "\n" + statia);
                        ora = dBaccess.getCloserTransport(currTime, aut, statia);
                        aut = "Aut_";

                            int ora2 =  Integer.parseInt(ora);

                        int hh = 0, mm = 0;

                        hh = ora2 / 100;
                        mm = ora2 % 100;

                        if(hh < 10){
                            ora = "0" + hh + ":" + mm;
                        }
                        else {
                            ora = hh + ":" + mm;
                        }

                        if(mm == 0){
                            ora += "0";
                        }

                        auto+= " " + ora;

                    }




                    auto = auto + " ";
                    if(check){
                        auto += "\n";
                        check=false;
                    }


                } else {
                    micro = micro + aux_arr[j];
                }
            }

        }



        troleibus.setText(trol);
        autobus.setText(auto);
        microbus.setText(micro);
        dBaccess.close();
    }

}
