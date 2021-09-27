package com.netease.vce.demo.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.netease.vce.demo.Constant;
import com.netease.vce.demo.GuestActivity;
import com.netease.vce.demo.R;
import com.netease.vce.demo.SelfInfo;

import androidx.core.app.NotificationCompat;

public class NotificationUtils {

    public static void showCallNotification(Context context, SelfInfo selfInfo) {
        Intent intent = new Intent(context, GuestActivity.class);
        intent.putExtra(Constant.TO_PAGE, Constant.PAGE_CALL);
        intent.putExtra(Constant.SELF_INFO, selfInfo);
        NotificationUtils.showHighNotification(context, context.getString(R.string.background_notification_content), intent);
    }

    public static void showMeetingNotification(Context context, String meetingId, String name) {
        Intent intent = new Intent(context, GuestActivity.class);
        intent.putExtra(Constant.TO_PAGE, Constant.PAGE_MEETING);
        intent.putExtra(Constant.MEETING_ID, meetingId);
        intent.putExtra(Constant.NAME, name);
        NotificationUtils.showHighNotification(context, context.getString(R.string.background_notification_content), intent);
    }

    public static void showHighNotification(Context context, String content, Intent fullScreenIntent) {
        int NOTIFICATION_ID = 234;
        String CHANNEL_ID = "my_channel_01";
        CharSequence name = "my_channel";
        int appIconResId = R.mipmap.ic_launcher;
        String appName = context.getString(R.string.app_name);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name,
                                                                  NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(false); //是否在桌面icon右上角展示小红点
            channel.setShowBadge(false); //是否在久按桌面图标时显示此渠道的通知
            notificationManager.createNotificationChannel(channel);
        }
        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(context, 0, fullScreenIntent,
                                                                          PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID).setSmallIcon(appIconResId)
                                                                                       .setContentTitle(appName)
                                                                                       .setContentText(content)
                                                                                       .setPriority(
                                                                                               NotificationCompat.PRIORITY_HIGH)
                                                                                       .setCategory(
                                                                                               NotificationCompat.CATEGORY_CALL)
                                                                                       .setAutoCancel(true)
                                                                                       .setFullScreenIntent(
                                                                                               fullScreenPendingIntent,
                                                                                               true).setContentIntent(
                        fullScreenPendingIntent).build();
        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}
