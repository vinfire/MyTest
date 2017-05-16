package com.example.materialtest;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private boolean isBackPressed;
    private DrawerLayout mDrawerlayout;

    private People[] peoples = {
            new People("Bruce Lee", R.drawable.bruce_lee),
            new People("Diana", R.drawable.diana),
            new People("Jackie Chan", R.drawable.jackie_chan),
            new People("Schwarzenegger", R.drawable.schwarzenegger),
            new People("Stallone", R.drawable.stallone)
    };
    private List<People> peopleList = new ArrayList<>();

    private PeopleAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化状态栏
        initStatusBar();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //也可以通过代码把action菜单的三个点点换成其他图片
        //toolbar.setOverflowIcon(BaseUtils.getResImg(com.example.materialtest.R.drawable.ico_success));  

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Data download", Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "Data restored", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });

        mDrawerlayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setCheckedItem(R.id.nav_call); //将Call菜单项设置为默认选中
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerlayout.closeDrawers();
                return true;
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true); //让导航按钮显示出来
            actionBar.setHomeAsUpIndicator(R.drawable.menu); //设置一个导航按钮的图标
        }

        setDrawerLeftEdgeSize(this, mDrawerlayout, 0.3f); //DrawerLayout默认只能从屏幕边缘划出，可利用反射，修改DrawerLayout的侧滑菜单的感应范围

        initPeoples();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle_view);

        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true); //用来使RecyclerView保持固定的大小
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE); //防止滑动过程中item之间互换位置
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                layoutManager.invalidateSpanAssignments(); //防止第一行的图片距离顶端出现空白区域
            }
        });
        adapter = new PeopleAdapter(peopleList);
        recyclerView.setAdapter(adapter);

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshPeople();
            }
        });
    }

    private void initPeoples() {
        PeopleAdapter.clearHeights();
        peopleList.clear();
        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            int index = random.nextInt(peoples.length);
            peopleList.add(peoples[index]);
        }
    }

    private void refreshPeople() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initPeoples();
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    /**
     * 加载Toolbar的菜单文件
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    /**
     * 处理Toolbar的各个点击事件
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:  //HomeAsUp按钮的id永远都是android.R.id.home
                mDrawerlayout.openDrawer(GravityCompat.START); //将滑动菜单展示出来
                break;
            case R.id.bcakup:
                Toast.makeText(this, "You Clicked Backup", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this, "You Clicked Delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(this, "You Clicked Settings", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*修改DrawerLayout的侧滑菜单的感应范围*/
    public static void setDrawerLeftEdgeSize(Activity activity,
                                             DrawerLayout drawerLayout, float displayWidthPercentage) {
        if (activity == null || drawerLayout == null)
            return;
        try {
            // find ViewDragHelper and set it accessible
            Field leftDraggerField = drawerLayout.getClass().getDeclaredField(
                    "mLeftDragger");
            leftDraggerField.setAccessible(true);
            // 找到 ViewDragHelper 并设置 Accessible 为true
            ViewDragHelper leftDragger = (ViewDragHelper) leftDraggerField
                    .get(drawerLayout);
            // 找到 edgeSizeField 并设置 Accessible 为true
            Field edgeSizeField = leftDragger.getClass().getDeclaredField(
                    "mEdgeSize");
            edgeSizeField.setAccessible(true);
            int edgeSize = edgeSizeField.getInt(leftDragger);
            // 设置新的边缘大小
//            Point displaySize = new Point();
//            activity.getWindowManager().getDefaultDisplay().getSize(displaySize);
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            edgeSizeField.setInt(leftDragger, Math.max(edgeSize,
                    (int) (dm.widthPixels * displayWidthPercentage)));
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalArgumentException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        }
    }

    private void initStatusBar(){
        //5.0以下的系统
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            //将侧边栏顶部延伸至status bar
            mDrawerlayout.setFitsSystemWindows(true);
            //将主页面顶部延伸至status bar
            mDrawerlayout.setClipToPadding(false);
        }
    }

    //实现双击 Back键 退出
    @Override
    public void onBackPressed() {
        if (mDrawerlayout.isDrawerOpen(GravityCompat.START)){
            mDrawerlayout.closeDrawer(GravityCompat.START);
        }else {
            if (isBackPressed){
                super.onBackPressed();
                return;
            }

            isBackPressed = true;

            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isBackPressed = false;
                }
            }, 2000); //两秒内双击 Back 才生效
        }
    }








}
