package raum.muchbeer.total.adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import raum.muchbeer.total.databinding.VehicleItemShowBinding
import raum.muchbeer.total.model.vehicle.VehiclesData


class VehicleFormListAdapter() : ListAdapter<VehiclesData, VehicleFormListAdapter.VFormVH>(vehicleFormDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VFormVH {
        val inflater = LayoutInflater.from(parent.context)
      val  binding = VehicleItemShowBinding.inflate(inflater)

        return VFormVH(binding)

    }

    override fun onBindViewHolder(holder: VFormVH, position: Int) {
        val item = getItem(position)
        holder.bindData(item)
    }

    class VFormVH(val binding: VehicleItemShowBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bindData(vehicleData: VehiclesData) {
                binding.vehicleForm = vehicleData
                binding.executePendingBindings()
            }
    }

    companion object vehicleFormDiffUtil : DiffUtil.ItemCallback<VehiclesData>() {
        override fun areItemsTheSame(oldItem: VehiclesData, newItem: VehiclesData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: VehiclesData, newItem: VehiclesData): Boolean {
            return oldItem.primary_key ==newItem.primary_key
        }
    }
}