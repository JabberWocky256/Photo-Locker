package com.pereverzev.encryptedimage.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Александр on 10.12.2014.
 */
public class MainActivity extends Activity {

    public static final String PREFS_NAME = "AUTHORISATION";
    public static final String PREF_PASSWORD = "password";
    private EditText password ;
    private ImageView eye;
    private Button btnOk;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locker);

        hasThePass();

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
                SharedPreferences loginPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                String pass = (String) loginPreferences.getAll().get(PREF_PASSWORD);

                if(password.getText().toString().equals(pass.trim())){
                    Intent gallery = new Intent(MainActivity.this, GridViewActivity.class);
                    startActivity(gallery);
                    finish();
                } else {
                    ShowWrangMessage showWrangMessage = new ShowWrangMessage();
                    showWrangMessage.execute();
                }
            }
        });

    }

    private void hasThePass(){
        SharedPreferences loginPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String pass = (String) loginPreferences.getAll().get(PREF_PASSWORD);

        if(pass == null){
            Intent registr = new Intent(MainActivity.this, Registr.class);
            startActivity(registr);
        }
    }

    private class ShowPassword extends AsyncTask<Void, Void, Void>{

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
            ((TextView)findViewById(R.id.txtNotCorrect)).setText("WRANG. TRY AGAIN!");
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
