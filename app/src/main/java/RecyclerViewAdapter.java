import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cjz.wasif.trafficwarden.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{


    private static final String TAG ="RecyclerViewAdapter";

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout parentLayout;
        CircleImageView imageView;
        public TextView driver_id;
        TextView driver_contact_view;
        TextView driver_name_view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.image);
            driver_id=itemView.findViewById(R.id.driverID);
            driver_contact_view=itemView.findViewById(R.id.driver_contact);
            driver_name_view=itemView.findViewById(R.id.driver_name);
            parentLayout=itemView.findViewById(R.id.parent_layout);

        }
    }
}
