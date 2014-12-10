package com.pereverzev.encryptedimage.app;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by Александр on 10.12.2014.
 */
public class MainActivity extends Activity {

    private EditText password ;
    private ImageView eye;
    private Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locker);

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

    }

    private class ShowPassword extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            password.setInputType(InputType.TYPE_CLASS_TEXT);
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
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }
}
