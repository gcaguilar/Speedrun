package com.gcaguilar.android.speedrun.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.gcaguilar.android.speedrun.ui.R
import com.gcaguilar.android.speedrun.ui.detail.DetailFragment
import com.gcaguilar.android.speedrun.ui.detail.DetailViewModel
import com.gcaguilar.android.speedrun.ui.game.GameListFragment
import com.gcaguilar.android.speedrun.ui.game.GameListViewModel
import com.gcaguilar.android.speedrun.ui.game.GameModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private val detailViewModel: DetailViewModel by viewModel()
    private val gameListViewModel: GameListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeView()

        gameListViewModel.getNavigateToDetail().observe(this, Observer {
            it.getContentIfNotHandled()?.let { source ->
                navigateToItem(source)
            }
        })
        detailViewModel.navigetToVideo().observe(this, Observer {
            it.getContentIfNotHandled()?.let { source ->
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(source)
                startActivity(i)
            }
        })
    }

    private fun navigateToItem(source: GameModel) {
        val detailFragment = DetailFragment.newInstance(source.id, source.name, source.photoUri)
        replaceFragment(detailFragment, DetailFragment.tag, true)
    }

    private fun initializeView() {
        val fragment = GameListFragment.newInstance()
        replaceFragment(fragment, GameListFragment.tag, false)
    }


    private fun replaceFragment(fragment: Fragment, tag: String, popBackStack: Boolean = false) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.cf_main_container, fragment, tag)
        if (popBackStack) transaction.addToBackStack(null)
        transaction.commit()
    }

}
