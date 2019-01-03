//package wolfsoft.routes.Notification;
//
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.StrictMode;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.TextView;
//
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.Scanner;
//
//import wolfsoft.routes.Main3Activity;
//import wolfsoft.routes.Profile;
//import wolfsoft.routes.R;
//
//public class ResponseNotification extends AppCompatActivity {
//
//    private TextView accepted;
//    private TextView refused;
//    public static  String fetch = "true";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_response_notification);
//
//        accepted = (TextView)findViewById(R.id.accepted);
//        refused = (TextView)findViewById(R.id.refused);
//
//        accepted.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                AsyncTask.execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        int SDK_INT = android.os.Build.VERSION.SDK_INT;
//                        if (SDK_INT > 8) {
//                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
//                                    .permitAll().build();
//                            StrictMode.setThreadPolicy(policy);
//                            String send_email;
//
//                            //This is a Simple Logic to Send Notification different Device Programmatically....
//
//                            send_email = "test@live.com";
//                            fetch = "true";
//
//
//                            try {
//                                String jsonResponse;
//
//                                URL url = new URL("https://onesignal.com/api/v1/notifications");
//                                HttpURLConnection con = (HttpURLConnection) url.openConnection();
//                                con.setUseCaches(false);
//                                con.setDoOutput(true);
//                                con.setDoInput(true);
//
//                                con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
//                                con.setRequestProperty("Authorization", "Basic MDg2OGM4MDUtNGY1MS00YmU4LWE5ZjctNzJjMGFkMDBkNTQz");
//                                con.setRequestMethod("POST");
//
//                                String strJsonBody = "{"
//                                        + "\"app_id\": \"21128ac1-8b35-454e-99c7-378a5073d951\","
//
//                                        + "\"filters\": [{\"field\": \"tag\", \"key\": \"User_ID\", \"relation\": \"=\", \"value\": \"" + send_email + "\"}],"
//
//                                        + "\"data\": {\"foo\": \"bar\"},"
//                                        + "\"contents\": {\"en\": \"accepted \"}"
//                                        + "}";
//
//
//                                System.out.println("strJsonBody:\n" + strJsonBody);
//
//                                byte[] sendBytes = strJsonBody.getBytes("UTF-8");
//                                con.setFixedLengthStreamingMode(sendBytes.length);
//
//                                OutputStream outputStream = con.getOutputStream();
//                                outputStream.write(sendBytes);
//
//                                int httpResponse = con.getResponseCode();
//                                System.out.println("httpResponse: " + httpResponse);
//
//                                if (httpResponse >= HttpURLConnection.HTTP_OK
//                                        && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
//                                    Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
//                                    jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
//                                    scanner.close();
//                                } else {
//                                    Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
//                                    jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
//                                    scanner.close();
//                                }
//                                System.out.println("jsonResponse:\n" + jsonResponse);
//
//                            } catch (Throwable t) {
//                                t.printStackTrace();
//                            }
//                        }
//                    }
//                });
//
//
//
//            }
//
//
//        });
//
//        refused.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AsyncTask.execute(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        int SDK_INT = android.os.Build.VERSION.SDK_INT;
//                        if (SDK_INT > 8) {
//                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
//                                    .permitAll().build();
//                            StrictMode.setThreadPolicy(policy);
//                            String send_email;
//
//                            //This is a Simple Logic to Send Notification different Device Programmatically....
//
//                            send_email = "test@live.com";
//                            fetch = "false";
//
//
//                            try {
//                                String jsonResponse;
//
//                                URL url = new URL("https://onesignal.com/api/v1/notifications");
//                                HttpURLConnection con = (HttpURLConnection) url.openConnection();
//                                con.setUseCaches(false);
//                                con.setDoOutput(true);
//                                con.setDoInput(true);
//
//                                con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
//                                con.setRequestProperty("Authorization", "Basic MDg2OGM4MDUtNGY1MS00YmU4LWE5ZjctNzJjMGFkMDBkNTQz");
//                                con.setRequestMethod("POST");
//
//                                String strJsonBody = "{"
//                                        + "\"app_id\": \"21128ac1-8b35-454e-99c7-378a5073d951\","
//
//                                        + "\"filters\": [{\"field\": \"tag\", \"key\": \"User_ID\", \"relation\": \"=\", \"value\": \"" + send_email + "\"}],"
//
//                                        + "\"data\": {\"foo\": \"bar\"},"
//                                        + "\"contents\": {\"en\": \"refused \"}"
//                                        + "}";
//
//
//                                System.out.println("strJsonBody:\n" + strJsonBody);
//
//                                byte[] sendBytes = strJsonBody.getBytes("UTF-8");
//                                con.setFixedLengthStreamingMode(sendBytes.length);
//
//                                OutputStream outputStream = con.getOutputStream();
//                                outputStream.write(sendBytes);
//
//                                int httpResponse = con.getResponseCode();
//                                System.out.println("httpResponse: " + httpResponse);
//
//                                if (httpResponse >= HttpURLConnection.HTTP_OK
//                                        && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
//                                    Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
//                                    jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
//                                    scanner.close();
//                                } else {
//                                    Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
//                                    jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
//                                    scanner.close();
//                                }
//                                System.out.println("jsonResponse:\n" + jsonResponse);
//
//                            } catch (Throwable t) {
//                                t.printStackTrace();
//                            }
//                        }
//                    }
//                });
//
//
//                Intent intent = new Intent(ResponseNotification.this,Main3Activity.class);
//                startActivity(intent);
//                finish();
//
//
//
//            }
//
//
//
//        });
//
//    }
//}
