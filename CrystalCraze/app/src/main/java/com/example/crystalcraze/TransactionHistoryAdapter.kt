package com.example.crystalcraze

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crystalcraze.databinding.ItemTransactionHistoryBinding
import com.example.crystalcraze.model.TransactionHistoryModel
import java.text.NumberFormat
import java.util.Locale

class TransactionHistoryAdapter(
    private val transactionHistoryList: List<TransactionHistoryModel>,
    private val onClickItem: (TransactionHistoryModel) -> Unit
) :
    RecyclerView.Adapter<TransactionHistoryAdapter.TopUpViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopUpViewHolder {
        val binding = ItemTransactionHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TopUpViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopUpViewHolder, position: Int) {
        val topUp = transactionHistoryList[position]
        holder.bind(topUp)
    }

    override fun getItemCount(): Int {
        return transactionHistoryList.size
    }

    inner class TopUpViewHolder(private val binding: ItemTransactionHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: TransactionHistoryModel) {
            binding.tvDate.text = data.dateTime
            binding.tvGameName.text = data.gameName
            binding.ivGame.setImageResource(data.gameIcon)
            binding.tvUserId.text = "Account ID: ${data.accountId}"
            binding.tvTitleTopup.text = data.titleTopUp
            binding.tvPrice.text = "Rp ${
                NumberFormat.getNumberInstance(
                    Locale("in", "ID")
                ).format(data.priceTopUp)
            }"
            binding.tvWhatsapp.text = "WA: " + data.whatsappNumber.toString()
            binding.ivPaymentMethod.setImageResource(
                when (data.paymentMethod) {
                    -1 -> 0
                    0 -> R.drawable.ic_qris
                    1 -> R.drawable.ic_bca
                    2 -> R.drawable.ic_bni
                    3 -> R.drawable.ic_shopeepay
                    4 -> R.drawable.ic_alfamart
                    else -> R.drawable.ic_dana
                }
            )

            binding.root.setOnClickListener {
                onClickItem.invoke(data)
            }
        }
    }
}
