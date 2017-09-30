package cn.com.sdq.smilefriends.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.sdq.smilefriends.R;
import cn.com.sdq.smilefriends.base.BaseTwoFragment;
import cn.com.sdq.smilefriends.commn.AppConstans;
import cn.com.sdq.smilefriends.ui.adapter.MyFragmentPagerAdapter;

/**
 * Created by Administrator on 2017/2/11.
 */

public class ConsultFragment extends BaseTwoFragment implements AppConstans{

    @Bind(R.id.tab_menu_new_class)
    TabLayout tabMenuNewClass;
    @Bind(R.id.vp_new_content)
    ViewPager vpNewContent;
    private MyFragmentPagerAdapter myFragmentPagerAdapter;

    @Override
    protected boolean hasTitleBar() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.consult_fragmentlayout;
    }

    @Override
    protected void initView() {
        initTab();

    }

    private void initTab() {
        initTitles(NEWS_CLASS);
    }
    private void initTitles(String[] titles) {
        if (titles != null) {

            List<BaseTwoFragment> listFragments = new ArrayList<>();
            for (int i = 0; i < titles.length; i++) {
//                if (i == 0) {
//                    listFragments.add(new HomePageFragment());
//                } else {
//                    BaseTwoFragment fragment = new CommodityClassFragment();
//                    fragment.setCatetoryId(categoryIds.get(i - 1));
//                    listFragments.add(fragment);
//                }
                BaseTwoFragment fragment =new NewsFragment();
                fragment.setCatetoryId(i+"");
                listFragments.add(fragment);
            }
            initTabView(titles, listFragments);
        }
    }

    private void initTabView(String[] titles, List<BaseTwoFragment> fragments) {
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), titles, fragments);
        vpNewContent.setAdapter(myFragmentPagerAdapter);
//        vpNewContent.setNoScroll(true);//控制ViewPager是否可以左右滑动(true表示不可以)
        if (titles != null && titles.length > 0) {
            vpNewContent.setOffscreenPageLimit(titles.length);
        } else {
            vpNewContent.setOffscreenPageLimit(20);
        }
//        lmdNspViewpager.setNoScroll(false);
        //将TabLayout和ViewPager绑定在一起，使双方各自的改变都能直接影响另一方，解放了开发人员对双方变动事件的监听
        tabMenuNewClass.setupWithViewPager(vpNewContent);
        //指定Tab的位置
    }

}
