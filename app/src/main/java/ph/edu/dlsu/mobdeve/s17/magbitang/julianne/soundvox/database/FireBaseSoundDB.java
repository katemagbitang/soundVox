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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.R;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.SoundFB;

public class FireBaseSoundDB {

    private DatabaseReference soundsDB;
    private String SOUND_DB_ERROR = "SOUND_DB_ERROR";
    private ArrayList<SoundFB> Sounds;
    boolean exists;
    private ChangeListener listener;
    GenericTypeIndicator<Map<String, Object>> soundListType = new GenericTypeIndicator<Map<String, Object>>() {
    };

    ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Sounds  = new ArrayList<SoundFB>();
            Collection<Object> newSounds;
            if(dataSnapshot.getValue(soundListType) != null){

                newSounds = dataSnapshot.getValue(soundListType).values();

                /*READ SOUND */
                String label = "";
                String url = "";

                for(Object object : newSounds){
                    HashMap<String, String> hash = (HashMap<String, String>) object;
                        for(Map.Entry<String, String> sound : hash.entrySet()){
                            if(sound.getKey().equals("label")){
                                label = sound.getValue();
                            }
                            else if (sound.getKey().equals("url")){
                                url = sound.getValue();
                            }
                        }
                        Sounds.add(new SoundFB(label, url));
                    }
                }
            soundsUpdate();

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
        soundsDB.addValueEventListener(postListener);
    }

    /*
    public void init(){
        ArrayList<SoundFB> sounds = new ArrayList<SoundFB>();
        addSounds(new SoundFB("a_major", Integer.toString(R.raw.piano_a_major)));
        addSounds(new SoundFB("b_major", Integer.toString(R.raw.piano_b_major)));
        addSounds(new SoundFB("c_major", Integer.toString(R.raw.piano_c_major)));
        addSounds(new SoundFB("c_sharp", Integer.toString(R.raw.piano_c_sharp)));
        addSounds(new SoundFB("d_major", Integer.toString(R.raw.piano_d_major)));
        addSounds(new SoundFB("e_major", Integer.toString(R.raw.piano_e_major)));
        addSounds(new SoundFB("f_major", Integer.toString(R.raw.piano_f_major)));
        addSounds(new SoundFB("g_major", Integer.toString(R.raw.piano_g_major)));
    }
    */

    public void addSounds(SoundFB sound) {
        //check if exists already
        Query songs = soundsDB.orderByChild("url").equalTo(sound.getURL());
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
        Query songs = soundsDB.orderByChild("url").equalTo(sound.getURL());

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
        listener = null;
    }


    public void soundsUpdate() {
        if (listener != null) listener.onChange();
    }

    public ChangeListener getListener() {
        return listener;
    }

    public void setListener(ChangeListener listener) {
        this.listener = listener;
    }

    public interface ChangeListener {
        void onChange();
    }

}
