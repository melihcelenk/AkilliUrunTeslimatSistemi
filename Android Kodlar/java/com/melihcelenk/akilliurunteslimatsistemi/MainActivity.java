package com.melihcelenk.akilliurunteslimatsistemi;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.melihcelenk.akilliurunteslimatsistemi.dal.AUTSFirebase;
import com.melihcelenk.akilliurunteslimatsistemi.model.Kullanici;

import java.util.HashMap;

public class MainActivity extends AkilliUrunTeslimatSistemiActivity {

    private HesapYoneticisi mHy;
    private AUTSFirebase mFirebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHy = new HesapYoneticisi(this);
        if (!mHy.girisYapildiMi()){
            yonlendir(OturumAcActivity.class);
            finish();
        } else{
            mFirebase = new AUTSFirebase();
            tekSeferlikVerileriAta();
            degiskenBilgilereListenerAta();
            Switch switchOtomatikSatinAl = $(R.id.act_main_switch_otomatik_satin_al);
            switchOtomatikSatinAl.setOnCheckedChangeListener((buttonView, isChecked) -> {
                Button btnUrunSatinAl = $(R.id.act_main_btn_urun_satin_al);
                if(isChecked){
                    butonuDevreDisiBirak(btnUrunSatinAl);
                    mFirebase.otomatikAlAta(mHy, true);
                }else{
                    butonuAktiflestir(btnUrunSatinAl);
                    mFirebase.otomatikAlAta(mHy, false);
                }
            });

            $btn(R.id.act_main_btn_urun_satin_al).setOnClickListener(v->{
                mFirebase.urunSatinAl(mHy);
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        tekSeferlikVerileriAta();

    }

    private void tekSeferlikVerileriAta(){
        Query kullaniciQuery = mFirebase.db.orderByChild("email")
                .equalTo(mHy.kullaniciEmailiniAl())
                .limitToFirst(1);
        kullaniciQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot kullaniciSnapshot: dataSnapshot.getChildren()){
                        Kullanici kullanici = kullaniciSnapshot.getValue(Kullanici.class);
                        $tv(R.id.act_main_tv_kullanici).setText("Hoşgeldin " + kullaniciSnapshot.getKey());
                        mHy.setKullaniciAdi(kullaniciSnapshot.getKey());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void degiskenBilgilereListenerAta(){
        Query kullaniciQuery = mFirebase.db.orderByChild("email")
                .equalTo(mHy.kullaniciEmailiniAl())
                .limitToFirst(1);
        kullaniciQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot kullaniciSnapshot: dataSnapshot.getChildren()){
                        Kullanici kullanici = kullaniciSnapshot.getValue(Kullanici.class);
                        $tv(R.id.act_main_tv_urun_var_mi).setText(kullanici.doluMu ? "Ürün var" : "Ürün yok");
                        $tv(R.id.act_main_tv_bakiye).setText("Bakiye: " + kullanici.bakiye);
                        Switch switchOtomatikSatinAl = $(R.id.act_main_switch_otomatik_satin_al);
                        switchOtomatikSatinAl.setChecked(kullanici.otomatikAl);
                        switchOtomatikSatinAl.setText("Otomatik Satın Al");
                        if(kullanici.istekVarMi)
                            butonuDevreDisiBirak($btn(R.id.act_main_btn_urun_satin_al));
                        else
                            butonuAktiflestir($btn(R.id.act_main_btn_urun_satin_al));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
    private void butonuAktiflestir(Button b){
        b.setBackgroundResource(R.color.colorPrimary);
        $btn(R.id.act_main_btn_urun_satin_al).setEnabled(true);
    }
    private void butonuDevreDisiBirak(Button b) {
        b.setBackgroundResource(R.color.common_google_signin_btn_text_dark_disabled);
        $btn(R.id.act_main_btn_urun_satin_al).setEnabled(false);
    }
}


