package br.com.viniciusalmada.samplechatfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import br.com.viniciusalmada.samplechatfirebase.adapter.MessagesRoomAdapter;
import br.com.viniciusalmada.samplechatfirebase.domain.Message;
import br.com.viniciusalmada.samplechatfirebase.domain.Room;

/**
 * Created by vinicius-almada on 27/12/16.
 */

public class RoomActivity extends AppCompatActivity {
    private String uuidRoom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        Intent it = getIntent();
        uuidRoom = it.getStringExtra("room");
        init();
    }

    private void init() {
        DatabaseReference roomRef = FirebaseDatabase.getInstance().getReference().child("rooms").child(uuidRoom);
        roomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Room room = dataSnapshot.getValue(Room.class);
                ((TextView) findViewById(R.id.name)).setText(room.getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference messagesRef = FirebaseDatabase.getInstance().getReference().child("messages").child(uuidRoom);
        messagesRef.orderByChild("timestamp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Message> listMessages = new ArrayList<>();
                Iterable<DataSnapshot> it = dataSnapshot.getChildren();
                for (int i = 0; i < dataSnapshot.getChildrenCount(); i++) {
                    listMessages.add(it.iterator().next().getValue(Message.class));
                }

                MessagesRoomAdapter adapter = new MessagesRoomAdapter(RoomActivity.this, listMessages);
                LinearLayoutManager llm = new LinearLayoutManager(RoomActivity.this,LinearLayoutManager.VERTICAL, false);
                RecyclerView rv = (RecyclerView) findViewById(R.id.rvmsgs);

                rv.setAdapter(adapter);
                rv.setLayoutManager(llm);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void sendMessage(View view) {
        Message message = new Message();
        message.setMessage(((EditText) findViewById(R.id.text)).getText().toString());
        message.setUidUser(MainActivity.mUser.getUid());
        message.setTimestamp(System.currentTimeMillis());
        message.setNameUser(MainActivity.mUser.getName());
        message.saveOnFirebase(uuidRoom);
        Toast.makeText(this, "message sended", Toast.LENGTH_SHORT).show();
    }
}
