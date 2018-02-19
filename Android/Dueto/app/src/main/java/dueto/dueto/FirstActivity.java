package dueto.dueto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FirstActivity extends AppCompatActivity {

    private Button Login;
    private Button SignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Login = (Button)findViewById(R.id.logButt);
        SignIn = (Button)findViewById(R.id.suButt);

        Login.setOnClickListener(new View.OnClickListener() { //calls onClick(default name) through ClickListener which takes you to LoginActivity
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstActivity.this, LoginActivity.class));
            }
        });
        SignIn.setOnClickListener(new View.OnClickListener() { //calls onClick(default name) through ClickListener which takes you to LoginActivity
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstActivity.this, SigninActivity.class));
            }
        });
    }
}
