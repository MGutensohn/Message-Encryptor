package cms341.message_encryptor;


import android.app.Activity;
import android.os.Bundle;

/**
 * Created by michael on 9/25/16.
 */
public class SettingsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction().add(android.R.id.content, new SettingsFragment()).commit();
    }
}
