package in.asphire.demoapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

public class ForgetPassword extends AppCompatActivity {

    private EditText recover_mail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        recover_mail = (EditText)findViewById(R.id.recovery_email);

        findViewById(R.id.send_mail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validationError = false;
                StringBuilder validateErrorMessage = new StringBuilder("Please");
                if (isEmpty(recover_mail)) {
                    validationError = true;
                    validateErrorMessage.append("Enter your Email-Id");
                }
                if (validationError) {
                    Toast.makeText(ForgetPassword.this, validateErrorMessage.toString(), Toast.LENGTH_LONG).show();
                    return;
                }

                final ProgressDialog dlg = new ProgressDialog(ForgetPassword.this);
                dlg.setTitle("Please wait.");
                dlg.setMessage("Logging in.  Please wait.");
                dlg.show();


                ParseUser.requestPasswordResetInBackground(recover_mail.getText().toString(),
                        new RequestPasswordResetCallback() {
                    @Override
                    public void done(ParseException e) {
                        dlg.dismiss();
                        if (e != null) {
                            // Show the error message
                            Toast.makeText(ForgetPassword.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            // Start an intent for the dispatch activity
                            Toast.makeText(ForgetPassword.this,"A mail sucessfully sent to you email address", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ForgetPassword.this, LauncherActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
