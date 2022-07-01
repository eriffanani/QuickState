package com.erif.quickstate;

import android.app.Application;

public class QuickStateApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        QuickStateBuilder builder = new QuickStateBuilder(this);
        builder.addState(ConstantState.NETWORK, R.layout.state_network);
        builder.addState(ConstantState.EMPTY, R.layout.state_empty);
        builder.addState(ConstantState.INTERNAL, R.layout.state_internal_server);
    }

}
