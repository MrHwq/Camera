package com.hwq.videocamera;

import java.util.Timer;
import java.util.TimerTask;

import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class RecordThread extends Thread {
    private MediaRecorder mediarecorder;// 录制视频的类
    private SurfaceHolder surfaceHolder;
    private long recordTime;
    private Camera mCamera;

    public RecordThread(long recordTime, SurfaceView surfaceview,
            SurfaceHolder surfaceHolder) {
        this.recordTime = recordTime;
        this.surfaceHolder = surfaceHolder;
    }

    @Override
    public void run() {

        /**
         * 开始录像
         */
        startRecord();

        /**
         * 启动定时器，到规定时间recordTime后执行停止录像任务
         */
        Timer timer = new Timer();

        timer.schedule(new TimerThread(), recordTime);
    }

    /**
     * 获取摄像头实例对象
     * 
     * @return
     */
    public Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e) {
            // 打开摄像头错误
            Log.i("info", "打开摄像头错误");
        }
        return c;
    }

    /**
     * 开始录像
     */
    public void startRecord() {
        mediarecorder = new MediaRecorder();// 创建mediarecorder对象
        mCamera = getCameraInstance();
        // 解锁camera
        mCamera.setDisplayOrientation(90);
        mCamera.unlock();
        mediarecorder.setCamera(mCamera);

        // 设置录制视频源为Camera(相机)
        mediarecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mediarecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        // 设置录制文件质量，格式，分辨率之类，这个全部包括了
        mediarecorder.setProfile(CamcorderProfile
                .get(CamcorderProfile.QUALITY_480P));
        mediarecorder.setPreviewDisplay(surfaceHolder.getSurface());

        // 设置视频文件输出的路径
        mediarecorder.setOutputFile(Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/test.3gp");
        try {
            // 准备录制
            mediarecorder.prepare();
            // 开始录制
            mediarecorder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止录制
     */
    public void stopRecord() {
        if (mediarecorder != null) {
            // 停止录制
            mediarecorder.stop();
            // 释放资源
            mediarecorder.release();
            mediarecorder = null;

            if (mCamera != null) {
                mCamera.release();
                mCamera = null;
            }
        }
    }

    /**
     * 定时器
     * 
     * @author bcaiw
     * 
     */
    class TimerThread extends TimerTask {

        /**
         * 停止录像
         */
        @Override
        public void run() {
            stopRecord();
            this.cancel();
        }
    }
}
