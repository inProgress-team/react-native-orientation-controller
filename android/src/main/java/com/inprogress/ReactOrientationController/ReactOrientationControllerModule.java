package com.inprogress.ReactOrientationController;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.Display;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.WindowManager;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class ReactOrientationControllerModule extends ReactContextBaseJavaModule {

    ReactApplicationContext reactContext;
    OrientationEventListener mApplicationOrientationListener;
    Activity mActivity;
    String Orientation = "";


    public ReactOrientationControllerModule(ReactApplicationContext reactContext, Activity activity) {
        super(reactContext);
        this.reactContext = reactContext;
        this.mActivity = activity;
        final ReactApplicationContext thisContext = reactContext;

        mApplicationOrientationListener = new OrientationEventListener(reactContext,
                SensorManager.SENSOR_DELAY_NORMAL) {
            @Override
            public void onOrientationChanged(int orientation) {
                WritableNativeMap params = new WritableNativeMap();

                if (Orientation.compareTo(getApplicationOrientation()) != 0) {
                    Orientation = getApplicationOrientation();
                    try {
                        params.putString("orientation", Orientation);
                        thisContext
                                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                                .emit("applicationOrientationDidChange", params);
                    } catch (RuntimeException e) {
                        Log.e("ERROR ", "java.lang.RuntimeException: Trying to invoke JS before CatalystInstance has been set!");
                    }
                }
            }
        };

        if (mApplicationOrientationListener.canDetectOrientation() == true) {
            mApplicationOrientationListener.enable();
        } else {
            mApplicationOrientationListener.disable();
        }
    }


    @Override
    public String getName() {
        return "OrientationController";
    }

    @ReactMethod
    public void getDeviceOrientation(Callback success) {
        WritableNativeMap data = new WritableNativeMap();
        data.putString("err", "Not yet implemented !");
        success.invoke(data);
    }

    @ReactMethod
    public void getApplicationOrientation(Callback success) {
        WritableNativeMap data = new WritableNativeMap();
        data.putString("orientation", getApplicationOrientation());
        success.invoke(data);
    }

    @ReactMethod
    public void rotate(int rotation) {
        setApplicationOrientation(rotation);
    }

    private String getApplicationOrientation() {
        String orientationStr = "";
        switch (getApplicationOrientationAsNumber()) {
            case 0:
                orientationStr = "PORTRAIT";
                break;
            case 1:
                orientationStr = "LANDSCAPE LEFT";
                break;
            case 2:
                orientationStr = "PORTRAIT UPSIDE DOWN";
                break;
            case 3:
                orientationStr = "LANDSCAPE RIGHT";
                break;
            default:
                orientationStr = "UNKNOWN";
                break;
        }
        return orientationStr;
    }

    private int getApplicationOrientationAsNumber() {

        final Display display = ((WindowManager) getReactApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        switch (display.getRotation()) {
            case Surface.ROTATION_0:
                //System.out.println("SCREEN_ORIENTATION_PORTRAIT");
                return 0;
            case Surface.ROTATION_90:
                //System.out.println("SCREEN_ORIENTATION_LANDSCAPE");
                return 1;
            case Surface.ROTATION_180:
                // System.out.println("SCREEN_ORIENTATION_REVERSE_PORTRAIT");
                return 2;
            case Surface.ROTATION_270:
                //System.out.println("SCREEN_ORIENTATION_REVERSE_LANDSCAPE");
                return 3;
        }
        return 0;
    }

    private void setApplicationOrientation(int orientation) {
        switch ((getApplicationOrientationAsNumber() + orientation) % 4) {
            case 0:
                mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            case 1:
                mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
            case 2:
                mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
                break;
            case 3:
                mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                break;
        }

        if (Orientation.compareTo(getApplicationOrientation()) != 0) {
            Orientation = getApplicationOrientation();
            WritableNativeMap params = new WritableNativeMap();
            try {
                params.putString("orientation", Orientation);
                getReactApplicationContext()
                        .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                        .emit("applicationOrientationDidChange", params);
            } catch (RuntimeException e) {
                Log.e("ERROR ", "java.lang.RuntimeException: Trying to invoke JS before CatalystInstance has been set!");
            }
        }

    }

}