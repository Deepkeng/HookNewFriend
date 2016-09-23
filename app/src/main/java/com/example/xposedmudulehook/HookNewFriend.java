package com.example.xposedmudulehook;
import android.content.ContentValues;

import org.json.JSONObject;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;


/**
 * Created by psq on 2016/9/22
 */
public class HookNewFriend implements IXposedHookLoadPackage  {
  //  private ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(30);

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        if (!lpparam.packageName.equals("com.tencent.mm")) {
            return;
        }



        XposedBridge.log("找到微信包 ： " + lpparam.packageName);
        //找到要Hook的类名和函数，创建自己的类
        XposedHelpers.findAndHookMethod("com.tencent.kingkong.database.SQLiteDatabase",lpparam.classLoader, "insert",
                String.class,
                String.class,
                ContentValues.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        // 这里的调用在正常函数调用之后执行
                        XposedBridge.log("-------------HOOK开始------------------");

                        String tableName = (String) param.args[0];
                        String nullColumnHack = (String) param.args[1];
                        ContentValues values = (ContentValues) param.args[2];

                      /*  XposedBridge.log("tableName ： " + tableName);
                        XposedBridge.log("nullColumnHack ： " + nullColumnHack);
                        XposedBridge.log("values ： " + values);*/

                        //如果变动的表是“fmessage_conversation”，取其中的参数
                        if(tableName.equals("fmessage_conversation")){
                            String contentNickname = (String) values.get("contentNickname");
                            String contentFromUsername = (String) values.get("contentFromUsername");
                            XposedBridge.log("contentNickname： " +contentNickname);
                            XposedBridge.log("contentFromUsername:"+contentFromUsername);

                            JSONObject object = new JSONObject();
                            object.put("contentNickname",contentNickname);
                            object.put("contentFromUsername",contentFromUsername);
                            XposedBridge.log("object:"+object);

                            new Thread(new XposedClientSender(object.toString())).start();
                        }

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



