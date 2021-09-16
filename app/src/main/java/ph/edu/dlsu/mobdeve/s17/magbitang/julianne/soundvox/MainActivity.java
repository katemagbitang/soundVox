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

import com.google.android.gms.common.util.Strings;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    //firebase
    private DatabaseReference mDatabase;
// ...
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance("https://soundvox-data-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

        this.soundArrayList.add(new Sound("Sound 1", MediaPlayer.create(this, R.raw.piano_a_major)));
        this.soundArrayList.add(new Sound("Sound 2", MediaPlayer.create(this, R.raw.piano_b_major)));
        this.soundArrayList.add(new Sound("Sound 3", MediaPlayer.create(this, R.raw.piano_c_major)));
//        populate_data();
        ArrayList<String> stringsDB = new ArrayList<String>();
        for(int i = 0; i < 3; i++){
            stringsDB.add(soundArrayList.get(i).toString());
        }
        mDatabase.child("sound_database").child("profile").setValue(stringsDB);





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