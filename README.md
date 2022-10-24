# QuickState
Show your state (internal error, network error, empty data, etc) quickly and easily with easy customization. This library can be used in both Java and Kotlin.

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
    android:layout_height="match_parent"
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
activity_main.xml
```xml
<androidx.coordinatorlayout.widget.CoordinatorLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/parentView"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">
    <ChildView/>
</androidx.coordinatorlayout.widget.CoordinatorLayout>
```
MainActivity
```java
CoordinatorLayout parentView = findViewById(R.id.parentView);

// Default
QuickState state = new QuickState(parentView);
// Enable Fade Animation
QuickState state = new QuickState(parentView, true);

// Set content loader for automatically hide loading view when state is showing
ProgressBar pb = findViewById(R.id.progress);
state.contentLoader(pb);

// Show Loading ...
showLoading();
// Loading Finish
if(false) {
    state.show(StateName.ERROR);
} else {
    // Continue task
}
```

#### Output
<img width="300px" src="https://user-images.githubusercontent.com/26743731/197487875-df442119-c738-437d-971d-27550dd60bc1.gif"/>

#### Add Button
Use @id instead of @+id for button state, below is the id that you can use 
- quickStateButtonSingle
- quickStateButtonLeft
- quickStateButtonTop
- quickStateButtonRight
- quickStateButtonBottom
```xml
<com.erif.quickstate.QuickStateView>
    <!-- Custom Your Button Layout Here -->
    <YourButton
        android:id="@id/quickStateButtonSingle"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Retry"/>
<com.erif.quickstate.QuickStateView>
```

Button Click Listener
```java
state.onClickListener((stateView, buttonState, tag) -> {
    if (buttonState == QuickState.BUTTON_SINGLE) {
        // TODO ACTION
    }
});
```
<img width="300px" src="https://user-images.githubusercontent.com/26743731/197492840-757699b6-cb12-4984-9b79-9bd3cb9a2a89.gif"/>

#### Finish
You can repeat the steps from the beginning to create a new state. You can use it on any activity without having to repeat the tiring steps.

#### All Quick State View Properties
* For image when you designed in layout xml like lottie libraries
```xml
<com.erif.quickstate.QuickStateView
    app:srcLayout="@layout/lottie_error">
</com.erif.quickstate.QuickStateView>
```

* Change image scale
```xml
<com.erif.quickstate.QuickStateView
    app:imageScale="S1x1">
</com.erif.quickstate.QuickStateView>
```

* When using Animated Vector Drawable
```xml
<com.erif.quickstate.QuickStateView
    app:avdRepeat="true">
</com.erif.quickstate.QuickStateView>
```

* Title Styling
```xml
<com.erif.quickstate.QuickStateView
    android:titleTextColor="@color/colorBlack"
    app:titleTextSize="18sp"
    app:titleFontFamily="@font/my_font">
</com.erif.quickstate.QuickStateView>
```

* Subtitle Styling
```xml
<com.erif.quickstate.QuickStateView
    android:subtitleTextColor="@color/colorBlack"
    app:subtitleTextSize="18sp"
    app:subtitleFontFamily="@font/my_font">
</com.erif.quickstate.QuickStateView>
```

#### Information
This library is still being developed further, please provide feedback if you find a bug. Thank you

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
