package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters.ProfileAdapter;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters.SoundAdapter;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.FireBaseProfileDB;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.ProfileFB;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.SoundFB;

public class MainActivity extends AppCompatActivity {

    Button btn_selectprofile_menu;
    ImageButton btn_menu;
    private RecyclerView rvSound;
    private RecyclerView.LayoutManager layout;
    private SoundAdapter soundAdapter;
    private ProfileAdapter profileAdapter;
    private ArrayList<SoundFB> soundArrayList = new ArrayList<>();
    private Integer profileNo = 0;
    private TextView tv_noprofile;
    private RecyclerView rv_soundlist;
    private Button btn_profile_menu;
    private TextView profile_name_label;
    private TextView profile_name_id;
    private Profile profile;

    private ArrayList<ProfileFB> Profiles = new ArrayList<>();

    private boolean profileExists;

    //firebase

    private FireBaseProfileDB profileDB = new FireBaseProfileDB();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        /*
        FireBaseSoundDB soundDB = new FireBaseSoundDB();
        soundDB.init();
        soundDB.destroyDBInstance();
        */

        /*IF SELECTED PROFILE EXISTS*/
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

        /* INITIALIZE */
        init();

        /*ON CLICK FOR MENU BAR*/
        btn_menu.setOnClickListener(view -> {
            Intent goToMenu = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(goToMenu);
            finish();
        });

        /* ON CLICK FOR SELECT PROFILE */
        btn_selectprofile_menu.setOnClickListener(view -> {
                Intent goToSelectProfileMenu = new Intent(MainActivity.this, SelectProfileActivity.class);

                /*Pass Loaded Users*/
                Bundle args = new Bundle();
                args.putSerializable("PROFILES",(Serializable)Profiles);
                goToSelectProfileMenu.putExtra("BUNDLE",args);
                profileDB.destroyDBInstance();

                startActivity(goToSelectProfileMenu);
                finish();
        });
    }

    public void setSoundBtnVisibility(boolean profileExists){
        Intent intent = getIntent();
        if(profileExists) {
            this.btn_profile_menu.setText(intent.getStringExtra("name"));
            soundArrayList = (ArrayList<SoundFB>) intent.getSerializableExtra("sounds");
            tv_noprofile.setVisibility(View.INVISIBLE);
            rv_soundlist.setVisibility(View.VISIBLE);
        }
        else{
            tv_noprofile.setVisibility(View.VISIBLE);
            rv_soundlist.setVisibility(View.INVISIBLE);
        }

    }
    private void init(){

        this.rvSound = findViewById(R.id.rv_soundlist);
        this.layout = new GridLayoutManager(this,4);
        this.rvSound.setLayoutManager(this.layout);
        if(profileExists) {
            Intent intent = getIntent();
            soundArrayList = (ArrayList<SoundFB>) intent.getSerializableExtra("sounds");
        }
        this.soundAdapter = new SoundAdapter(getApplicationContext(), soundArrayList,null,false,false,false);
        this.rvSound.setAdapter(this.soundAdapter);

        tv_noprofile = (TextView)findViewById(R.id.tv_noprofile);
        rv_soundlist = (RecyclerView)findViewById(R.id.rv_soundlist);
        btn_profile_menu = (Button)findViewById(R.id.btn_profile_menu);

        btn_menu = findViewById(R.id.btn_menu);
        btn_selectprofile_menu = findViewById(R.id.btn_profile_menu);
        this.profile_name_label = findViewById(R.id.tv_profilename_debug);
        this.profile_name_id = findViewById(R.id.tv_profileid_debug);
        profile = new Profile();

        setSoundBtnVisibility(profileExists);
        // DEBUG
        //        this.profile_name_label.setText(intent.getStringExtra("name"));
        //        this.profile_name_id.setText(String.valueOf(intent.getIntExtra("id",0)));
    }

    private ActivityResultLauncher<Intent> launchMenuProfile =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            Intent intent = result.getData();
                            Profile profile = new Profile();
                            String name = intent.getStringExtra("name");
                            int id = intent.getIntExtra("id",0);
//                            if (profile.getId() < 5){
//                                if (profile.getSounds().isEmpty()){
//                                    rvSound.setVisibility(View.INVISIBLE);
//                                }
//                                else{
//                                    rvSound.setVisibility(View.VISIBLE);
//                                }
//                            }
                            TextView test = findViewById(R.id.btn_profile_name);
                            test.setText(name);
                        }
                    });


}