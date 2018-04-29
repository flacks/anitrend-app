package com.mxt.anitrend.model.entity.base;

import android.os.Parcel;
import android.os.Parcelable;

import com.mxt.anitrend.model.entity.group.EntityGroup;
import com.mxt.anitrend.util.KeyUtil;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by max on 2018/02/24.
 * Notification base meta data class
 */
public class NotificationBase extends EntityGroup implements Parcelable {

    private long id;
    private @KeyUtil.NotificationType String type;
    private long createdAt;
    private String context;

    public NotificationBase() {
        // empty constructor required by object box
    }

    protected NotificationBase(Parcel in) {
        id = in.readLong();
        type = in.readString();
        createdAt = in.readLong();
        context = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(type);
        dest.writeLong(createdAt);
        dest.writeString(context);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NotificationBase> CREATOR = new Creator<NotificationBase>() {
        @Override
        public NotificationBase createFromParcel(Parcel in) {
            return new NotificationBase(in);
        }

        @Override
        public NotificationBase[] newArray(int size) {
            return new NotificationBase[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public @KeyUtil.NotificationType String getType() {
        return type;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public String getContext() {
        return context;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NotificationBase)
            return ((NotificationBase)obj).getId() == id;
        return super.equals(obj);
    }
}
