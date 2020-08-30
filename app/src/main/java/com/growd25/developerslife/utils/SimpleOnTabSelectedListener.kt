package com.growd25.developerslife.utils

import com.google.android.material.tabs.TabLayout

class SimpleOnTabSelectedListener(
    private val onSelected: (tab: TabLayout.Tab) -> Unit
) : TabLayout.OnTabSelectedListener {
    override fun onTabSelected(tab: TabLayout.Tab) {
        onSelected.invoke(tab)
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {

    }

    override fun onTabReselected(tab: TabLayout.Tab) {

    }

}