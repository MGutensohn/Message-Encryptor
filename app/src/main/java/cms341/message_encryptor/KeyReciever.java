package cms341.message_encryptor;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by mshrestha on 7/23/2014.
 */
public class KeyReciever extends Activity {

    private EditText mNewKeyTitle;
    private Button mAddKey;
    private DBManager dbm;
    private String recievedKey;
    private SharedPreferences prefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_reciever);
        mNewKeyTitle = (EditText) findViewById(R.id.new_key);
        mAddKey = (Button)findViewById(R.id.add_key);
        dbm = new DBManager(this);
        prefs = getSharedPreferences("user",MODE_PRIVATE);

        mAddKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbm.insert(prefs.getString("pass","password"),mNewKeyTitle.getText().toString(),recievedKey);
                startActivity(new Intent(getApplicationContext(),KeyArchive.class));
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        Intent intent = getIntent();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Parcelable[] rawMessages = intent.getParcelableArrayExtra(
                    NfcAdapter.EXTRA_NDEF_MESSAGES);

            NdefMessage message = (NdefMessage) rawMessages[0];
            recievedKey = new String(message.getRecords()[0].getPayload());

        } else
            mNewKeyTitle.setText("Waiting for NDEF Message");
    }

}