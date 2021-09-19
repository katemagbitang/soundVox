package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters.ProfileAdapter;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters.SoundAdapter;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.FireBaseSoundDB;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.SoundDAO;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.SoundDAOSqlImpl;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Sound;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.SoundFB;

public class AddSoundsActivity extends AppCompatActivity {

    private Button back_btn;
    private TextView tv_label;
    private SoundAdapter openSoundsAdapter;
    private RecyclerView rv_opensounds;
    private RecyclerView.LayoutManager layout;
    private ArrayList<SoundFB> soundArrayList = new ArrayList<>();
    private TextView profile_name_id;
    private TextView pathFile;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_GALLERY = 200;
    private SoundAdapter soundAdapter;
    private boolean deleteState = false;
//    private Profile profile = new Profile();

    FireBaseSoundDB soundDB = new FireBaseSoundDB();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuprofile);

        init();
        back_btn = findViewById(R.id.btn_back);
        tv_label = findViewById(R.id.tv_label);
        tv_label.setText("Select a sound to add");

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
                filePicker();
            } else {
                requestPermission();
                filePicker();
            }
        }
        else{
             filePicker();
        }

        back_btn.setOnClickListener(view -> {
            Intent goToEdit = new Intent(AddSoundsActivity.this, EditProfileActivity.class);
            startActivity(goToEdit);
            soundDB.destroyDBInstance();
            finish();
        });

    }

    private void init(){
        this.rv_opensounds = findViewById(R.id.rv_profile);
        this.layout = new LinearLayoutManager(this);
        this.rv_opensounds.setLayoutManager(this.layout);
//        ProfileDAO profileDAO = new ProfileDAOSqlImpl(getApplicationContext());
        this.openSoundsAdapter = new SoundAdapter(getApplicationContext(),soundArrayList,false,true,true);
        this.rv_opensounds.setAdapter(this.openSoundsAdapter);
        this.rv_opensounds.setVisibility(View.VISIBLE);
        this.profile_name_id = findViewById(R.id.tv_profileid_debug);
        this.pathFile = findViewById(R.id.pathOfFilePicked);
    }

    /*FILE PICKER REQ PERMISSION*/
    private void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(AddSoundsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            Toast.makeText(AddSoundsActivity.this, "Please give permission to upload file", Toast.LENGTH_SHORT).show();
        }
        else{
            ActivityCompat.requestPermissions(AddSoundsActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
        }
    }
    /*FILE PICKER CHECK PERMISSION*/
    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(AddSoundsActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else{
            return false;
        }
    }
    /*FILE PICKER */
    private void filePicker(){
        Toast.makeText(AddSoundsActivity.this,"File Picker Call", Toast.LENGTH_SHORT).show();
        Intent opengallery = new Intent(Intent.ACTION_GET_CONTENT);
        opengallery.setType("*/*");
        startActivityForResult(opengallery,REQUEST_GALLERY);
    }

    /*FILE PICKER */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(AddSoundsActivity.this,"Permission Successful",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(AddSoundsActivity.this,"Permission Failed",Toast.LENGTH_SHORT).show();
                }
        }
    }

    /* FILE PICKER RESULT*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("MAMA", "MAMA");

        if (requestCode == REQUEST_GALLERY && resultCode == Activity.RESULT_OK){
            String path = data.getData().getPath();
//            String filePath = getRealPathFromUri(data.getData(),EditProfileActivity.this);
            Log.d("File Path: ",""+path);

            this.pathFile.setText(path);

            Log.d("path", pathFile + "");

            String label = path.substring(path.lastIndexOf('/') + 1);
            int pos = label.lastIndexOf(".");
            if (pos > 0) {
                label = label.substring(0, pos);
            }

            SoundFB sound = new SoundFB(label, path);
            soundDB.addSounds(sound);


            Toast.makeText(getApplicationContext(),"Sound was stored.", Toast.LENGTH_SHORT).show();

//            this.soundArrayList.add(new Sound("Test Sound",path));
            this.soundAdapter = new SoundAdapter(getApplicationContext(),soundArrayList,deleteState,false,false);
            this.rv_opensounds.setAdapter(this.soundAdapter);

        }
    }

    public String getRealPathFromUri(Uri uri, Activity activity){
        Cursor cursor = activity.getContentResolver().query(uri, null, null, null, null);
        if (cursor == null){
            return uri.getPath();
        }
        else{
            cursor.moveToFirst();
            int id = cursor.getColumnIndex(MediaStore.Audio.AudioColumns._ID);
            return cursor.getString(id);
        }
    }

}
