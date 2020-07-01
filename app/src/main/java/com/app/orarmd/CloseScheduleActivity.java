package com.app.orarmd;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.orarmd.Adapter.transportAdapter;
import com.app.orarmd.Adapter.transportModel;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CloseScheduleActivity extends AppCompatActivity {
    private int currentApiVersion;
    TextView statia;
    ListView listatransport;
    ImageView faqImg;
    TextView faqTxt;
    TextView currentTimeGlobal;
    DBaccess dBaccess;
    ArrayList<transportModel> transportModelArr;
    transportAdapter adapter;
    String ora = "";
    BroadcastReceiver _broadcastReceiver;
    private final SimpleDateFormat _sdfWatchTime = new SimpleDateFormat("HH:mm");

    @Override
    public void onStart() {
        super.onStart();
        _broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context ctx, Intent intent) {
                if (intent.getAction().compareTo(Intent.ACTION_TIME_TICK) == 0)
                    currentTimeGlobal.setText("Ora: " + _sdfWatchTime.format(new Date()));
                String formatH = _sdfWatchTime.format(new Date()).substring(0, _sdfWatchTime.format(new Date()).indexOf(":"));
                String formatM = _sdfWatchTime.format(new Date()).substring(_sdfWatchTime.format(new Date()).indexOf(":") + 1);


                for (int i = 0; i < transportModelArr.size(); i++) {
                    String ora = transportModelArr.get(i).getOra().substring(0, transportModelArr.get(i).getOra().indexOf(":")) +
                            transportModelArr.get(i).getOra().substring(transportModelArr.get(i).getOra().indexOf(":") + 1);

                    if(formatH.equals("00")){
                        transportModelArr.get(i).
                                setAproximativ(Integer.toString(60*(Integer.parseInt(ora)/100) + Integer.parseInt(ora)%100 - Integer.parseInt(formatM)));
                    } else{


                    if ((Integer.parseInt(ora) / 100 - Integer.parseInt(formatH)) == 1) {
                        transportModelArr.get(i).
                                setAproximativ(Integer.toString(60 - Integer.parseInt(formatM) + Integer.parseInt(ora) % 100));
                    } else if ((Integer.parseInt(ora) / 100 - Integer.parseInt(formatH)) > 1) {

                        transportModelArr.get(i).
                                setAproximativ(Integer.toString(
                                        60 - Integer.parseInt(formatM) + Integer.parseInt(ora) % 100 + 60 *
                                        (Integer.parseInt(ora) / 100 - Integer.parseInt(formatH))
                                ));
                    } else if ((Integer.parseInt(ora) / 100 - Integer.parseInt(formatH)) == 0) {
                        transportModelArr.get(i).
                                setAproximativ(Integer.toString(Integer.parseInt(ora) - Integer.parseInt(formatH + formatM)));
                    } else if ((Integer.parseInt(ora) / 100 - Integer.parseInt(formatH)) < 0) {
                        transportModelArr.get(i).
                                setAproximativ(Integer.toString(
                                        (23 - Integer.parseInt(formatH) + Integer.parseInt(ora) / 100) * 60 +
                                                60 - Integer.parseInt(formatM) + Integer.parseInt(ora) % 100
                                ));
                    }
                    }

                    transportModelArr.get(i).setUrmatorul(Integer.toString(Integer.parseInt(transportModelArr.get(i).getUrmatorul()) - 1));

                    if (transportModelArr.get(i).getOra().equals(_sdfWatchTime.format(new Date()))) {
                        dBaccess.open();
                        String oraNoua;


                        if (transportModelArr.get(i).getTip().equals("tr")) {
                            oraNoua = dBaccess.getCloserTransport(formatH + formatM,
                                    "Tr_" + transportModelArr.get(i).getTransport(),
                                    transportModelArr.get(i).getStatia());
                        } else {
                            oraNoua = dBaccess.getCloserTransport(formatH + formatM,
                                    "Aut_" + transportModelArr.get(i).getTransport(),
                                    transportModelArr.get(i).getStatia());
                        }
                        if (oraNoua.equals("")) {
                            if (transportModelArr.get(i).getTip().equals("tr")) {
                                oraNoua = dBaccess.getCloserTransport("0",
                                        "Tr_" + transportModelArr.get(i).getTransport(),
                                        transportModelArr.get(i).getStatia());
                            } else {
                                oraNoua = dBaccess.getCloserTransport("0",
                                        "Aut_" + transportModelArr.get(i).getTransport(),
                                        transportModelArr.get(i).getStatia());
                            }
                        }
                        String oraNoua2;
                        String ora_2;
                        if(!oraNoua.contains(":")){
                            if (transportModelArr.get(i).getTip().equals("tr")) {
                                oraNoua2 = dBaccess.getCloserTransport("0",
                                        "Tr_" + transportModelArr.get(i).getTransport(),
                                        transportModelArr.get(i).getStatia());
                            } else {
                                oraNoua2 = dBaccess.getCloserTransport("0",
                                        "Aut_" + transportModelArr.get(i).getTransport(),
                                        transportModelArr.get(i).getStatia());
                            }
                            ora = oraNoua;
                            ora_2 = oraNoua2.substring(0, oraNoua2.indexOf(":"));
                        } else {
                        ora = oraNoua.substring(0, oraNoua.indexOf(":"));
                        ora_2 = oraNoua.substring(oraNoua.indexOf(":") + 1);
                        }
                        int oraAfis = Integer.parseInt(ora);
                        if (oraAfis / 100 < 10 && oraAfis % 100 < 10) {
                            transportModelArr.get(i).setOra("0" + oraAfis / 100 + ":" + "0" + oraAfis % 100);
                        }
                        if (oraAfis / 100 < 10 && oraAfis % 100 >= 10) {
                            transportModelArr.get(i).setOra("0" + oraAfis / 100 + ":" + oraAfis % 100);
                        }
                        if (oraAfis / 100 >= 10 && oraAfis % 100 < 10) {
                            transportModelArr.get(i).setOra(oraAfis / 100 + ":" + "0" + oraAfis % 100);
                        }
                        if (oraAfis / 100 >= 10 && oraAfis % 100 >= 10) {
                            transportModelArr.get(i).setOra(oraAfis / 100 + ":" + oraAfis % 100);
                        }

                        int time = Integer.parseInt(ora) - Integer.parseInt(formatH + formatM);
                        transportModelArr.get(i).setUrmatorul(Integer.toString(Integer.parseInt(ora_2) - Integer.parseInt(formatH + formatM)));

                        if ((Integer.parseInt(ora) / 100 - Integer.parseInt(formatH)) == 1) {
                            transportModelArr.get(i).
                                    setAproximativ(Integer.toString(60 - Integer.parseInt(formatM) + Integer.parseInt(ora) % 100));
                        } else if ((Integer.parseInt(ora) / 100 - Integer.parseInt(formatH)) > 1) {
                            transportModelArr.get(i).
                                    setAproximativ(Integer.toString(
                                            60 - Integer.parseInt(formatM) + Integer.parseInt(ora) % 100) + 60 *
                                            (Integer.parseInt(ora) / 100 - Integer.parseInt(formatH))
                                    );
                        } else if ((Integer.parseInt(ora) / 100 - Integer.parseInt(formatH)) == 0) {
                            transportModelArr.get(i).
                                    setAproximativ(Integer.toString(Integer.parseInt(ora) - Integer.parseInt(formatH + formatM)));
                        } else if ((Integer.parseInt(ora) / 100 - Integer.parseInt(formatH)) < 0) {
                            transportModelArr.get(i).
                                    setAproximativ(Integer.toString(
                                            (23 - Integer.parseInt(formatH) + Integer.parseInt(ora) / 100) * 60 +
                                                    60 - Integer.parseInt(formatM) + Integer.parseInt(ora) % 100
                                    ));
                        }

                        if ((Integer.parseInt(ora_2) / 100 - Integer.parseInt(formatH)) == 1) {
                            transportModelArr.get(i).
                                    setUrmatorul(Integer.toString(60 - Integer.parseInt(formatM) + Integer.parseInt(ora_2) % 100));
                        } else if ((Integer.parseInt(ora_2) / 100 - Integer.parseInt(formatH)) > 1) {
                            transportModelArr.get(i).
                                    setUrmatorul(Integer.toString(
                                            60 - Integer.parseInt(formatM) + Integer.parseInt(ora_2) % 100 + 60 *
                                                    (Integer.parseInt(ora_2) / 100 - Integer.parseInt(formatH)))
                                    );
                        } else if ((Integer.parseInt(ora_2) / 100 - Integer.parseInt(formatH)) == 0) {
                            transportModelArr.get(i).
                                    setUrmatorul(Integer.toString(Integer.parseInt(ora_2) - Integer.parseInt(formatH + formatM)));
                        } else if ((Integer.parseInt(ora_2) / 100 - Integer.parseInt(formatH)) < 0) {
                            transportModelArr.get(i).
                                    setUrmatorul(Integer.toString(
                                            (23 - Integer.parseInt(formatH) + Integer.parseInt(ora_2) / 100) * 60 +
                                                    60 - Integer.parseInt(formatM) + Integer.parseInt(ora_2) % 100
                                    ));
                        }
                        dBaccess.close();
                    }

                }
                adapter.notifyDataSetChanged();

            }
        };

        registerReceiver(_broadcastReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
    }

    @Override
    public void onStop() {
        super.onStop();
        if (_broadcastReceiver != null)
            unregisterReceiver(_broadcastReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close_schedule);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        currentApiVersion = android.os.Build.VERSION.SDK_INT;


        final int flags =
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        // This work only for android 4.4+
        if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {

            getWindow().getDecorView().setSystemUiVisibility(flags);


            // Code below is to handle presses of Volume up or Volume down.
            // Without this, after pressing volume buttons, the navigation bar will
            // show up and won't hide
            final View decorView = getWindow().getDecorView();
            decorView
                    .setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {

                        @Override
                        public void onSystemUiVisibilityChange(int visibility) {
                            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                                decorView.setSystemUiVisibility(flags);
                            }
                        }
                    });
        }
        transportModelArr = new ArrayList<>();
        faqImg = findViewById(R.id.faqImg);
        faqTxt = findViewById(R.id.faqText);
        adapter = new transportAdapter(CloseScheduleActivity.this, transportModelArr);
        dBaccess = DBaccess.getInstance(getApplicationContext());
        dBaccess.open();
        currentTimeGlobal = findViewById(R.id.currentTime);

        Calendar rightNow = Calendar.getInstance();
        int currentHourIn24Format = rightNow.get(Calendar.HOUR_OF_DAY);
        int currentMinutes = rightNow.get(Calendar.MINUTE);
        String currTime = "";
        String currTime2 = "";

        if (currentHourIn24Format < 10) {
            currTime2 += "0" + currentHourIn24Format;
        } else {
            currTime2 += currentHourIn24Format;
        }
        currTime += currentHourIn24Format;


        if (currentMinutes < 10) {
            currTime += "0" + currentMinutes;
            currTime2 += ":" + "0" + currentMinutes;

        } else {
            currTime += currentMinutes;
            currTime2 += ":" + currentMinutes;
        }
        currentTimeGlobal.setText("Ora: " + currTime2);
        Intent intent = getIntent();

        String titlu = intent.getStringExtra("Statia");
        statia = findViewById(R.id.textviewStatia);
        statia.setText(titlu);
        String aux = intent.getStringExtra("Rute");
        listatransport = findViewById(R.id.transportList);
        String trol = "";
        String auto = "";
        String micro = "";
        String statia = "";
        String tr = "Tr_";

        String aut = "Aut_";
        Boolean check = false;


        transportModelArr.clear();
        if (!(aux == null)) {
            char[] aux_arr = aux.toCharArray();
            int i = 0;
            statia = "L_";
            for (int j = 0; j < aux_arr.length; j++) {
                transportModel tm = new transportModel();
                if (aux_arr[j] == '+') {
                    i++; /*alexandrul1*/
                } else if (i == 0) {
                    statia = statia + aux_arr[j];
                } else if (i == 1) {
                    if (aux_arr[j] != ',' && aux_arr[j] != ' ') {
                        tr += aux_arr[j];
                    } else {
                        dBaccess.open();
                        check = true;
                        Log.e("INFORMATIE", currTime + "\n" + tr + "\n" + statia);
                        tm.setTip("tr");
                        tm.setStatia(statia);
                        tm.setPret("2 lei");

                        tm.setTransport(tr.substring(tr.indexOf("_") + 1));

                        String raw_ora = dBaccess.getCloserTransport(currTime, tr, statia);
                        String raw_ora2;
                        String ora_2;
                        boolean dimineata = false;
                        if (raw_ora.equals("")) {
                            raw_ora = dBaccess.getCloserTransport("0", tr, statia);
                            dimineata = true;
                        }
                        if (!raw_ora.contains(":")) {
                                raw_ora2 = dBaccess.getCloserTransport("0", tr, statia);
                                ora = raw_ora;
                                ora_2 = raw_ora2.substring(0, raw_ora2.indexOf(":"));
                        } else {
                            ora = raw_ora.substring(0, raw_ora.indexOf(":"));
                            ora_2 = raw_ora.substring(raw_ora.indexOf(":") + 1);
                        }


                        dBaccess.close();


                        int oraAfis = Integer.parseInt(ora);
                        if (oraAfis / 100 < 10 && oraAfis % 100 < 10) {
                            tm.setOra("0" + oraAfis / 100 + ":" + "0" + oraAfis % 100);
                        }
                        if (oraAfis / 100 < 10 && oraAfis % 100 >= 10) {
                            tm.setOra("0" + oraAfis / 100 + ":" + oraAfis % 100);
                        }
                        if (oraAfis / 100 >= 10 && oraAfis % 100 < 10) {
                            tm.setOra(oraAfis / 100 + ":" + "0" + oraAfis % 100);
                        }
                        if (oraAfis / 100 >= 10 && oraAfis % 100 >= 10) {
                            tm.setOra(oraAfis / 100 + ":" + oraAfis % 100);
                        }

                        int time = Integer.parseInt(ora) - Integer.parseInt(currTime);
                        if(Integer.parseInt(currTime)/100 == 0){
                            tm.setAproximativ(Integer.toString(60*(Integer.parseInt(ora)/100) + (Integer.parseInt(ora)%100 - Integer.parseInt(currTime)%100)));
                            tm.setUrmatorul(Integer.toString(60*(Integer.parseInt(ora_2)/100) + (Integer.parseInt(ora_2)%100 - Integer.parseInt(currTime)%100)));
                        } else{
                        if ((Integer.parseInt(ora) / 100 - Integer.parseInt(currTime) / 100) == 1) {

                            tm.setAproximativ(Integer.toString(60 - Integer.parseInt(currTime) % 100 + Integer.parseInt(ora) % 100));
                        } else if ((Integer.parseInt(ora) / 100 - Integer.parseInt(currTime) / 100) > 1) {
                            tm.setAproximativ(Integer.toString(60 - Integer.parseInt(currTime) % 100 + Integer.parseInt(ora) % 100 +
                                    60 * (Integer.parseInt(ora) / 100 - Integer.parseInt(currTime) / 100)));
                        } else if ((Integer.parseInt(ora) / 100 - Integer.parseInt(currTime) / 100) == 0) {
                            tm.setAproximativ(Integer.toString(time));
                        }

                        if ((Integer.parseInt(ora_2) / 100 - Integer.parseInt(currTime) / 100) == 1) {
                            tm.setUrmatorul(Integer.toString(60 - Integer.parseInt(currTime) % 100 + Integer.parseInt(ora_2) % 100));
                        } else if ((Integer.parseInt(ora_2) / 100 - Integer.parseInt(currTime) / 100) > 1) {
                            tm.setUrmatorul(Integer.toString(60 - Integer.parseInt(currTime) % 100 + Integer.parseInt(ora_2) % 100 +
                                    (Integer.parseInt(ora_2) / 100 - Integer.parseInt(currTime) / 100) * 60));
                        } else if ((Integer.parseInt(ora_2) / 100 - Integer.parseInt(currTime) / 100) == 0) {
                            tm.setUrmatorul(Integer.toString(Integer.parseInt(ora_2) - Integer.parseInt(currTime)));
                        }   else if ((Integer.parseInt(ora_2) / 100 - Integer.parseInt(currTime) / 100) < 0) {
                            tm.setUrmatorul(Integer.toString(
                                    (23 - Integer.parseInt(currTime) / 100 + Integer.parseInt(ora_2) / 100) * 60 +
                                            60 - Integer.parseInt(currTime) % 100 + Integer.parseInt(ora_2) % 100
                            ));
                        }
                        }
                        if (dimineata) {
                            tm.setAproximativ(Integer.toString((23 - Integer.parseInt(currTime) / 100 + Integer.parseInt(ora) / 100) * 60
                                    + 60 - Integer.parseInt(currTime) % 100 + Integer.parseInt(ora) % 100));
                            tm.setUrmatorul(Integer.toString((23 - Integer.parseInt(currTime) / 100 + Integer.parseInt(ora_2) / 100) * 60
                                    + 60 - Integer.parseInt(currTime) % 100 + Integer.parseInt(ora_2) % 100));
                        }


                        tr = "Tr_";

                      /*  int ora2 =  Integer.parseInt(ora);
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

                        trol+= " " + ora;*/
                        transportModelArr.add(tm);
                    }


                    trol = trol + aux_arr[j];
                    if (check) {
                        trol += "\n";
                        check = false;

                    }


                } else if (i == 2) {
                    dBaccess.open();
                    if (aux_arr[j] != ',' && aux_arr[j] != ' ') {
                        aut += aux_arr[j];
                    } else {
                        check = true;
                        Boolean dimineata = false;
                        Log.e("INFORMATIE", currTime + "\n" + aut + "\n" + statia);
                        tm.setStatia(statia);
                        String raw_ora = dBaccess.getCloserTransport(currTime, aut, statia);
                        String raw_ora2;
                        String ora_2;
                        if (raw_ora.equals("")) {
                            raw_ora = dBaccess.getCloserTransport("0", aut, statia);
                            dimineata = true;
                        }
                        if (!raw_ora.contains(":")) {
                            raw_ora2 = dBaccess.getCloserTransport("0", aut, statia);
                            ora = raw_ora;
                            ora_2 = raw_ora2.substring(0, raw_ora2.indexOf(":"));
                        } else {
                            ora = raw_ora.substring(0, raw_ora.indexOf(":"));
                            ora_2 = raw_ora.substring(raw_ora.indexOf(":") + 1);
                        }

                        tm.setTip("aut");
                        tm.setPret("3 lei");
                        tm.setTransport(aut.substring(aut.indexOf("_") + 1));
                        aut = "Aut_";
                        int oraAfis = Integer.parseInt(ora);
                        if (oraAfis / 100 < 10 && oraAfis % 100 < 10) {
                            tm.setOra("0" + oraAfis / 100 + ":" + "0" + oraAfis % 100);
                        }
                        if (oraAfis / 100 < 10 && oraAfis % 100 >= 10) {
                            tm.setOra("0" + oraAfis / 100 + ":" + oraAfis % 100);
                        }
                        if (oraAfis / 100 >= 10 && oraAfis % 100 < 10) {
                            tm.setOra(oraAfis / 100 + ":" + "0" + oraAfis % 100);
                        }
                        if (oraAfis / 100 >= 10 && oraAfis % 100 >= 10) {
                            tm.setOra(oraAfis / 100 + ":" + oraAfis % 100);
                        }


                        int time = Integer.parseInt(ora) - Integer.parseInt(currTime);

                        if(Integer.parseInt(currTime)/100 == 0){
                            tm.setAproximativ(Integer.toString(60*(Integer.parseInt(ora)/100) + (Integer.parseInt(ora)%100 - Integer.parseInt(currTime)%100)));
                            tm.setUrmatorul(Integer.toString(60*(Integer.parseInt(ora_2)/100) + (Integer.parseInt(ora_2)%100 - Integer.parseInt(currTime)%100)));
                        } else{

                        if ((Integer.parseInt(ora) / 100 - Integer.parseInt(currTime) / 100) == 1) {
                            tm.setAproximativ(Integer.toString(60 - Integer.parseInt(currTime) % 100 + Integer.parseInt(ora) % 100));
                        } else if ((Integer.parseInt(ora) / 100 - Integer.parseInt(currTime) / 100) > 1) {
                            tm.setAproximativ(Integer.toString(60 - Integer.parseInt(currTime) % 100 + Integer.parseInt(ora) % 100 +
                                    60 * (Integer.parseInt(ora) / 100 - Integer.parseInt(currTime) / 100)));
                        } else if ((Integer.parseInt(ora) / 100 - Integer.parseInt(currTime) / 100) == 0) {
                            tm.setAproximativ(Integer.toString(time));
                        }
                        if ((Integer.parseInt(ora_2) / 100 - Integer.parseInt(currTime) / 100) == 1) {
                            tm.setUrmatorul(Integer.toString(60 - Integer.parseInt(currTime) % 100 + Integer.parseInt(ora_2) % 100));
                        } else if ((Integer.parseInt(ora_2) / 100 - Integer.parseInt(currTime) / 100) > 1) {
                            tm.setUrmatorul(Integer.toString(60 - Integer.parseInt(currTime) % 100 + Integer.parseInt(ora_2) % 100 +
                                    (Integer.parseInt(ora_2) / 100 - Integer.parseInt(currTime) / 100) * 60));
                        } else if ((Integer.parseInt(ora_2) / 100 - Integer.parseInt(currTime) / 100) == 0) {
                            tm.setUrmatorul(Integer.toString(Integer.parseInt(ora_2) - Integer.parseInt(currTime)));
                        } else if ((Integer.parseInt(ora_2) / 100 - Integer.parseInt(currTime) / 100) < 0) {
                            tm.setUrmatorul(Integer.toString(
                                    (23 - Integer.parseInt(currTime) / 100 + Integer.parseInt(ora_2) / 100) * 60 +
                                            60 - Integer.parseInt(currTime) % 100 + Integer.parseInt(ora_2) % 100
                            ));
                        }
                        }

                        if (dimineata) {
                            tm.setAproximativ(Integer.toString((23 - Integer.parseInt(currTime) / 100 + Integer.parseInt(ora) / 100) * 60
                                    + 60 - Integer.parseInt(currTime) % 100 + Integer.parseInt(ora) % 100));
                            tm.setUrmatorul(Integer.toString((23 - Integer.parseInt(currTime) / 100 + Integer.parseInt(ora_2) / 100) * 60
                                    + 60 - Integer.parseInt(currTime) % 100 + Integer.parseInt(ora_2) % 100));
                        }


                        int ora2 = Integer.parseInt(ora);
                        /*int hh = 0, mm = 0;

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

                        auto+= " " + ora;*/
                        transportModelArr.add(tm);
                    }


                    auto = auto + aux_arr[j];
                    if (check) {
                        auto += "\n";
                        check = false;
                    }


                } else {
                    micro = micro + aux_arr[j];
                }
            }

        }


        listatransport.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        dBaccess.close();

    faqImg.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent toFaq = new Intent(CloseScheduleActivity.this, FAQ.class);
            startActivity(toFaq);
        }
    });

    faqTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toFaq = new Intent(CloseScheduleActivity.this, FAQ.class);
                startActivity(toFaq);
            }
        });


    }






    @SuppressLint("NewApi")
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (currentApiVersion >= Build.VERSION_CODES.KITKAT && hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }


}
