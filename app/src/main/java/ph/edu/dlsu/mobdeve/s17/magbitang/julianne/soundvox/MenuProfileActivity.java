package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MenuProfileActivity extends AppCompatActivity {

    Button profile_btn, back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuprofile);

        profile_btn = findViewById(R.id.profile_item_1_btn);
        back_btn = findViewById(R.id.goback_btn);

        profile_btn.setOnClickListener(view -> {
            Intent goToProfile = new Intent(MenuProfileActivity.this, ProfileActivity.class);
            startActivity(goToProfile);
            finish();
        });

        back_btn.setOnClickListener(view -> {
            Intent goToMain = new Intent(MenuProfileActivity.this, MainActivity.class);
            startActivity(goToMain);
            finish();
        });
    }
}