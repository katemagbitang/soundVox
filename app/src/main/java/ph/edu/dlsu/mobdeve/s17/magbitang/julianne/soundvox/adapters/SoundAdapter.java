package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.EditProfileActivity;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.R;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Profile;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Sound;

public class SoundAdapter extends RecyclerView.Adapter<SoundAdapter.SoundViewHolder> {

    private ArrayList<Sound> soundArrayList;
    private Context context;
    private int inflater;

    public SoundAdapter(Context context, ArrayList<Sound> soundArrayList, int phase) {
        this.soundArrayList = soundArrayList;
        this.context = context;
        if(phase == 0){
            inflater = R.layout.button_sound_list;
        }else{
            inflater = R.layout.button_sound_list_delete;
        }
    }

    public void addSounds(ArrayList<Sound> soundArrayList){
        soundArrayList.clear();
        soundArrayList.addAll(soundArrayList);
        notifyDataSetChanged();
    }

    @Override
    public SoundAdapter.SoundViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(inflater,parent,false);
        SoundAdapter.SoundViewHolder soundViewHolder = new SoundAdapter.SoundViewHolder(view);


        soundViewHolder.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundArrayList.get(soundViewHolder.getBindingAdapterPosition()).getSoundref().start();
            }});

        return soundViewHolder;
    }

    @Override
    public void onBindViewHolder(SoundAdapter.SoundViewHolder holder, int position) {
        Sound sound = this.soundArrayList.get(position);
        holder.setName(sound.getLabel());
    }

    @Override
    public int getItemCount() {
        return soundArrayList.size();
    }

    public class SoundViewHolder extends RecyclerView.ViewHolder{

        Button btn_sound;

        public SoundViewHolder(View itemView) {
            super(itemView);
            btn_sound = itemView.findViewById(R.id.sound_item);
        }

        public void setName(String name){ this.btn_sound.setText(name);}
        public Button getButton() { return this.btn_sound;}
    }
}
