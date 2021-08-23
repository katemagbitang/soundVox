package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MenuProfileActivity extends AppCompatActivity {

    Button profile_btn, back_btn;
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

//        profile_btn = findViewById(R.id.profile_item_btn);
        back_btn = findViewById(R.id.goback_btn);

//        profile_btn.setOnClickListener(view -> {
//            Intent goToProfile = new Intent(MenuProfileActivity.this, ProfileActivity.class);
//            startActivity(goToProfile);
//            finish();
//        });

        back_btn.setOnClickListener(view -> {
            Intent goToMain = new Intent(MenuProfileActivity.this, MainActivity.class);
            startActivity(goToMain);
            finish();
        });
    }

    private void populate_data() {
//        this.profileArrayList = new ArrayList<>();

        this.profileArrayList.add(new Profile("Profile 1"));
        this.profileArrayList.add(new Profile("Profile 2")); //not reflecting
        this.profileArrayList.add(new Profile("Profile 3")); //not reflecting
    }

    private void init(){
        this.rvProfile = findViewById(R.id.profileRecyclerView);
        this.layout = new LinearLayoutManager(this);
        this.rvProfile.setLayoutManager(this.layout);
        this.profileAdapter = new ProfileAdapter(getApplicationContext(),this.profileArrayList);
        this.rvProfile.setAdapter(this.profileAdapter);
    }


}