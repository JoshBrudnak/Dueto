package dueto.dueto;

import android.app.Activity;
import android.content.Intent;
import android.gesture.GestureLibraries;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class VideoActivity extends Activity {

    Button mRecordView, mPlayView, mUploadView;
    private VideoView mVideoView;
    Integer SELECT_FILE = 0;
    private int ACTIVITY_START_CAMERA_APP = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        mRecordView = (Button) findViewById(R.id.recordButton);
        mPlayView = (Button) findViewById(R.id.playButton);
        mUploadView = (Button) findViewById(R.id.uploadButton);

        mVideoView = (VideoView) findViewById(R.id.videoView);

        mRecordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent callVideoAppIntent = new Intent();
//                callVideoAppIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
//
//                startActivityForResult(callVideoAppIntent, ACTIVITY_START_CAMERA_APP);
                Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takeVideoIntent, ACTIVITY_START_CAMERA_APP);
                    mVideoView.seekTo(100);
                }

            }
        });

        mPlayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideoView.seekTo(100);
                mVideoView.start();

            }
        });

        mUploadView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI); //This code selects a video from the phone's gallery
                intent.setType("video/*");
                startActivityForResult(intent, SELECT_FILE);
                mVideoView.seekTo(100);
                mVideoView.start();

            }
        });

        mVideoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == ACTIVITY_START_CAMERA_APP && resultCode == RESULT_OK) {
            Uri videoUri = data.getData();
            mVideoView.seekTo(100);
            mVideoView.setVideoURI(videoUri);
        }

    }

}

