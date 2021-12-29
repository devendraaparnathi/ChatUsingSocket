package com.example.kotlinstart.database;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kotlinstart.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter {

    private static final int TYPE_MESSAGE_SENT = 0;
    private static final int TYPE_MESSAGE_RECEIVED = 1;

    private LayoutInflater inflater;
    private List<JSONObject> messages = new ArrayList<>();

    public MessageAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public SentMessageHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.sentText);
        }
    }

    private class ReceiveMessage extends RecyclerView.ViewHolder {

        TextView nameTxt, messageTxt;

        public ReceiveMessage(@NonNull View itemView) {
            super(itemView);

            nameTxt = itemView.findViewById(R.id.nameTxt);
            messageTxt = itemView.findViewById(R.id.receiveText);
        }
    }

    @Override
    public int getItemViewType(int position) {

        JSONObject message = messages.get(position);

        try {
            if (message.getBoolean("isSent")) {
                if (message.has("message"))
                    return TYPE_MESSAGE_SENT;
            } else {
                if (message.has("message"))
                    return TYPE_MESSAGE_RECEIVED;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        switch (viewType)
        {
            case TYPE_MESSAGE_SENT:
                view = inflater.inflate(R.layout.item_sent_message,parent,false);
                return new SentMessageHolder(view);
            case TYPE_MESSAGE_RECEIVED:
                view = inflater.inflate(R.layout.item_receive_message,parent,false);
                return new ReceiveMessage(view);


        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        JSONObject message = messages.get(position);

        try {
            if (message.getBoolean("isSent"))
            {
                if (message.has("message"))
                {
                    SentMessageHolder messageHolder = (SentMessageHolder) holder;
                    messageHolder.textView.setText(message.getString("message"));
                }else
                {
                    if (message.has("message")){
                        ReceiveMessage receiveMessage = (ReceiveMessage) holder;
                        receiveMessage.nameTxt.setText(message.getString("name"));
                        receiveMessage.messageTxt.setText(message.getString("message"));

                }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void addItem(JSONObject jsonObject){
        messages.add(jsonObject);
        notifyDataSetChanged();
    }

}
