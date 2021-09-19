package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters.ProfileAdapter;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters.SoundAdapter;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.FireBaseProfileDB;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.ProfileDAO;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.ProfileDAOSqlImpl;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Profile;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.ProfileFB;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Sound;

public class MenuActivity extends AppCompatActivity {

    private Button btn_back, btn_record, btn_upload, btn_open, btn_create, btn_edit, btn_delete;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText profileName;
    private Button btn_cancel,btn_save;
    private ArrayList<ProfileFB> Profiles = new ArrayList<>();
    private ArrayList<Sound> sounds = new ArrayList<>();
    private ProfileAdapter profileAdapter;
    private SoundAdapter soundAdapter;
//    private ActivityMainBinding binding;

    private FireBaseProfileDB profileDB = new FireBaseProfileDB();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        /*LISTEN FOR PROFILE DB UPDATES*/
        profileDB.setListener(new FireBaseProfileDB.ChangeListener() {
            @Override
            public void onChange() {
                if(profileDB.getProfiles().size() > 0){
                    Profiles = profileDB.getProfiles();
                    Log.d("HEHE","working");
                }
            }
        });

        btn_back = findViewById(R.id.btn_back);
        btn_record = findViewById(R.id.btn_smenu_1);
        btn_upload = findViewById(R.id.btn_smenu_2);
        btn_open = findViewById(R.id.btn_smenu_3);
        btn_create = findViewById(R.id.btn_smenu_4);
        btn_edit = findViewById(R.id.btn_smenu_5);
        btn_delete = findViewById(R.id.btn_smenu_6);

        btn_back.setOnClickListener(view -> {
            Intent goToMain = new Intent(MenuActivity.this, MainActivity.class);
            startActivity(goToMain);
            profileDB.destroyDBInstance();
            finish();
        });

        btn_record.setOnClickListener(view -> {
            Intent goToRecord = new Intent(MenuActivity.this, RecordingActivity.class);
            startActivity(goToRecord);
            profileDB.destroyDBInstance();
            finish();
        });

        btn_upload.setOnClickListener(view -> {
            //TO DO: this is where the upload goes to using file picker
            Intent goToUpload = new Intent(MenuActivity.this, AddSoundsActivity.class);
            startActivity(goToUpload);
            profileDB.destroyDBInstance();
            finish();
        });

        btn_open.setOnClickListener(view -> {
            Intent goToRecord = new Intent(MenuActivity.this, SelectAllSoundsActivity.class);
            startActivityForResult (goToRecord, 911);
            profileDB.destroyDBInstance();
//            finish();
        });

        btn_create.setOnClickListener(view -> {
            createNewProfile();
        });

        btn_edit.setOnClickListener(view -> {
                Intent goToSelectEditProfile = new Intent(MenuActivity.this, SelectEditProfileActivity.class);

                /*Pass Loaded Users*/
                Bundle args = new Bundle();
                args.putSerializable("PROFILES",(Serializable)Profiles);
                goToSelectEditProfile.putExtra("BUNDLE",args);

                startActivity(goToSelectEditProfile);
                profileDB.destroyDBInstance();
                finish();
        });

        btn_delete.setOnClickListener(view -> {
            Intent goToDeleteProfile = new Intent(MenuActivity.this, SelectDeleteProfileActivity.class);

            /*Pass Loaded Users*/
            Bundle args = new Bundle();
            args.putSerializable("PROFILES",(Serializable)Profiles);
            goToDeleteProfile.putExtra("BUNDLE",args);

            profileDB.destroyDBInstance();
            startActivity(goToDeleteProfile);
            finish();
        });
    }

    public void createNewProfile(){

        /*INIT DIALOG*/
        dialogBuilder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.activity_pop_up_create_profile,null);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_save = view.findViewById(R.id.btn_save);
        profileName = (EditText) view.findViewById(R.id.input_profile);

        /*START DIALOG*/
        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        dialog.show();

        /*CANCEL BUTTON*/
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        /*SAVE USER*/
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save to DB
                FireBaseProfileDB profileDB = new FireBaseProfileDB();

                // Init Profile
                ProfileFB profile = new ProfileFB(profileName.getText().toString(), profileDB.defaultMusic());
                profileDB.addProfile(profile);

                Toast.makeText(getApplicationContext(),"Save was pressed.", Toast.LENGTH_SHORT).show();

                //Go to Edit Profile
                Intent goToEditProfile = new Intent(MenuActivity.this, EditProfileActivity.class);
                goToEditProfile.putExtra("profile", profile);
                profileDB.destroyDBInstance();
                dialog.dismiss();
                startActivity(goToEditProfile);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == 911) {

            String name = data.getStringExtra("name");
            String soundref = data.getStringExtra("soundref");

            sounds.add(0, new Sound(name,soundref));

//            Bundle args = new Bundle();
//            args.putSerializable("SOUNDS",(Serializable)sounds);

            Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
        }
    }
}