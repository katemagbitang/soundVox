package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.EditProfileActivity;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.R;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Profile;

public class SelectProfileAdapter extends RecyclerView.Adapter<SelectProfileAdapter.SelectProfileViewHolder> {

    private ArrayList<Profile> selectProfileArrayList;
    private Context context;

    public SelectProfileAdapter(Context context, ArrayList<Profile> selectProfileArrayList) {
        this.selectProfileArrayList = selectProfileArrayList;
        this.context = context;

    }

    @Override
    public SelectProfileAdapter.SelectProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.button_select_profile_list,parent,false);
        SelectProfileAdapter.SelectProfileViewHolder selectProfileViewHolder = new SelectProfileAdapter.SelectProfileViewHolder(view);
        selectProfileViewHolder.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditProfileActivity.class);

                v.getContext().startActivity(intent);
            }
        });
        return selectProfileViewHolder;
    }

    @Override
    public void onBindViewHolder(SelectProfileAdapter.SelectProfileViewHolder holder, int position) {
        Profile profile = this.selectProfileArrayList.get(position);
        holder.setButton(profile.getName());
    }

    @Override
    public int getItemCount() {
        return selectProfileArrayList.size();
    }

    public class SelectProfileViewHolder extends RecyclerView.ViewHolder{

        Button btn_profile;

        public SelectProfileViewHolder(View itemView) {
            super(itemView);
            btn_profile = itemView.findViewById(R.id.select_profile_item);
        }

        public void setButton(String name){ this.btn_profile.setText(name);}
        public Button getButton() { return this.btn_profile;}
    }
}
