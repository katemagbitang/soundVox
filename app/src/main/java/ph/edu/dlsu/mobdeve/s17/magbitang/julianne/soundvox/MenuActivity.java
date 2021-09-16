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

import java.util.ArrayList;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters.ProfileAdapter;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.ProfileDAO;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.ProfileDAOSqlImpl;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Profile;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Sound;

public class MenuActivity extends AppCompatActivity {

    private Button record_btn, back_btn, create_btn, edit_btn, delete_btn;

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

        record_btn = findViewById(R.id.menu_item_1_btn);
        back_btn = findViewById(R.id.goback_btn);
        create_btn = findViewById(R.id.menu_item_4_btn);
        edit_btn = findViewById(R.id.menu_item_5_btn);
        delete_btn = findViewById(R.id.menu_item_6_btn);

        record_btn.setOnClickListener(view -> {
            Intent goToRecord = new Intent(MenuActivity.this, RecordingActivity.class);
            startActivity(goToRecord);
        });

        back_btn.setOnClickListener(view -> {
            Intent goToMain = new Intent(MenuActivity.this, MainActivity.class);
            startActivity(goToMain);
        });

        create_btn.setOnClickListener(view -> {
//            Intent goToCreate = new Intent(MenuActivity.this, PopUpCreateProfile.class);
//            startActivity(goToCreate);
            createNewProfile();
        });

        edit_btn.setOnClickListener(view -> {
            Intent goToSelectEditProfile = new Intent(MenuActivity.this, SelectEditProfileActivity.class);
            //0 - edit
            //1 - delete
            //2 - select
            startActivity(goToSelectEditProfile);
        });

        delete_btn.setOnClickListener(view -> {
            Intent goToDeleteProfile = new Intent(MenuActivity.this, SelectDeleteProfileActivity.class);
            startActivity(goToDeleteProfile);
        });
    }

    public void createNewProfile(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.activity_pop_up_create_profile,null);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_save = view.findViewById(R.id.btn_save);
        profileName = (EditText) view.findViewById(R.id.input_profile);

        ProfileDAO profileDAO = new ProfileDAOSqlImpl(getApplicationContext());
        profileAdapter = new ProfileAdapter(getApplicationContext(), profileDAO.getProfiles(), (byte) 2);

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
                Profile profile = new Profile();
//                ArrayList<Sound> newSounds = new ArrayList<>();
//                int count = profileDAO.getProfiles().size();
                int count = profileAdapter.getItemCount();
                Log.d(String.valueOf(count),"profile id");
                profile.setId(count + 1);
                profile.setName(profileName.getText().toString());
//                profile.setSounds(newSounds);
                profileDAO.createProfile(profile);
                profileAdapter.addProfiles(profileDAO.getProfiles());

                Toast.makeText(getApplicationContext(),"Save was pressed.", Toast.LENGTH_SHORT).show();

                Intent goToMainProfile = new Intent(MenuActivity.this, SelectProfileActivity.class);
                startActivity(goToMainProfile);
            }
        });
    }
}