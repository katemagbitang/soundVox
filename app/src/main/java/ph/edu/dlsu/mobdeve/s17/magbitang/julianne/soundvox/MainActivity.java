package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters.ProfileAdapter;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters.SoundAdapter;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.FireBaseProfileDB;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.FireBaseSoundDB;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Profile;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Sound;

public class MainActivity extends AppCompatActivity {

    Button btn_selectprofile_menu;
    ImageButton btn_menu;
    private RecyclerView rvSound;
    private RecyclerView.LayoutManager layout;
    private SoundAdapter soundAdapter;
    private ProfileAdapter profileAdapter;
    private ArrayList<Sound> soundArrayList = new ArrayList<>();
    private Integer profileNo = 0;
    private TextView tv_noprofile;
    private RecyclerView rv_soundlist;
    private Button btn_profile_menu;
    private TextView profile_name_label;
    private TextView profile_name_id;
    private Profile profile;

    private boolean profileExists;

    //firebase

    private FireBaseProfileDB profileDB = new FireBaseProfileDB();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileDB.setListener(new FireBaseProfileDB.ChangeListener() {
            @Override
            public void onChange() {
                if(profileDB.getProfiles().size() > 0){
                        profileExists = true;
                        Log.d("HEHE","working");
                }
            }
        });

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

        init();
        tv_noprofile = (TextView)findViewById(R.id.tv_noprofile);
        rv_soundlist = (RecyclerView)findViewById(R.id.rv_soundlist);
        btn_profile_menu = (Button)findViewById(R.id.btn_profile_menu);
        setSoundBtnVisibility(profileExists);
        btn_menu = findViewById(R.id.btn_menu);
        btn_selectprofile_menu = findViewById(R.id.btn_profile_menu);
        this.profile_name_label = findViewById(R.id.tv_profilename_debug);
        this.profile_name_id = findViewById(R.id.tv_profileid_debug);
        profile = new Profile();


//        this.profile_name_label.setText(intent.getStringExtra("name"));
//        this.profile_name_id.setText(String.valueOf(intent.getIntExtra("id",0)));

        btn_menu.setOnClickListener(view -> {
            Intent goToMenu = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(goToMenu);
            finish();
        });

        btn_selectprofile_menu.setOnClickListener(view -> {
            Intent goToSelectProfileMenu = new Intent(MainActivity.this, SelectProfileActivity.class);
            startActivity(goToSelectProfileMenu);
            finish();
        });
    }

    public void setSoundBtnVisibility(boolean profileExists){
        Intent intent = getIntent();
        if(profileExists) {
            this.btn_profile_menu.setText(intent.getStringExtra("name"));
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
        this.soundAdapter = new SoundAdapter(getApplicationContext(),soundArrayList,false,false,false);
        this.rvSound.setAdapter(this.soundAdapter);
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