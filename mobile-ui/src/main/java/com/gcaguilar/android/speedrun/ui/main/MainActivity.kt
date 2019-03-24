package com.gcaguilar.android.speedrun.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.gcaguilar.android.speedrun.ui.R
import com.gcaguilar.android.speedrun.ui.detail.DetailFragment
import com.gcaguilar.android.speedrun.ui.game.GameListFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val navigationViewModel: NavigationViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeView()

        navigationViewModel.navigateToItem.observe(this, Observer {
            it.getContentIfNotHandled()?.let { sourceId ->
                navigateToItem(sourceId)
            }
        }
        )
    }

    private fun navigateToItem(itemId: String) {
        val detailFragment = DetailFragment.newInstance(itemId)
        replaceFragment(detailFragment, "detail", true)
    }

    private fun initializeView() {
        val fragment = GameListFragment.newInstance()
        replaceFragment(fragment, GameListFragment.tag)
    }


    private fun replaceFragment(fragment: Fragment, tag: String, popBackStack: Boolean = false) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.cf_main_container, fragment, tag)
                .commit()
    }

}
