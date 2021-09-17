package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters.ProfileAdapter;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters.SoundAdapter;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.ProfileDAO;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.ProfileDAOSqlImpl;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Sound;

public class OpenSoundsActivity extends AppCompatActivity {

    private Button back_btn;
    private TextView tv_label;
    private SoundAdapter openSoundsAdapter;
    private RecyclerView rvProfile;
    private RecyclerView.LayoutManager layout;
    private ArrayList<Sound> soundArrayList = new ArrayList<>();
//    private Profile profile = new Profile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuprofile);

        init();
        back_btn = findViewById(R.id.goback_btn);
        tv_label = findViewById(R.id.tv_label);
        tv_label.setText("Select a sound to add");
        back_btn.setOnClickListener(view -> {
            Intent goToEdit = new Intent(OpenSoundsActivity.this, EditProfileActivity.class);
            startActivity(goToEdit);
            finish();
        });

    }

    private void init(){
        this.rvProfile = findViewById(R.id.profileRecyclerView);
        this.layout = new LinearLayoutManager(this);
        this.rvProfile.setLayoutManager(this.layout);
        ProfileDAO profileDAO = new ProfileDAOSqlImpl(getApplicationContext());
        this.openSoundsAdapter = new SoundAdapter(getApplicationContext(),soundArrayList,false,true);
        this.rvProfile.setAdapter(this.openSoundsAdapter);
    }

}
