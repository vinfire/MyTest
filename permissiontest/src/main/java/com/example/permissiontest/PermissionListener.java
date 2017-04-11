package com.example.permissiontest;

import java.util.List;

/**
 * Created by GTR on 2017/4/9.
 */

public interface PermissionListener {

    void onGranted();

    void onDenied(List<String> denidePermission);
}
