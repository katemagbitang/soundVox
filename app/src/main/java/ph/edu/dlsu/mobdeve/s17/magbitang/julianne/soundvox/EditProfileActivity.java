package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters.ProfileAdapter;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters.SelectProfileAdapter;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters.SoundAdapter;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.ProfileDAO;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.ProfileDAOSqlImpl;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.SoundDAO;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.SoundDAOSqlImpl;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Profile;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Sound;

public class EditProfileActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_GALLERY = 200;
    Button back_btn, trash_btn, add_btn;
    private SoundAdapter soundAdapter;
    private ProfileAdapter profileAdapter;
    private ArrayList<Sound> soundArrayList = new ArrayList<>();
    private RecyclerView rvSound;
    private RecyclerView.LayoutManager layout;
    private boolean deleteState = false;
    private boolean addState = false;
    private Integer profileNo = null;
    Intent myFileIntent;
    private TextView profile_name_label;
    private TextView profile_name_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        populate_data();
        init();
        this.rvSound.setVisibility(View.VISIBLE);
        back_btn = findViewById(R.id.goback_btn);
        trash_btn = findViewById(R.id.trash_btn);
        add_btn = findViewById(R.id.add_btn);
        this.profile_name_label = findViewById(R.id.profile_name_label);
        this.profile_name_id = findViewById(R.id.profile_name_id);

        Intent intent = getIntent();
        this.profile_name_label.setText(intent.getStringExtra("name"));
        this.profile_name_id.setText(intent.getStringExtra("id"));

        ProfileDAO profileDAO = new ProfileDAOSqlImpl(getApplicationContext());
        ProfileAdapter profileAdapter = new ProfileAdapter(getApplicationContext(),profileDAO.getProfiles());
        SelectProfileAdapter selectProfileAdapter = new SelectProfileAdapter(getApplicationContext(),profileDAO.getProfiles());

        back_btn.setOnClickListener(view -> {
            Intent goToMenu = new Intent(EditProfileActivity.this, MenuActivity.class);
            startActivity(goToMenu);
        });
        trash_btn.setOnClickListener(view -> {
            if(deleteState){
                this.soundAdapter = new SoundAdapter(getApplicationContext(),soundArrayList,0);
                this.rvSound.setAdapter(this.soundAdapter);
                deleteState = false;
            }
            else{
                this.soundAdapter = new SoundAdapter(getApplicationContext(),soundArrayList,1);
                this.rvSound.setAdapter(this.soundAdapter);
                deleteState = true;
            }


            int status = profileDAO.deleteProfile(Integer.parseInt(profile_name_id.getText().toString()));
            if (status > 0){
//                profileAdapter.removeProfile(Integer.parseInt(profile_name_label.getText().toString()));
                profileAdapter.removeProfile(Integer.parseInt(profile_name_id.getText().toString()));
                selectProfileAdapter.removeProfile(Integer.parseInt(profile_name_id.getText().toString()));
                profileAdapter.addProfiles(profileDAO.getProfiles());
                selectProfileAdapter.addProfiles(profileDAO.getProfiles());
            }
            else{
                Toast.makeText(getApplicationContext(),"Profile not found", Toast.LENGTH_SHORT).show();
            }

            Intent goToMain = new Intent(EditProfileActivity.this, MainActivity.class);
            startActivity(goToMain);
        });

        add_btn.setOnClickListener(view -> {
            if (Build.VERSION.SDK_INT >= 23){
                if (checkPermission()){
                    filePicker();
                }
                else{
                    requestPermission();
                }
            }
            else{
                filePicker();
            }
        });
    }

    private void populate_data() {
        if (profileNo == null){
            for(int i = 0; i < 9; i++)
                this.soundArrayList.add(new Sound("Sound" + i));
        }

    }

    private void init(){
        this.rvSound = findViewById(R.id.soundRecyclerView);
        this.layout = new GridLayoutManager(this,4);
        this.rvSound.setLayoutManager(this.layout);
        SoundDAO soundDAO = new SoundDAOSqlImpl(getApplicationContext());
//        this.soundAdapter = new SoundAdapter(getApplicationContext(),soundDAO.getSounds());
        this.soundAdapter = new SoundAdapter(getApplicationContext(),soundArrayList,0);
        this.rvSound.setAdapter(this.soundAdapter);
    }

    private void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(EditProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            Toast.makeText(EditProfileActivity.this, "Please give permission to upload file", Toast.LENGTH_SHORT).show();
        }
        else{
            ActivityCompat.requestPermissions(EditProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
        }
    }

    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(EditProfileActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else{
            return false;
        }
    }

    private void filePicker(){
        Toast.makeText(EditProfileActivity.this,"File Picker Call", Toast.LENGTH_SHORT).show();
        Intent opengallery = new Intent(Intent.ACTION_PICK);
        opengallery.setType("*/*");
        startActivityForResult(opengallery,REQUEST_GALLERY);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(EditProfileActivity.this,"Permission Successful",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(EditProfileActivity.this,"Permission Failed",Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY && resultCode == Activity.RESULT_OK){
            String filePath = getRealPathFromUri(data.getData(),EditProfileActivity.this);
            Log.d("File Path: ",""+filePath);
            this.soundArrayList.add(new Sound("Test Sound",filePath));
            this.soundAdapter = new SoundAdapter(getApplicationContext(),soundArrayList,0);
            this.rvSound.setAdapter(this.soundAdapter);
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