package winq.keult.foxplan.hu.winq.locationshare;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.example.keult.networking.NetworkManager;
import com.example.keult.networking.callback.PositionSendCallback;
import com.example.keult.networking.error.NetworkError;
import com.example.keult.networking.model.PositionSendResponse;
import com.example.keult.networking.model.ProfileData;

import java.util.HashMap;
import java.util.Map;

import winq.keult.foxplan.hu.winq.Winq;


/**
 * Pozíció küldése a szerverre
 */

class LocationShare {

    static synchronized void sendMyLocation(Context context, Location location) {
        // show my position
        final Map<String, Object> body = new HashMap<>();
        ProfileData profileData = Winq.getCurrentUserProfileData();

        body.put("apikey", "a");
        body.put("username", profileData.getUsername());
        body.put("password", profileData.getPassword());
        body.put("facebookid", profileData.getFacebookid());
        body.put("locx", Double.toString(location.getLatitude()));
        body.put("locy", Double.toString(location.getLongitude()));

        NetworkManager.getInstance().sendPosition(body, new PositionSendCallback() {

            @Override
            public void forwardResponse(PositionSendResponse positionSendResponse) {
                Log.v("LocationShare", "locx:" + body.get("locx") + " locy:" + body.get("locy"));
            }

            @Override
            public void forwardError(NetworkError networkError) {
                Log.v("LocationShare", networkError.getThrowable().getMessage());
            }
        });

    }
}
