package com.techno.TouchMe;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

public class SplashActivity extends Activity implements View.OnClickListener, SurfaceHolder.Callback {
    Button btn_direct, btn_vr;
    SurfaceView splash_sv;
    LinearLayout splash_ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        btn_direct = (Button) findViewById(R.id.btn_direct);
        btn_vr = (Button) findViewById(R.id.btn_vr);
        splash_sv = (SurfaceView) findViewById(R.id.splash_sv);
        splash_sv.setOnTouchListener(new OnSwipeTouchListener(this));


        btn_direct.setOnClickListener(this);
        btn_vr.setOnClickListener(this);

        splash_sv.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeTop() {
                // Toast.makeText(MyCamActivity.this, "top", Toast.LENGTH_SHORT).show();

            }

            public void onSwipeRight() {
                //Toast.makeText(MyCamActivity.this, "right", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), DirectActivity.class));


            }

            public void onSwipeLeft() {
                //Toast.makeText(MyCamActivity.this, "left", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), VRActivity.class));

            }

            public void onSwipeBottom() {
                //Toast.makeText(MyCamActivity.this, "bottom", Toast.LENGTH_SHORT).show();

            }
        });
        //For animation
        AnimUtils.activitySlideUpAnim(SplashActivity.this);

        // Assume thisActivity is the current activity
        int permissionCheck = ContextCompat.checkSelfPermission(SplashActivity.this,
                Manifest.permission.CAMERA);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_direct:
                startActivity(new Intent(getApplicationContext(), DirectActivity.class));
                break;
            case R.id.btn_vr:
                startActivity(new Intent(getApplicationContext(), VRActivity.class));
                break;
            default:
                break;
        }
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

    }

}
