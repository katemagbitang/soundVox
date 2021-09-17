package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.SoundFB;

public class FireBaseSoundDB {

    private DatabaseReference soundsDB;
    private String SOUND_DB_ERROR = "SOUND_DB_ERROR";
    private ArrayList<SoundFB> Sounds;
    boolean exists;
    GenericTypeIndicator<ArrayList<SoundFB>> soundListType = new GenericTypeIndicator<ArrayList<SoundFB>>() {};

    ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // Get Post object and use the values to update the UI
            Sounds = dataSnapshot.getValue(soundListType);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
            Log.w(SOUND_DB_ERROR, "loadPost:onCancelled", databaseError.toException());
        }
    };


    public FireBaseSoundDB() {
        soundsDB = FirebaseDatabase.getInstance("https://soundvox-data-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("test-user").child("sounds");
        Sounds = new ArrayList<SoundFB>();
        SoundsFirstRead();
        soundsDB.addValueEventListener(postListener);
    }


    private void SoundsFirstRead() {
        soundsDB.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e(SOUND_DB_ERROR, "Error getting data", task.getException());
                }
                else {
                    if (task.getResult().getValue(soundListType) != null) {
                        Sounds = task.getResult().getValue(soundListType);
                    }
                }
            }
        });
    }

    public void addSounds(SoundFB sound) {
        //check if exists already
        Query songs = soundsDB.orderByChild("URL").equalTo(sound.getURL());
        exists = false;
        songs.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                exists = true;
            }

            @Override public void onCancelled(@NonNull DatabaseError error) {
                Log.e(SOUND_DB_ERROR, "Error getting data");
            }

            @Override public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
            @Override public void onChildRemoved(@NonNull DataSnapshot snapshot) {}
            @Override public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
        });

        if(!exists){
            String key = soundsDB.push().getKey();
            Map<String, Object> postValues = sound.toMap();

            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put(key, postValues);

            soundsDB.updateChildren(childUpdates);
        }

    }

    public ArrayList<SoundFB> getSounds() {
            return Sounds;
    }


    public void deleteSound(SoundFB sound) {
        Query songs = soundsDB.orderByChild("URL").equalTo(sound.getURL());

        songs.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                dataSnapshot.getRef().removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(SOUND_DB_ERROR, "Error getting data");
            }

            @Override public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
            @Override public void onChildRemoved(@NonNull DataSnapshot snapshot) { }
            @Override public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
        });
    }

    public void destroyDBInstance() {
        soundsDB.removeEventListener(postListener);
    }
}
