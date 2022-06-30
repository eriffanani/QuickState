package com.erif.quickstate;

import android.app.Application;

public class QuickStateApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        QuickStateBuilder builder = new QuickStateBuilder(this);
        builder.addState(ConstantState.INTERNET, R.layout.state_internet);
        builder.addState(ConstantState.EMPTY, R.layout.state_empty);
    }

}
