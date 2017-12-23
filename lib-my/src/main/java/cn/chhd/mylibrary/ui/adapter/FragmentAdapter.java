package cn.chhd.mylibrary.ui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.TextUtils;

import java.util.List;

/**
 * Created by congh on 2017/11/26.
 */

public class FragmentAdapter extends FragmentStatePagerAdapter {

    public static final String KEY_TITLE = "title";

    private List<Fragment> fragmentList;
    private List<String> titleList;

    public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titleList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titleList == null) {
            Bundle arguments = fragmentList.get(position).getArguments();
            if (arguments != null && !TextUtils.isEmpty(arguments.getString(KEY_TITLE))) {
                return arguments.getString(KEY_TITLE);
            } else {
                return super.getPageTitle(position);
            }
        } else {
            return titleList.get(position);
        }
    }

    public void notifyDataSetChanged(List<Fragment> fragmentList) {
        this.fragmentList = fragmentList;
        notifyDataSetChanged();
    }

    public void notifyDataSetChanged(List<Fragment> fragmentList, List<String> titleList) {
        this.fragmentList = fragmentList;
        this.titleList = titleList;
        notifyDataSetChanged();
    }

}
