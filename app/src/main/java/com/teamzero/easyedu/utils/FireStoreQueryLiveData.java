package com.teamzero.easyedu.utils;

import android.os.Handler;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.orhanobut.logger.Logger;

import javax.annotation.Nullable;

import androidx.lifecycle.LiveData;

public class FireStoreQueryLiveData extends LiveData<QuerySnapshot> {
    private static final String TAG = "FirestoreQueryLiveData";
    private final MyValueEventListener listener = new MyValueEventListener();
    private final Handler handler = new Handler();
    private Query query;
    private ListenerRegistration listenerRegistration;
    private boolean listenerRemovePending = false;
    private final Runnable removeListener = new Runnable() {
        @Override
        public void run() {
            listenerRegistration.remove();
            listenerRemovePending = false;
        }
    };

    public FireStoreQueryLiveData(Query query) {
        this.query = query;
    }

    public FireStoreQueryLiveData(CollectionReference collectionReference) {
        this.query = collectionReference;
    }

    @Override
    protected void onActive() {
        super.onActive();

//        Logger.d(TAG, "onActive");
        if (listenerRemovePending) {
            handler.removeCallbacks(removeListener);
        } else {
            listenerRegistration = query.addSnapshotListener(listener);
        }
        listenerRemovePending = false;
    }

    @Override
    protected void onInactive() {
        super.onInactive();

//        Logger.d(TAG, "onInactive: ");
        // Listener removal is schedule on a two second delay
        handler.postDelayed(removeListener, 2000);
        listenerRemovePending = true;
    }

    private class MyValueEventListener implements EventListener<QuerySnapshot> {
        @Override
        public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {
            if (e != null) {
                Logger.e(TAG, "Can't listen to query snapshots: " + querySnapshot + ":::" + e.getMessage());
                return;
            }
            setValue(querySnapshot);
        }
    }
}
