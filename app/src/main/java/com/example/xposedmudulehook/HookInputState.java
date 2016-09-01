package com.example.xposedmudulehook;
import android.app.Activity;
import android.app.Dialog;
import android.inputmethodservice.InputMethodService;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;


/**
 * Created by psq on 2016/8/29
 */
public class HookInputState implements IXposedHookLoadPackage  {

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        //找到要Hook的类名和函数，创建自己的类
        //HOOK android.inputmethodservice.InputMethodService类的 onWindowShown() 方法可以全局捕捉当前输入法的弹出状态，抓到的包名都是当前输入法的包名。
        // HOOK android.view.inputmethod.InputMethodManager类的 showSoftInput() 依赖EditText控件，如果当前是Web页面，Hook不到。可以抓到当前是哪个包弹出的软键盘
        XposedHelpers.findAndHookMethod("android.inputmethodservice.InputMethodService",lpparam.classLoader,"onWindowShown", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        // 这里的调用在正常函数调用之前执行
                    }
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        // 这里的调用在正常函数调用之后执行
                        XposedBridge.log(lpparam.packageName + "弹出了软键盘");
                        InputMethodService ims = (InputMethodService)param.thisObject;
                        Dialog window = ims.getWindow();//?输入法的Dialog
                        Window window1 = window.getWindow();




                    }
                });


        XposedHelpers.findAndHookMethod("android.inputmethodservice.InputMethodService",lpparam.classLoader,"onWindowHidden",new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

            }

            @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        // 这里的调用在正常函数调用之后执行
                        XposedBridge.log(lpparam.packageName + "隐藏了软键盘");
                        

                    }
                });
    }

}




 /*   // XposedBridge.log("系统所有包名" + lpparam.packageName);
       //找到系统的UI
        //判断需要Hook的包是否正确
        if (!lpparam.packageName.equals("com.android.view")) {
            XposedBridge.log("没有com.android.view");
            return;
        }*/


      /*  XposedHelpers.findAndHookMethod("android.view.inputmethod.InputMethodManager",

                // hideSoftInputFromWindow(IBinder windowToken, int flags)
                lpparam.classLoader,
                "hideSoftInputFromWindow",
                IBinder.class,
                int.class,
                new XC_MethodHook() {

                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        // 这里的调用在正常函数调用之前执行，现在是Hook时间显示，需要在显示之后调用，所以省略

                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("WindowManager隐藏了软键盘" + lpparam.packageName);
                    }
                });*/



   /* @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        XposedBridge.log("系统所以包名" + lpparam.packageName);
        //找到系统的UI
        //判断需要Hook的包是否正确
        if (!lpparam.packageName.equals("com.android.systemui")) {
            XposedBridge.log("not found package");
            return;
        }
        //找到要Hook的类名和函数，创建自己的类
        XposedHelpers.findAndHookMethod("com.android.systemui.statusbar.policy.Clock", lpparam.classLoader, "updateClock", new XC_MethodHook() {

            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                // 这里的调用在正常函数调用之前执行，现在是Hook时间显示，需要在显示之后调用，所以省略
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                // 这里的调用在正常函数调用之后执行
                XposedBridge.log("aaaaaaaaaaaaaa" + lpparam.packageName);
                TextView tv = (TextView) param.thisObject;
                String text = tv.getText().toString();
                tv.setText(text + ":)");
                tv.setTextColor(Color.GREEN);
            }
        });
    }*/


