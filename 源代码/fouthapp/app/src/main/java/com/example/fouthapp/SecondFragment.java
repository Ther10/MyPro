package com.example.fouthapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.fouthapp.activity.CreateGestureActivity;
import com.example.fouthapp.activity.GestureLoginActivity;

public class SecondFragment extends Fragment implements View.OnClickListener {

    private SecondViewModel mViewModel;
    private ImageButton toMapSign;
    private ImageButton toGesture;
    private ImageButton toFinger;

    private String userId;
    private String password;
    private String username;

    /*private static final int CHANGE = 200;
    //在Fragment声明context    private Context context;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;//这个代码片段是必要的
    }*/

    public static SecondFragment newInstance() {
        return new SecondFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //获取用户信息
        getActivityMessage();
        View view= inflater.inflate(R.layout.second_fragment, container, false);
        toMapSign = (ImageButton) view.findViewById(R.id.toMapSign);
        toGesture = (ImageButton) view.findViewById(R.id.toGesture);
        toFinger = (ImageButton) view.findViewById(R.id.toFinger);
        toMapSign.setOnClickListener(this);
        toGesture.setOnClickListener(this);
        toFinger.setOnClickListener(this);


        return view;

    }

    private void getActivityMessage(){
        BottomActivity activity = (BottomActivity) getActivity();
        userId = activity.getUserId();
        username = activity.getUsername();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.toMapSign:
                /*Intent intent1 = new Intent(getActivity(),MapSign.class);
                startActivity(intent1);*/
                MapSign.actionStart(getActivity(),userId,username);
                break;
            case R.id.toGesture:
                /*Intent intent2 = new Intent(getActivity(), CreateGestureActivity.class);
                startActivity(intent2);*/
                GestureLoginActivity.actionStart(getActivity(),userId,username);
                break;
            case R.id.toFinger:
                Intent intent3 = new Intent(getActivity(),FingerActivity.class);
                startActivity(intent3);
                break;
        }

    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SecondViewModel.class);
        // TODO: Use the ViewModel
    }


}