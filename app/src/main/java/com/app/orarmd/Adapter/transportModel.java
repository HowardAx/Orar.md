package com.app.orarmd.Adapter;

public class transportModel {

    public String transport;
    public String aproximativ;
    public String urmatorul;
    public String ora;
    public String pret;
    public String tip;
    public String statia;





    public transportModel(String transport, String aproximativ, String urmatorul, String ora, String pret, String tip, String statia) {
        this.transport = transport;
        this.aproximativ = aproximativ;
        this.urmatorul = urmatorul;
        this.ora = ora;
        this.pret = pret;
        this.tip = tip;
        this.statia = statia;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getStatia() {
        return statia;
    }

    public void setStatia(String statia) {
        this.statia = statia;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getAproximativ() {
        return aproximativ;
    }

    public void setAproximativ(String aproximativ) {
        this.aproximativ = aproximativ;
    }

    public String getUrmatorul() {
        return urmatorul;
    }

    public void setUrmatorul(String urmatorul) {
        this.urmatorul = urmatorul;
    }

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    public String getPret() {
        return pret;
    }

    public void setPret(String pret) {
        this.pret = pret;
    }



    public transportModel() {

    }

}
