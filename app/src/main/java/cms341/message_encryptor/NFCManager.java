package cms341.message_encryptor;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.Charset;
import java.util.ArrayList;

public class NFCManager extends AppCompatActivity implements NfcAdapter.OnNdefPushCompleteCallback,
        NfcAdapter.CreateNdefMessageCallback {
    private NfcAdapter mNfcAdapter;
    private ArrayList<String> message = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView title = (TextView)findViewById(R.id.conversation);
        Intent intent = getIntent();
        String conversation = intent.getStringExtra("conversation");
        String key = intent.getStringExtra("key");
        title.setText(conversation);
        message.add(conversation);
        message.add(key);



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
        if(message.isEmpty()){
            return null;
        }

        NdefRecord[] recordsToAttach = createRecords();
        return new NdefMessage(recordsToAttach);
    }

    @Override
    public void onNdefPushComplete(NfcEvent nfcEvent) {
        message.clear();
    }

    public NdefRecord[] createRecords() {

        NdefRecord[] records = new NdefRecord[message.size()];

        for (int i = 0; i < message.size(); i++){

            byte[] payload = message.get(i).
                    getBytes(Charset.forName("UTF-8"));
            NdefRecord record = new NdefRecord(
                    NdefRecord.TNF_WELL_KNOWN,  //Our 3-bit Type name format
                    NdefRecord.RTD_TEXT,        //Description of our payload
                    new byte[0],                //The optional id for our Record
                    payload);                   //Our payload for the Record
            records[i] = record;
        }
        return records;
    }
}
