package com.example.snapchat_clone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.snapchat_clone.Fragments.CameraFragment;
import com.example.snapchat_clone.Fragments.ChatFragment;
import com.example.snapchat_clone.Fragments.StoryFragment;

public class MainActivity extends AppCompatActivity {
    ViewPager vwPager;
    FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //instantiating the obj of Class userInformation here and after it we are calling its method startFetching !!!!
        userInformation obj_userInformation = new userInformation();
        obj_userInformation.startFetching();

        vwPager =(ViewPager)findViewById(R.id.viewPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vwPager.setAdapter(adapterViewPager);
        // to set the camera fragment on the screen !!!
        vwPager.setCurrentItem(1);

    }
    public static class MyPagerAdapter extends FragmentPagerAdapter{

        public MyPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    //return fragment chat
                    //calling the method here !!!!
                    return ChatFragment.newInstance();
                case 1:
                    // return fragment camera
                    return CameraFragment.newInstance();
                case 2:
                    //return fragment stories
                    return StoryFragment.newInstance();
            }

            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}