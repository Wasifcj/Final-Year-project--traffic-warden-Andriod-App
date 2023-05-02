package com.cjz.wasif.trafficwarden;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class FirebaseViewHolder extends RecyclerView.ViewHolder {

    public TextView driver_namee;
    public TextView driver_id;
    public CircleImageView driver_profile_image;
    public TextView driver_contacte;


    public FirebaseViewHolder(@NonNull View itemView) {
        super(itemView);
        driver_id=itemView.findViewById(R.id.driverID);
        driver_namee=itemView.findViewById(R.id.driver_name);

        driver_profile_image=itemView.findViewById(R.id.image);
        driver_contacte=itemView.findViewById(R.id.driver_contact);

    }
}
