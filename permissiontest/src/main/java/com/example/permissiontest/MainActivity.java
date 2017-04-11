package com.example.permissiontest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void makeCall(View view){

        requestRuntimePermission(new String[]{
                Manifest.permission.CALL_PHONE
                ,Manifest.permission.ACCESS_FINE_LOCATION
                ,Manifest.permission.WRITE_EXTERNAL_STORAGE
                ,Manifest.permission.CAMERA}, new PermissionListener() {
            @Override
            public void onGranted() {
                Toast.makeText(MainActivity.this, "You have got all permissions", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(List<String> denidePermission) {
                for (String permission : denidePermission) {
                    Toast.makeText(MainActivity.this, "You deny the permission: "+permission, Toast.LENGTH_LONG).show();
                }
            }
        });
//        //一次申请多个权限
//        List<String> permissionList = new ArrayList<>();
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
//            permissionList.add(Manifest.permission.CALL_PHONE);
//        }
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        }
//        if ( !permissionList.isEmpty()){
//            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), 1);
//        }else {
//            Toast.makeText(this, "You have got all permissions", Toast.LENGTH_SHORT).show();
//            call();
//        }

//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
//        }else {
//            call();
//        }
    }

    private void call() {
        try{
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:10086"));
            startActivity(intent);
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//
//        switch (requestCode){
//            case 1:
//                if (grantResults.length>0){
//                    for (int grantResult : grantResults) {
//                        if (grantResult!=PackageManager.PERMISSION_GRANTED){
//                            Toast.makeText(this, "某个权限被拒绝了", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                    }
//                    call();
//                }
//
////                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
////                    call();
////                }else {
////                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
////                }
//                break;
//            default:
//                break;
//        }
//    }
}
