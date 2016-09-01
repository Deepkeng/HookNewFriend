package com.example.xposedmudulehook;


import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by psq on 2016/8/31
 */
public class GetEditTextContent implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        XposedHelpers.findAndHookMethod("android.view.inputmethod.InputMethodManager", loadPackageParam.classLoader, "focusOut",View.class, new XC_MethodHook() {
            public String s;
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                if (param.args[0] instanceof EditText){
                    EditText et = (EditText) param.args[0];
                    Editable text = et.getText();
                    if(text!=null){
                        s = text.toString();
                        if(!s.equals("null")&&!s.equals("")){
                            XposedBridge.log("输入的内容："+ s);

                        }
                    }

                }
            }
        });
    }
}
