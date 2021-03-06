package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters.SoundAdapter;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.FireBaseSoundDB;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.ProfileFB;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.SoundFB;

public class AddSongsEditViewActivity extends AppCompatActivity {

    private Button back_btn;
    private TextView tv_label;
    private SoundAdapter openSoundsAdapter;
    private RecyclerView rv_opensounds;
    private RecyclerView.LayoutManager layout;
    private ArrayList<SoundFB> soundArrayList = new ArrayList<>();
    private ProfileFB profile;

    FireBaseSoundDB soundDB = new FireBaseSoundDB();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuprofile);
        Intent intent = getIntent();
        Log.d("hello",""+(ProfileFB) intent.getSerializableExtra("profile"));
        if((ProfileFB) intent.getSerializableExtra("profile") != null){
            Log.d("hello",""+intent.getSerializableExtra("profile"));
            profile = (ProfileFB) intent.getSerializableExtra("profile");
        }
        init();

        soundDB.setListener(new FireBaseSoundDB.ChangeListener() {
            @Override
            public void onChange() {
                if(soundDB.getSounds().size() > 0){
                    soundArrayList = soundDB.getSounds();
                    Log.d("HEHE","working");
                    openSoundsAdapter = new SoundAdapter(getApplicationContext(),soundArrayList, profile,false,true,false);
                    rv_opensounds.setAdapter(openSoundsAdapter);
                }
            }
        });

        back_btn = findViewById(R.id.btn_back);
        tv_label = findViewById(R.id.tv_label);
        tv_label.setText("Select a sound");

        back_btn.setOnClickListener(view -> {
            Intent goToMenu = new Intent(AddSongsEditViewActivity.this, MenuActivity.class);
            startActivity(goToMenu);
            soundDB.destroyDBInstance();
            finish();
        });

    }

    private void init(){
        this.rv_opensounds = findViewById(R.id.rv_profile);
        this.layout = new LinearLayoutManager(this);
        this.rv_opensounds.setLayoutManager(this.layout);
        openSoundsAdapter = new SoundAdapter(getApplicationContext(),soundArrayList,profile,false,true,false);
        rv_opensounds.setAdapter(openSoundsAdapter);


    }

}
