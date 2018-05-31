package com.better.appbase.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lianghuiyong on 2017/7/19.
 * Info：
 */

public class LoopViewpagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public LoopViewpagerAdapter(FragmentManager fm) {
        super(fm);
        this.fragments = new ArrayList<>();
    }

    public void addFragment(Fragment fragment) {
        fragments.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        KLog.e("getItem = " + position);
        return this.fragments.get(position % fragments.size());
    }

    @Override
    public long getItemId(int position) {
        KLog.e("getItemId  = " + position);
        return super.getItemId(position%fragments.size());
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //屏蔽该方法，可重复利用已显示的fragment
        //super.destroyItem(container, position, object);
    }
}
