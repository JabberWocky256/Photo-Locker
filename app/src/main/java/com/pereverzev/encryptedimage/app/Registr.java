package com.pereverzev.encryptedimage.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by opereverzyev on 10.12.14.
 */
public class Registr extends Activity {
    public static final String PREFS_NAME = "AUTHORISATION";
    public static final String PREF_PASSWORD = "password";
    private EditText password ;
    private ImageView eye;
    private Button btnOk;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registr);

        password = (EditText) findViewById(R.id.editTextPass);
        eye = (ImageView) findViewById(R.id.eye);
        btnOk = (Button) findViewById(R.id.btnOk);

        eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPassword showPassword = new ShowPassword();
                showPassword.execute();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().length()<3){
                    ShowWrangMessage showWrangMessage = new ShowWrangMessage();
                    showWrangMessage.execute();
                } else {
                    SharedPreferences loginPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

                    SharedPreferences.Editor loginPreferencesEditor = loginPreferences.edit();
                    loginPreferencesEditor.clear();
                    loginPreferencesEditor.apply();

                    loginPreferencesEditor.putString(PREF_PASSWORD, password.getText().toString());
                    loginPreferencesEditor.apply();

                    Intent grid = new Intent(Registr.this, GridViewActivity.class);
                    startActivity(grid);
                }
            }
        });

    }

    private class ShowPassword extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            password.setInputType(InputType.TYPE_CLASS_TEXT);
            password.setSelection(password.length());
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            password.setSelection(password.length());
        }
    }

    private class ShowWrangMessage extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            ((TextView)findViewById(R.id.txtNotCorrect)).setText("Bad login");
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            ((TextView)findViewById(R.id.txtNotCorrect)).setText("");
        }
    }
}
