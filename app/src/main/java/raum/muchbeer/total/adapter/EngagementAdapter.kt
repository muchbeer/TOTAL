package raum.muchbeer.total.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import raum.muchbeer.total.databinding.EngageListItemBinding
import raum.muchbeer.total.model.engagement.EngageModel
import raum.muchbeer.total.model.grievance.papform.PapEntryListModel

class EngagementAdapter(val onClickListener: OnEngageClickListener) :
    ListAdapter<EngageModel, EngagementAdapter.EngageVH>(engDiffUtil){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EngageVH {
            val inflater = LayoutInflater.from(parent.context)
            val binding = EngageListItemBinding.inflate(inflater)
            return EngageVH(binding)
    }

    override fun onBindViewHolder(holder: EngageVH, position: Int) {
        val engageList = getItem(position)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(engageList)      }
        holder.bindData(engageList)    }

    companion object engDiffUtil : DiffUtil.ItemCallback<EngageModel>() {
        override fun areItemsTheSame(oldItem: EngageModel, newItem: EngageModel): Boolean {
         return   oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: EngageModel, newItem: EngageModel): Boolean {
            return oldItem.reg_date == newItem.reg_date       }

    }

    class EngageVH(val binding : EngageListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(engage : EngageModel) {
            binding.engageEntry = engage
            binding.executePendingBindings()
        }
    }
}

class OnEngageClickListener(val clickListener: (engageListModel: EngageModel) -> Unit) {
    fun onClick(engageListFun: EngageModel) = clickListener(engageListFun)
}