package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox;

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
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters.ProfileAdapter;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters.SoundAdapter;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.FireBaseProfileDB;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.FireBaseSoundDB;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.ProfileDAO;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.ProfileDAOSqlImpl;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.SoundDAO;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.SoundDAOSqlImpl;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.ProfileFB;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Sound;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.SoundFB;

public class EditProfileActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_GALLERY = 200;
    Button back_btn, trash_btn, add_btn;
    private SoundAdapter soundAdapter;
    private ProfileAdapter profileAdapter;
    private ArrayList<SoundFB> soundArrayList = new ArrayList<>();
    private RecyclerView rvSound;
    private RecyclerView.LayoutManager layout;
    private boolean deleteState = false;
    private boolean addState = false;
//    private Integer profileNo = null;
    Intent myFileIntent;
    private TextView profile_name_label;
    private ArrayList<ProfileFB> Profiles = new ArrayList<>();
    private ProfileFB profile;
    private FireBaseProfileDB fireBaseProfileDB = new FireBaseProfileDB();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        /* SET CURRENT USER */
        Intent intent = getIntent();
        profile = (ProfileFB) intent.getSerializableExtra("profile");
        soundArrayList = profile.getSounds();

        init();

        /*ON CLICK BACK*/
        back_btn.setOnClickListener(view -> {
            Intent goToMenu = new Intent(EditProfileActivity.this, MenuActivity.class);
            startActivity(goToMenu);
            finish();
        });

        /*ON CLICK DELETE*/
        trash_btn.setOnClickListener(view -> {
            if(deleteState)
                deleteState = false;
            else
                deleteState = true;

            this.soundAdapter = new SoundAdapter(getApplicationContext(),
                    soundArrayList,
                    profile,
                    deleteState,
                    false,false);

            this.rvSound.setAdapter(this.soundAdapter);
        });

        /*ON CLICK ADD*/
        add_btn.setOnClickListener(view -> {
            //Intent goToOpenSounds = new Intent(EditProfileActivity.this, AddSoundsActivity.class);
            //startActivity(goToOpenSounds);
        });
    }

//    private void populate_data() {
//        if (profileNo == null){
//            for(int i = 0; i < 9; i++)
//                this.soundArrayList.add(new Sound("Sound" + i));
//        }
//
//    }

    private void init(){
        this.rvSound = findViewById(R.id.rv_soundlist);
        this.layout = new GridLayoutManager(this,4);

        this.rvSound.setLayoutManager(this.layout);
        this.soundAdapter = new SoundAdapter(getApplicationContext(),soundArrayList,profile,deleteState,false,false);
        this.rvSound.setAdapter(this.soundAdapter);


        this.rvSound.setVisibility(View.VISIBLE);
        back_btn = findViewById(R.id.btn_back);
        trash_btn = findViewById(R.id.trash_btn);
        add_btn = findViewById(R.id.add_btn);
        this.profile_name_label = findViewById(R.id.tv_profilename_debug);
        this.profile_name_label.setText(profile.getName());

        ProfileAdapter profileAdapter = new ProfileAdapter(getApplicationContext(), Profiles, (byte) 0);

    }


}