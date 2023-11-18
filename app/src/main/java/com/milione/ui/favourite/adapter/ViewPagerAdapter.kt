package com.milione.ui.favourite.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.milione.ui.favourite.fragment.competition.CompetitionFragment
import com.milione.ui.favourite.fragment.match.MatchFragment
import com.milione.ui.favourite.fragment.team.TeamFragment

class ViewPagerAdapter(manager: FragmentManager, val sportId: String?, val chooseDate: String?) : FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val COUNT = 3

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MatchFragment(sportId,chooseDate)
            1 -> CompetitionFragment(sportId,chooseDate)
            2 -> TeamFragment(sportId,chooseDate)
            else -> MatchFragment(sportId, chooseDate)
        }
    }

    override fun getCount(): Int {
        return COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return "Tab " + (position + 1)
    }
}
