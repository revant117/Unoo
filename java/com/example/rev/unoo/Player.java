package com.example.rev.unoo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class Player extends AppCompatActivity implements View.OnClickListener{

    ArrayList<File> mySongs;
    static MediaPlayer mp;
    int position;

    Uri u;
    Thread th;

    SeekBar sb;
    Button btPlay , btFF , btFB  ;
    TextView songName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        btPlay = (Button) findViewById(R.id.btPlay);
        btFF = (Button) findViewById(R.id.btFF);
        btFB = (Button) findViewById(R.id.btFB);
        songName = (TextView)findViewById(R.id.songName);

        btPlay.setOnClickListener(this);
        btFF.setOnClickListener(this);
        btFB.setOnClickListener(this);

        sb = (SeekBar) findViewById(R.id.seekBar);
        th = new Thread(){
            @Override
            public void run(){
                int total = mp.getDuration();
                int current = 0;
                sb.setMax(total);
                while (current < total) try {
                    sleep(500);
                    current = mp.getCurrentPosition();
                    sb.setProgress(current);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };




        if(mp!=null){
            mp.stop();
            mp.release();
        }

        Intent i = getIntent();
        Bundle b = i.getExtras();
        mySongs = (ArrayList)b.getParcelableArrayList("songs");
        position = b.getInt("pos", 0);
        String song_name = mySongs.get(position).toString();
        songName.setText(song_name);
        u = Uri.parse(song_name);
        mp = MediaPlayer.create(getApplicationContext(), u);
        mp.start();


        th.start();

//        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                mp.seekTo(seekBar.getProgress());
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_player, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id){
            case R.id.btPlay:
                if(mp.isPlaying())
                    {
                        btPlay.setText(">");
                        mp.pause();}
                else{
                    btPlay.setText("||");
                    mp.start();
                    }
                break;
            case R.id.btFF:
                mp.stop();
                mp.release();
                position = (position+1)%mySongs.size();
                String name = mySongs.get(position).toString();
                songName.setText(name);
                u = Uri.parse(mySongs.get(position).toString());

                mp = MediaPlayer.create(getApplicationContext(),u);
                mp.start();
                break;
            case R.id.btFB:
                mp.stop();
                mp.release();
                position  = (position-1<0)?mySongs.size()-1:position-1;
                name = mySongs.get(position).toString();
                songName.setText(name);
                u = Uri.parse(mySongs.get(position).toString());

                mp = MediaPlayer.create(getApplicationContext(),u);
                mp.start();
                break;




        }

    }
}
