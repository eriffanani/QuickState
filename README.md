# QuickState
Create and display your state quickly and easily with easy customization.

## Installation
#### repositories
```gradle
maven { url 'https://jitpack.io' }
```

#### dependencies
```gradle
implementation 'com.github.eriffanani:QuickState:1.4.5'
```

### How To Use
Create your layout for showing the state. Example: state_error.xml
```xml
<com.erif.quickstate.QuickStateView
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:src="@mipmap/error_illustration"
    app:imageWidth="200dp"
    android:title="Internal Server Error"
    android:subtitle="@string/message_error">
</com.erif.quickstate.QuickStateView>
```
* Result
<img width="300px" src="https://user-images.githubusercontent.com/26743731/197474424-b6d504eb-551a-4e02-bbd4-f4c88a06a4b3.png"/>

#### State name helper
Create state helper for storing your state name.
```java
public class StateName {
    public static final String ERROR = "internal_server_error";
}
```

#### Add layout to application
Create or modify your application class
```java
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize your state
        QuickStateBuilder builder = new QuickStateBuilder(this);
        builder.addState(StateName.ERROR, R.layout.state_error);
    }
}
```

#### Manifest file
```xml
<application
    android:name=".MyApplication">
```

#### Apply the state
```java
QuickState state = new QuickState(parentView);
state.show(StateName.ERROR);
```

### Licence
```license
Copyright 2022 Mukhammad Erif Fanani

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
