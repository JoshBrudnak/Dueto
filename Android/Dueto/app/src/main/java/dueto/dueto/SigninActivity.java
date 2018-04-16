package dueto.dueto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.regex.Pattern;

public class SigninActivity extends AppCompatActivity {

    private EditText Username;
    private EditText Address;
    private EditText Password;
    private EditText Confirmation;
    private Button SignIn;
    private TextView Cancel;

    private TextView Message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        Username = (EditText)findViewById(R.id.unText);
        Address = (EditText)findViewById(R.id.emText);
        Password = (EditText)findViewById(R.id.pwText);
        Confirmation = (EditText)findViewById(R.id.confirmText);
        SignIn = (Button)findViewById(R.id.suButt);
        Message = (TextView)findViewById(R.id.errMessage);
        Cancel = (TextView) findViewById(R.id.cancelView);

        //Message.setText("Welcome to Dueto!");

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validPass(Password.getText().toString(), Confirmation.getText().toString(), Username.getText().toString(), Address.getText().toString());
            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SigninActivity.this, FirstActivity.class);
                startActivity(intent);
            }
        });

    }

    private void validPass(String userPass, String userConfirm, String userName, String userAddress) {
        if(!userPass.equals(userConfirm)) {
            Message.setText("The passwords don't match!");
            if (userName.isEmpty()) {
                Message.setText("Enter a Username + match passwords!");
            }
            else if (userAddress.isEmpty()) {
                Message.setText("Enter an e-mail address + match passwords!");
            }
            else if (userAddress.isEmpty() && userName.isEmpty()) {
                Message.setText("Please enter in valid information!");
            }
        }
        else if (userName.isEmpty()) {
            Message.setText("Enter a Username!");
        }
        else if (userAddress.isEmpty() || !userAddress.contains("@")) {
            Message.setText("Enter an e-mail address!");
        }
        else if (!isValid(userAddress)){
            Message.setText("Enter a valid e-mail address!");
        }
//        else if (userAddress.contains("@")){
//            String[] parts = userAddress.split("@", 0);
//            String part1 = parts[0]; //before the @
//            String part2 = parts[1]; //after the @
//            if (!parts[1].contains(".")){
//                Message.setText("Enter a valid e-mail address!");
//            }
//            else if (part2 == "") {
//                Message.setText("Enter a valid e-mail address!");
//            }
//        }
        else {
            Intent intent = new Intent(SigninActivity.this, MainActivity.class);
            startActivity(intent);
        }
        }

        public static boolean isValid(String email)
        {
            String emailRegex = "^[a-zA-Z0-9_+&*-]+" +
                                "(?:\\." +
                                "[a-zA-Z0-9_+&*-]+)*@" +
                                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                                "A-Z]{2,7}$";
            Pattern pat = Pattern.compile(emailRegex);
            if (email == null) {
                return false;
            }

            return pat.matcher(email).matches();
        }
    }
