package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.EditProfileActivity;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.MainActivity;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.MenuActivity;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.ProfileDAO;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.ProfileDAOSqlImpl;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Profile;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.R;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {

    private ArrayList<Profile> profileArrayList;
    private Context context;
    private Class pClass; //class to go

    public ProfileAdapter(Context context, ArrayList<Profile> profileArrayList, byte pSelect) {
        this.profileArrayList = profileArrayList;
        this.context = context;
        switch(pSelect){
            //edit
            case 0:
                this.pClass = EditProfileActivity.class; break;//For editing profile
            case 1:
                this.pClass = MenuActivity.class; break;//For deleting profile
            case 2:
                this.pClass = MainActivity.class; break;//For select profile
        }

    }

    public void addProfiles(ArrayList<Profile> profileArrayList){
        profileArrayList.clear();
        profileArrayList.addAll(profileArrayList);
        notifyDataSetChanged();
    }

    public void removeProfile(int position){
        profileArrayList.remove(position);
//        int i = 0;
//        int count = 1;
//
//        while(i < profileArrayList.size()){
//            profileArrayList.get(i).setId(count);
//            count++;
//            i++;
//        }

        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    //pSelect = What screen is being selected
    @Override
    public ProfileAdapter.ProfileViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.button_profile_list,parent,false);
        ProfileViewHolder profileViewHolder = new ProfileViewHolder(view);
        profileViewHolder.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create intent based on switch case
                Intent intent = new Intent(v.getContext(), pClass);

                intent.putExtra("id", profileArrayList.get(profileViewHolder.getBindingAdapterPosition()).getId());
                intent.putExtra("name", profileArrayList.get(profileViewHolder.getBindingAdapterPosition()).getName());
                intent.putExtra("sound", profileArrayList.get(profileViewHolder.getBindingAdapterPosition()).getSounds());
                if(pClass == MenuActivity.class){

                    ProfileDAO profileDAO = new ProfileDAOSqlImpl(context);
                    int id = profileArrayList.get(profileViewHolder.getBindingAdapterPosition()).getId();
                    int status = profileDAO.deleteProfile(id);
                    if (status > 0){
//                profileAdapter.removeProfile(Integer.parseInt(profile_name_label.getText().toString()));
                        removeProfile(id);
//                selectProfileAdapter.removeProfile(Integer.parseInt(profile_name_id.getText().toString()));
                        addProfiles(profileDAO.getProfiles());
//                selectProfileAdapter.addProfiles(profileDAO.getProfiles());
                        Toast.makeText(context,""+getItemCount(), Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(context,"Profile not found", Toast.LENGTH_SHORT).show();
                    }
                }
                v.getContext().startActivity(intent);
            }

        });

//        profileViewHolder.getButton().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String profileName = profileViewHolder.getButton().getText().toString();
//
//                ProfileDAO profileDAO = new ProfileDAOSqlImpl(v.getContext());
////            profiles = profileDAO.getProfiles();
//
////                Profile profile = profileDAO.getProfile(profileName);
//                Profile profile = profileDAO.getProfile(profileName);
//
//                if (profile != null){
//                    Intent intent = new Intent(v.getContext(), ProfileActivity.class);
//
//                    v.getContext().startActivity(intent);
//                    Toast.makeText(v.getContext(),"User" + profileName + "found", Toast.LENGTH_SHORT).show();
//                }
//                else{
////                binding.uName.setText("");
////                binding.uEmail.setText("");
//                    Toast.makeText(v.getContext(),"User not found", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

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

//    TextView tv_label;
//    tv_label = findViewById(R.id.tv_label);
//    public void setLabel(String name){ this.tv_label.setText(name);}
//    public TextView getLabel() { return this.tv_label;}

}
