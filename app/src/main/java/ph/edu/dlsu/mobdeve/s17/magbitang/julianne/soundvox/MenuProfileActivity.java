package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters.ProfileAdapter;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Profile;

public class MenuProfileActivity extends AppCompatActivity {

    Button back_btn;
    private ProfileAdapter profileAdapter;
    private ArrayList<Profile> profileArrayList = new ArrayList<>();
    private RecyclerView rvProfile;
    private RecyclerView.LayoutManager layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuprofile);

        populate_data();

        init();

        back_btn = findViewById(R.id.goback_btn);

        back_btn.setOnClickListener(view -> {
            Intent goToMain = new Intent(MenuProfileActivity.this, MainActivity.class);
            startActivity(goToMain);
        });
    }

    private void populate_data() {
        this.profileArrayList.add(new Profile("Profile 1"));
        this.profileArrayList.add(new Profile("Profile 2"));
        this.profileArrayList.add(new Profile("Profile 3"));
    }

    private void init(){
        this.rvProfile = findViewById(R.id.profileRecyclerView);
        this.layout = new LinearLayoutManager(this);
        this.rvProfile.setLayoutManager(this.layout);
        this.profileAdapter = new ProfileAdapter(getApplicationContext(),this.profileArrayList);
        this.rvProfile.setAdapter(this.profileAdapter);
    }


}