package com.example.fouthapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DirectAction;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.geofence.GeoFence;
import com.baidu.geofence.GeoFenceClient;
import com.baidu.geofence.GeoFenceListener;
import com.baidu.geofence.model.DPoint;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.fouthapp.dao.RegisterDao;
import com.example.fouthapp.dao.StudentDao;
import com.example.fouthapp.entity.User;

import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.baidu.geofence.GeoFenceClient.GEOFENCE_IN;
import static com.baidu.geofence.GeoFenceClient.GEOFENCE_IN_OUT_STAYED;
import static com.baidu.mapapi.CoordType.BD09LL;

public class MapSign extends BaseActivity {

    TextView mTime_tv;
    private MapView mMapView = null;
    BaiduMap mBaiduMap = null;
    LocationClient mLocationClient;
    boolean isFirstLocate = true;
    MyLocationConfiguration myLocationConfiguration;
    public static final String GEOFENCE_BROADCAST_ACTION = "com.location.apis.geofencedemo.broadcast";
    private GeoFenceClient mGeoFenceClient;
    private double mDistance=0;
    private InfoWindow mInfoWindow;
    //打卡背景色
    private RelativeLayout commit_bt;
    private TextView mDistance_tv;
    Marker marker1 = null;
    LatLng prell=null;
    boolean isClicked = false;

    private String userId;
    private String username;
    private String successSign = null;

    public static void actionStart(Context context, String userId, String username){
        Intent intent = new Intent(context,MapSign.class);
        intent.putExtra("userId",userId);
        intent.putExtra("username",username);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_sign);
        setToolbar("定位打卡");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //获取用户信息
        userId = getIntent().getStringExtra("userId");
        username = getIntent().getStringExtra("username");
        //初始化地图
        initMap();
        //权限获取
        getPermission();

        //绘制打卡范围
        initRange();
        //绘制marker目标点
        initMarker();

        //创建地理围栏
        initGeoFenceClient();

        mHandler.post(run);//设置系统时间
        mDistance_tv = (TextView) findViewById(R.id.distance_tv);
        mTime_tv = (TextView) findViewById(R.id.arriver_timetv);
        commit_bt = (RelativeLayout) findViewById(R.id.arriver_bt);

