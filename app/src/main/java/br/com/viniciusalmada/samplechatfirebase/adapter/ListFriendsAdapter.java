package br.com.viniciusalmada.samplechatfirebase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.viniciusalmada.samplechatfirebase.domain.User;

/**
 * Created by vinicius-almada on 27/12/16.
 */

public class ListFriendsAdapter extends BaseAdapter {
    private List<User> usersList;
    private Context context;
    private LayoutInflater inflater;

    public ListFriendsAdapter(List<User> usersList, Context context) {
        this.usersList = usersList;
        this.context = context;
        try {
            inflater = LayoutInflater.from(context);
        } catch (NullPointerException ignored){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
    }

    @Override
    public int getCount() {
        return usersList.size();
    }

    @Override
    public Object getItem(int position) {
        return usersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.two_line_list_item, parent, false);
            holder = new ViewHolder();
            convertView.setTag(holder);

            holder.tv1 = (TextView) convertView.findViewById(android.R.id.text1);
            holder.tv2 = (TextView) convertView.findViewById(android.R.id.text2);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv1.setText(usersList.get(position).getName());
        holder.tv2.setText(usersList.get(position).getEmail());
        return convertView;
    }

    private class ViewHolder{
        TextView tv1;
        TextView tv2;
    }
}
