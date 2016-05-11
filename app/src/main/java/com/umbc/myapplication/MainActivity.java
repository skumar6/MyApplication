package com.umbc.myapplication;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button play,stop,record;
    EditText recName;
    private MediaRecorder myAudioRecorder;
    private String outputFile = null;
    String Filename = "Sanjeev";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play=(Button)findViewById(R.id.bt_play);
        stop=(Button)findViewById(R.id.bt_stop);
        record=(Button)findViewById(R.id.bt_record);
        recName = (EditText) findViewById(R.id.editText);
        play.setVisibility(View.INVISIBLE);
        stop.setVisibility(View.INVISIBLE);

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Toast.makeText(getApplicationContext(), ""+String.valueOf(recName.getText()),Toast.LENGTH_LONG).show();
                    if(!String.valueOf(recName.getText()).equals("")  ){
                        Filename = String.valueOf(recName.getText());
                        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+Filename+".mp3";;
                        myAudioRecorder=new MediaRecorder();
                        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                        myAudioRecorder.setOutputFile(outputFile);
                       // ok.setVisibility(View.INVISIBLE);
                        play.setVisibility(View.VISIBLE);
                        stop.setVisibility(View.VISIBLE);
                        myAudioRecorder.prepare();
                        myAudioRecorder.start();
                        Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
                       // record.setVisibility(View.VISIBLE);
                        recName.setVisibility(View.INVISIBLE);
                    }else{
                        Toast.makeText(getApplicationContext(), "Enter name for Recording",Toast.LENGTH_LONG).show();
                    }

                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                record.setEnabled(false);
                stop.setEnabled(true);

               // Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAudioRecorder.stop();
                myAudioRecorder.release();
                myAudioRecorder  = null;

                stop.setEnabled(false);
                play.setEnabled(true);
                record.setEnabled(true);
                recName.setVisibility(View.VISIBLE);

                Toast.makeText(getApplicationContext(), "Audio recorded successfully",Toast.LENGTH_LONG).show();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) throws IllegalArgumentException,SecurityException,IllegalStateException {
                MediaPlayer m = new MediaPlayer();

                try {
                    m.setDataSource(outputFile);
                }

                catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    m.prepare();
                }

                catch (IOException e) {
                    e.printStackTrace();
                }

                m.start();
                Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();
            }
        });


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




}
