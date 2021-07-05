package raum.muchbeer.total.adapter

import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import raum.muchbeer.total.databinding.HseListItemBinding
import raum.muchbeer.total.model.hse.Hsedata

class HseAdapter() : ListAdapter<Hsedata, HseAdapter.HseVH>(hsediffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HseVH {
     val inflater = LayoutInflater.from(parent.context)
    val binding = HseListItemBinding.inflate(inflater)
        return HseVH(binding)
    }

    override fun onBindViewHolder(holder: HseVH, position: Int) {
        val hseItem = getItem(position)
         holder.bind(hseItem)
          }


    class HseVH(val binding : HseListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(hseItem: Hsedata) {
            binding.hseEntry = hseItem
            binding.executePendingBindings()
        }
    }

    companion object hsediffUtil : DiffUtil.ItemCallback<Hsedata>() {
        override fun areItemsTheSame(oldItem: Hsedata, newItem: Hsedata): Boolean {
         return   oldItem ==newItem
        }

        override fun areContentsTheSame(oldItem: Hsedata, newItem: Hsedata): Boolean {
             return oldItem.primary_key == newItem.primary_key
        }

    }
}