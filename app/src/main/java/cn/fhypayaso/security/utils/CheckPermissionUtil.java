package cn.fhypayaso.security.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class CheckPermissionUtil {
    private CheckPermissionUtil() {
    }

    private static boolean PermissionAllow = false;
    //需要申请的权限
    private static String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    //检测权限
    public static String[] checkPermission(Context context){
        List<String> data = new ArrayList<>();//存储未申请的权限
        for (String permission : permissions) {
            int checkSelfPermission = ContextCompat.checkSelfPermission(context, permission);
            if(checkSelfPermission != PackageManager.PERMISSION_GRANTED){//未申请
                data.add(permission);
            }
        }
        return data.toArray(new String[data.size()]);
    }

    public static boolean isPermissionAllow() {
        return PermissionAllow;
    }

    public static void setPermissionAllow(boolean isPermissionAllow) {
        CheckPermissionUtil.PermissionAllow = isPermissionAllow;
    }
}
