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

####getDeviceOrientation(callback)

This method will return the current orientation of the device and device string in the form of a callback:

```javascript
componentDidMount(){
  Orientation.getDeviceOrientation(
    (orientation, device, size) => {
      console.log(orientation, device, size);
    }
  );
}
```

####getApplicationOrientation(callback)

This method will return the current orientation of the application and device string in the form of a callback:

```javascript
componentDidMount(){
  Orientation.getApplicationOrientation(
    (orientation, device, size) => {
      console.log(orientation, device, size);
    }
  );
}
```

####addDeviceListener(callback)

This method will add a listener that will call the callback anytime the device orientation changes:

```javascript
_setOrientation(orientation, device, size) {
   console.log(orientation, device, size);
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
_setOrientation(orientation, device, size) {
   console.log(orientation, device, size);
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
