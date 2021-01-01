package com.gmail.anc.antenatalcare.anc_antenatalcare.Service;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        sendNewTokenToSever(FirebaseInstanceId.getInstance().getToken());
    }

    private void sendNewTokenToSever(String token) {

    }
}
