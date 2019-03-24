package com.gcaguilar.android.speedrun.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gcaguilar.android.speedrun.data.game.model.Game
import com.gcaguilar.android.speedrun.ui.R
import com.gcaguilar.android.speedrun.ui.game.GameListState.Success
import com.gcaguilar.android.speedrun.ui.main.NavigationViewModel
import kotlinx.android.synthetic.main.game_list_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class GameListFragment : Fragment(), GameListAdapter.GameListListener {

    private val navigationViewModel: NavigationViewModel by sharedViewModel()

    override fun onItemClicked(id: String) {
        navigationViewModel.onItemClicked(id)
    }

    private lateinit var adapter: GameListAdapter
    private val gameListViewModel: GameListViewModel by viewModel()

    companion object {
        fun newInstance() = GameListFragment()
        const val tag = "GameListFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.game_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRecyclerView()
    }

    private fun initializeRecyclerView() {
        adapter = GameListAdapter(this, requireContext())
        rv_game_main_list.layoutManager = LinearLayoutManager(context,
                RecyclerView.VERTICAL, false)
        val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        rv_game_main_list.addItemDecoration(divider)
        rv_game_main_list.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        gameListViewModel.getGameListLiveData().observe(this, Observer<GameListState> {
            if (it != null) handleGameListState(it)
        }
        )
        gameListViewModel.fecthList()
    }

    private fun handleGameListState(state: GameListState) {
        when (state) {
            is Success -> {
                handleData(state.data)
                setupScreenSuccessState()
            }
            is GameListState.Loading -> setupScreenLoadingState()

            is Error -> {
                setupScreenErrorState()
                handleError()
            }
        }
    }

    private fun handleError() {
    }

    private fun setupScreenErrorState() {
        pb_game_list_loading.visibility = View.GONE
        rv_game_main_list.visibility = View.GONE
    }

    private fun setupScreenLoadingState() {
        rv_game_main_list.visibility = View.GONE
        pb_game_list_loading.visibility = View.VISIBLE
    }

    private fun handleData(data: List<Game>?) {
        data?.let { adapter.setData(it) }
    }

    private fun setupScreenSuccessState() {
        rv_game_main_list.visibility = View.VISIBLE
        pb_game_list_loading.visibility = View.GONE

    }

}
