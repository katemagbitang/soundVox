package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.DeleteProfileActivity;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.R;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Profile;

public class DeleteProfileAdapter extends RecyclerView.Adapter<DeleteProfileAdapter.DeleteProfileViewHolder> {

    private ArrayList<Profile> deleteProfileArrayList;
    private Context context;

    public DeleteProfileAdapter(Context context, ArrayList<Profile> deleteProfileArrayList) {
        this.deleteProfileArrayList = deleteProfileArrayList;
        this.context = context;

    }

    @Override
    public DeleteProfileAdapter.DeleteProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.button_delete_profile_list,parent,false);
        DeleteProfileAdapter.DeleteProfileViewHolder deleteProfileViewHolder = new DeleteProfileAdapter.DeleteProfileViewHolder(view);
        deleteProfileViewHolder.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DeleteProfileActivity.class);

                v.getContext().startActivity(intent);
            }
        });
        return deleteProfileViewHolder;
    }

    @Override
    public void onBindViewHolder(DeleteProfileAdapter.DeleteProfileViewHolder holder, int position) {
        Profile profile = this.deleteProfileArrayList.get(position);
        holder.setButton(profile.getName());
    }

    @Override
    public int getItemCount() {
        return deleteProfileArrayList.size();
    }

    public class DeleteProfileViewHolder extends RecyclerView.ViewHolder{

        Button btn_profile;

        public DeleteProfileViewHolder(View itemView) {
            super(itemView);
            btn_profile = itemView.findViewById(R.id.delete_profile_item);
        }

        public void setButton(String name){ this.btn_profile.setText(name);}
        public Button getButton() { return this.btn_profile;}
    }
}
