package com.erif.quickstates;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

public class QuickStateBuilder {

    private final SharedPreferences.Editor editor;

    public QuickStateBuilder(Context context) {
        String packageName = context.getPackageName();
        String name = packageName + ".QuickState";
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public void addState(@NonNull String name, @LayoutRes int layoutState) {
        if (layoutState != 0 && layoutState != -1)
            save(name, layoutState);
    }

    public void removeState(@NonNull String name) {
        remove(name);
    }

    public void clearStates() {
        editor.clear();
        editor.commit();
    }

    private void save(String name, int layoutState) {
        editor.putInt(name, layoutState);
        editor.commit();
    }

    private void remove(String name) {
        editor.remove(name);
        editor.commit();
    }

}
