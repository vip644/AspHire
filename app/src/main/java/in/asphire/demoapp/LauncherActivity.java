

package in.asphire.demoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.parse.ParseUser;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       if(ParseUser.getCurrentUser() != null)
        {
            startActivity(new Intent(LauncherActivity.this,MainActivity.class));
        }
        else
        {
            startActivity(new Intent(LauncherActivity.this,SigninActivity.class));
        }
    }
}
