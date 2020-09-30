package com.example.fouthapp.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.fouthapp.ActivityCollector;
import com.example.fouthapp.BottomActivity;
import com.example.fouthapp.ChangePassActivity;
import com.example.fouthapp.LoginActivity;
import com.example.fouthapp.R;
import com.example.fouthapp.dao.UserDao;
import com.example.fouthapp.entity.User;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class FourthFragment extends Fragment implements View.OnClickListener {

    private FourthViewModel mViewModel;

    private ImageView blurImageView;

    private ImageView avatarImageView;

    private ImageView image_login;

    /*@BindView(R.id.user_id)*/
    private TextView user_id;
    private TextView user_name;
    private String userId;
    private String username;
    private Button exit;

    private LinearLayout change_pass_line;


    public static FourthFragment newInstance() {
        return new FourthFragment();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            //super.handleMessage(msg);
            if (msg.what==1){
                user_name = (TextView) getActivity().findViewById(R.id.user_name);
                user_name.setText((String)msg.obj);
            }
            if (msg.what==0){
                user_name = (TextView) getActivity().findViewById(R.id.user_name);
                user_name.setText("zhangsan");
            }
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       /* return inflater.inflate(R.layout.fourth_fragment, container, false); */
        View view =  inflater.inflate(R.layout.teacher_fourth_fragment, container, false);
        blurImageView = (ImageView) view.findViewById(R.id.h_back);

        avatarImageView = (ImageView) view.findViewById(R.id.h_head);

        //修改密码
        change_pass_line = (LinearLayout) view.findViewById(R.id.change_pass_line);
        change_pass_line.setOnClickListener(this);

        //登录界面跳转
        image_login = (ImageView) view.findViewById(R.id.img_login);
        image_login.setOnClickListener(this);

        //登录信息显示
        TeacherMainActivity activity = (TeacherMainActivity) getActivity();
        userId = activity.getUserId();
        username = activity.getUsername();

        //退出登录
        exit = (Button) view.findViewById(R.id.teacher_exit);
        exit.setOnClickListener(this);

        user_id = (TextView) view.findViewById(R.id.user_id);
        user_id.setText(userId);

        user_name = (TextView) view.findViewById(R.id.user_name);
        user_name.setText(username);

        //根据userId获取username
        //getName(userId);

        //设置背景磨砂效果
        Glide.with(getActivity()).load(R.drawable.touxiang)
                .bitmapTransform(new BlurTransformation(getActivity(), 25), new CenterCrop(getActivity()))
                .into(blurImageView);

        //设置圆形图像
        Glide.with(getActivity()).load(R.drawable.touxiang)
                .bitmapTransform(new CropCircleTransformation(getActivity()))
                .into(avatarImageView);

        return view;

    }

    private void getName(String userId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserDao dao = new UserDao();
                User user = dao.getUserThroughUserId(userId);
                if (user!=null){
                    username = user.getUsername();
                    Message msg = Message.obtain();
                    msg.what = 1;
                    msg.obj = username;
                    //向主线程发送数据
                    handler.sendMessage(msg);

                }else{
                    Message msg = Message.obtain();
                    msg.what = 0;
                    //向主线程发送数据
                    handler.sendMessage(msg);
                }
                System.out.println(userId);
                /*username = dao.getNameByUserId(userId);
                System.out.println("username"+username);
                if (username!=null){
                    Message msg = Message.obtain();
                    msg.what = 1;
                    msg.obj = username;
                    //向主线程发送数据
                    handler.sendMessage(msg);
                }else{
                    Message msg = Message.obtain();
                    msg.what = 0;
                    //向主线程发送数据
                    handler.sendMessage(msg);
                }*/
            }
        }).start();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FourthViewModel.class);
        // TODO: Use the ViewModel

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_login:
                Intent intent1 = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent1);
                break;
            case R.id.teacher_exit:
                isLogin();
                ActivityCollector.finishAll();
                break;
            case R.id.change_pass_line:
                //判断用户是否已登录
                isLogin();
                ChangePassActivity.actionStart(getActivity(),userId,username);
                break;

        }

    }

    private void isLogin(){
        if (userId == null){
            Toast.makeText(getActivity(),"您尚未登录，请先登录！",Toast.LENGTH_SHORT).show();
            ActivityCollector.finishAll();
        }
    }
}