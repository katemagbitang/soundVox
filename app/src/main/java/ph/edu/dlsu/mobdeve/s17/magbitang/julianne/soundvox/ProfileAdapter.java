package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {

    private ArrayList<Profile> profileArrayList;
//    private String[] profile_name;
    private Context context;

    public ProfileAdapter(Context context, ArrayList<Profile> profileArrayList) {
        this.profileArrayList = profileArrayList;
        this.context = context;
    }

    @Override
    public ProfileAdapter.ProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.button_profile_list,parent,false);
        ProfileViewHolder profileViewHolder = new ProfileViewHolder(view);

        return profileViewHolder;
    }

    @Override
    public void onBindViewHolder(ProfileAdapter.ProfileViewHolder holder, int position) {
        Profile profile = this.profileArrayList.get(position);
//        holder.profile_btn.setText(profileArrayList.get(position).getName());
        holder.setName(profile.getName());
    }

    @Override
    public int getItemCount() {
        return profileArrayList.size();
    }

    public class ProfileViewHolder extends RecyclerView.ViewHolder{

        TextView profile_name;

        public ProfileViewHolder(View itemView) {
            super(itemView);
            profile_name = itemView.findViewById(R.id.profile_item);
        }

        public void setName(String name){ this.profile_name.setText(name);}
    }
}
