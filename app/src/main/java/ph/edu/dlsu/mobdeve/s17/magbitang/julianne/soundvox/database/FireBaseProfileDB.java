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

import java.lang.reflect.Array;
import java.security.Key;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.MainActivity;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.R;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Profile;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.ProfileFB;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Sound;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.SoundFB;

public class FireBaseProfileDB {

    private DatabaseReference profileDB;
    private ArrayList<ProfileFB> Profiles;
    private ChangeListener listener;
    boolean exists;
    private String PROFILE_DB_ERROR = "PROFILE_DB_ERROR";
    GenericTypeIndicator<Map<String, Object>> profileListType = new GenericTypeIndicator<Map<String, Object>>() {
    };

    ValueEventListener postListener = new ValueEventListener() {
        public void onDataChange(DataSnapshot dataSnapshot) {
            // Get Post object and use the values to update the UI
            Profiles = new ArrayList<ProfileFB>();
            Collection<Object> newProfiles;
            if(dataSnapshot.getValue(profileListType) != null){
                newProfiles = dataSnapshot.getValue(profileListType).values();

                /*READ PROFILE*/
                for(Object object : newProfiles){
                    HashMap<String, Object> element = (HashMap<String, Object>) object;
                    String name = "";
                    ArrayList<HashMap<String, String>> sounds = new ArrayList<HashMap<String, String>>();
                    for(Object item : element.values()){
                        if(item instanceof  ArrayList)
                            sounds = (ArrayList<HashMap<String, String>>) item;
                        if(item instanceof  String)
                            name = item.toString();
                    }

                    /*READ SOUND */
                    String label = "";
                    String url = "";
                    int i = 0;
                    ArrayList<SoundFB> soundRefs = new ArrayList<>();
                    for(HashMap<String, String> hash: sounds){
                        i = 0;
                        for(Object sound  : hash.values()){
                            if(i==0){
                                label = sound.toString();
                            }
                            else{
                                url = sound.toString();
                            }
                            i++;
                        }
                        soundRefs.add(new SoundFB(label, url));
                    }

                    Profiles.add(new ProfileFB(name, soundRefs));
                }

                profilesUpdated();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
            Log.w(PROFILE_DB_ERROR, "loadPost:onCancelled", databaseError.toException());
        }
    };



    public FireBaseProfileDB(){
        profileDB = FirebaseDatabase.getInstance("https://soundvox-data-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("test-user").child("profiles");
        //ProfilesFirstRead();
        profileDB.addValueEventListener(postListener);
    }

    public ArrayList<SoundFB> defaultMusic(){
        ArrayList<SoundFB> sounds = new ArrayList<SoundFB>();
        sounds.add(new SoundFB("a_major", Integer.toString(R.raw.piano_a_major)));
        sounds.add(new SoundFB("b_major", Integer.toString(R.raw.piano_b_major)));
        sounds.add(new SoundFB("c_major", Integer.toString(R.raw.piano_c_major)));
        sounds.add(new SoundFB("c_sharp", Integer.toString(R.raw.piano_c_sharp)));
        sounds.add(new SoundFB("d_major", Integer.toString(R.raw.piano_d_major)));
        sounds.add(new SoundFB("e_major", Integer.toString(R.raw.piano_e_major)));
        sounds.add(new SoundFB("f_major", Integer.toString(R.raw.piano_f_major)));
        sounds.add(new SoundFB("g_major", Integer.toString(R.raw.piano_g_major)));
        return sounds;
    }


    public void addProfile(ProfileFB profile) {
        //check if exists already
        Query profiles = profileDB.orderByChild("name").equalTo(profile.getName());
        exists = false;
        profiles.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                exists = true;
            }

            @Override public void onCancelled(@NonNull DatabaseError error) {
                Log.e(PROFILE_DB_ERROR, "Error getting data");
            }

            @Override public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
            @Override public void onChildRemoved(@NonNull DataSnapshot snapshot) {}
            @Override public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
        });

        if(!exists){
            String key = profileDB.push().getKey();
            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put(key, profile);

            profileDB.updateChildren(childUpdates);
            for(SoundFB sound : defaultMusic())
                addProfileSong(profile, sound);
        }

    }

    public ArrayList<ProfileFB> getProfiles() {
        return this.Profiles;
    }


    public void addProfileSong(ProfileFB profile, SoundFB sound ) {

        Query profiles = profileDB.child(profile.getName()).orderByChild("sound").equalTo(sound.getURL());
        exists = false;
        profiles.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                exists = true;
            }

            @Override public void onCancelled(@NonNull DatabaseError error) {
                Log.e(PROFILE_DB_ERROR, "Error getting data");
            }

            @Override public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
            @Override public void onChildRemoved(@NonNull DataSnapshot snapshot) {}
            @Override public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
        });

        if(!exists){
           Query profileKey = profileDB.orderByChild("name").equalTo(profile.getName());
           profileKey.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                    String key = dataSnapshot.getKey();
                    profile.addSound(sound);
                    Log.d("chis", key);

                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put(key, profile);

                    profileDB.updateChildren(childUpdates);
                }

                @Override public void onCancelled(@NonNull DatabaseError error) {
                    Log.e(PROFILE_DB_ERROR, "Error getting data");
                }

                @Override public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
                @Override public void onChildRemoved(@NonNull DataSnapshot snapshot) {}
                @Override public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
            });

        }
    }

    public void deleteProfileSong(ProfileFB profile, SoundFB sound ) {
        Query profiles = profileDB.orderByChild("name").equalTo(profile.getName()).orderByChild("sound").equalTo(sound.getURL());

        profiles.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                dataSnapshot.getRef().removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(PROFILE_DB_ERROR, "Error getting data");
            }

            @Override public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
            @Override public void onChildRemoved(@NonNull DataSnapshot snapshot) { }
            @Override public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
        });
    }

    public void deleteProfile(ProfileFB profile ) {
        Query profiles = profileDB.orderByChild("name").equalTo(profile.getName());

        profiles.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                dataSnapshot.getRef().removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(PROFILE_DB_ERROR, "Error getting data");
            }

            @Override public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
            @Override public void onChildRemoved(@NonNull DataSnapshot snapshot) { }
            @Override public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
        });
    }

    public void destroyDBInstance() {
        profileDB.removeEventListener(postListener);
        listener = null;
    }


    public void profilesUpdated() {
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
