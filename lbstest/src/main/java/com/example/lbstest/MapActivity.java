package com.example.lbstest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

public class MapActivity extends AppCompatActivity {

    public static final String TAG = "MapActivity";
    private MapView mapView;
    private BaiduMap baiduMap;
    private boolean isFirstLocate = true;
    private LocationClient locationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);

        locationClient = new LocationClient(this);
        locationClient.registerLocationListener(new MapLocationListener());

        mapView = (MapView) findViewById(R.id.bmapView);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true); //让设备的位置在地图上显示
        initLocation();
        locationClient.start(); //开始定位
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy); //设置定位模式
        option.setCoorType("bd09ll"); // 设置返回的定位结果是百度经纬度,默认值gcj02
        option.setScanSpan(10000); //设置更新时间间隔
        option.setIsNeedAddress(true); //需要获取当前位置详细的地址信息
        locationClient.setLocOption(option);
    }

    private void navigateTo(BDLocation location) {
        Log.i(TAG, "navigateTo: "+1);
        if (isFirstLocate){
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(latLng);
            baiduMap.animateMapStatus(mapStatusUpdate);
            mapStatusUpdate=MapStatusUpdateFactory.zoomTo(16.5f);
            baiduMap.animateMapStatus(mapStatusUpdate);
            isFirstLocate=false;

            Log.i(TAG, "navigateTo: "+2);
        }
        MyLocationData.Builder builder = new MyLocationData.Builder();
        builder.latitude(location.getLatitude());
        builder.longitude(location.getLongitude());
        MyLocationData locationData = builder.build();
        baiduMap.setMyLocationData(locationData);
    }

    public class MapLocationListener implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation.getLocType()==BDLocation.TypeGpsLocation || bdLocation.getLocType()==BDLocation.TypeNetWorkLocation){
                Log.i(TAG, "onReceiveLocation: ");
                navigateTo(bdLocation);
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationClient.stop(); //避免活动被销毁后，程序持续在后台不断地进行定位，严重消耗手机的电量
        mapView.onDestroy(); //保证资源能够及时地得到释放
        baiduMap.setMyLocationEnabled(false);
    }
}
