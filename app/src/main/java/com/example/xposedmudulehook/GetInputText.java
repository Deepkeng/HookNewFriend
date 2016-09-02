package com.example.xposedmudulehook;

import java.util.ArrayList;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by psq on 2016/9/2
 */
public class GetInputText implements IXposedHookLoadPackage{
    //boolean	commitText(CharSequence text, int newCursorPosition)
    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        final ArrayList<String> list = new ArrayList<>();

        XposedHelpers.findAndHookMethod("android.view.inputmethod.BaseInputConnection", loadPackageParam.classLoader, "commitText", CharSequence.class, int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                CharSequence text = (CharSequence) param.args[0];
                String s = text.toString();
                list.add(s);
            }
        });



        if(list.size()>0){
           for(int i =0;i<list.size();i++){
               String s = list.get(i);
               String s1 = s + list.get(i);
               XposedBridge.log(s1);
           }
        }
    }
}
