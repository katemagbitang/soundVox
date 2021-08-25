package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters.ProfileAdapter;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.ProfileDAO;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.ProfileDAOSqlImpl;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.databinding.ActivityMainBinding;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Profile;

public class MenuActivity extends AppCompatActivity {

    private Button record_btn, back_btn, create_btn;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText profileName;
    private Button btn_cancel,btn_save;

    private ProfileAdapter profileAdapter;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        record_btn = findViewById(R.id.menu_item_1_btn);
        back_btn = findViewById(R.id.goback_btn);
        create_btn = findViewById(R.id.menu_item_4_btn);

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
    }

    public void createNewProfile(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.activity_pop_up_create_profile,null);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_save = view.findViewById(R.id.btn_save);
        profileName = (EditText) view.findViewById(R.id.input_profile);

        ProfileDAO profileDAO = new ProfileDAOSqlImpl(getApplicationContext());
        profileAdapter = new ProfileAdapter(getApplicationContext(),profileDAO.getProfiles());

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
                int count = profileDAO.getProfiles().size();
                profile.setId(count);
                profile.setName(profileName.getText().toString());
                profileDAO.createProfile(profile);
                profileAdapter.addProfiles(profileDAO.getProfiles());

                Toast.makeText(getApplicationContext(),"Save was pressed.", Toast.LENGTH_SHORT).show();

                Intent goToMenuProfile = new Intent(MenuActivity.this,MenuProfileActivity.class);
                startActivity(goToMenuProfile);
            }
        });
    }
}