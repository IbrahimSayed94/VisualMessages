package com.example.ibrahim.messages.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ibrahim.messages.Model.Message;
import com.example.ibrahim.messages.R;


import java.util.List;

/**
 * Created by Ibrahim on 1/27/2019.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder>
{
    Context context ;
    List<Message> messageList;

    public MessageAdapter(Context context, List<Message> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_message , txt_sentiment ;

        public MyViewHolder(View itemView) {
            super(itemView);

            txt_message = itemView.findViewById(R.id.txt_message);
            txt_sentiment = itemView.findViewById(R.id.txt_sentiment);
        }
    }

    @NonNull
    @Override
    public MessageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MyViewHolder holder, int position) {

        holder.txt_message.setText(messageList.get(position).getMessage());
        holder.txt_sentiment.setText(messageList.get(position).getSentiment());

        String sentiment = messageList.get(position).getSentiment().trim();

        if(sentiment.equals("Neutral"))
            holder.txt_sentiment.setTextColor(Color.parseColor("#0eb511"));
        else if(sentiment.equals("Positive"))
            holder.txt_sentiment.setTextColor(Color.parseColor("#110eb5"));

        else  if(sentiment.equals("Negative"))
            holder.txt_sentiment.setTextColor(Color.parseColor("#ce0a1a"));


        Log.e("QP","ADapter : "+sentiment);



    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }


} // class of MessageAdapter
