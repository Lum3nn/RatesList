package com.lumen.rateslist.ui.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lumen.rateslist.databinding.RateDateItemBinding
import com.lumen.rateslist.databinding.RateDateLoadingItemBinding
import com.lumen.rateslist.databinding.RateItemBinding
import com.lumen.rateslist.repository.EmojiRepository
import com.lumen.rateslist.ui.list.item.Date
import com.lumen.rateslist.ui.list.item.Rate
import com.lumen.rateslist.ui.list.item.RatesListItem

class RateListAdapter(
    private val onRateItemListener: OnRateItemClickListener
) : ListAdapter<RatesListItem, RateListAdapter.RatesListItemViewHolder>(DIFF) {

    companion object {

        const val TYPE_DATE = 1
        const val TYPE_RATE = 2
        const val TYPE_LOADING = 3

        private val DIFF = object : DiffUtil.ItemCallback<RatesListItem>() {

            override fun areItemsTheSame(
                oldItem: RatesListItem, newItem: RatesListItem
            ): Boolean {
                if (oldItem is Rate && newItem is Rate) {
                    return oldItem == newItem
                } else if (oldItem is Date && newItem is Date) {
                    return oldItem.date == newItem.date
                }
                return false
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: RatesListItem, newItem: RatesListItem
            ): Boolean = oldItem == newItem
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Date -> TYPE_DATE
            is Rate -> TYPE_RATE
            else -> TYPE_LOADING
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatesListItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_DATE -> {
                val itemView = RateDateItemBinding.inflate(layoutInflater, parent, false)
                RateDataViewHolder(itemView)
            }
            TYPE_RATE -> {
                val itemView = RateItemBinding.inflate(layoutInflater, parent, false)
                RateItemViewHolder(itemView, onRateItemListener)
            }
            else -> {
                val itemView = RateDateLoadingItemBinding.inflate(layoutInflater, parent, false)
                LoadingViewHolder(itemView)
            }
        }
    }

    override fun onBindViewHolder(holder: RatesListItemViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    interface OnRateItemClickListener {
        fun onClick(rateItem: Rate)
    }

    abstract class RatesListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(data: RatesListItem)
    }

    class LoadingViewHolder(
        binding: RateDateLoadingItemBinding
    ) : RatesListItemViewHolder(binding.root) {
        override fun bind(data: RatesListItem) {}
    }

    class RateDataViewHolder(
        private val binding: RateDateItemBinding
    ) : RatesListItemViewHolder(binding.root) {

        override fun bind(data: RatesListItem) {
            val dataData = data as Date
            binding.rateDate.text = dataData.date
        }
    }

    class RateItemViewHolder(
        private val binding: RateItemBinding,
        private val onRateItemListener: OnRateItemClickListener
    ) : RatesListItemViewHolder(binding.root) {

        override fun bind(data: RatesListItem) {
            val dataRate = data as Rate
            val name = dataRate.name
            val value = dataRate.value
            val emojiCode = EmojiRepository.getEmojiCode(name)

            binding.rateItemFlag.text = emojiCode
            binding.rateItemName.text = name
            binding.rateItemValue.text = value.toString()
            binding.rateItem.setOnClickListener {
                onRateItemListener.onClick(dataRate)
            }
        }
    }
}