        ActivityCollector.addActivity(this);
    }



    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            BDLocation location = (BDLocation) msg.obj;
            LatLng LocationPoint = new LatLng(location.getLatitude(),location.getLongitude());
            LatLng center = new LatLng(35.602917,116.974441);

             mDistance= DistanceUtil.getDistance(center, LocationPoint);
            if (mDistance <= 200) {
                //显示文字
                setTextOption(LocationPoint, "您已在本节课上课范围", "#7ED321");
                //目的地图标
                //setMarkerOptions(LocationPoint, R.mipmap.arrive_icon);
                //clearMarker1();
                //按钮颜色
                if (!isClicked)
                    commit_bt.setBackgroundDrawable(getResources().getDrawable(R.mipmap.restaurant_btbg_yellow));
                else
                    commit_bt.setBackgroundDrawable(getResources().getDrawable(R.mipmap.restaurant_btbg_gray));
                commit_bt.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (!isClicked){
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    String date = sdf.format(new Date());
                                    System.out.println(date);
                                    StudentDao dao = new StudentDao();
                                    dao.setSign(userId);
                                    dao.setSignTime(userId,date);
                                }
                            }).start();

                            commit_bt.setBackgroundDrawable(getResources().getDrawable(R.mipmap.restaurant_btbg_gray));
                            isClicked = true;
                            Toast.makeText(MapSign.this,"打卡成功",Toast.LENGTH_SHORT).show();
                        }else {
                            //Toast.makeText(MapSign.this,"您已成功打卡",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                mBaiduMap.setMyLocationEnabled(true);
            } else {
                setTextOption(LocationPoint, "您不在本节课上课范围", "#FF6C6C");
                //setMarkerOptions(LocationPoint, R.mipmap.restaurant_icon);
                clearMarker1();
                commit_bt.setBackgroundDrawable(getResources().getDrawable(R.mipmap.restaurant_btbg_gray));
                mBaiduMap.setMyLocationEnabled(true);
            }
            findViewById(R.id.distance_tv);
            mDistance_tv.setText("距离目的地：" + mDistance + "米");
        }
    };

    private void setMarkerOptions(LatLng ll, int icon) {
        if (ll == null) return;
        prell = ll;
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(icon);
        MarkerOptions ooD = new MarkerOptions().position(ll).icon(bitmap);
        marker1 = (Marker) mBaiduMap.addOverlay(ooD);
        //marker1.remove();
    }

    private void clearMarker1() {
        if (marker1!=null)
          marker1.remove();
    }

    /**
     * 设置系统时间
     */
    private Runnable run = new Runnable() {
        @Override
        public void run() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");// HH:mm:ss
            Date date = new Date(System.currentTimeMillis());//获取当前时间
            mTime_tv.setText(simpleDateFormat.format(date)); //更新时间
            mHandler.postDelayed(run, 1000);
        }
    };

    /**
     * 添加地图文字
     *
     * @param point
     * @param str
     * @param color 字体颜色
     */
    private void setTextOption(LatLng point, String str, String color) {
        //使用MakerInfoWindow
        if (point == null) return;
        TextView view = new TextView(getApplicationContext());
        view.setBackgroundResource(R.mipmap.map_textbg);
        view.setPadding(0, 23, 0, 0);
        view.setTypeface(Typeface.DEFAULT_BOLD);
        view.setTextSize(14);
        view.setGravity(Gravity.CENTER);
        view.setText(str);
        view.setTextColor(Color.parseColor(color));
        mInfoWindow = new InfoWindow(view, point, 170);
        mBaiduMap.showInfoWindow(mInfoWindow);
    }

    //设置地理围栏
    private void initGeoFenceClient(){
        //实例化地理围栏客户端
        mGeoFenceClient = new GeoFenceClient(getApplicationContext());
        //设置希望侦测的围栏触发行为，默认只侦测用户进入围栏的行为
        mGeoFenceClient.setActivateAction(GEOFENCE_IN);

        //创建回调监听
        GeoFenceListener fenceListenter = new GeoFenceListener() {

            @Override
            public void onGeoFenceCreateFinished(List<GeoFence> list, int i, String s) {
                if (i == GeoFence.ADDGEOFENCE_SUCCESS){
                    //判断围栏是否创建成功
                    //Toast.makeText(MapSign.this,"添加围栏成功!!",Toast.LENGTH_SHORT).show();

                    //geoFenceList是已经添加的围栏列表，可据此查看创建的围栏
                } else {
                    //Toast.makeText(MapSign.this,"添加围栏失败!!",Toast.LENGTH_SHORT).show();
                }
            }
        };

        mGeoFenceClient.setGeoFenceListener(fenceListenter);//设置回调监听

        //创建并设置PendingIntent
        mGeoFenceClient.createPendingIntent(GEOFENCE_BROADCAST_ACTION);
        mGeoFenceClient.isHighAccuracyLoc(true);

        //注册
        IntentFilter filter = new IntentFilter(
                ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(GEOFENCE_BROADCAST_ACTION);
        registerReceiver(mGeoFenceReceiver, filter);
        //绘制围栏
        addRoundFence();


    }

    //绘制圆型地理围栏
    private void addRoundFence() {
//        String customId = etCustomId.getText().toString();
//        String radiusStr = etRadius.getText().toString();
        /*if (null == new LatLng(latitude, longitude) || TextUtils.isEmpty("100")||latitude ==0.0) {
            Toast.makeText(getApplicationContext(), "参数不全", Toast.LENGTH_SHORT)
                    .show();
            return;
        }*/
        //创建一个中心点坐标
        DPoint centerPoint = new DPoint(35.602917,116.974441);
        //fenceRadius = Float.parseFloat(radiusStr);
        mGeoFenceClient.addGeoFence (centerPoint,GeoFenceClient.BD09LL,100,"业务ID");
    }

    private BroadcastReceiver mGeoFenceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 接收广播
            if (intent.getAction().equals(GEOFENCE_BROADCAST_ACTION)) {
                //解析内容
                //获取Bundle
                Bundle bundle = intent.getExtras();
                //获取围栏行为：
                int status = bundle.getInt(GeoFence.BUNDLE_KEY_FENCESTATUS);
                //获取自定义的围栏标识：
                String customId = bundle.getString(GeoFence.BUNDLE_KEY_CUSTOMID);
                //获取围栏ID:
                String fenceId = bundle.getString(GeoFence.BUNDLE_KEY_FENCEID);
                //获取当前有触发的围栏对象：
                GeoFence geoFence = bundle.getParcelable(GeoFence.BUNDLE_KEY_FENCE);

                int locType = bundle.getInt(GeoFence.BUNDLE_KEY_LOCERRORCODE);
                StringBuffer sb = new StringBuffer();
                switch (status) {
                    case GeoFence.INIT_STATUS_IN:
                        sb.append("围栏初始状态:在围栏内");
                        Toast.makeText(MapSign.this, "到教室啦", Toast.LENGTH_SHORT).show();
                        break;
                    case GeoFence.INIT_STATUS_OUT:
                        sb.append("围栏初始状态:在围栏外");
                        Toast.makeText(MapSign.this, "快去教室", Toast.LENGTH_SHORT).show();

                        break;
                    case GeoFence.STATUS_LOCFAIL:
                        sb.append("定位失败,无法判定目标当前位置和围栏之间的状态");
                        Toast.makeText(MapSign.this, "无法判定目标当前位置和围栏之间的状态", Toast.LENGTH_SHORT).show();

                        break;
                    case GeoFence.STATUS_IN:
                        sb.append("进入围栏 ");
                        break;
                    case GeoFence.STATUS_OUT:
                        sb.append("离开围栏 ");
                        break;
                    default:
                        break;
                }
                if (status != GeoFence.STATUS_LOCFAIL) {
                    if (!TextUtils.isEmpty(customId)) {
                        sb.append(" customId: " + customId);
                    }
                    sb.append(" fenceId: " + fenceId);
                }
                String str = sb.toString();
                Log.d("状态：", str);
            }
        }
    };


    //设置打卡范围
    private void initRange() {
        LatLng center = new LatLng(35.602917,116.974441);

        //构造CircleOptions对象
        CircleOptions mCircleOptions = new CircleOptions().center(center)
                .radius(200)
                .fillColor(0xAA35BECF) //填充颜色
                .stroke(new Stroke(5, 0xAA2998A6)); //边框宽和边框颜色

        //在地图上显示圆
        Overlay mCircle = mBaiduMap.addOverlay(mCircleOptions);
    }

    private void initMarker() {
        //定义Maker坐标点
         LatLng point = new LatLng(35.602917,116.974441);
        //构建Marker图标
         BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.flag);
        //构建MarkerOption，用于在地图上添加Marker
         OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        //在地图上添加Marker，并显示
         mBaiduMap.addOverlay(option);
    }

    private void initMap() {
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.mapview);
        mBaiduMap = mMapView.getMap();

        mBaiduMap.setIndoorEnable(true);

        //开启地图的定位图层
        mBaiduMap.setMyLocationEnabled(true);

        //设置缩放级别
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.zoom(19.0f);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }
    private void getPermission() {
        List<String> permissionList = new ArrayList<String>();
        if (ContextCompat.checkSelfPermission(MapSign.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(MapSign.this, Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(MapSign.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()){
            String [] permissions = permissionList.toArray(new  String[permissionList.size()]);
            ActivityCompat.requestPermissions(MapSign.this,permissions,1);
        }else{
            //初始化定位信息
            requestLocation();
        }
    }

    private void requestLocation(){
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        mLocationClient = new LocationClient(this);

        LocationClientOption option = new LocationClientOption();
        //option.setLocationPurpose(LocationClientOption.BDLocationPurpose.SignIn);
        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy:高精度；
        //LocationMode.Battery_Saving:低功耗;
        //LocationMode.Device_Sensors;仅适用设备;
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        option.setCoorType("bd09ll");
        //可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
        option.setScanSpan(1000);
        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);
        //可选，设置是否需要地址描述
        option.setIsNeedLocationDescribe(true);
        //可选，设置是否需要设备方向结果
        option.setNeedDeviceDirect(false);
        //可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setLocationNotify(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setIgnoreKillProcess(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否开启Gps定位
        option.setOpenGps(true);
        //可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        option.setIsNeedAltitude(false);
        option.setEnableSimulateGps(false);
        //可选，设置是否需要过滤gps仿真结果，默认需要，即参数为false
        option.setWifiCacheTimeOut(5 * 60 * 1000);
        //可选，V7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前wi-fi是否超出有效期，若超出有效期，会重新扫描wi-fi

        //设置locationClientOption
        mLocationClient.setLocOption(option);
        //注册LocationListener监听器
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            initMyLocationConfiguration();
            navigateTo(location);
            /*//mapView 销毁后不在处理新接收的位置
            if (location == null || mMapView == null){
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);*/

        }
    }

    //自定义定位属性
    private void initMyLocationConfiguration(){
        MyLocationConfiguration.LocationMode mCurrentMode;
        boolean enableDirection;
        BitmapDescriptor mCurrentMarker;
        int accuracyCircleFillColor;
        int accuracyCircleStrokeColor;

        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        enableDirection = true;
        mCurrentMarker = BitmapDescriptorFactory.fromResource(R.mipmap.icon1);
        accuracyCircleFillColor = 0xAAFFFF88;
        accuracyCircleStrokeColor = 0xAA00FF00;
        myLocationConfiguration = new MyLocationConfiguration(mCurrentMode,enableDirection,mCurrentMarker,accuracyCircleFillColor,accuracyCircleStrokeColor);
        mBaiduMap.setMyLocationConfiguration(myLocationConfiguration);
    }

    private void navigateTo(BDLocation location){

        if (isFirstLocate){
            LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            mBaiduMap.animateMapStatus(update);
            /*//自定义定位坐标
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.arrive_icon);
            OverlayOptions options = new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude())).icon(bitmapDescriptor);
            mBaiduMap.addOverlay(options);*/
            update = MapStatusUpdateFactory.zoomTo(19.0f);
            mBaiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }

        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.longitude(location.getLongitude());
        locationBuilder.latitude(location.getLatitude());
        MyLocationData locationData = locationBuilder.build();
        mBaiduMap.setMyLocationData(locationData);

        Message message = new Message();
        message.obj =location;
        mHandler.sendMessage(message);

    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0){
                    for (int result:grantResults){
                        if (result!=PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"必须同意所有的权限才能使用本程序",Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                        requestLocation();
                    }
                }else {
                    Toast.makeText(this,"发生未知错误",Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        unregisterReceiver(mGeoFenceReceiver);
        if (null!=mGeoFenceClient)
            mGeoFenceClient.removeGeoFence();
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}