package com.app.orarmd.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.orarmd.R;

import java.util.ArrayList;

public class transportAdapter extends BaseAdapter {

    ArrayList<transportModel> transportModelsArr;
    Activity activity;
    public LayoutInflater inflater;
    public Context context;
    public transportAdapter(Activity activity, ArrayList<transportModel> transportModelsArr) {
        this.activity = activity;
        this.transportModelsArr = transportModelsArr;
    }

    @Override
    public int getCount() {
        return this.transportModelsArr.size();
    }

    @Override
    public Object getItem(int i) {
        return this.transportModelsArr.get(i);
    }

    @Override
    public long getItemId(int i) {
        return (long)i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {



        if (inflater == null) {
            inflater = this.activity.getLayoutInflater();
        }

        if (view == null) {
            view = inflater.inflate(R.layout.list_items, null);
        }
        final transportModel TransportModel = this.transportModelsArr.get(i);
        TextView pret = view.findViewById(R.id.pretTransport);
        TextView aproximat = view.findViewById(R.id.aproximat);
        TextView urmTransport = view.findViewById(R.id.urmTransport);
        TextView nrTransport = view.findViewById(R.id.nrTransport);
        TextView oraTransport = view.findViewById(R.id.oraTransport);
        ImageView tipTransport = view.findViewById(R.id.tipTransport);


        pret.setText(TransportModel.getPret());
        aproximat.setText(TransportModel.getAproximativ());
        urmTransport.setText("Următorul transport în " + TransportModel.getUrmatorul() + " min");
        nrTransport.setText(TransportModel.getTransport());
        oraTransport.setText(TransportModel.getOra());
        if(TransportModel.getTip()=="tr"){
            tipTransport.setImageResource(R.drawable.ic_trolley);
        }
        if(TransportModel.getTip()=="aut"){
            tipTransport.setImageResource(R.drawable.ic_bus2);
        }




        return view;
    }
}
