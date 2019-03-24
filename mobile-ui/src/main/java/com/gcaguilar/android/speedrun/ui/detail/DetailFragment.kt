package com.gcaguilar.android.speedrun.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.gcaguilar.android.speedrun.data.game.model.Runs
import com.gcaguilar.android.speedrun.ui.R
import com.gcaguilar.android.speedrun.ui.detail.ItemDetailState.*
import kotlinx.android.synthetic.main.fragment_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val ARG_PARAM1 = "itemId"

class DetailFragment : Fragment() {
    private var itemId: String? = null

    private val detailViewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            itemId = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        detailViewModel.itemDetailLiveData().observe(this, Observer {
            if (it != null) handleDetailState(it)
        }
        )
        detailViewModel.getDetails(itemId)
    }

    private fun handleDetailState(state: ItemDetailState?) {
        when (state) {
            is Success -> {
                setupSuccessDetailState()
                handleData(state.data)
            }
            is Loading -> setupLoadingDetailScreen()
            is Error -> setupErrorDetailScreen()
        }
    }

    private fun handleData(data: Runs?) {
        data?.let {
            tv_detail_player_name.text = it.playerName
            tv_detail_player_time.text = it.time
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
        fun newInstance(itemId: String) =
                DetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, itemId)
                    }
                }

        val tag = "DetailFragment"
    }
}
