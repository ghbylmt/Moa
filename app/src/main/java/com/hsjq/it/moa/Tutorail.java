package com.hsjq.it.moa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.hsjq.it.moa.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class Tutorail extends Activity implements ViewPager.OnPageChangeListener {

    private ViewPager vp;
    private ViewPagerAdapter vpAdapter;
    private List<View> views;
    private ImageView[] dots;
    private int[] ids = {R.id.iv1, R.id.iv2, R.id.iv3};
    private Button start_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutroail);
        initViews();
        initDots();
    }

    private void initViews() {
        LayoutInflater inflater = LayoutInflater.from(this);

        views = new ArrayList<View>();
        views.add(inflater.inflate(R.layout.turtorial_one, null));
        views.add(inflater.inflate(R.layout.turtorial_two, null));
        views.add(inflater.inflate(R.layout.turtorial_three, null));

        vpAdapter = new ViewPagerAdapter(views, this);
        vp = (ViewPager) findViewById(R.id.viewpager);
        vp.setAdapter(vpAdapter);
        // 下标从0开始，所以第三个页面是get(2)。
        start_btn = (Button) views.get(2).findViewById(R.id.start_btn);
        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //将展示信息保存到SharedPreferences中
                //getSharedPreferences("showTutorail", Activity.MODE_PRIVATE).getBoolean("flag", false))
                SharedPreferences.Editor editor = getSharedPreferences("showTutorail", Activity.MODE_APPEND).edit();
                editor.putBoolean("flag", true);
                editor.commit();
                Intent i = new Intent(Tutorail.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        vp.setOnPageChangeListener(this);
    }

    /**
     * 循环设置点
     */
    private void initDots() {
        dots = new ImageView[views.size()];
        for (int i = 0; i < views.size(); i++) {
            dots[i] = (ImageView) findViewById(ids[i]);
        }
    }

    @Override
    /** 滑动状态改变的时候 */
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    /** 当页面被滑动时候调用 */
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub
    }

    @Override
    /** 当前新的页面被选中时调用 */
    public void onPageSelected(int arg0) {
        for (int i = 0; i < ids.length; i++) {
            if (arg0 == i) {
                // 亮点
                dots[i].setImageResource(R.drawable.login_point_selected);
            } else {
                // 暗点
                dots[i].setImageResource(R.drawable.login_point);
            }
        }
    }

    /*
  Double Press back to Exist
  双击退出应用程序
   */
    private long exitTime = 0;

    /*
    *双击退出应用程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), R.string.tips_exist, Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
