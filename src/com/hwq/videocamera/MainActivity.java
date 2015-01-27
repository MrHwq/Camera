package com.hwq.videocamera;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
    private Button start;// 开始录制按钮
    private Button stop;// 停止录制按钮

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new TestVideoListener());
    }

    class TestVideoListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            if (v == start) {
                Intent startIntent = new Intent(MainActivity.this,
                        HawkEye.class);
                startService(startIntent);
            } else if (v == stop) {
            }
        }
    }
}
