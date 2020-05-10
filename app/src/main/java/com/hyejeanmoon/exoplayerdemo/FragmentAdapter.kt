package com.hyejeanmoon.exoplayerdemo

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class FragmentAdapter(
    fragmentManager: FragmentManager,
    val tagFragments: List<Fragment>,
    val titles: List<String>
) : FragmentPagerAdapter(fragmentManager) {
    override fun getCount(): Int {
        return tagFragments.size
    }

    override fun getItem(position: Int): Fragment {
        return tagFragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[0]
    }

}