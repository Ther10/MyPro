package com.example.fouthapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ThirdFragment extends Fragment {

    private ThirdViewModel mViewModel;
    private List<Message> messageList = new ArrayList<>();


    public static ThirdFragment newInstance() {
        return new ThirdFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.third_fragment, container, false);
        initMessages();
        MessageAdapter adapter = new MessageAdapter(
                getActivity(),R.layout.message_item,messageList);
        ListView listView = (ListView)view.findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Message message = messageList.get(position);
                //Toast.makeText(getActivity(), message.getMessageId(),Toast.LENGTH_SHORT).show();
                MessageActivity.actionStart(getActivity(),message.getMessageId(),message.getName());
            }
        });
        return view;
    }

    private void initMessages(){
            Message message1 = new Message("计算机网络上课啦，快去打卡哟！",R.mipmap.message2,"1");
            messageList.add(message1);
            Message message2 = new Message("算法设计与分析上课啦，快去打卡哟！",R.mipmap.message2,"2");
            messageList.add(message2);
            Message message3 = new Message("版本更新通知",R.mipmap.message2,"3");
            messageList.add(message3);
            Message message4 = new Message("你的假条已被辅导员老师批准",R.mipmap.message2,"4");
            messageList.add(message4);
            Message message5 = new Message("密码更改成功",R.mipmap.message2,"5");
            messageList.add(message5);
            Message message6 = new Message("您的账号已在别地登录！",R.mipmap.message2,"6");
            messageList.add(message6);
            Message message7 = new Message("数据结构上课啦，快去打卡呦",R.mipmap.message2,"7");
            messageList.add(message7);
            Message message8 = new Message("近日Java课程未签到，老师已记录在案",R.mipmap.message2,"8");
            messageList.add(message8);
            Message message9 = new Message("账号注册成功，欢迎使用",R.mipmap.message2,"9");
            messageList.add(message9);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ThirdViewModel.class);
        // TODO: Use the ViewModel
    }

}