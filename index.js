'use strict';

var React = require('react-native');

var { NativeModules, RCTDeviceEventEmitter, DeviceEventEmitter } = React;

var getRotation = function (rotation) {
  if(rotation == 90) {
    rotation = 1;
  } else if (rotation == 180) {
    rotation = 2;
  } else if(rotation == 270) {
    rotation = 3;
  }
  return rotation;
};

DeviceEventEmitter = RCTDeviceEventEmitter ? RCTDeviceEventEmitter : DeviceEventEmitter;

module.exports = {
  rotate: function (rotation) {
    NativeModules.OrientationController.rotate(getRotation(rotation));
  },
  getDeviceOrientation: function(callback) {
    NativeModules.OrientationController.getDeviceOrientation(callback);
  },
  getApplicationOrientation: function(callback) {
    NativeModules.OrientationController.getApplicationOrientation(callback);
  },
  addApplicationListener: function(callback) {
    return DeviceEventEmitter.addListener(
      'applicationOrientationDidChange', function () {
        callback.apply(this, arguments[0])
      }
    );
  },
  removeApplicationListener: function(listener) {
    DeviceEventEmitter.removeListener(
      'applicationOrientationDidChange', listener
    );
   },
  addDeviceListener: function(callback) {
    return DeviceEventEmitter.addListener(
      'deviceOrientationDidChange', function () {
        callback.apply(this, arguments[0])
      }
    );
  },
  removeDeviceListener: function(listener) {
    DeviceEventEmitter.removeListener(
      'deviceOrientationDidChange', listener
    );
  }
}
