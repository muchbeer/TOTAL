package raum.muchbeer.total.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import raum.muchbeer.total.databinding.VehicleListItemBinding
import raum.muchbeer.total.model.grievance.papform.PapEntryListModel
import raum.muchbeer.total.model.vehicle.request.Vehicle

class VehicleListAdapter(val onClickListener: OnVehicleClickListener) : ListAdapter<Vehicle, VehicleListAdapter.VehicleVM>(diffUtili) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleVM {
        val inflater = LayoutInflater.from(parent.context)
        val binding = VehicleListItemBinding.inflate(inflater)
        return VehicleVM(binding)
    }

    override fun onBindViewHolder(holder: VehicleVM, position: Int) {
        val vehicleListist = getItem(position)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(vehicleListist)      }
        holder.binding(vehicleListist)

    }
    class VehicleVM( val binding: VehicleListItemBinding) : RecyclerView.ViewHolder(binding.root) {
            fun binding(vehicle : Vehicle){
                binding.vehicle = vehicle
                binding.executePendingBindings()
            }
    }


    companion object diffUtili : DiffUtil.ItemCallback<Vehicle>() {
        override fun areItemsTheSame(oldItem: Vehicle, newItem: Vehicle): Boolean {
          return  oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Vehicle, newItem: Vehicle): Boolean {
            return oldItem.vehicle_number == (newItem.vehicle_number)
        }

    }
}

class OnVehicleClickListener(val clickListener: (vehicleList: Vehicle) -> Unit) {
    fun onClick(vehicleListFun: Vehicle) = clickListener(vehicleListFun)
}