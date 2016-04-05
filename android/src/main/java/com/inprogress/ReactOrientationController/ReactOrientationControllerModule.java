package com.inprogress.ReactOrientationController;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Build;
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

    final ReactApplicationContext reactContext;
    OrientationEventListener mApplicationOrientationListener;
    Activity mActivity;
    int deviceOrientation = 0;
    String lastDeviceOrientation ="";


    public ReactOrientationControllerModule(final ReactApplicationContext reactContext, Activity activity) {
        super(reactContext);
        this.reactContext = reactContext;
        this.mActivity = activity;

        mApplicationOrientationListener = new OrientationEventListener(reactContext) {
            @Override
            public void onOrientationChanged(int orientation) {
                deviceOrientation = orientation;
                if(lastDeviceOrientation.compareTo(getDeviceOrientationAsString())!=0){
                    lastDeviceOrientation = getDeviceOrientationAsString();
                    WritableNativeMap data = getDataMap();
                    try{
                    reactContext
                            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                            .emit("orientationDidChange", data);
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
    public void getOrientation(Callback success) {
        WritableNativeMap data = getDataMap();
        Log.e("DeviceOrientation",data.toString());
        success.invoke(data);
    }


    @ReactMethod
    public void rotate(int rotation) {
        Log.e("YES","rotation : "+rotation);
        setApplicationOrientation(rotation);
    }

    public WritableNativeMap getDataMap(){
        WritableNativeMap data = new WritableNativeMap();
        data.putString("deviceOrientation",getDeviceOrientationAsString());
        data.putString("applicationOrientation", getApplicationOrientation());
        data.putString("device", getModel());
        final int width = getDimension()[0];
        final int height = getDimension()[1];
        data.putMap("size",new WritableNativeMap(){{putInt("width",width);putInt("height",height);}});
        return data;
    }

    /**
     * To get the model of the phone
     * @return the model of the phone as String
     */
    public String getModel() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    /**
     * Return the application orientation
     * @return the application orientation of a String
     */
    private String getApplicationOrientation() {
        String orientationStr = "";
        switch (getApplicationOrientationAsNumber()) {
            case 0:
                orientationStr = "PORTRAIT";
                break;
            case 1:
                orientationStr = "LANDSCAPE RIGHT";
                break;
            case 2:
                orientationStr = "PORTRAIT UPSIDE DOWN";
                break;
            case 3:
                orientationStr = "LANDSCAPE LEFT";
                break;
            default:
                orientationStr = "UNKNOWN";
                break;
        }
        return orientationStr;
    }


    /**
     * Return the dimension of the screen
     * @return the dimension of the screen
     */
    private int[] getDimension() {
        final Display display = ((WindowManager) getReactApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        int[] dim = new int[]{width, height};
        if(dim.length==2)
            return dim;
        else return new int[]{-1, -1};
    }


    /**
     * Return the device orientation
     * @return the device orientation of a String
     */
    private String getDeviceOrientationAsString(){
        if((deviceOrientation>=0&&deviceOrientation<45)||(deviceOrientation>=315&&deviceOrientation<360)){
            return "PORTRAIT";
        }else if(deviceOrientation>=45&&deviceOrientation<135){
            return "LANDSCAPE LEFT";
        }else if(deviceOrientation>=135&&deviceOrientation<225){
            return "PORTRAIT UPSIDE DOWN";
        }else if(deviceOrientation>=225&&deviceOrientation<315){
            return "LANDSCAPE RIGHT";
        }else return "UNKNOWN";
    }

    /**
     * Return the application orientation
     * @return the application orientation of a Integer
     */
    private int getApplicationOrientationAsNumber() {

        final Display display = ((WindowManager) getReactApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        switch (display.getRotation()) {
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 1;
            case Surface.ROTATION_180:
                return 2;
            case Surface.ROTATION_270:
                return 3;
        }
        return 0;
    }

    /**
     * Set the application orientation
     */
    private void setApplicationOrientation(int rotation) {

        if(rotation>3||rotation<1)rotation = 0;

        switch ((getApplicationOrientationAsNumber() + rotation) % 4) {
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

        WritableNativeMap data = getDataMap();
        try {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit("orientationDidChange", data);
        } catch (RuntimeException e) {
            Log.e("ERROR ", "java.lang.RuntimeException: Trying to invoke JS before CatalystInstance has been set!");
        }

    }


}
