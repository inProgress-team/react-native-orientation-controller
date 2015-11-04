'use strict';

var React = require('react-native');
var RCTDeviceEventEmitter = require('RCTDeviceEventEmitter');

var {NativeModules} = React;

module.exports = {
  rotate: function (rotation) {
    NativeModules.OrientationController.rotate(rotation);
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
