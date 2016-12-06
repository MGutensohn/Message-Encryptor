package cms341.message_encryptor;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import static android.content.Context.MODE_PRIVATE;



public class LoginFragment extends DialogFragment {
    private EditText pass;
    private SharedPreferences prefs;
    private SharedPreferences.Editor edit;




    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        prefs = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        edit = prefs.edit();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.fragment_login, null);
        pass = (EditText)view.findViewById(R.id.password);


        builder.setView(view)


                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        edit.putString("pass", pass.getText().toString());
                        edit.commit();
                        ((KeyArchive) getActivity()).getStoredKeys();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LoginFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
