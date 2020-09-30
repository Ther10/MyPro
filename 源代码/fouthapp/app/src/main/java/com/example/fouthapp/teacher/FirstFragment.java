package com.example.fouthapp.teacher;

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

import com.example.fouthapp.LeaveActivity;
import com.example.fouthapp.R;
import com.example.fouthapp.SubjectActivity;
import com.example.fouthapp.TableActivity;
import com.example.fouthapp.activity.CreateGestureActivity;

public class FirstFragment extends Fragment implements View.OnClickListener {

    private FirstViewModel mViewModel;

    private ImageButton my_course_btn;
    private ImageButton my_leave_btn;
    private ImageButton student_sign_btn;
    private ImageButton create_gesture;

    public static FirstFragment newInstance() {
        return new FirstFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_first_fragment, container, false);
        my_course_btn = (ImageButton) view.findViewById(R.id.my_course_btn);
        my_leave_btn = (ImageButton) view.findViewById(R.id.my_leave_btn);
        student_sign_btn = (ImageButton) view.findViewById(R.id.student_sign_btn);
        create_gesture = (ImageButton) view.findViewById(R.id.create_gesture);

        my_course_btn.setOnClickListener(this);
        my_leave_btn.setOnClickListener(this);
        student_sign_btn.setOnClickListener(this);
        create_gesture.setOnClickListener(this);
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
            case R.id.student_sign_btn:
                Intent intent2 = new Intent(getActivity(), TableActivity.class);
                startActivity(intent2);
                break;
            case R.id.create_gesture:
                Intent intent3 = new Intent(getActivity(), CreateGestureActivity.class);
                startActivity(intent3);
                break;
        }
    }
}