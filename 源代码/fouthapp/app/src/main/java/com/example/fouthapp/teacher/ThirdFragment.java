package com.example.fouthapp.teacher;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.fouthapp.Message;
import com.example.fouthapp.MessageActivity;
import com.example.fouthapp.MessageAdapter;
import com.example.fouthapp.R;

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
            Message message1 = new Message("版本更新通知",R.mipmap.message2,"1");
            messageList.add(message1);
            Message message2 = new Message("您教授的课程距离开课还有5分钟",R.mipmap.message2,"2");
            messageList.add(message2);
            Message message3 = new Message("您的假条已被学院批准",R.mipmap.message2,"3");
            messageList.add(message3);
            Message message4 = new Message("手势密码创建成功，已有同学成功签到",R.mipmap.message2,"4");
            messageList.add(message4);
            Message message5 = new Message("您的账号注册已成功，欢迎使用！",R.mipmap.message2,"5");
            messageList.add(message5);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ThirdViewModel.class);
        // TODO: Use the ViewModel
    }

}