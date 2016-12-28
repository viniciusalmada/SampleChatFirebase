package br.com.viniciusalmada.samplechatfirebase.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.viniciusalmada.samplechatfirebase.MainActivity;
import br.com.viniciusalmada.samplechatfirebase.R;
import br.com.viniciusalmada.samplechatfirebase.adapter.ListFriendsAdapter;
import br.com.viniciusalmada.samplechatfirebase.adapter.ListFriendsToCreateRoomAdapter;
import br.com.viniciusalmada.samplechatfirebase.domain.Room;
import br.com.viniciusalmada.samplechatfirebase.domain.User;

/**
 * Created by vinicius-almada on 27/12/16.
 */

public class CreateRoomActivity extends AppCompatActivity {
    public static final String TAG = "CreateRoomActivity";
    private ListView mFriendsListView;
    //    private ListFriendsToCreateRoomAdapter mAdapter;
    private List<User> mUsersList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        init();
    }

    private void init() {
        Intent intent = getIntent();
        String uid = intent.getStringExtra("uid");
        mFriendsListView = (ListView) findViewById(R.id.list_friends);

        mUsersList = new ArrayList<>();
        DatabaseReference usersFriends = FirebaseDatabase.getInstance().getReference().child("users_friends");
        usersFriends.child(uid).orderByChild("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> it = dataSnapshot.getChildren();
                Log.d(TAG, "onDataChange: " + dataSnapshot.getChildrenCount());
                for (int i = 0; i < dataSnapshot.getChildrenCount(); i++) {
                    mUsersList.add(it.iterator().next().getValue(User.class));
                    Log.d(TAG, "onDataChange: " + i + "\t" + mUsersList.get(i).getEmail());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(CreateRoomActivity.this, android.R.layout.simple_list_item_multiple_choice, getStrings(mUsersList));
                mFriendsListView.setAdapter(adapter);
                mFriendsListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private String[] getStrings(List<User> list) {
        String[] aux = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            aux[i] = list.get(i).getName();
        }
        return aux;
    }

    public void createRoom(View v) {
        SparseBooleanArray checkeds = mFriendsListView.getCheckedItemPositions();
        ArrayList<User> selectedItems = new ArrayList<>();
        for (int i = 0; i < checkeds.size(); i++) {
            // Item position in adapter
            int position = checkeds.keyAt(i);
            // Add sport if it is checked i.e.) == TRUE!
            if (checkeds.valueAt(i))
                selectedItems.add(mUsersList.get(position));
        }
        selectedItems.add(MainActivity.mUser);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        Room room = new Room();
        room.setName(((EditText) findViewById(R.id.name_of_room)).getText().toString());
        room.setUUID(uuid);
        room.setMembers(selectedItems);
        room.saveOnFirebase();
        finish();
    }
}
