package dueto.dueto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import org.w3c.dom.Text;

import dueto.dueto.servercom.Server;

public class LoginActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private TextView Create;
    private int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        Name = (EditText)findViewById(R.id.nameText);
        Password = (EditText) findViewById(R.id.passText);
        Info = (TextView)findViewById(R.id.logInfo);
        Login = (Button)findViewById(R.id.logButt);
        Create = (TextView) findViewById(R.id.createView);

        //Info.setText("Attempts Remaining: 3");

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validID(Name.getText().toString(), Password.getText().toString());

                System.out.println("reached 1");
            }
        });
        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SigninActivity.class);
                startActivity(intent);
            }
        });
    }

    private void validID(String userName, String userPass) {

        if(Server.SERVER.login(userName,userPass)) {
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
