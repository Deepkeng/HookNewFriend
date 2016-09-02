package com.example.xposedmudulehook;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by psq on 2016/9/2
 */
public class GetInputText implements IXposedHookLoadPackage{
    //boolean	commitText(CharSequence text, int newCursorPosition)
    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        XposedHelpers.findAndHookMethod("android.view.inputmethod.BaseInputConnection", loadPackageParam.classLoader, "commitText", CharSequence.class, int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                CharSequence text = (CharSequence) param.args[0];
                String s = text.toString();

            }
        });
    }
}
