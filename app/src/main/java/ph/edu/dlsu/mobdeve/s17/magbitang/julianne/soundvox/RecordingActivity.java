package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class RecordingActivity extends AppCompatActivity {

    Button back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);

        back_btn = findViewById(R.id.btn_back);

        back_btn.setOnClickListener(view -> {
            Intent goToMenu = new Intent(RecordingActivity.this, MenuActivity.class);
            startActivity(goToMenu);
        });
    }
}