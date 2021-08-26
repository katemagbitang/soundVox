package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.ProfileActivity;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Profile;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.R;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {

    private ArrayList<Profile> profileArrayList;
    private Context context;

    public ProfileAdapter(Context context, ArrayList<Profile> profileArrayList) {
        this.profileArrayList = profileArrayList;
        this.context = context;

    }

    public void addProfiles(ArrayList<Profile> profileArrayList){
        profileArrayList.clear();
        profileArrayList.addAll(profileArrayList);
        notifyDataSetChanged();
    }

    @Override
    public ProfileAdapter.ProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.button_profile_list,parent,false);
        ProfileViewHolder profileViewHolder = new ProfileViewHolder(view);
        profileViewHolder.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ProfileActivity.class);

                v.getContext().startActivity(intent);
            }
        });

        return profileViewHolder;
    }

    @Override
    public void onBindViewHolder(ProfileAdapter.ProfileViewHolder holder, int position) {
        Profile profile = this.profileArrayList.get(position);
        holder.setButton(profile.getName());
    }

    @Override
    public int getItemCount() {
        return profileArrayList.size();
    }

    public class ProfileViewHolder extends RecyclerView.ViewHolder{

        Button btn_profile;

        public ProfileViewHolder(View itemView) {
            super(itemView);
            btn_profile = itemView.findViewById(R.id.profile_item);
        }

        public void setButton(String name){ this.btn_profile.setText(name);}
        public Button getButton() { return this.btn_profile;}
    }
}