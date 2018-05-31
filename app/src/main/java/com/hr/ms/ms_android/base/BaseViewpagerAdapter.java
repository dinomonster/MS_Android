package com.hr.ms.ms_android.base;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lianghuiyong on 2017/7/19.
 * Info：
 */

public class BaseViewpagerAdapter extends FragmentPagerAdapter {
    private List<BaseAppCompatFragment> fragments;
    private FragmentManager mFm;
    private BaseAppCompatFragment replaceFragment;
    private boolean canReplace;
    private int mPosition;

    public BaseViewpagerAdapter(FragmentManager fm) {
        super(fm);
        this.mFm = fm;
        this.fragments = new ArrayList<>();
    }

    public void addFragment(BaseAppCompatFragment fragment) {
        fragments.add(fragment);
    }

    public void replaceFragment(int position, BaseAppCompatFragment fragment) {
        if (fragment.getClass().getName().equals(fragments.get(position).getClass().getName()))
            return;
        if (position >= 0 && position < fragments.size()) {
            for (int i = 0; i < fragments.size(); i++) {
                if(i == position){
                    fragments.remove(i);
                    fragments.add(i,fragment);
                }
            }
            this.replaceFragment = fragment;
            this.mPosition = position;
            canReplace = true;
            this.notifyDataSetChanged();
        }
    }

    @Override
    public BaseAppCompatFragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getTitle();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //得到缓存的fragment
        BaseAppCompatFragment fragment = (BaseAppCompatFragment) super.instantiateItem(container,
                position);
        //得到tag，这点很重要
        String fragmentTag = fragment.getTag();
        if (canReplace && mPosition == position) {
            //如果这个fragment需要更新
            FragmentTransaction ft = mFm.beginTransaction();
            //移除旧的fragment
            ft.remove(fragment);
            //换成新的fragment
            fragment = replaceFragment;
            //添加新fragment时必须用前面获得的tag，这点很重要
            ft.add(container.getId(), fragment, fragmentTag);
            ft.attach(fragment);
            ft.commit();
            //复位更新标志
            canReplace = false;
        }
        return fragment;
    }
}
