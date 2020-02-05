package com.melihcelenk.akilliurunteslimatsistemi;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AkilliUrunTeslimatSistemiActivity extends AppCompatActivity {

    public void toast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
    public void toast(String message, int duration){
        Toast.makeText(getApplicationContext(), message, duration).show();
    }

    public void yonlendir(Class<?> cls){
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    public ProgressBar $pb(int id){
        return (ProgressBar)findViewById(id);
    }

    public EditText $et(int id){
        return (EditText)findViewById(id);
    }

    public TextView $tv(int id){
        return (TextView)findViewById(id);
    }

    public Button $btn(int id){
        return (Button)findViewById(id);
    }

    public CheckBox $chk(int id){
        return (CheckBox)findViewById(id);
    }

    public RadioButton $rb(int id){
        return (RadioButton)findViewById(id);
    }

    public ToggleButton $tb(int id){
        return (ToggleButton)findViewById(id);
    }

    public Spinner $spn(int id){
        return (Spinner)findViewById(id);
    }

    public Menu $mnu(int id){
        return (Menu)findViewById(id);
    }

    public ListView $lv(int id){
        return (ListView)findViewById(id);
    }

    public LinearLayout $ll(int id){
        return (LinearLayout)findViewById(id);
    }

    public RelativeLayout $rl(int id){
        return (RelativeLayout)findViewById(id);
    }

    public ImageButton $ib(int id){
        return (ImageButton) findViewById(id);
    }

    public TableRow $tr(int id){
        return (TableRow) findViewById(id);
    }

    public <T extends View> T $(int id){
        return (T)findViewById(id);
    }

    public void showKeyboard(final EditText ettext){
        ettext.requestFocus();
        ettext.postDelayed(new Runnable(){
                               @Override public void run(){
                                   InputMethodManager keyboard=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                   keyboard.showSoftInput(ettext,0);
                               }
                           }
                ,200);
    }

}

