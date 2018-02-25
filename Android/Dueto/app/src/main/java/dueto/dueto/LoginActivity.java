package dueto.dueto;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Name = (EditText)findViewById(R.id.nameText);
        Password = (EditText) findViewById(R.id.passText);
        Info = (TextView)findViewById(R.id.logInfo);
        Login = (Button)findViewById(R.id.logButt);

        //Info.setText("Attempts Remaining: 3");

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validID(Name.getText().toString(), Password.getText().toString());

                final ServerCommunicator sc = new ServerCommunicator();
                final HashMap<String, String> params = new HashMap<>();

                System.out.println("reached 1");

//                params.put("username", Name.getText().toString());
//                params.put("password", Password.getText().toString());
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        System.out.println(sc.makeRequest("login", params));
//                    }
//                }, 10000);
            }
        });

    }

    private void validID(String userName, String userPass) {
        if((userName.equals("Admin")) && (userPass.equals("1234"))) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else {
            counter--;

            Info.setText("Attempts Remaining: " + String.valueOf(counter));
            if(counter == 0){
                Login.setEnabled(false);
                Info.setText("Try again next time.");
            }
        }
    }
}
