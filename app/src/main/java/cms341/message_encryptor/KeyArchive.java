package cms341.message_encryptor;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static java.sql.Types.NULL;

public class KeyArchive extends AppCompatActivity {
    private DBManager dbm;
    private AutoCompleteTextView search;
    ArrayAdapter searchAdapter;
    LinearLayout results;
    File dbFile;
    Dialog PasswordDialog;
    TextView passwordPrompt;
    EditText passwordet;
    Bundle bundle;
    HashMap<String, String> keys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_archive);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbm = new DBManager(this);
        dbFile = this.getDatabasePath("keys.db");
        bundle = new Bundle();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent( getApplicationContext( ),
                        KeyGenerator.class));
            }
        });
    }



    public void enterPass(){
        LayoutInflater li = LayoutInflater.from(this);
        ArrayList<String> convos = dbm.selectAll(passwordet.getText().toString());

        int id = 0;
        int index = 0;

        if(convos.equals(null)){
            Toast.makeText( this, "Password incorrect", Toast.LENGTH_LONG).show();
            PasswordDialog.show();
        }else{
            while(index < convos.size()){
                index++;
                TextView date = createTV(this, convos.get(index));
                date.setTypeface(null, Typeface.ITALIC);
                date.setTextSize(15);
                index++;

                TextView sub = createTV(this,"Create: " + convos.get(index));
                sub.setTypeface(null, Typeface.BOLD);
                sub.setTextSize(25);
                sub.setTag("convo" + id);
                index++;

                keys.put("convo" + id, convos.get(index));
                index++;
                id++;

                sub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bundle.putString("key", keys.get(v.getTag()));
                    }
                });
                results.addView(sub);
                results.addView(date);
                results.addView(createLine());
            }
        }
    }


    public TextView createTV(Context context, String text){
        TextView tv = new TextView(context);
        tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        tv.setText(text);
        tv.setTextColor(Color.parseColor("#00FF00"));

        return tv;
    }

    public View createLine(){
        View line = new View(this);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3);
        params.setMargins(0, 5, 0, 10);
        line.setLayoutParams(params);
        line.setBackgroundColor(Color.parseColor("#00FF00"));

        return line;
    }

}

