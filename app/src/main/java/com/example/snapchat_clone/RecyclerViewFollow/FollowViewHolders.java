package com.example.snapchat_clone.RecyclerViewFollow;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.snapchat_clone.R;

import org.w3c.dom.Text;

public class FollowViewHolders extends RecyclerView.ViewHolder {

    public TextView tv_email;
    public Button follow;

    public FollowViewHolders(View itemView){
        super(itemView);

        tv_email = (TextView) itemView.findViewById(R.id.tv_email);
        follow = (Button) itemView.findViewById(R.id.btn_follow);



    }
}
