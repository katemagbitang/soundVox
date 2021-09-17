package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.R;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.Sound;

public class SoundAdapter extends RecyclerView.Adapter<SoundAdapter.SoundViewHolder> {

    private ArrayList<Sound> soundArrayList;
    private Context context;
    private int inflater;
    private boolean phase; //for deleting
    private boolean openAccess;

    public SoundAdapter(Context context, ArrayList<Sound> soundArrayList, boolean phase, boolean openAccess) {
        this.soundArrayList = soundArrayList;
        this.context = context;
        this.phase = phase;
        this.openAccess= openAccess;
        if(openAccess)
            inflater = R.layout.button_item_list;
        else
            inflater = R.layout.button_sound_list;
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
                soundArrayList.get(soundViewHolder.getBindingAdapterPosition()).getSoundref().stop();
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
        Button btn_delete;

        public SoundViewHolder(View itemView) {
            super(itemView);
            btn_sound = itemView.findViewById(R.id.btn_sound);
            btn_delete = itemView.findViewById(R.id.btn_delete);

            //
            if(phase)
                btn_delete.setVisibility(View.VISIBLE);
            else{
                btn_delete.setVisibility(View.GONE);
            }

        }

        public void setName(String name){ this.btn_sound.setText(name);}
        public Button getButton() { return this.btn_sound;}
    }



}
