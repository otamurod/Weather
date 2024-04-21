package uz.otamurod.presentation.ui.location_search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import uz.otamurod.domain.api.model.location.Place
import uz.otamurod.presentation.databinding.LocationSearchResultsRvItemBinding

class LocationSearchResultsRecyclerViewAdapter :
    RecyclerView.Adapter<LocationSearchResultsRecyclerViewAdapter.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Place>() {
        override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    inner class ViewHolder(val locationSearchResultsRvItemBinding: LocationSearchResultsRvItemBinding) :
        RecyclerView.ViewHolder(locationSearchResultsRvItemBinding.root) {
        fun onBind(place: Place) {
            locationSearchResultsRvItemBinding.apply {
                addressTextView.text =
                    String.format("%s, %s %s", place.name, place.admin1, place.country)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): LocationSearchResultsRecyclerViewAdapter.ViewHolder {
        return ViewHolder(
            LocationSearchResultsRvItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = differ.currentList[position]
        holder.onBind(place)

        holder.locationSearchResultsRvItemBinding.placeItemContainer.setOnClickListener {
            onClick?.invoke(place)
        }
    }

    var onClick: ((Place) -> Unit)? = null

    companion object {
        private const val TAG = "HourlyWeatherRVAdapter"
    }
}