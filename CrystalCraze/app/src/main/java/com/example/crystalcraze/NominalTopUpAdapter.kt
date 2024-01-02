package com.example.crystalcraze

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crystalcraze.databinding.ItemNominalTopupBinding
import com.example.crystalcraze.model.TopUpModel
import java.text.NumberFormat
import java.util.Locale

class NominalTopUpAdapter(private val itemList: List<TopUpModel>) :
    RecyclerView.Adapter<NominalTopUpAdapter.NominalTopUpViewHolder>() {

    private var selectedItem = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NominalTopUpViewHolder {
        val binding =
            ItemNominalTopupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NominalTopUpViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NominalTopUpViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun getSelectedItemPosition(): Int {
        return selectedItem
    }

    fun getItemByPosition(position: Int): TopUpModel {
        return itemList[position]
    }

    inner class NominalTopUpViewHolder(private val binding: ItemNominalTopupBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TopUpModel) {
            binding.tvNominal.text = "${item.title}\n Rp ${
                NumberFormat.getNumberInstance(
                    Locale("in", "ID")
                ).format(item.price)
            }"

            if (selectedItem == adapterPosition) {
                binding.tvNominal.setBackgroundResource(R.drawable.ic_rounded_black)
            } else {
                binding.tvNominal.setBackgroundResource(R.drawable.ic_rounded_lagi)
            }

            binding.root.setOnClickListener {
                // Update selection and refresh the view
                selectedItem = adapterPosition
                notifyDataSetChanged()
            }
        }
    }
}


