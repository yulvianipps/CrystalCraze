package com.example.crystalcraze

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crystalcraze.databinding.ItemTopupBinding
import com.example.crystalcraze.model.GameModel

class TopUpAdapter(private val topUpList: List<GameModel>) :
    RecyclerView.Adapter<TopUpAdapter.TopUpViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopUpViewHolder {
        val binding = ItemTopupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopUpViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopUpViewHolder, position: Int) {
        val topUp = topUpList[position]
        holder.bind(topUp)
    }

    override fun getItemCount(): Int {
        return topUpList.size
    }

    class TopUpViewHolder(private val binding: ItemTopupBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: GameModel) {
            binding.ivGame.setImageResource(data.icon)
            binding.root.setOnClickListener {
                val intent = Intent(it.context, TopUpGames::class.java)
                intent.putExtra("data", data)
                it.context.startActivity(intent)
            }
        }
    }
}
