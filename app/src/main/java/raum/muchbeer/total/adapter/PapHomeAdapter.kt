package raum.muchbeer.total.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import raum.muchbeer.total.databinding.PapGItemBinding
import raum.muchbeer.total.model.grievance.papform.PapEntryListModel

class PapHomeAdapter(val onClickListener: OnPapClickListener) : ListAdapter<PapEntryListModel, PapHomeAdapter.PapEntryVH>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PapEntryVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PapGItemBinding.inflate(inflater)
        return PapEntryVH(binding)
    }


    override fun onBindViewHolder(holder: PapEntryVH, position: Int) {
        val papList = getItem(position)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(papList)      }
        holder.bind(papList)

    }

    class PapEntryVH(val binding : PapGItemBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(papList: PapEntryListModel) {
                binding.papListEntry = papList
                binding.executePendingBindings()
            }
    }

    companion object diffUtil : DiffUtil.ItemCallback<PapEntryListModel>() {
        override fun areItemsTheSame(
            oldItem: PapEntryListModel,
            newItem: PapEntryListModel
        ): Boolean {
          return  oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: PapEntryListModel,
            newItem: PapEntryListModel
        ): Boolean {
           return oldItem.phone_number==newItem.phone_number
        }
    }
}

class OnPapClickListener(val clickListener: (papListModel:PapEntryListModel) -> Unit) {
    fun onClick(papListFun: PapEntryListModel) = clickListener(papListFun)
}