package com.example.medicom;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.vidyo.VidyoClient.Connector.Connector;
import com.vidyo.VidyoClient.Connector.ConnectorPkg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VideoConference extends AppCompatActivity implements Connector.IConnect {

    private FrameLayout videoView;
    private TextView messageBox;
    private Connector videoConnector;
    private ImageView joinCall;
    private ProgressBar connectProgress;
    private Activity currentActivity = this;
    private boolean isConnected = false;
    private FirestoreHandler firestoreHandler;
    private String accessToken = "";
    private ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener = null;

    private static final String[] mPermissions = new String[] {
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };
    private final int PERMISSIONS_REQUEST_ALL = 1988;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_conference);

        videoView = findViewById(R.id.videoView);
        messageBox = findViewById(R.id.messageBox);
        joinCall = findViewById(R.id.connectButton);
        connectProgress = findViewById(R.id.connectProgress);

        firestoreHandler = new FirestoreHandler(this);
        firestoreHandler.getAccessToken(this);

        ConnectorPkg.setApplicationUIContext(this);
        if (ConnectorPkg.initialize()) {
            videoConnector = new Connector(
                    videoView,
                    Connector.ConnectorViewStyle.VIDYO_CONNECTORVIEWSTYLE_Default,
                    2,
                    "",
                    "",
                    0
            );

            if (Build.VERSION.SDK_INT > 22) {
                List<String> permissionsNeeded = new ArrayList<>();
                for (String permission : mPermissions) {
                    // Check if the permission has already been granted.
                    if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
                        permissionsNeeded.add(permission);
                }
                if (permissionsNeeded.size() > 0) {
                    // Request any permissions which have not been granted. The result will be called back in onRequestPermissionsResult.
                    ActivityCompat.requestPermissions(this, permissionsNeeded.toArray(new String[0]), PERMISSIONS_REQUEST_ALL);
                } else {
                    // Begin listening for video view size changes.
                    this.startVideoViewSizeListener();
                }
            } else {
                // Begin listening for video view size changes.
                this.startVideoViewSizeListener();
            }

            videoConnector.showViewAt(videoView, 0, 0, videoView.getWidth(), videoView.getHeight());
            showMsg("Cam connected");
        }
        else {
            showMsg("Cam connection failed");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Release device resources
        if (videoConnector != null) {
            videoConnector.selectLocalCamera(null);
            videoConnector.selectLocalMicrophone(null);
            videoConnector.selectLocalSpeaker(null);
        }

        // Connector will be destructed upon garbage collection.
        videoConnector = null;

        ConnectorPkg.setApplicationUIContext(null);

        // Uninitialize the VidyoClient library - this should be done once in the lifetime of the application.
        ConnectorPkg.uninitialize();

        // Remove the global layout listener on the video frame.
        if (mOnGlobalLayoutListener != null) {
            videoView.getViewTreeObserver().removeOnGlobalLayoutListener(mOnGlobalLayoutListener);
        }
    }

    void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    private void startVideoViewSizeListener() {
        // Render the video each time that the video view (mVideoFrame) is resized. This will
        // occur upon activity creation, orientation changes, and when foregrounding the app.
        ViewTreeObserver viewTreeObserver = videoView.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    // Specify the width/height of the view to render to.
                    videoConnector.showViewAt(videoView, 0, 0, videoView.getWidth(), videoView.getHeight());
                    mOnGlobalLayoutListener = this;
                }
            });
        } else {
            showMsg("Preview failed");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        // If the expected request code is received, begin rendering video.
        if (requestCode == PERMISSIONS_REQUEST_ALL) {
            for (int i = 0; i < permissions.length; ++i)

            // Begin listening for video view size changes.
            this.startVideoViewSizeListener();
        } else {
            Toast.makeText(this,"ERROR! Unexpected permission requested. Video will not be rendered.", Toast.LENGTH_LONG).show();
        }
    }

    public void connectRoom(View view) {
        if (isConnected) {
            showMsg("Disconnecting....");
            videoConnector.disconnect();
        }
        else {
            showMsg("Connecting....");
            videoConnector.connect(
                    "prod.vidyo.io",
                    accessToken,
                    "DemoUser",
                    "DemoRoom",
                    this);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    connectProgress.setVisibility(View.VISIBLE);
                }
            });
        }

    }

    void showMsg(final String msg) {
        currentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageBox.setText(msg);
                messageBox.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        messageBox.setVisibility(View.INVISIBLE);
                    }
                }, 2000);
            }
        });
    }

    void changeUIState() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isConnected)
                    joinCall.setImageResource(R.drawable.disconnect);
                else
                    joinCall.setImageResource(R.drawable.phone);
            }
        });
    }

    @Override
    public void onSuccess() {
        showMsg("Connected to room");
        changeUIState();
        isConnected = !isConnected;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                connectProgress.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onFailure(Connector.ConnectorFailReason connectorFailReason) {
        showMsg("Connection failed");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                connectProgress.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onDisconnected(Connector.ConnectorDisconnectReason connectorDisconnectReason) {
        showMsg("Conference disconnected");
        changeUIState();
        isConnected = !isConnected;
    }
}