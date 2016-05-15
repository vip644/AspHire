package in.asphire.demoapp;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity{

    private EditText edit_name, edit_email, edit_number, edit_password;
    private TextInputLayout layout_name, layout_email, layout_number, layout_password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        edit_name = (EditText)findViewById(R.id.edit_name);
        edit_email = (EditText)findViewById(R.id.edit_email);
        edit_number = (EditText)findViewById(R.id.edit_number);
        edit_password = (EditText)findViewById(R.id.edit_password);

        layout_name = (TextInputLayout)findViewById(R.id.layout_name);
        layout_email = (TextInputLayout)findViewById(R.id.layout_email);
        layout_number = (TextInputLayout)findViewById(R.id.layout_number);
        layout_password = (TextInputLayout)findViewById(R.id.layout_password);


        edit_name.addTextChangedListener(new MyTextWatcher(edit_name));
        edit_email.addTextChangedListener(new MyTextWatcher(edit_email));
        edit_number.addTextChangedListener(new MyTextWatcher(edit_number));
        edit_password.addTextChangedListener(new MyTextWatcher(edit_password));

        findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {
        if (!validateName()) {
            return;
        }

        if(!validateEmail()){
            return;
        }

        if(!validatePassword()){
            return;
        }

        if(!validateNumber()){
            return;
        }


        // Set up a progress dialog
        final ProgressDialog dlg = new ProgressDialog(SignupActivity.this);
        dlg.setTitle("Please wait.");
        dlg.setMessage("Signing up.  Please wait.");
        dlg.show();


        ParseUser user = new ParseUser();
        user.setUsername(edit_name.getText().toString());
        user.setEmail(edit_email.getText().toString());
        user.setPassword(edit_password.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                dlg.dismiss();

                if(e!=null){
                    Toast.makeText(SignupActivity.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(SignupActivity.this, LauncherActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }


    private boolean validateName() {

        if(edit_name.getText().toString().trim().isEmpty()){
            layout_name.setError(getString(R.string.error_name));
            requestFocus(edit_name);
            return false;
        }
        else {
                layout_name.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateEmail() {

        String email = edit_email.getText().toString().trim();
        if(email.isEmpty() || !isValidEmail(email)){
            layout_email.setError(getString(R.string.error_email));
            requestFocus(edit_email);
            return false;
        }
        else {
            layout_email.setErrorEnabled(false);
        }
        return true;
    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }



    private boolean validateNumber() {

        String number = edit_number.getText().toString().trim();
        if(number.isEmpty() || !isValidNumber(number)){
            layout_number.setError(getString(R.string.error_number));
            requestFocus(edit_number);
            return false;
        }
        else {
            layout_number.setErrorEnabled(false);
        }
        return true;
    }

    private boolean isValidNumber(String number) {
        return Patterns.PHONE.matcher(number).matches();
    }


    private boolean validatePassword() {

        if(edit_password.getText().toString().isEmpty()){
            layout_password.setError(getString(R.string.error_password));
            requestFocus(edit_password);
            return false;
        }
        else {
            layout_password.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if(view.requestFocus()){
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    private class MyTextWatcher implements TextWatcher{

        private View view;
        private MyTextWatcher(View view){
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            switch (view.getId()){
                case R.id.edit_name:
                    validateName();
                    break;
                case R.id.edit_email:
                    validateEmail();
                    break;
                case R.id.edit_number:
                    validateNumber();
                    break;
                case R.id.edit_password:
                    validatePassword();
                    break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
