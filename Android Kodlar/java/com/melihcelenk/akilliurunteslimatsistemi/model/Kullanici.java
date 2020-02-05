package com.melihcelenk.akilliurunteslimatsistemi.model;

public class Kullanici {

    public String email;
    public boolean doluMu;
    public boolean otomatikAl;
    public int bakiye;
    public boolean istekVarMi;

    public Kullanici(String email, Boolean doluMu, Boolean otomatikAl, int bakiye, boolean istekVarMi) {
        this.email = email;
        this.doluMu = doluMu;
        this.otomatikAl = otomatikAl;
        this.bakiye = bakiye;
        this.istekVarMi = istekVarMi;
    }
    public Kullanici(){}

}

