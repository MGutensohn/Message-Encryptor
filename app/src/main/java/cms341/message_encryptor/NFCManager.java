package cms341.message_encryptor;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class NFCManager extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback {

    private TextView mToSend;
    private Intent key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfcmanager);
        key = getIntent();
        mToSend = (TextView) findViewById(R.id.conversation);
        mToSend.setText(key.getStringExtra("conversation"));

        NfcAdapter mAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mAdapter == null) {
            mToSend.setText("Sorry this device does not have NFC.");
            return;
        }

        if (!mAdapter.isEnabled()) {
            Toast.makeText(this, "Please enable NFC via Settings.", Toast.LENGTH_LONG).show();
        }

        mAdapter.setNdefPushMessageCallback(this, this);
    }

    /**
     * Ndef Record that will be sent over via NFC
     *
     * @param nfcEvent
     * @return
     */
    @Override
    public NdefMessage createNdefMessage(NfcEvent nfcEvent) {
        String message = key.getStringExtra("key");
        NdefRecord ndefRecord = NdefRecord.createMime("text/plain", message.getBytes());
        NdefMessage ndefMessage = new NdefMessage(ndefRecord);
        return ndefMessage;
    }
}
