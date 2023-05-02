package com.cjz.wasif.trafficwarden;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {



private RecyclerView recyclerView;
private DatabaseReference mdatabase;
private ArrayList<DataSetFire> arrayList;

private FirebaseRecyclerOptions<DataSetFire> options;
private FirebaseRecyclerAdapter<DataSetFire,FirebaseViewHolder> adapter;
private DatabaseReference databaseReference;
MediaPlayer mMediaPlayer;




    @Override
    protected void onStart(){

        super.onStart();

adapter.startListening();




    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.startListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        recyclerView=(RecyclerView)findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList=new ArrayList<DataSetFire>();
        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(this,alert);
        } catch (IOException e) {
            e.printStackTrace();
        }
        final AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
            mMediaPlayer.setLooping(true);
            try {
                mMediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }



        databaseReference=FirebaseDatabase.getInstance().getReference().child("Tracking");
        databaseReference.keepSynced(true);
        final android.support.v7.app.AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()&& dataSnapshot.getChildrenCount()>0){
                    Notification_builder();

                }
                else {
                    Toast.makeText(MainActivity.this, "No Tracking Found!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        options=new FirebaseRecyclerOptions.Builder<DataSetFire>().setQuery(databaseReference,DataSetFire.class).build();
        adapter= new FirebaseRecyclerAdapter<DataSetFire, FirebaseViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseViewHolder holder, int position, @NonNull final DataSetFire model) {

                holder.driver_id.setText("DriverID: "+model.getDriverID());
                holder.driver_namee.setText("Name: "+model.getName());
                holder.driver_contacte.setText("Phone: "+model.getPhone());
                Glide.with(getApplicationContext()).load(model.getProfileImageUrl()).into(holder.driver_profile_image);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(MainActivity.this,home.class);
                        intent.putExtra("Driver ID",model.getDriverID());
                        Toast.makeText(MainActivity.this, "Tracking", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                });


            }

            @NonNull
            @Override
            public FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new FirebaseViewHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.row,viewGroup,false));
            }
        };

        recyclerView.setAdapter(adapter);





}



    private void Notification_builder() {



        final Vibrator vibrator;
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(20000, VibrationEffect.DEFAULT_AMPLITUDE));


            mMediaPlayer.start();


        } else {
            vibrator.vibrate(20000);


            mMediaPlayer.start();

        }

        dialog.setTitle("AMBULANCE");
        dialog.setMessage("Please Accept Ambulance Emergency Request!");

        dialog.setPositiveButton("accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                vibrator.cancel();
mMediaPlayer.stop();

            }
        });
        dialog.setCancelable(false);
        dialog.show();

    }


}


