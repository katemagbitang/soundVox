package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters.ProfileAdapter;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters.SoundAdapter;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Profile;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Sound;

public class MainActivity extends AppCompatActivity {

    Button profile_menu_btn;
    ImageButton home_menu_btn;
    private RecyclerView rvSound;
    private RecyclerView.LayoutManager layout;
    private SoundAdapter soundAdapter;
    private ProfileAdapter profileAdapter;
    private ArrayList<Sound> soundArrayList = new ArrayList<>();
    private Integer profileNo = 0;
    TextView tv;
    private TextView profile_name_label;
    private TextView profile_name_id;
    private Profile profile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populate_data();
        init();
        tv = (TextView)findViewById(R.id.tv_noprofile);
        setSoundBtnVisibility(View.VISIBLE);
        home_menu_btn = findViewById(R.id.menu_btn);
        profile_menu_btn = findViewById(R.id.btn_profile_menu);
        this.profile_name_label = findViewById(R.id.profile_name_label);
        this.profile_name_id = findViewById(R.id.profile_name_id);
        profile = new Profile();

//        while ()

        Intent intent = getIntent();
        this.profile_name_label.setText(intent.getStringExtra("name"));
        this.profile_name_id.setText(String.valueOf(intent.getIntExtra("id",0)));


        home_menu_btn.setOnClickListener(view -> {
            Intent goToMenu = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(goToMenu);
        });

        profile_menu_btn.setOnClickListener(view -> {
            Intent goToSelectProfileMenu = new Intent(MainActivity.this, SelectProfileActivity.class);
//            launchMenuProfile.launch(goToProfileMenu);
            startActivity(goToSelectProfileMenu);
        });
    }

    public void setSoundBtnVisibility(int value){
        this.rvSound.setVisibility(value);
        if(value == View.INVISIBLE)
            tv.setVisibility(View.VISIBLE);
        else
            tv.setVisibility(View.INVISIBLE);
    }
    private void populate_data() {
        if (profileNo == 0){
            for(int i = 0; i < 8; i++){
                switch(i) {
                    case 0:
                        this.soundArrayList.add(new Sound("Sound" + i, MediaPlayer.create(this, R.raw.piano_a_major)));
                        break;
                    case 1:
                        this.soundArrayList.add(new Sound("Sound" + i, MediaPlayer.create(this, R.raw.piano_b_major)));
                        break;
                    case 2:
                        this.soundArrayList.add(new Sound("Sound" + i, MediaPlayer.create(this, R.raw.piano_c_major)));
                        break;
                    case 3:
                        this.soundArrayList.add(new Sound("Sound" + i, MediaPlayer.create(this, R.raw.piano_c_sharp)));
                        break;
                    case 4:
                        this.soundArrayList.add(new Sound("Sound" + i, MediaPlayer.create(this, R.raw.piano_d_major)));
                        break;
                    case 5:
                        this.soundArrayList.add(new Sound("Sound" + i, MediaPlayer.create(this, R.raw.piano_e_major)));
                        break;
                    case 6:
                        this.soundArrayList.add(new Sound("Sound" + i, MediaPlayer.create(this, R.raw.piano_f_major)));
                        break;
                    case 7:
                        this.soundArrayList.add(new Sound("Sound" + i, MediaPlayer.create(this, R.raw.piano_g_major)));
                        break;
                }
            }
        }

    }

    private void init(){
        this.rvSound = findViewById(R.id.soundRecyclerView);
        this.layout = new GridLayoutManager(this,4);
        this.rvSound.setLayoutManager(this.layout);
//        SoundDAO soundDAO = new SoundDAOSqlImpl(getApplicationContext());
//        this.soundAdapter = new SoundAdapter(getApplicationContext(),soundDAO.getSounds());
        this.soundAdapter = new SoundAdapter(getApplicationContext(),soundArrayList,0);
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
                            TextView test = findViewById(R.id.profile_label);
                            test.setText(name);
                        }
                    });


}