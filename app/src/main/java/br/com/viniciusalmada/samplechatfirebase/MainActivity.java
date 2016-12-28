package br.com.viniciusalmada.samplechatfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.viniciusalmada.samplechatfirebase.LoginActivity;
import br.com.viniciusalmada.samplechatfirebase.R;
import br.com.viniciusalmada.samplechatfirebase.domain.User;
import br.com.viniciusalmada.samplechatfirebase.main.CreateRoomActivity;
import br.com.viniciusalmada.samplechatfirebase.main.FindFriendsActivity;
import br.com.viniciusalmada.samplechatfirebase.main.ListFriendsActitity;
import br.com.viniciusalmada.samplechatfirebase.main.MyRoomsActivity;

/**
 * Created by vinicius-almada on 26/12/16.
 */

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity: ";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private FirebaseUser mFirebaseUser;
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mUsersRef = mRootRef.child("users");

    public static User mUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mFirebaseUser = firebaseAuth.getCurrentUser();
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUser = dataSnapshot.child(mFirebaseUser.getUid()).getValue(User.class);
                ((TextView) findViewById(R.id.name)).setText(mUser.getName());
                ((TextView) findViewById(R.id.email)).setText(mUser.getEmail());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthStateListener);
    }

    public void onClickButtons(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.find:
                intent = new Intent(this, FindFriendsActivity.class);
                intent.putExtra("uid",mFirebaseUser.getUid());
                startActivity(intent);
                break;
            case R.id.list:
                intent = new Intent(this, ListFriendsActitity.class);
                intent.putExtra("uid",mFirebaseUser.getUid());
                startActivity(intent);
                break;
            case R.id.create_room:
                intent = new Intent(this, CreateRoomActivity.class);
                intent.putExtra("uid",mFirebaseUser.getUid());
                startActivity(intent);
                break;
            case R.id.my_rooms:
                intent = new Intent(this, MyRoomsActivity.class);
                intent.putExtra("uid",mFirebaseUser.getUid());
                startActivity(intent);
                break;
            case R.id.log_out:
                mAuth.signOut();
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
