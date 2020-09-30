package com.example.fouthapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class FirstFragment extends Fragment implements View.OnClickListener {

    private FirstViewModel mViewModel;

    private ImageButton my_course_btn;
    private ImageButton my_leave_btn;
    private ImageButton my_sign_btn;

    private String userId;
    private String username;

    public static FirstFragment newInstance() {
        return new FirstFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment, container, false);
        my_course_btn = (ImageButton) view.findViewById(R.id.my_course_btn);
        my_leave_btn = (ImageButton) view.findViewById(R.id.my_leave_btn);
        my_sign_btn = (ImageButton) view.findViewById(R.id.my_sign_btn);

        my_course_btn.setOnClickListener(this);
        my_leave_btn.setOnClickListener(this);
        my_sign_btn.setOnClickListener(this);

        BottomActivity activity = (BottomActivity) getActivity();
        userId = activity.getUserId();
        username = activity.getUsername();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FirstViewModel mViewModel = ViewModelProviders.of(this).get(FirstViewModel.class);
        // TODO: Use the ViewModel

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.my_course_btn:
                Intent intent = new Intent(getActivity(),SubjectActivity.class);
                startActivity(intent);
                break;
            case R.id.my_leave_btn:
                Intent intent1 = new Intent(getActivity(), LeaveActivity.class);
                startActivity(intent1);
                break;
            case R.id.my_sign_btn:
                MySignActivity.actionStart(getActivity(),userId,username);
                break;
        }
    }
}