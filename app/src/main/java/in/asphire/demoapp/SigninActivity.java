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
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class SigninActivity extends AppCompatActivity {

    private EditText enter_email, enter_password;
    private TextInputLayout signin_email, signin_password;
    private Button signin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_layout);

        enter_email = (EditText)findViewById(R.id.enter_email);
        enter_password = (EditText)findViewById(R.id.enter_password);

        signin_email = (TextInputLayout)findViewById(R.id.signin_email);
        signin_password = (TextInputLayout)findViewById(R.id.signin_password);

        findViewById(R.id.forgot_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SigninActivity.this, ForgetPassword.class));
            }
        });

        findViewById(R.id.create_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SigninActivity.this, SignupActivity.class));
            }
        });

        signin = (Button)findViewById(R.id.signin);

        enter_email.addTextChangedListener(new SigninTextWatcher(enter_email));
        enter_password.addTextChangedListener(new SigninTextWatcher(enter_password));

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


    }

    private void login() {

        if(!validateEmail()){
            return;
        }
        if(!validatePassword()){
            return;
        }

        // Set up a progress dialog
        final ProgressDialog dlg = new ProgressDialog(SigninActivity.this);
        dlg.setTitle("Please wait.");
        dlg.setMessage("Logging in.  Please wait.");
        dlg.show();

        ParseUser.logInInBackground(enter_email.getText().toString(), enter_password.getText()
                .toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                dlg.dismiss();
                if(e!=null){

                    Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(SigninActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    private class SigninTextWatcher implements TextWatcher {

        private View view;
        private SigninTextWatcher(View view){
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
                case R.id.edit_email:
                    validateEmail();
                        break;
                case R.id.edit_password:
                    validatePassword();
                    break;
            }
        }
    }

    private boolean validateEmail() {

        String email = enter_email.getText().toString().trim();
        if(email.isEmpty() || !isValidEmail(email)){
            signin_email.setError(getString(R.string.error_email));
            requestFocus(enter_email);
            return false;
        }
        else {
            signin_email.setErrorEnabled(false);
        }
        return true;
    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean validatePassword() {

        if(enter_password.getText().toString().isEmpty()){
            signin_password.setError(getString(R.string.error_password));
            requestFocus(enter_password);
            return false;
        }
        else {
            signin_password.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if(view.requestFocus()){
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
