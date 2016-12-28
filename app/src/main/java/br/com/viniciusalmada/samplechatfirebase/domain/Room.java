package br.com.viniciusalmada.samplechatfirebase.domain;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by vinicius-almada on 27/12/16.
 */

public class Room {
    private String name;
    private String uuid;
    private List<User> members;

    public Room() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUUID() {
        return uuid;
    }

    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public void saveOnFirebase() {
        DatabaseReference roomRef = FirebaseDatabase.getInstance().getReference().child("rooms");
        roomRef.child(uuid).setValue(this);

       /* DatabaseReference roomMembersRef = FirebaseDatabase.getInstance().getReference().child("rooms_members");
        for (User u : members)
            roomMembersRef.child(uuid).child(u.getUid()).setValue(u);*/
    }

    public boolean containsUser(String uid){
        for (User u : members){
            if (u.getUid().equalsIgnoreCase(uid)){
                return true;
            }
        }
        return false;
    }
}
