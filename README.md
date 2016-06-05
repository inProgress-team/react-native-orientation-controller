## react-native-orientation-controller

> A react-native library for obtaining and controlling current device or application orientation

This library is a fork of [this library by Ken Wheeler](https://github.com/walmartreact/react-native-orientation-listener). We thank him.

###Getting Started

- Run `npm install --save react-native-orientation-controller`

### Install with RNPM

- `npm install -g rnpm`
- `rnpm link react-native-orientation-controller`

### Or Manual Installation

#### Android

Add to `android/settings.gradle`:

```
include ':react-native-orientation-controller'
project(':react-native-orientation-controller').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-orientation-controller/android')
```

Add the compile project line to `android/app/build.gradle` (inside `dependencies`):

```
dependencies {
    // ... other content ... 
    compile project(':react-native-orientation-controller')
}
```

Inside `MainActivity.java` (normally somewhere here `android/app/src/main/java/com/<your-app-name>/MainActivity.java`)
add `import com.inprogress.ReactOrientationController.ReactOrientationController;` and ` new ReactOrientationController(this)`
like in the example below

```
import com.inprogress.ReactOrientationController.ReactOrientationController; // <<< ADD THIS

/* ... other content ... */

    @Override
    protected List<ReactPackage> getPackages() {
        return Arrays.<ReactPackage>asList(
            new MainReactPackage(),
            new ReactOrientationController(this) // <<< AND THIS
            /* ... other packages can be here ... */
        );
    }

```


### Usage

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
  this.orientationListener = Orientation.addListener(this._setOrientation);
}
```

####removeListener(callback)

This method removes the listener you added in componentDidMount:

```javascript
componentWillUnmount() {
  this.orientationListener.remove(); 
}
```

