package cms341.message_encryptor;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import static android.content.Context.MODE_PRIVATE;



public class LoginFragment extends DialogFragment {
    private EditText pass;

//    public interface getPasswordListener{
//        public void getPassword(String s);
//    }

    private SharedPreferences prefs;
    private SharedPreferences.Editor edit;

//    getPasswordListener getPass;

//    public void onAttach(Context context) {
//        super.onAttach(context);
//         Activity a;
//
//        if (context instanceof Activity){
//            a=(Activity) context;
//            getPass = (getPasswordListener) a;
//        }else{
//            getPass = (getPasswordListener) context;
//        }
//    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        prefs = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        edit = prefs.edit();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        //getPass = (getPasswordListener) getActivity();
        View view = inflater.inflate(R.layout.fragment_login, null);
        pass = (EditText)view.findViewById(R.id.password);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)

                // Add action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        //getPass.getPassword(pass.getText().toString());
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
