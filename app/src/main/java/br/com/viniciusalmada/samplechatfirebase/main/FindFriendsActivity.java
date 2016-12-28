package br.com.viniciusalmada.samplechatfirebase.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.viniciusalmada.samplechatfirebase.R;
import br.com.viniciusalmada.samplechatfirebase.domain.User;

/**
 * Created by vinicius-almada on 26/12/16.
 */

public class FindFriendsActivity extends AppCompatActivity {
    public static final String TAG = "FindFriendsActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
    }

    public void addFriend(View v) {
        Intent intent = getIntent();
        final String uid = intent.getStringExtra("uid");

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");
        final String s = ((EditText) findViewById(R.id.email)).getText().toString();
        userRef.orderByChild("email").equalTo(s).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.toString().contains(s)) {
                    User u = dataSnapshot.getChildren().iterator().next().getValue(User.class);
                    Log.d(TAG, "onDataChange: " + u.getName());
                    DatabaseReference usersFriends = FirebaseDatabase.getInstance().getReference()
                            .child("users_friends").child(uid);
                    usersFriends.child(u.getUid()).setValue(u);
                    finish();
                } else {
                    Toast.makeText(FindFriendsActivity.this, "Not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
