'use strict';

var React = require('react-native');

var { NativeModules, RCTDeviceEventEmitter } = React;

var getRotation = function (rotation) {
  if(rotation == 1) {
    rotation = 90;
  } else if (rotation == 2) {
    rotation = 180;
  } else if(rotation == 3) {
    rotation = 270;
  }
  return rotation;
};


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
    return RCTDeviceEventEmitter.addListener(
      'applicationOrientationDidChange', callback
    );
  },
  removeApplicationListener: function(listener) {
    RCTDeviceEventEmitter.removeListener(
      'applicationOrientationDidChange', listener
    );
   },
  addDeviceListener: function(callback) {
    return RCTDeviceEventEmitter.addListener(
      'deviceOrientationDidChange', callback
    );
  },
  removeDeviceListener: function(listener) {
    RCTDeviceEventEmitter.removeListener(
      'deviceOrientationDidChange', listener
    );
  }
}
