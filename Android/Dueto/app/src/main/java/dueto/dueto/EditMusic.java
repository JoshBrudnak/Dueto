package dueto.dueto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class EditMusic extends AppCompatActivity {

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button noMusic;

    private Button Save;
    private TextView Cancel;
    private MediaPlayer player1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_music);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        noMusic = (Button) findViewById(R.id.button5);

        Save = (Button) findViewById(R.id.saveMusicChange);
        Cancel = (TextView) findViewById(R.id.cancelMusicView);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String musicPlay = player1.toString();

                Intent myIntent = new Intent(view.getContext(), UserProfileActivity.class);
                myIntent.putExtra("song", musicPlay);

                startActivity(myIntent);

            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditMusic.this, UserProfileActivity.class));
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player1 = MediaPlayer.create(EditMusic.this, R.raw.ambient);
                player1.setLooping(true);
                player1.start();

                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
                button4.setEnabled(false);

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player1 = MediaPlayer.create(EditMusic.this, R.raw.classical);
                player1.setLooping(true);
                player1.start();

                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
                button4.setEnabled(false);

            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player1 = MediaPlayer.create(EditMusic.this, R.raw.funky);
                player1.setLooping(true);
                player1.start();

                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
                button4.setEnabled(false);

            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player1 = MediaPlayer.create(EditMusic.this, R.raw.hiphop);
                player1.setLooping(true);
                player1.start();

                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
                button4.setEnabled(false);

            }
        });

        noMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player1.pause();

                if (button1.isEnabled() == false){
                    button1.setEnabled(true);
                }
                if (button2.isEnabled() == false){
                    button2.setEnabled(true);
                }
                if (button3.isEnabled() == false){
                    button3.setEnabled(true);
                }
                if (button4.isEnabled() == false){
                    button4.setEnabled(true);
                }

            }
        });
    }
}
