//package wolfsoft.routes.Notification;
//
//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//
//import com.onesignal.OSNotificationAction;
//import com.onesignal.OSNotificationOpenResult;
//import com.onesignal.OneSignal;
//
//import org.json.JSONObject;
//
///**
// * Created by G on 05/12/2017.
// */
//
//public class MyNotificationOpenedHandler2 implements OneSignal.NotificationOpenedHandler{
//
//    private Context application;
//
//    public MyNotificationOpenedHandler2( Context application) {
//        this.application = application;
//    }
//    @Override
//    public void notificationOpened(OSNotificationOpenResult result) {
//        // Get custom datas from notification
//        JSONObject data = result.notification.payload.additionalData;
//        if (data != null) {
//            String myCustomData = data.optString("key", null);
//        }
//
//        // React to button pressed
//        OSNotificationAction.ActionType actionType = result.action.type;
//        if (actionType == OSNotificationAction.ActionType.ActionTaken)
//            Log.i("OneSignalExample", "Button pressed with id: " + result.action.actionID);
//
//        // Launch new activity using Application object
//    }
//
//
//}
//
//
//
