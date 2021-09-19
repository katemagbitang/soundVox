package ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.EditProfileActivity;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.R;
import ph.edu.dlsu.mobdeve.s17.magbitang.julianne.soundvox.models.SoundFB;

public class SoundAdapter extends RecyclerView.Adapter<SoundAdapter.SoundViewHolder> {

    private ArrayList<SoundFB> soundArrayList;
    private Context context;
    private int inflater;
    private boolean phase; //for deleting
    private boolean openAccess;
    private boolean assigning;

    public SoundAdapter(Context context,
                        ArrayList<SoundFB> soundArrayList,
                        boolean phase, //if removing sound; true
                        boolean openAccess, //if using soundadapter for ALL sounds; true
                        boolean assigning)//if assigning to profile)
    {
        this.soundArrayList = soundArrayList;
        this.context = context;
        this.phase = phase;
        this.openAccess= openAccess;
        //if(openAccess)
            //inflater = R.layout.button_item_list;
        //else
        /*TEMP REMOVE IF ELSE CAUSE BUTTONS WONT APPEAR*/
            inflater = R.layout.button_sound_list;
        this.assigning = assigning;
    }

    public void addSounds(ArrayList<SoundFB> soundArrayList){
        soundArrayList.clear();
        soundArrayList.addAll(soundArrayList);
        notifyDataSetChanged();
    }

    @Override
    public SoundAdapter.SoundViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(inflater,parent,false);
        SoundAdapter.SoundViewHolder soundViewHolder = new SoundAdapter.SoundViewHolder(view);
        if (!assigning){
            soundViewHolder.getButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MediaPlayer mPlayer;
                    String url = soundArrayList.get(soundViewHolder.getBindingAdapterPosition()).getURL();
                    switch(url){
                        case R.raw.piano_a_major + "" : mPlayer = MediaPlayer.create(v.getContext(), R.raw.piano_a_major); break;
                        case R.raw.piano_b_major + "" : mPlayer = MediaPlayer.create(v.getContext(), R.raw.piano_b_major); break;
                        case R.raw.piano_c_major + "" : mPlayer = MediaPlayer.create(v.getContext(), R.raw.piano_c_major); break;
                        case R.raw.piano_c_sharp + "" : mPlayer = MediaPlayer.create(v.getContext(), R.raw.piano_c_sharp); break;
                        case R.raw.piano_d_major + "" : mPlayer = MediaPlayer.create(v.getContext(), R.raw.piano_d_major); break;
                        case R.raw.piano_e_major + "" : mPlayer = MediaPlayer.create(v.getContext(), R.raw.piano_e_major); break;
                        case R.raw.piano_f_major + "" : mPlayer = MediaPlayer.create(v.getContext(), R.raw.piano_f_major); break;
                        case R.raw.piano_g_major + "" : mPlayer = MediaPlayer.create(v.getContext(), R.raw.piano_g_major); break;
                        default: mPlayer = new MediaPlayer();
                                try {
                                    mPlayer.setDataSource(url);
                                    Log.d("MOMO", url + " " + mPlayer);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                break;
                    }

                    mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                        public void onCompletion(MediaPlayer mPlayer) {
                            mPlayer.stop();
                            mPlayer.release();
                        }
                    });

                    mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mPlayer.start();
                        }
                    });




                }});
        }else{
            soundViewHolder.getButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), EditProfileActivity.class);
                    intent.putExtra("name", soundArrayList.get(soundViewHolder.getBindingAdapterPosition()).getLabel());
                    intent.putExtra("soundref", soundArrayList.get(soundViewHolder.getBindingAdapterPosition()).getURL());
                    v.getContext().startActivity(intent);
                }});
        }


        return soundViewHolder;
    }

    @Override
    public void onBindViewHolder(SoundAdapter.SoundViewHolder holder, int position) {
        holder.setName(this.soundArrayList.get(position).getLabel());
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
