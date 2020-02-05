package com.melihcelenk.akilliurunteslimatsistemi;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class OturumAcActivity extends AkilliUrunTeslimatSistemiActivity {
    private HesapYoneticisi mHy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oturum_ac);
        mHy = new HesapYoneticisi(this);

    }
    @Override
    protected void onStart(){
        super.onStart();
        $pb(R.id.progressBar).setVisibility(View.GONE);
        getSupportActionBar().setTitle("Oturum Aç");
        mHy = new HesapYoneticisi(this);
    }

    public void oturumAc(View view) {
        String email = $et(R.id.act_oturum_ac_et_email).getText().toString();
        String parola = $et(R.id.act_oturum_ac_et_parola).getText().toString();
        $pb(R.id.progressBar).setVisibility(View.VISIBLE);
        mHy.girisYap(email, parola, task -> {
            if(task.isSuccessful()){
                $pb(R.id.progressBar).setVisibility(View.GONE);
                toast("Hoşgeldin " + mHy.kullaniciEmailiniAl(), Toast.LENGTH_SHORT);
                yonlendir(MainActivity.class);
            } else{
                $pb(R.id.progressBar).setVisibility(View.GONE);
                toast("Giriş yaparken bir hata oluştu.", Toast.LENGTH_SHORT);
            }
        });
    }
}

