package com.app.orarmd.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.app.orarmd.MapsActivity;
import com.app.orarmd.OrarCirculatie;
import com.app.orarmd.R;
import com.appolica.interactiveinfowindow.InfoWindow;
import com.appolica.interactiveinfowindow.fragment.MapInfoWindowFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class InfoAdapter implements GoogleMap.InfoWindowAdapter {

    private  final View mWindow;
    private Context mContext;

    public InfoAdapter(Context context) {
        mContext = context;
        mWindow = LayoutInflater.from(mContext).inflate(R.layout.custominfo, null);
    }

    private void renderWindow(Marker marker, View view){



/*
        String title = marker.getTitle();
        String aux = marker.getSnippet();

        String trol = "";
        String auto = "";
        String micro = "";

        TextView infotitle = view.findViewById(R.id.titleinfo);
        TextView troleibus = view.findViewById(R.id.trol);
        TextView autobus = view.findViewById(R.id.auto);
        TextView microbus = view.findViewById(R.id.micro);
        if(!title.equals("")){
            infotitle.setText(title);
        }
        if(!(aux == null)) {
            char[] aux_arr = aux.toCharArray();
            int i = 0;

                for (int j = 0; j < aux_arr.length; j++) {
                    if (aux_arr[j] == '+') {
                        i++;

                    } else if (i == 0) {
                        trol = trol + aux_arr[j];
                    } else if (i == 1) {
                        auto = auto + aux_arr[j];
                    } else {
                        micro = micro + aux_arr[j];
                    }
                }

        }
        troleibus.setText(trol);
        autobus.setText(auto);
        microbus.setText(micro);*/
    }

    @Override
    public View getInfoWindow(Marker marker) {
        renderWindow(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        renderWindow(marker, mWindow);
        return mWindow;
    }
}
