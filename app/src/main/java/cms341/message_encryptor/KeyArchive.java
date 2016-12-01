package cms341.message_encryptor;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;

import java.io.File;

public class KeyArchive extends AppCompatActivity {
    private DBManager dbm;
    private AutoCompleteTextView search;
    ArrayAdapter searchAdapter;
    LinearLayout keys;
    Dialog password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_archive);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        password = new Dialog(this);
        password.setContentView(R.layout.password_popup);
        password.show();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent( getApplicationContext( ),
                        KeyGenerator.class));
            }
        });
    }

    private static boolean doesDatabaseExist(Context context) {
        File dbFile = context.getDatabasePath("keys");
        return dbFile.exists();
    }

}

