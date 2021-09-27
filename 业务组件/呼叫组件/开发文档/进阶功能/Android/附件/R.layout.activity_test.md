#### R.layout.activity_test.xml：

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/black">

    <com.netease.lava.nertc.sdk.video.NERtcVideoView
        android:id="@+id/videoViewRemote"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        android:visibility="gone"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <com.netease.lava.nertc.sdk.video.NERtcVideoView
        android:id="@+id/videoViewLocalBig"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        android:visibility="gone"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <com.netease.lava.nertc.sdk.video.NERtcVideoView
        android:id="@+id/videoViewLocalSmall"
        android:layout_width="90dp"
        android:layout_height="160dp"
        android:layout_margin="15dp"
        android:visibility="gone"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="parent" />


    <!-- 通话中操作布局 start -->
    <LinearLayout
        android:id="@+id/llOnTheCallOperation"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerHorizontal="true"
        android:layout_marginBottom="50dp"
        android:background="@drawable/calling_control_bg"
        android:orientation="horizontal"
        android:paddingStart="36dp"
        android:paddingTop="14dp"
        android:paddingEnd="36dp"
        android:paddingBottom="14dp"
        android:visibility="gone"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent">

        <ImageView
            android:id="@+id/ivHangUp"
            android:layout_width="32dp"
            android:layout_height="32dp"
            android:layout_gravity="center_vertical"
            android:src="@drawable/hangup" />
    </LinearLayout>
    <!-- 通话中操作布局 end -->

    <TextView
        android:id="@+id/tvRemoteVideoCloseTip"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="对方关闭了摄像头"
        android:textColor="@color/colorWhite"
        android:textSize="20sp"
        android:visibility="gone"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />


    <!-- 被叫用户邀请操作控制 start-->
    <ImageView
        android:id="@+id/ivReject"
        android:layout_width="75dp"
        android:layout_height="75dp"
        android:layout_gravity="center_horizontal"
        android:layout_marginBottom="75dp"
        android:src="@drawable/call_reject"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toStartOf="@id/ivAccept"
        app:layout_constraintStart_toStartOf="parent" />

    <TextView
        android:id="@+id/tvRejectDesc"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal"
        android:layout_marginTop="8dp"
        android:text="拒绝"
        android:textColor="@color/colorWhite"
        android:textSize="14sp"
        app:layout_constraintEnd_toEndOf="@id/ivReject"
        app:layout_constraintStart_toStartOf="@id/ivReject"
        app:layout_constraintTop_toBottomOf="@id/ivReject" />

    <ImageView
        android:id="@+id/ivAccept"
        android:layout_width="75dp"
        android:layout_height="75dp"
        android:layout_gravity="center_horizontal"
        android:layout_marginStart="70dp"
        android:layout_marginBottom="75dp"
        android:src="@drawable/call_accept"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toEndOf="@id/ivReject" />

    <TextView
        android:id="@+id/tvAcceptTip"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal"
        android:layout_marginTop="8dp"
        android:text="接听"
        android:textColor="@color/colorWhite"
        android:textSize="14sp"
        app:layout_constraintEnd_toEndOf="@id/ivAccept"
        app:layout_constraintStart_toStartOf="@id/ivAccept"
        app:layout_constraintTop_toBottomOf="@id/ivAccept" />

    <androidx.constraintlayout.widget.Group
        android:id="@+id/calledOperationGroup"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:visibility="gone"
        app:constraint_referenced_ids="ivReject,ivAccept,tvRejectDesc,tvAcceptTip" />
    <!-- 被叫用户邀请操作控制 end-->


    <!-- 主叫用户呼叫操作控制 start-->
    <ImageView
        android:id="@+id/ivCancel"
        android:layout_width="75dp"
        android:layout_height="75dp"
        android:layout_marginBottom="75dp"
        android:src="@drawable/call_reject"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent" />

    <TextView
        android:id="@+id/tvCancelTip"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal"
        android:layout_marginTop="8dp"
        android:text="取消"
        android:textColor="@color/colorWhite"
        android:textSize="14sp"
        app:layout_constraintEnd_toEndOf="@id/ivCancel"
        app:layout_constraintStart_toStartOf="@id/ivCancel"
        app:layout_constraintTop_toBottomOf="@id/ivCancel" />

    <androidx.constraintlayout.widget.Group
        android:id="@+id/callerOperationGroup"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:visibility="gone"
        app:constraint_referenced_ids="ivCancel,tvCancelTip" />
    <!-- 主叫用户呼叫操作控制 end-->

</androidx.constraintlayout.widget.ConstraintLayout>
```

