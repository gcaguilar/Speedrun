package com.gcaguilar.android.speedrun.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.gcaguilar.android.speedrun.data.game.model.Runs
import com.gcaguilar.android.speedrun.ui.R
import com.gcaguilar.android.speedrun.ui.detail.ItemDetailState.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_detail.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

private const val itemIdParam = "itemId"
private const val nameParam = "gameName"
private const val photoUriParam = "photoUri"


class DetailFragment : Fragment() {
    private var itemId: String? = null
    private var gameName: String? = null
    private var photoUri: String? = null

    private val detailViewModel: DetailViewModel by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            itemId = it.getString(itemIdParam)
            gameName = it.getString(nameParam)
            photoUri = it.getString(photoUriParam)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeView()
    }

    private fun initializeView() {
        gameName?.let { tv_detail_game_name.text = it }
        photoUri?.let {
            context?.let { c ->
                Glide.with(c)
                        .load(it)
                        .centerInside()
                        .into(im_detail_logo_game)
            }
        }
        fb_detail_play_video.setOnClickListener {
            detailViewModel.onFloatingClicked()
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        detailViewModel.itemDetailLiveData().observe(viewLifecycleOwner, Observer {
            if (it != null) handleDetailState(it)
        })
        detailViewModel.getDetails(itemId)
    }

    private fun handleDetailState(state: ItemDetailState?) {
        when (state) {
            is Success -> {
                setupSuccessDetailState()
                handleData(state.data)
            }
            Loading -> setupLoadingDetailScreen()
            is Error -> {
                setupErrorDetailScreen()
                state.errorMessage?.let {
                    view?.let { v ->
                        Snackbar.make(v, state.errorMessage, Snackbar.LENGTH_LONG).show()
                    } ?: Timber.e("View is null")
                } ?: Timber.e("Cant recover error message")
            }
        }
    }

    private fun handleData(data: Runs?) {
        data?.let {
            tv_detail_player_name.text = it.playerName
            tv_detail_player_time.text = detailViewModel.formatDate(it.time)
        }
    }


    private fun setupErrorDetailScreen() {
        pb_detail_loading.visibility = View.GONE

    }

    private fun setupLoadingDetailScreen() {
        pb_detail_loading.visibility = View.VISIBLE
    }

    private fun setupSuccessDetailState() {
        pb_detail_loading.visibility = View.GONE
    }



    companion object {
        fun newInstance(itemId: String, name: String, uri: String) =
                DetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(itemIdParam, itemId)
                        putString(nameParam, name)
                        putString(photoUriParam, uri)
                    }
                }

        const val tag = "DetailFragment"
    }
}
