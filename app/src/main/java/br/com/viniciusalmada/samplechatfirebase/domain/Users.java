package br.com.viniciusalmada.samplechatfirebase.domain;

import java.util.Arrays;

/**
 * Created by vinicius-almada on 26/12/16.
 */

public class Users {
    private String[] usersUid;

    public Users(String... usersUid) {
        this.usersUid = usersUid;
    }

    public Users() {
    }

    public String getUsers(){
        return Arrays.deepToString(usersUid);
    }
}
