package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters.DeleteProfileAdapter;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.ProfileDAO;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.ProfileDAOSqlImpl;

public class DeleteProfileActivity extends AppCompatActivity {

    private Button back_btn;
    private DeleteProfileAdapter deleteProfileAdapter;
    private RecyclerView rvProfile;
    private RecyclerView.LayoutManager layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_profile);

        init();

        back_btn = findViewById(R.id.goback_btn);

        back_btn.setOnClickListener(view -> {
            Intent goToMain = new Intent(DeleteProfileActivity.this, MenuActivity.class);
            startActivity(goToMain);
        });
    }

    private void init(){
        this.rvProfile = findViewById(R.id.deleteProfileRecyclerView);
        this.layout = new LinearLayoutManager(this);
        this.rvProfile.setLayoutManager(this.layout);
        ProfileDAO profileDAO = new ProfileDAOSqlImpl(getApplicationContext());
        this.deleteProfileAdapter = new DeleteProfileAdapter(getApplicationContext(),profileDAO.getProfiles());
        this.rvProfile.setAdapter(this.deleteProfileAdapter);
    }
}