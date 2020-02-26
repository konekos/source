package com.jasu.concurrent.jcia.chapter11;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;
import java.util.Set;

/*****************************************
 * @author hjs
 * @date 2020-02-21 1:25
 *****************************************/
@ThreadSafe
public class ServerStatus {
    @GuardedBy("users")
    public final Set<String> users;
    @GuardedBy("queries")
    public final Set<String> queries;

    public ServerStatus(Set<String> users, Set<String> queries) {
        this.users = users;
        this.queries = queries;
    }

    public void addUser(String u) {
        synchronized (users) {
            users.add(u);
        }
    }

    public void addQuery(String s) {
        synchronized (queries) {
            queries.add(s);
        }
    }
}
