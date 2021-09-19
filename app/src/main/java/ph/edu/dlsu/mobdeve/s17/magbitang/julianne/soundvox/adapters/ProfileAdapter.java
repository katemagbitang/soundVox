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
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.FireBaseProfileDB;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.ProfileDAO;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.database.ProfileDAOSqlImpl;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Profile;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.R;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.ProfileFB;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {

    private ArrayList<ProfileFB> profileArrayList;
    private Context context;
    private Class pClass; //class to go

    public ProfileAdapter(Context context, ArrayList<ProfileFB> profileArrayList, byte pSelect) {
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

    public void addProfiles(ArrayList<ProfileFB> profileArrayList){
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.button_item_list,parent,false);
        ProfileViewHolder profileViewHolder = new ProfileViewHolder(view);
        profileViewHolder.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create intent based on switch case
                Intent intent = new Intent(v.getContext(), pClass);
                intent.putExtra("SPM_BOOL",true);
                intent.putExtra("profile", profileArrayList.get(profileViewHolder.getBindingAdapterPosition()));
                intent.putExtra("name", profileArrayList.get(profileViewHolder.getBindingAdapterPosition()).getName());
                intent.putExtra("sounds", profileArrayList.get(profileViewHolder.getBindingAdapterPosition()).getSounds());


                /* DELETE USER*/
               if(pClass == MenuActivity.class){
                   FireBaseProfileDB profileDB = new FireBaseProfileDB();
                   profileDB.deleteProfile(profileArrayList.get(profileViewHolder.getBindingAdapterPosition()));
                   profileDB.destroyDBInstance();
                }
                v.getContext().startActivity(intent);
            }

        });
        return profileViewHolder;
    }

    @Override
    public void onBindViewHolder(ProfileAdapter.ProfileViewHolder holder, int position) {
        ProfileFB profile = this.profileArrayList.get(position);
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
            btn_profile = itemView.findViewById(R.id.btn_soundall);

        }

        public void setButton(String name){ this.btn_profile.setText(name);}
        public Button getButton() { return this.btn_profile;}

    }

//    TextView tv_label;
//    tv_label = findViewById(R.id.tv_label);
//    public void setLabel(String name){ this.tv_label.setText(name);}
//    public TextView getLabel() { return this.tv_label;}

}
