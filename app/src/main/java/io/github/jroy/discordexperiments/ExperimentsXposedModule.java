package io.github.jroy.discordexperiments;

import android.content.res.AssetManager;
import android.content.res.XModuleResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import java.lang.reflect.Method;

public class ExperimentsXposedModule implements IXposedHookZygoteInit, IXposedHookLoadPackage {
  private XModuleResources resources;

  @Override
  public void initZygote(StartupParam startupParam) {
    resources = XModuleResources.createInstance(startupParam.modulePath, null);
  }

  @Override
  public void handleLoadPackage(XC_LoadPackage.LoadPackageParam params) throws Throwable {
    if (params.packageName.contains(".webview")) {
      return;
    }

    final Class<?> catalystInstanceImpl = params.classLoader.loadClass("com.facebook.react.bridge.CatalystInstanceImpl");
    final Method loadScriptAssetMethod = catalystInstanceImpl.getDeclaredMethod("jniLoadScriptFromAssets", AssetManager.class, String.class, boolean.class);

    final Method loadScriptFileMethod = catalystInstanceImpl.getDeclaredMethod("jniLoadScriptFromFile", String.class, String.class, boolean.class);
    loadScriptFileMethod.setAccessible(true);

    final XC_MethodHook hook = new XC_MethodHook() {
      @Override
      protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
        XposedBridge.invokeOriginalMethod(loadScriptAssetMethod, param.thisObject, new Object[]{resources.getAssets(), "assets://js/modules.js", true});
      }

      @Override
      protected void afterHookedMethod(MethodHookParam param) throws Throwable {
        XposedBridge.invokeOriginalMethod(loadScriptAssetMethod, param.thisObject, new Object[]{resources.getAssets(), "assets://js/payload.js", param.args[2]});
      }
    };

    XposedBridge.hookMethod(loadScriptAssetMethod, hook);
    XposedBridge.hookMethod(loadScriptFileMethod, hook);
  }
}
