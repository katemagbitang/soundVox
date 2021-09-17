package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters.ProfileAdapter;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.FireBaseProfileDB;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.ProfileDAO;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.ProfileDAOSqlImpl;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Profile;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.ProfileFB;

public class MenuActivity extends AppCompatActivity {

    private Button btn_back, btn_record, btn_upload, btn_open, btn_create, btn_edit, btn_delete;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText profileName;
    private Button btn_cancel,btn_save;

    private ProfileAdapter profileAdapter;
//    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


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
            finish();
        });

        btn_record.setOnClickListener(view -> {
            Intent goToRecord = new Intent(MenuActivity.this, RecordingActivity.class);
            startActivity(goToRecord);
            finish();
        });

        btn_upload.setOnClickListener(view -> {
            //TO DO: this is where the upload goes to using file picker
        });

        btn_open.setOnClickListener(view -> {
            Intent goToRecord = new Intent(MenuActivity.this, SelectAllSoundsActivity.class);
            startActivity(goToRecord);
            finish();
        });

        btn_create.setOnClickListener(view -> {
            createNewProfile();
        });

        btn_edit.setOnClickListener(view -> {
            Intent goToSelectEditProfile = new Intent(MenuActivity.this, SelectEditProfileActivity.class);
            startActivity(goToSelectEditProfile);
            finish();
        });

        btn_delete.setOnClickListener(view -> {
            Intent goToDeleteProfile = new Intent(MenuActivity.this, SelectDeleteProfileActivity.class);
            startActivity(goToDeleteProfile);
            finish();
        });
    }

    public void createNewProfile(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.activity_pop_up_create_profile,null);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_save = view.findViewById(R.id.btn_save);
        profileName = (EditText) view.findViewById(R.id.input_profile);

//        ProfileDAO profileDAO = new ProfileDAOSqlImpl(getApplicationContext());
//        profileAdapter = new ProfileAdapter(getApplicationContext(), profileDAO.getProfiles(), (byte) 2);

        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        dialog.show();

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // insert save profile name functions here
                ProfileFB profile = new ProfileFB(profileName.getText().toString());
                FireBaseProfileDB profileDB = new FireBaseProfileDB();
                profileDB.addProfile(profile);
//                ArrayList<Sound> newSounds = new ArrayList<>();
//                int count = profileDAO.getProfiles().size();
//                int count = profileAdapter.getItemCount();
//                Log.d(String.valueOf(count),"profile id");
//                profile.setId(count + 1);
//                profile.setName(profileName.getText().toString());
////                profile.setSounds(newSounds);
//
//                profileDAO.createProfile(profile);
//                profileAdapter.addProfiles(profileDAO.getProfiles());

                Toast.makeText(getApplicationContext(),"Save was pressed.", Toast.LENGTH_SHORT).show();

                Intent goToMainProfile = new Intent(MenuActivity.this, EditProfileActivity.class);
                startActivity(goToMainProfile);
            }
        });
    }
}