## react-native-orientation-controller

> A react-native library for obtaining and controlling current device or application orientation

This library is a fork of [this library by Ken Wheeler](https://github.com/walmartreact/react-native-orientation-listener). We thank him.

###Getting Started

- Run `npm install --save react-native-orientation-controller`

###IOS

- Open your Xcode project, and select your project in the Project Navigator tab
- Right click the `Libraries` folder and select "Add files to [your project]"
- Add `RCTOrientationController.xcodeproj` from your `node_modules` folder
- Click your main project icon back in the Project Navigator to bring up preferences, and go to the `Build Phases` tab.
- Click the `+` button underneath `Link Binary With Libraries` section.
- Add `libRCTOrientationController.a`

###Android

###NOT YET IMPLEMENTED !!!


###Usage

Import the library:

```javascript
var Orientation = require('react-native-orientation-controller');
```

####getDeviceOrientation(callback)

This method will return the current orientation of the device and device string in the form of a callback:

```javascript
componentDidMount(){
  Orientation.getDeviceOrientation(
    (orientation, device) => {
      console.log(orientation, device);
    }
  );
}
```

####getApplicationOrientation(callback)

This method will return the current orientation of the application and device string in the form of a callback:

```javascript
componentDidMount(){
Orientation.getApplicationOrientation(
(orientation, device) => {
console.log(orientation, device);
}
);
}
```

####addDeviceListener(callback)

This method will add a listener that will call the callback anytime the device orientation changes:

```javascript
_setOrientation(data) {
  this.setState({
    orientation: evt.orientation,
    device: evt.device
  });
},
componentDidMount(){
  Orientation.addDeviceListener(this._setOrientation);
}
```

####removeDeviceListener(callback)

This method removes the listener you added in componentDidMount:

```javascript
componentWillUnmount() {
  Orientation.removeDeviceListener(this._setOrientation);
}
```

####addApplicationListener(callback)

This method will add a listener that will call the callback anytime the application orientation changes:

```javascript
_setOrientation(data) {
this.setState({
orientation: evt.orientation,
device: evt.device
});
},
componentDidMount(){
Orientation.addApplicationListener(this._setOrientation);
}
```

####removeApplicationListener(callback)

This method removes the listener you added in componentDidMount:

```javascript
componentWillUnmount() {
Orientation.removeApplicationListener(this._setOrientation);
}
```
