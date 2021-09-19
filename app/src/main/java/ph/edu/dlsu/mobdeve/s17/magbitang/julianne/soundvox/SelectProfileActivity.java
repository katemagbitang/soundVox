package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters.ProfileAdapter;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.ProfileFB;

public class SelectProfileActivity extends AppCompatActivity {

    private Button back_btn;
    private TextView tv_label;
    private ProfileAdapter profileAdapter;
    private RecyclerView rvProfile;
    private RecyclerView.LayoutManager layout;
    private boolean profileExists;
    private ArrayList<ProfileFB> Profiles = new ArrayList<>();
//    private Profile profile = new Profile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuprofile);

        /* CHECK IF USER SELECTED */
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                profileExists = false;
            } else {
                profileExists = extras.getBoolean("SPM_BOOL");
            }
        } else {
            profileExists = (Boolean) savedInstanceState.getSerializable("SPM_BOOL");
        }

        /*GET USERS*/
        Bundle args = getIntent().getBundleExtra("BUNDLE");
        Profiles = (ArrayList<ProfileFB>) args.getSerializable("PROFILES");

        init();

        /*SELECT PROFILE BUTTON, MOVE BACK TO MAIN AFTER SELECTING*/
        back_btn.setOnClickListener(view -> {
            Intent goToMain = new Intent(SelectProfileActivity.this, MainActivity.class);
            startActivity(goToMain);
            finish();
        });

    }

    private void init(){
        this.rvProfile = findViewById(R.id.rv_profile);
        this.layout = new LinearLayoutManager(this);
        this.rvProfile.setLayoutManager(this.layout);
        ProfileDAO profileDAO = new ProfileDAOSqlImpl(getApplicationContext());
        this.profileAdapter = new ProfileAdapter(getApplicationContext(), Profiles, (byte) 2);
        this.rvProfile.setAdapter(this.profileAdapter);

        back_btn = findViewById(R.id.btn_back);
        tv_label = findViewById(R.id.tv_label);
        tv_label.setText("Select a profile to play");
    }

}