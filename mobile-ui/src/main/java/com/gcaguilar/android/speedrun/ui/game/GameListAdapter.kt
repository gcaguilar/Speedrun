package com.gcaguilar.android.speedrun.ui.game

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gcaguilar.android.speedrun.data.game.model.Game
import com.gcaguilar.android.speedrun.ui.R

class GameListAdapter(val gameListListener: GameListListener,
                      val context: Context) : RecyclerView.Adapter<GameListAdapter.ViewHolder>() {
    private var gameList: List<Game> = ArrayList()

    interface GameListListener {
        fun onItemClicked(itemId: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_game, parent, false))
    }

    override fun getItemCount(): Int {
        return gameList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val game = gameList[position]
        holder.bind(game)
    }

    fun setData(gameList: List<Game>) {
        this.gameList = gameList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tv_game_title)
        private val imLogo: ImageView = itemView.findViewById(R.id.iv_game_logo)

        fun bind(game: Game) {
            game.apply {
                tvName.text = name
                if (!logo.isEmpty()) {
                    Glide.with(context)
                            .load(logo)
                            .centerInside()
                            .into(imLogo)
                } else {
                    imLogo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_error))
                }
            }
            itemView.setOnClickListener {
                gameListListener.onItemClicked(gameList[adapterPosition].id)
            }
        }

    }
}