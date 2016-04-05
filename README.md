## react-native-orientation-controller

> A react-native library for obtaining and controlling current device or application orientation

This library is a fork of [this library by Ken Wheeler](https://github.com/walmartreact/react-native-orientation-listener). We thank him.

###Getting Started

- Run `npm install --save react-native-orientation-controller`

### Install with RNPM

- `npm install -g rnpm`
- `rnpm link react-native-orientation-controller`


###Usage

Import the library:

```javascript
var Orientation = require('react-native-orientation-controller');
```

####rotate(orientation)

This method will change the current orientation of the device of 90° for parameter=1, 180° for parameter=2, 270° for parameter=3 :

```javascript
componentDidMount(){
  Orientation.rotate(parameter);
}
```

####getOrientation(callback)

This method will return the current orientation of the device, the current orientation of the application, the device model and the screen size in the form of a callback:

```javascript
componentDidMount(){
  Orientation.getOrientation(
    (deviceOrientation, applicationOrientation, device, size) => {
      console.log(deviceOrientation, applicationOrientation, device, size);
    }
  );
}
```

####addListener(callback)

This method will add a listener that will call the callback anytime the orientation changes:

```javascript
_setOrientation(deviceOrientation, applicationOrientation, device, size) {
   console.log(deviceOrientation, applicationOrientation, device, size);
},
componentDidMount(){
  Orientation.addListener(this._setOrientation);
}
```

####removeListener(callback)

This method removes the listener you added in componentDidMount:

```javascript
componentWillUnmount() {
  Orientation.removeListener(this._setOrientation);
}
```

