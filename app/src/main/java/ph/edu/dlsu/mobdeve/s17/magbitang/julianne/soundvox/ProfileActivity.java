package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters.ProfileAdapter;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters.SoundAdapter;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.ProfileDAO;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.ProfileDAOSqlImpl;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.SoundDAO;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.SoundDAOSqlImpl;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Profile;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Sound;

public class ProfileActivity extends AppCompatActivity {

    Button back_btn;
    private SoundAdapter soundAdapter;
//    private ArrayList<Sound> soundArrayList = new ArrayList<>();
    private RecyclerView rvSound;
    private RecyclerView.LayoutManager layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//        populate_data();

        init();

        back_btn = findViewById(R.id.goback_btn);

        back_btn.setOnClickListener(view -> {
            Intent goToMenu = new Intent(ProfileActivity.this, MenuActivity.class);
            startActivity(goToMenu);
        });
    }

//    private void populate_data() {
////        this.profileArrayList = new ArrayList<>();
//
//        this.soundArrayList.add(new Sound("Sound 1"));
//        this.soundArrayList.add(new Sound("Sound 2"));
//        this.soundArrayList.add(new Sound("Sound 3"));
//        this.soundArrayList.add(new Sound("Sound 4"));
//        this.soundArrayList.add(new Sound("Sound 5"));
//        this.soundArrayList.add(new Sound("Sound 6"));
//        this.soundArrayList.add(new Sound("Sound 7"));
//        this.soundArrayList.add(new Sound("Sound 8"));
//    }

    private void init(){
        this.rvSound = findViewById(R.id.soundRecyclerView);
        this.layout = new GridLayoutManager(this,4);
        this.rvSound.setLayoutManager(this.layout);
        SoundDAO soundDAO = new SoundDAOSqlImpl(getApplicationContext());
        this.soundAdapter = new SoundAdapter(getApplicationContext(),soundDAO.getSounds());
        this.rvSound.setAdapter(this.soundAdapter);
    }

}