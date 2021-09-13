package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters.ProfileAdapter;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.AppDAO;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.AppDAOSqlImpl;

public class SelectProfileActivity extends AppCompatActivity {

    private Button back_btn;
    private TextView tv_label;
    private ProfileAdapter profileAdapter;
    private RecyclerView rvProfile;
    private RecyclerView.LayoutManager layout;
//    private Profile profile = new Profile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuprofile);

        init();

        back_btn = findViewById(R.id.goback_btn);
        tv_label = findViewById(R.id.tv_label);
        tv_label.setText("Select a profile to play");
        back_btn.setOnClickListener(view -> {
            Intent goToMain = new Intent(SelectProfileActivity.this, MainActivity.class);
            startActivity(goToMain);
        });

    }

    private void init(){
        this.rvProfile = findViewById(R.id.profileRecyclerView);
        this.layout = new LinearLayoutManager(this);
        this.rvProfile.setLayoutManager(this.layout);
        AppDAO appDAO = new AppDAOSqlImpl(getApplicationContext());
        this.profileAdapter = new ProfileAdapter(getApplicationContext(), appDAO.getProfiles(), (byte) 2);
        this.rvProfile.setAdapter(this.profileAdapter);
    }

}