package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters.ProfileAdapter;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.ProfileFB;

public class SelectEditProfileActivity extends AppCompatActivity {

    private Button back_btn;
    private TextView tv_label;
    private ProfileAdapter selectProfileAdapter;
    private RecyclerView rvProfile;
    private RecyclerView.LayoutManager layout;
    private ArrayList<ProfileFB> Profiles = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuprofile);

        /*GET USERS*/
        Bundle args = getIntent().getBundleExtra("BUNDLE");
        Profiles = (ArrayList<ProfileFB>) args.getSerializable("PROFILES");

        init();

        back_btn = findViewById(R.id.btn_back);
        tv_label = findViewById(R.id.tv_label);
        tv_label.setText("Select a profile to edit");
        back_btn.setOnClickListener(view -> {
            Intent goToMain = new Intent(SelectEditProfileActivity.this, MenuActivity.class);
            startActivity(goToMain);
        });
    }

    private void init(){
        this.rvProfile = findViewById(R.id.rv_profile);
        this.layout = new LinearLayoutManager(this);
        this.rvProfile.setLayoutManager(this.layout);
        this.selectProfileAdapter = new ProfileAdapter(getApplicationContext(), Profiles, (byte) 0);
        this.rvProfile.setAdapter(this.selectProfileAdapter);
    }
}