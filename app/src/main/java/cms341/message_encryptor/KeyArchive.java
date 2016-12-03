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

import net.sqlcipher.database.SQLiteDatabase;

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
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_archive);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        SQLiteDatabase.loadLibs(this);
        results = (LinearLayout)findViewById(R.id.keys);
        dbm = new DBManager(this);
        bundle = new Bundle();
        enterPass();

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
        intent = new Intent(this, MainActivity.class);
        LayoutInflater li = LayoutInflater.from(this);
        dbm.insert("testkey", "12/3/2016","TestKey","qwertyuiopasdfghjklzxcvbnm123456");
        ArrayList<String> convos = dbm.selectAll("testkey");
        keys = new HashMap<>();

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

                TextView sub = createTV(this, convos.get(index));
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
                        //bundle.putString("key", keys.get(v.getTag()));
                        intent.putExtra("key", keys.get(v.getTag()));
                        startActivity(intent);
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

