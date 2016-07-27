package com.techno.TouchMe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class DirectActivity extends Activity implements SensorEventListener, SurfaceHolder.Callback {
    private SensorManager mSensorManager;
    private Sensor mSensor;
    ImageView iv;
    private LinearLayout layout_id;
    int Count = 1;
    public boolean flag = false;
    SurfaceView direct_sv;
    MediaPlayer mp;
    SensorEvent se;
    private float mDownX;
    private float mDownY;
    private final float SCROLL_THRESHOLD = 10;
    private boolean isOnClick;

    MotionEvent me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct);
        mp = MediaPlayer.create(this, R.raw.capture_sound);

        direct_sv = (SurfaceView) findViewById(R.id.direct_sv);

        final SurfaceHolder surfaceHolder = direct_sv.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


        layout_id = (LinearLayout) findViewById(R.id.layout_id);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        iv = (ImageView) findViewById(R.id.imageView1);


        direct_sv.setOnTouchListener(new OnSwipeTouchListener(this) {

            public void onSwipeTop() {
                mp.start();
                if (flag == true) {
                    iv.setImageResource(R.drawable.near);
                }
                flag = false;
            }

            public void onSwipeRight() {
                mp.start();
                if (flag == true) {
                    iv.setImageResource(R.drawable.near);
                }
                flag = false;

            }

            public void onSwipeLeft() {

                if (flag == true) {
                    mp.start();
                    if (flag == true) {
                        iv.setImageResource(R.drawable.near);
                    }
                    flag = false;
                } else {
                    startActivity(new Intent(getApplicationContext(), SplashActivity.class));
                }
            }

            public void onSwipeBottom() {
                //Toast.makeText(MyCamActivity.this, "bottom", Toast.LENGTH_SHORT).show();
                mp.start();
                if (flag == true) {
                    iv.setImageResource(R.drawable.near);
                }
                flag = false;
            }


        });

        //For animation
        AnimUtils.activityexitAnim(DirectActivity.this);
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }


    public void onSensorChanged(final SensorEvent sensorEvent) {
        if (sensorEvent.values[0] == 0) {
            se = sensorEvent;
            flag = true;
           /* direct_sv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mp.start();
                    if (flag == true) {
                        iv.setImageResource(R.drawable.near);
                    }
                    flag = false;
                    return false;
                }
            });*/
        } else {
            iv.setImageResource(R.drawable.far);
        }

    }


    /*Start Surface Code*/
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }


    public class OnSwipeTouchListener implements View.OnTouchListener {

        private final GestureDetector gestureDetector;

        public OnSwipeTouchListener(Context ctx) {
            gestureDetector = new GestureDetector(ctx, new GestureListener());
        }

        public boolean onTouch(final View view, final MotionEvent motionEvent) {
            return gestureDetector.onTouchEvent(motionEvent);
        }

        private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                            } else {
                                onSwipeLeft();
                            }
                        }
                    } else {
                        if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffY > 0) {
                                onSwipeBottom();
                            } else {
                                onSwipeTop();
                            }
                        }
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        }

        public void onSwipeRight() {
        }

        public void onSwipeLeft() {
        }

        public void onSwipeTop() {
        }

        public void onSwipeBottom() {


        }

    /*End Surface Code*/
    }
}
