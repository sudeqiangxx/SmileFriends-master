package cn.com.sdq.smilefriends.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;



import java.util.List;

import cn.com.sdq.smilefriends.base.BaseTwoFragment;


/**
 *
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] mTitles = null;
    private List<BaseTwoFragment> fragmentList;

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    public MyFragmentPagerAdapter(FragmentManager fm, String[] titles, List<BaseTwoFragment> fragments) {
        super(fm);
        mTitles=titles;
        fragmentList=fragments;
    }


    @Override
    public Fragment getItem(int position) {
       return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList == null ? 0 : fragmentList.size();
    }

    //用来设置tab的标题
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
