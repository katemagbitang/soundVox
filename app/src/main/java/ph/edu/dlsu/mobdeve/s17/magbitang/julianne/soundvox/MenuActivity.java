package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    Button record_btn, back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        record_btn = findViewById(R.id.menu_item_1_btn);
        back_btn = findViewById(R.id.goback_btn);

        record_btn.setOnClickListener(view -> {
            Intent goToRecord = new Intent(MenuActivity.this, RecordingActivity.class);
            startActivity(goToRecord);
            finish();
        });

        back_btn.setOnClickListener(view -> {
            Intent goToMain = new Intent(MenuActivity.this, MainActivity.class);
            startActivity(goToMain);
            finish();
        });
    }
}