package com.example.appdefilmes.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.appdefilmes.R
import com.example.appdefilmes.fragments.AssistaTambemFragment
import com.example.appdefilmes.fragments.FichaTecnicaFragment

class TabViewPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {

    val tabsText = arrayOf(R.string.text_tab_first, R.string.text_tab_second)
    val fragments = arrayOf(AssistaTambemFragment(), FichaTecnicaFragment())

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int): Fragment {
      return fragments[position]
    }
}