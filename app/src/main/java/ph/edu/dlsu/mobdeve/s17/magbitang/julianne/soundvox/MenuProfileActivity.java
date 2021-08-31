package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters.ProfileAdapter;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.ProfileDAO;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.ProfileDAOSqlImpl;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Profile;

public class MenuProfileActivity extends AppCompatActivity {

    private Button back_btn;
    private ProfileAdapter profileAdapter;
    private RecyclerView rvProfile;
    private RecyclerView.LayoutManager layout;
    private Profile profile = new Profile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuprofile);

        init();

        back_btn = findViewById(R.id.goback_btn);

        back_btn.setOnClickListener(view -> {
            Intent goToMain = new Intent(MenuProfileActivity.this, MainActivity.class);
            startActivity(goToMain);
        });

    }

    private void init(){
        this.rvProfile = findViewById(R.id.profileRecyclerView);
        this.layout = new LinearLayoutManager(this);
        this.rvProfile.setLayoutManager(this.layout);
        ProfileDAO profileDAO = new ProfileDAOSqlImpl(getApplicationContext());
        this.profileAdapter = new ProfileAdapter(getApplicationContext(),profileDAO.getProfiles());
        this.rvProfile.setAdapter(this.profileAdapter);
    }

}