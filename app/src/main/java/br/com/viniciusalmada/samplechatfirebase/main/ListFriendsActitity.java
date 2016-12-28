package br.com.viniciusalmada.samplechatfirebase.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.viniciusalmada.samplechatfirebase.R;
import br.com.viniciusalmada.samplechatfirebase.adapter.ListFriendsAdapter;
import br.com.viniciusalmada.samplechatfirebase.domain.User;

/**
 * Created by vinicius-almada on 27/12/16.
 */

public class ListFriendsActitity extends AppCompatActivity{
    public static final String TAG = "ListFriendsActitity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_friends);
        init();
    }

    private void init(){
        Intent intent = getIntent();
        String uid = intent.getStringExtra("uid");

        final List<User> usersList = new ArrayList<>();
        DatabaseReference usersFriends = FirebaseDatabase.getInstance().getReference().child("users_friends");
        usersFriends.child(uid).orderByChild("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> it = dataSnapshot.getChildren();
                for (int i = 0; i < dataSnapshot.getChildrenCount(); i++) {
                    usersList.add(it.iterator().next().getValue(User.class));
                }
                ListFriendsAdapter adapter = new ListFriendsAdapter(usersList, ListFriendsActitity.this);
                ((ListView) findViewById(R.id.list_friends)).setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
