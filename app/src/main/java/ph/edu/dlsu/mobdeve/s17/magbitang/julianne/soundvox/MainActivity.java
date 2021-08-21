package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    Button profile_menu_btn;
    ImageButton home_menu_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        home_menu_btn = findViewById(R.id.menu_btn);
        profile_menu_btn = findViewById(R.id.btn_profile_menu);

        home_menu_btn.setOnClickListener(view -> {
            Intent goToMenu = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(goToMenu);
        });

        profile_menu_btn.setOnClickListener(view -> {
            Intent goToProfileMenu = new Intent(MainActivity.this, MenuProfileActivity.class);
            startActivity(goToProfileMenu);
        });
    }
}