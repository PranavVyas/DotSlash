package com.teamzero.easyedu.database.followingdatabase;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "FollowingTable")
public class FollowingEntry {
    @PrimaryKey(autoGenerate = true)
    private long _ID;
    private String subject, branch, sem;
    private boolean following;

    @Ignore
    public FollowingEntry() {
    }

    @Ignore
    public FollowingEntry(String subject, String branch, String sem, boolean following) {
        this.subject = subject;
        this.branch = branch;
        this.sem = sem;
        this.following = following;
    }

    public FollowingEntry(long _ID, String subject, String branch, String sem, boolean following) {
        this._ID = _ID;
        this.subject = subject;
        this.branch = branch;
        this.sem = sem;
        this.following = following;
    }

    public long get_ID() {
        return _ID;
    }

    public void set_ID(long _ID) {
        this._ID = _ID;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }
}
