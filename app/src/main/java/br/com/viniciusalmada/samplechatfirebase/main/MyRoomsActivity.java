package br.com.viniciusalmada.samplechatfirebase.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.viniciusalmada.samplechatfirebase.MainActivity;
import br.com.viniciusalmada.samplechatfirebase.R;
import br.com.viniciusalmada.samplechatfirebase.RoomActivity;
import br.com.viniciusalmada.samplechatfirebase.domain.Room;
import br.com.viniciusalmada.samplechatfirebase.domain.User;

/**
 * Created by vinicius-almada on 27/12/16.
 */

public class MyRoomsActivity extends AppCompatActivity implements ListView.OnItemClickListener{
    public static final String TAG = "MyRoomsActivity";
    private List<Room> roomList = new ArrayList<>();
    private List<Room> roomListOfUser = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rooms);
        listView = (ListView) findViewById(R.id.list_rooms);
        init();
    }

    private void init(){
        DatabaseReference roomMembersRef = FirebaseDatabase.getInstance().getReference().child("rooms");
        roomMembersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> it = dataSnapshot.getChildren();
                for (int i = 0; i < dataSnapshot.getChildrenCount(); i++) {
                    roomList.add(it.iterator().next().getValue(Room.class));
                }

                for (Room r : roomList){
                    if (r.containsUser(MainActivity.mUser.getUid())){
                        roomListOfUser.add(r);
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(MyRoomsActivity.this, android.R.layout.simple_list_item_1, getStrings(roomListOfUser));
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(MyRoomsActivity.this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private String[] getStrings(List<Room> list) {
        String[] aux = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            aux[i] = list.get(i).getName();
        }
        return aux;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent it = new Intent(this, RoomActivity.class);
        it.putExtra("room",roomListOfUser.get(position).getUUID());
        startActivity(it);
    }
}
