
# react-native-pep-clickread

## Getting started

`$ npm install react-native-pep-clickread --save`

### Mostly automatic installation

`$ react-native link react-native-pep-clickread`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-pep-clickread` and add `RNPepClickread.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNPepClickread.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.pep.clickread.RNPepClickreadPackage;` to the imports at the top of the file
  - Add `new RNPepClickreadPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-pep-clickread'
  	project(':react-native-pep-clickread').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-pep-clickread/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-pep-clickread')
  	```


## Usage
```javascript
import RNPepClickread from 'react-native-pep-clickread';

// TODO: What to do with the module?
RNPepClickread;
```
  