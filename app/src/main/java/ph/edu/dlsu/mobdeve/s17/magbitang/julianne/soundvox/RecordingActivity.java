package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters.SoundAdapter;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.FireBaseSoundDB;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Sound;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.SoundFB;

public class RecordingActivity extends AppCompatActivity {

    private static final String LOG_TAG = "AudioRecordTest";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    private Button back_btn;
    private Button record_btn;
    private Button listen_btn;
    private Button stop_record_btn;
    private Button stop_listen_btn;
    private Button save_btn;

    private Button dialog_cancel;
    private Button dialog_save;
    private EditText dialog_soundName;


    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
//    private TextView record_label;
    private String fileName = null;

//    private RecordButton recordButton = null;
    private MediaRecorder recorder = null;

//    private PlayButton playButton = null;
    private MediaPlayer player = null;

    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private SoundAdapter soundAdapter;
    private ArrayList<SoundFB> soundArrayList = new ArrayList<>();
    private FireBaseSoundDB soundDB;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();

    }

//    private void onRecord(boolean start) {
//        if (start) {
//            startRecording();
//        } else {
//            stopRecording();
//        }
//    }
//
//    private void onPlay(boolean start) {
//        if (start) {
//            startPlaying();
//        } else {
//            stopPlaying();
//        }
//    }

    private void startPlaying() {
        player = new MediaPlayer();
        try {
            player.setDataSource(fileName);
            player.prepare();
            player.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        player.release();
        player = null;
    }

    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        recorder.start();
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
    }


//    class RecordButton extends androidx.appcompat.widget.AppCompatButton {
//        boolean mStartRecording = true;
//
//        OnClickListener clicker = new OnClickListener() {
//            public void onClick(View v) {
//                onRecord(mStartRecording);
//                if (mStartRecording) {
//                    setText("Stop recording");
//                } else {
//                    setText("Start recording");
//                }
//                mStartRecording = !mStartRecording;
//            }
//        };
//
//        public RecordButton(Context ctx) {
//            super(ctx);
//            setText("Start recording");
//            setOnClickListener(clicker);
//        }
//
//
//    }
//
//    class PlayButton extends androidx.appcompat.widget.AppCompatButton {
//        boolean mStartPlaying = true;
//
//        OnClickListener clicker = new OnClickListener() {
//            public void onClick(View v) {
//                onPlay(mStartPlaying);
//                if (mStartPlaying) {
//                    setText("Stop playing");
//                } else {
//                    setText("Start playing");
//                }
//                mStartPlaying = !mStartPlaying;
//            }
//        };
//
//        public PlayButton(Context ctx) {
//            super(ctx);
//            setText("Start playing");
//            setOnClickListener(clicker);
//        }
//
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Record to the external cache directory for visibility
        Date date = new java.util.Date();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(date.getTime());
        fileName = getExternalCacheDir().getAbsolutePath();
        fileName += "/"+timeStamp+".3gp";

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
        setContentView(R.layout.activity_recording);

//        recordButton = new RecordButton(this);
//        playButton = new PlayButton(this);

        back_btn = findViewById(R.id.btn_back);
        record_btn = findViewById(R.id.btn_record);
        listen_btn = findViewById(R.id.btn_listen);
        stop_record_btn = findViewById(R.id.btn_stop_record);
        stop_listen_btn = findViewById(R.id.btn_stop_listen);
        save_btn = findViewById(R.id.save_record_btn);
//        record_btn.setOnClickListener(recordButton.clicker);
//        listen_btn.setOnClickListener(playButton.clicker);
        record_btn.setOnClickListener(view -> {
            startRecording();
//            record_btn.setVisibility(View.INVISIBLE);
        });

        stop_record_btn.setOnClickListener(view -> {
            stopRecording();
        });

        listen_btn.setOnClickListener(view -> {
            startPlaying();
        });

        stop_listen_btn.setOnClickListener(view -> {
            stopPlaying();
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToMenu = new Intent(RecordingActivity.this, MenuActivity.class);
                startActivity(goToMenu);
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewSound();
            }
        });
    }

    public void createNewSound(){
        /*INIT DIALOG*/
        dialogBuilder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.activity_pop_up_add_sound,null);
        dialog_cancel = view.findViewById(R.id.btn_cancelSound);
        dialog_save = view.findViewById(R.id.btn_saveSound);
        dialog_soundName = (EditText) view.findViewById(R.id.input_sound);

        /*START DIALOG*/
        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        dialog.show();

        /*CANCEL BUTTON*/
        dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                soundAdapter = new SoundAdapter(getApplicationContext(),soundArrayList,false,true,false);
                SoundFB newSound = new SoundFB(dialog_soundName.getText().toString(),fileName);
                soundDB = new FireBaseSoundDB();
                soundDB.addSounds(newSound);
//                soundAdapter.addSounds(soundArrayList);

                Toast.makeText(getApplicationContext(),"Save was pressed.", Toast.LENGTH_SHORT).show();

                Intent goToRecord = new Intent();
                goToRecord.putExtra("name", dialog_soundName.getText().toString());
                goToRecord.putExtra("soundref",fileName);
                soundDB.destroyDBInstance();
                dialog.dismiss();
                setResult(RESULT_OK, goToRecord);
                finish();
            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        if (recorder != null) {
            recorder.release();
            recorder = null;
        }

        if (player != null) {
            player.release();
            player = null;
        }
    }
}