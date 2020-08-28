package com.example.snapchat_clone.RecyclerViewStories;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.Validator;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.snapchat_clone.R;
import com.example.snapchat_clone.showImageScreen;

public class StoryViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tv_Storyemail;
    public LinearLayout my_Layout;

    public StoryViewHolders(View itemView){
        super(itemView);
        itemView.setOnClickListener(this);
        tv_Storyemail = (TextView) itemView.findViewById(R.id.tv_Storyemail);
        my_Layout = (LinearLayout) itemView.findViewById(R.id.layout);

    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(view.getContext(), showImageScreen.class);
        Bundle b = new Bundle();
        b.putString("userId", tv_Storyemail.getTag().toString());
        b.putString("chatOrStory", my_Layout.getTag().toString());
        i.putExtras(b);
        view.getContext().startActivity(i);
    }
  }