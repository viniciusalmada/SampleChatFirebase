package br.com.viniciusalmada.samplechatfirebase.domain;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by vinicius-almada on 26/12/16.
 */

public class User {
    private String name;
    private String email;
    private String uid;

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void saveOnFirebase() {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference();
        userRef.child("users").child(uid).setValue(this);
    }
}
