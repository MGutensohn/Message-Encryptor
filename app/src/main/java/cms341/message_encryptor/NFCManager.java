package cms341.message_encryptor;

import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class NFCManager extends AppCompatActivity implements NfcAdapter.OnNdefPushCompleteCallback,
        NfcAdapter.CreateNdefMessageCallback {
    private NfcAdapter mNfcAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(mNfcAdapter != null) {
            //Handle some NFC initialization here
        }
        else {
            Toast.makeText(this, "NFC not available on this device",
                    Toast.LENGTH_SHORT).show();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfcmanager);
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent nfcEvent) {
        return null;
    }

    @Override
    public void onNdefPushComplete(NfcEvent nfcEvent) {

    }
}
