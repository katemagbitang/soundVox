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
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters.SoundAdapter;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.SoundDAO;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.SoundDAOSqlImpl;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Profile;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Sound;

public class MainActivity extends AppCompatActivity {

    Button profile_menu_btn;
    ImageButton home_menu_btn;
    private RecyclerView rvSound;
    private RecyclerView.LayoutManager layout;
    private SoundAdapter soundAdapter;
    private ArrayList<Sound> soundArrayList = new ArrayList<>();
    private Integer profileNo = null;
    TextView tv;




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

        home_menu_btn.setOnClickListener(view -> {
            Intent goToMenu = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(goToMenu);
        });

        profile_menu_btn.setOnClickListener(view -> {
            Intent goToProfileMenu = new Intent(MainActivity.this, MenuProfileActivity.class);
//            launchMenuProfile.launch(goToProfileMenu);
            startActivity(goToProfileMenu);
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

        if (profileNo == null){

            for(int i = 0; i < 8; i++)
                this.soundArrayList.add(new Sound("Sound" + i));
        }

    }

    private void init(){
        this.rvSound = findViewById(R.id.soundRecyclerView);
        this.layout = new GridLayoutManager(this,4);
        this.rvSound.setLayoutManager(this.layout);
        SoundDAO soundDAO = new SoundDAOSqlImpl(getApplicationContext());
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