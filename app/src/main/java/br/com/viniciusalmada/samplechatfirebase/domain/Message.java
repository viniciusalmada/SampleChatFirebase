package br.com.viniciusalmada.samplechatfirebase.domain;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by vinicius-almada on 27/12/16.
 */

public class Message {
    private String message;
    private String uidUser;
    private long timestamp;
    private String nameUser;

    public Message() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getUidUser() {
        return uidUser;
    }

    public void setUidUser(String uidUser) {
        this.uidUser = uidUser;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void saveOnFirebase(String uuidRoom){
        DatabaseReference messagesRoomRef = FirebaseDatabase.getInstance().getReference().child("messages").child(uuidRoom);
        messagesRoomRef.child("m" + timestamp).setValue(this);
    }
}
