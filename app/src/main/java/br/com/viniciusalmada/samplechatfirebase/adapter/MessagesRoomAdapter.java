package br.com.viniciusalmada.samplechatfirebase.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.viniciusalmada.samplechatfirebase.domain.Message;

/**
 * Created by vinicius-almada on 27/12/16.
 */

public class MessagesRoomAdapter extends RecyclerView.Adapter<MessagesRoomAdapter.CustomViewHolder> {
    private List<Message> messageList;
    private LayoutInflater inflater;

    public MessagesRoomAdapter(Context context, List<Message> messages){
        messageList = messages;
        inflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(android.R.layout.two_line_list_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.textView1.setText(messageList.get(position).getNameUser());
        holder.textView2.setText(messageList.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView textView1;
        private TextView textView2;

        public CustomViewHolder(View itemView) {
            super(itemView);
            textView1 = (TextView) itemView.findViewById(android.R.id.text1);
            textView2 = (TextView) itemView.findViewById(android.R.id.text2);
        }
    }
}
