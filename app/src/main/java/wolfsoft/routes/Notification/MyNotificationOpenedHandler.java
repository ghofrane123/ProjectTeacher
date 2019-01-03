//package wolfsoft.routes.Notification;
///*
//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//
//
//import org.json.JSONObject;
//
//import wolfsoft.routes.Historique;
//
///**
// * Created by G on 04/12/2017.
// */
//
//public class MyNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
//    private Context application;
//
//    public MyNotificationOpenedHandler( Context application) {
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
//        startApp();
//    }
//
//    private void startApp() {
//        Intent intent = new Intent(application, Historique.class)
//                .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//        application.startActivity(intent);
//    }
//    }
//
