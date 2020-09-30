package com.example.fouthapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<Message> {
    private int resourceId;


    public MessageAdapter(Context context, int textViewResourceId,
                        List<Message> objects) {
        super(context, textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Message message = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.MessageImage = (ImageView) view.findViewById(R.id.message_img);
            viewHolder.MessageName = (TextView) view.findViewById(R.id.message_name);
            view.setTag(viewHolder);

        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        ImageView MessageImage = (ImageView) view.findViewById(R.id.message_img);
        TextView MessageName = (TextView) view.findViewById(R.id.message_name);
        MessageImage.setImageResource(message.getImageId());
        MessageName.setText(message.getName());
        return view;
    }

    class ViewHolder {
        ImageView MessageImage;
        TextView MessageName;
    }


}