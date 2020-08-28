package com.example.snapchat_clone.RecyclerViewReciever;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.snapchat_clone.R;

public class RecieverViewHolders extends RecyclerView.ViewHolder {

    public TextView mEmail;
    public CheckBox mReceiver;

    public RecieverViewHolders(View itemView){
        super(itemView);

        mEmail = (TextView) itemView.findViewById(R.id.txt_email);
        mReceiver = (CheckBox) itemView.findViewById(R.id.chk_receive);




    }
}
