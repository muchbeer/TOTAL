package raum.muchbeer.total.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import raum.muchbeer.total.databinding.RecordedListItemBinding
import raum.muchbeer.total.model.grievance.CgrievanceModel
import raum.muchbeer.total.model.grievance.papform.PapEntryListModel

class GrievanceAdapter(val onClickListener: OnGrievanceClickListener) : ListAdapter<CgrievanceModel, GrievanceAdapter.GViewModel>(diffUtili) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GViewModel {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecordedListItemBinding.inflate(inflater)
        return GViewModel(binding)
    }

    override fun onBindViewHolder(holder: GViewModel, position: Int) {
        val recordsItem = getItem(position)
        holder.bind(recordsItem)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(recordsItem)
        Log.d("GrievanceAdapter","clicked")}
    }

    class GViewModel(val binding: RecordedListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recordList: CgrievanceModel) {
            binding.grievanceEntry = recordList
            binding.executePendingBindings()
        }
    }

    companion object diffUtili : DiffUtil.ItemCallback<CgrievanceModel>() {
        override fun areItemsTheSame(oldItem: CgrievanceModel, newItem: CgrievanceModel): Boolean {
            return  oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: CgrievanceModel,
            newItem: CgrievanceModel
        ): Boolean {
            return oldItem.full_name==newItem.full_name
        }

    }
}

class OnGrievanceClickListener(val clickListener: (papListModel:CgrievanceModel) -> Unit) {
    fun onClick(cgrievanceList: CgrievanceModel) = clickListener(cgrievanceList)
}