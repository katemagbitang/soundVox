package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters.ProfileAdapter;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.ProfileDAO;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.ProfileDAOSqlImpl;

public class SelectEditProfileActivity extends AppCompatActivity {

    private Button back_btn;
    private TextView tv_label;
    private ProfileAdapter selectProfileAdapter;
    private RecyclerView rvProfile;
    private RecyclerView.LayoutManager layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuprofile);

        init();

        back_btn = findViewById(R.id.goback_btn);
        tv_label = findViewById(R.id.tv_label);
        tv_label.setText("Select a profile to edit");
        back_btn.setOnClickListener(view -> {
            Intent goToMain = new Intent(SelectEditProfileActivity.this, MenuActivity.class);
            startActivity(goToMain);
        });
    }

    private void init(){
        this.rvProfile = findViewById(R.id.profileRecyclerView);
        this.layout = new LinearLayoutManager(this);
        this.rvProfile.setLayoutManager(this.layout);
        ProfileDAO profileDAO = new ProfileDAOSqlImpl(getApplicationContext());
        this.selectProfileAdapter = new ProfileAdapter(getApplicationContext(),profileDAO.getProfiles(), (byte) 0);
        this.rvProfile.setAdapter(this.selectProfileAdapter);
    }
}