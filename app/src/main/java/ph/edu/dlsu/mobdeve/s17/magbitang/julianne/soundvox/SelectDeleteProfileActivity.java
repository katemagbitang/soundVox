package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters.ProfileAdapter;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.FireBaseProfileDB;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.ProfileDAO;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.ProfileDAOSqlImpl;

public class SelectDeleteProfileActivity extends AppCompatActivity {

    private Button back_btn;
    private TextView tv_label;
    private ProfileAdapter deleteProfileAdapter;
    private RecyclerView rvProfile;
    private RecyclerView.LayoutManager layout;
    private FireBaseProfileDB profileDB = new FireBaseProfileDB();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuprofile);

        profileDB.setListener(new FireBaseProfileDB.ChangeListener() {
            @Override
            public void onChange() {
                if(profileDB.getProfiles().size() > 0){
                    Log.d("HEHE","working");
                }
            }
        });

        init();

        back_btn = findViewById(R.id.btn_back);
        tv_label = findViewById(R.id.tv_label);
        tv_label.setText("Select a profile to delete");
        back_btn.setOnClickListener(view -> {
            Intent goToMain = new Intent(SelectDeleteProfileActivity.this, MenuActivity.class);
            startActivity(goToMain);
        });
    }

    private void init(){
        this.rvProfile = findViewById(R.id.rv_profile);
        this.layout = new LinearLayoutManager(this);
        this.rvProfile.setLayoutManager(this.layout);
        ProfileDAO profileDAO = new ProfileDAOSqlImpl(getApplicationContext());
        this.deleteProfileAdapter = new ProfileAdapter(getApplicationContext(), profileDB.getProfiles(), (byte) 1);
        this.rvProfile.setAdapter(this.deleteProfileAdapter);
    }
}