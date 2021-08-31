package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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

    Button back_btn, trash_btn;
    private SoundAdapter soundAdapter;
    private ArrayList<Sound> soundArrayList = new ArrayList<>();
    private RecyclerView rvSound;
    private RecyclerView.LayoutManager layout;
    private boolean deleteState = false;
    private Integer profileNo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        populate_data();
        init();
        this.rvSound.setVisibility(View.VISIBLE);
        back_btn = findViewById(R.id.goback_btn);
        trash_btn = findViewById(R.id.trash_btn);

        back_btn.setOnClickListener(view -> {
            Intent goToMenu = new Intent(ProfileActivity.this, MenuActivity.class);
            startActivity(goToMenu);
        });
        trash_btn.setOnClickListener(view -> {
            if(deleteState){
                this.soundAdapter = new SoundAdapter(getApplicationContext(),soundArrayList,0);
                this.rvSound.setAdapter(this.soundAdapter);
                deleteState = false;
            }
            else{
                this.soundAdapter = new SoundAdapter(getApplicationContext(),soundArrayList,1);
                this.rvSound.setAdapter(this.soundAdapter);
                deleteState = true;
            }
        });
    }

    private void populate_data() {
        if (profileNo == null){
            for(int i = 0; i < 9; i++)
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

}