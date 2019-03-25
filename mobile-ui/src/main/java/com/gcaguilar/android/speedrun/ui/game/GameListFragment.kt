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
import kotlinx.android.synthetic.main.fragment_game_list.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class GameListFragment : Fragment(), GameListAdapter.GameListListener {


    override fun onItemClicked(id: String, name: String, photoUri: String) {
        gameListViewModel.onItemClicked(id, name, photoUri)
    }

    private lateinit var adapter: GameListAdapter
    private val gameListViewModel: GameListViewModel by sharedViewModel()

    companion object {
        fun newInstance() = GameListFragment()
        const val tag = "GameListFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game_list, container, false)
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
        gameListViewModel.getGameListLiveData().observe(viewLifecycleOwner, Observer<GameListState> {
            if (it != null) handleGameListState(it)
        })
        gameListViewModel.loadData()
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
