package com.melihcelenk.akilliurunteslimatsistemi;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class HesapYoneticisi {
    private AkilliUrunTeslimatSistemiActivity mActivity;
    private FirebaseAuth mAuth;
    private String mKullaniciAdi;

    public HesapYoneticisi(AkilliUrunTeslimatSistemiActivity activity){
        mActivity = activity;
        mAuth = FirebaseAuth.getInstance();
        mKullaniciAdi = null;
    }
    public void oturumuKapat(){
        FirebaseAuth.getInstance().signOut();
        mActivity.deleteDatabase("BilgiHazinem");
        mActivity.yonlendir(MainActivity.class);
    }
    public void verileriSil(){
        mActivity.deleteDatabase("BilgiHazinem");
    }
    public Boolean girisYapildiMi(){
        return mAuth.getCurrentUser() != null;
    }
    public void kullaniciOlustur(String eposta, String parola, OnCompleteListener<AuthResult> onCompleteListener){
        mAuth.createUserWithEmailAndPassword(eposta, parola).addOnCompleteListener(onCompleteListener);
    }
    public void girisYap(String eposta, String parola, OnCompleteListener<AuthResult> onCompleteListener){
        mAuth.signInWithEmailAndPassword(eposta, parola).addOnCompleteListener(onCompleteListener);
    }
    public String kullaniciEmailiniAl(){
        return mAuth.getCurrentUser().getEmail();
    }

    public void setKullaniciAdi(String kullaniciAdi){
        mKullaniciAdi = kullaniciAdi;
    }
    public String getKullaniciAdi(){
        return mKullaniciAdi;
    }
}

