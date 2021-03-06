package ru.virarnd.kommunalkin.ui.detailinfo

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.ajalt.timberkt.Timber
import ru.virarnd.kommunalkin.R
import ru.virarnd.kommunalkin.databinding.ItemDetailInfoBinding
import ru.virarnd.kommunalkin.models.Counter
import ru.virarnd.kommunalkin.models.EstateObjectStatus

class DetailInfoAdapter(val viewModel: DetailInfoViewModel, val status: EstateObjectStatus) :
    ListAdapter<Pair<Counter, Double>, DetailInfoAdapter.DetailViewHolder>(DetailInfoDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_detail_info, parent, false)
        return DetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.setupItem(getItem(position), position)
    }

    inner class DetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemDetailInfoBinding = ItemDetailInfoBinding.bind(itemView)

        fun setupItem(dataSet: Pair<Counter, Double>, position: Int) {
            binding.detailInfoViewModel = viewModel
            binding.counter = dataSet.first
            binding.previousReading = dataSet.second
            binding.position = position
            binding.tvConsumptionLabel.setTextColor(if ((dataSet.first.counterReading - dataSet.second) < 0) Color.RED else Color.BLACK)
        }
    }

    class DetailInfoDiffUtilCallback : DiffUtil.ItemCallback<Pair<Counter, Double>>() {
        override fun areItemsTheSame(oldItem: Pair<Counter, Double>, newItem: Pair<Counter, Double>): Boolean {
            return oldItem.first.counterStateId == newItem.first.counterStateId && oldItem.second == newItem.second
        }

        override fun areContentsTheSame(oldItem: Pair<Counter, Double>, newItem: Pair<Counter, Double>): Boolean {
            return oldItem.first.counterReading == newItem.first.counterReading
                    && oldItem.first.estateObjectFootprintId == newItem.first.estateObjectFootprintId
                    && oldItem.first.counterName == newItem.first.counterName
                    && oldItem.first.counterType == newItem.first.counterType
        }
    }
}