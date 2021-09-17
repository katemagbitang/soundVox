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
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Profile;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.ProfileFB;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.SoundFB;

public class FireBaseProfileDB {

    private DatabaseReference profileDB;
    private ArrayList<ProfileFB> Profiles;
    boolean exists;
    private String PROFILE_DB_ERROR = "PROFILE_DB_ERROR";
    GenericTypeIndicator<Map<String,Object>> profileListType = new GenericTypeIndicator<Map<String,Object>>() {
    };

    ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // Get Post object and use the values to update the UI

//            Profiles = ArrayList<ProfileFB>(dataSnapshot.getValue(profileListType).values());
//            Profiles = new ArrayList<ProfileFB>((Collection<? extends ProfileFB>) dataSnapshot.getValue(profileListType).values()));
            Profiles = new ArrayList<ProfileFB>();
            for (Object value : dataSnapshot.getValue(profileListType).values()) {
//                Profiles.add((ProfileFB) value);
                Profiles.add((ProfileFB) value);
                Log.d("valuez",""+ value);
            }
            Log.d("chis",""+  Profiles);
//

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
            Log.w(PROFILE_DB_ERROR, "loadPost:onCancelled", databaseError.toException());
        }
    };



    public FireBaseProfileDB(){
        profileDB = FirebaseDatabase.getInstance("https://soundvox-data-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("test-user").child("profiles");
        Profiles = new ArrayList<ProfileFB>();
        ProfilesFirstRead();
        profileDB.addValueEventListener(postListener);
    }


    private void ProfilesFirstRead() {
        profileDB.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    if (task.getResult().getValue(profileListType) != null) {
//                        Profiles = task.getResult().getValue(profileListType);
                    }
                }
            }
        });
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
//            Map<String, Object> postValues = profile.toMap();

            Map<String, Object> childUpdates = new HashMap<>();
//            childUpdates.put(key, postValues);
            childUpdates.put(key, profile);

            profileDB.updateChildren(childUpdates);
        }

    }

    public ArrayList<ProfileFB> getProfiles() {
        return Profiles;
    }


    public void addProfileSong(ProfileFB profile, SoundFB sound ) {

        Query profiles = profileDB.orderByChild("name").equalTo(profile.getName()).orderByChild("sound").equalTo(sound.getURL());
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
            profile.addSound(sound);
//            Map<String, Object> postValues = profile.toMap();

            Map<String, Object> childUpdates = new HashMap<>();
//            childUpdates.put(key, postValues);
            childUpdates.put(key, profile);

            profileDB.updateChildren(childUpdates);
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
    }

}
