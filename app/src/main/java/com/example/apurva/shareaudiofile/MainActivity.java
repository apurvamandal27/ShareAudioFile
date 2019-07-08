package com.example.apurva.shareaudiofile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;

import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;


public class MainActivity extends AppCompatActivity {

    Button share_audio;
    TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        info = findViewById(R.id.textview);
        share_audio = findViewById(R.id.share_audio);


        share_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //runtime permission
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

                    //open a dialog to take permission
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

                    //open a dialog to take permission
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                }
//                MediaPlayer mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.myaudio);
//
//                mediaPlayer.start();
                File dest = Environment.getExternalStorageDirectory();
                InputStream in = getApplicationContext().getResources().openRawResource(R.raw.ab);

                try
                {
                    OutputStream out = new FileOutputStream(new File(dest, "lastshared.mp3"));
                    byte[] buf = new byte[1024];
                    int len;
                    while ( (len = in.read(buf, 0, buf.length)) != -1)
                    {
                        out.write(buf, 0, len);
                    }
                    in.close();
                    out.close();
                }
                catch (Exception e) {
                    Toast.makeText(MainActivity.this, "excptn"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                Intent share = new Intent(Intent.ACTION_SEND);
                share.putExtra(Intent.EXTRA_STREAM, Uri.parse(Environment.getExternalStorageDirectory().toString() + "/lastshared.mp3"));
                share.setType("audio/mp3");
                getApplicationContext().startActivity(Intent.createChooser(share, "Condividi il suono \"" + share_audio.getText() + "\""));
            }
        });

    }
}


