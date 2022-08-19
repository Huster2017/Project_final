package com.example.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class Warning_Fall extends Service {




    public Warning_Fall() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String d = intent.getStringExtra("key");
        getdata(d);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    private void getdata(String ID){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("custom/"+ID+"/Fall");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if(value.equals("0")){
                    String mess = "Đã phát hiện hoạt động trở lại";
                    sendNotification2(mess);
                }else{
                    String mess = "Đã ngã và chưa phát hiện hoạt động";
                    sendNotification1(mess);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) { }
        });
    }
    private void sendNotification1(String mess){
        Notification notification = new NotificationCompat.Builder(this,MyApplication.CHANNEL_ID)
                .setContentTitle("Thông báo!")
                .setContentText(mess)
                .setSmallIcon(R.drawable.toang)
                .setColor(getResources().getColor(R.color.teal_200))
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        startForeground(getID(),notification);

    }
    private void sendNotification2(String mess){
        Notification notification = new NotificationCompat.Builder(this,MyApplication.CHANNEL_ID_2)
                .setContentTitle("Thông báo!")
                .setContentText(mess)
                .setSmallIcon(R.drawable.ok)
                .setColor(getResources().getColor(R.color.purple_700))
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        startForeground(getID(),notification);
    }
    private int getID(){
        return (int) new Date().getTime();
    }
}