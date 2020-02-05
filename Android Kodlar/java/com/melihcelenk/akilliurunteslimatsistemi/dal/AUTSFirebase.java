package com.melihcelenk.akilliurunteslimatsistemi.dal;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.melihcelenk.akilliurunteslimatsistemi.AkilliUrunTeslimatSistemiActivity;
import com.melihcelenk.akilliurunteslimatsistemi.HesapYoneticisi;
import com.melihcelenk.akilliurunteslimatsistemi.model.Kullanici;

public class AUTSFirebase {

    public DatabaseReference db;
    public AUTSFirebase(){
        db = FirebaseDatabase.getInstance().getReference();
    }

    public void otomatikAlAta(HesapYoneticisi mHy, Boolean deger){
        db.child(mHy.getKullaniciAdi()).child("otomatikAl").setValue(deger);
    }
    public void urunSatinAl(HesapYoneticisi mHy){
        db.child(mHy.getKullaniciAdi()).child("istekVarMi").setValue(true);
    }
//    public void kullaniciEkle(Kullanici kullanici){
//        db.child("users").child(kullanici.kullaniciAdi).setValue(kullanici);
//    }
}

