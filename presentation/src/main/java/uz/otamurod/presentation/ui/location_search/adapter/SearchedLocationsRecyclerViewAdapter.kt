package uz.otamurod.presentation.ui.location_search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import uz.otamurod.domain.api.model.location.Place
import uz.otamurod.presentation.databinding.SearchedLocationsRvItemBinding

class SearchedLocationsRecyclerViewAdapter :
    RecyclerView.Adapter<SearchedLocationsRecyclerViewAdapter.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Place>() {
        override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    inner class ViewHolder(val searchedLocationsRvItemBinding: SearchedLocationsRvItemBinding) :
        RecyclerView.ViewHolder(searchedLocationsRvItemBinding.root) {
        fun onBind(place: Place) {
            searchedLocationsRvItemBinding.apply {
                addressTextView.text =
                    String.format("%s, %s %s", place.name, place.admin1, place.country)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): SearchedLocationsRecyclerViewAdapter.ViewHolder {
        return ViewHolder(
            SearchedLocationsRvItemBinding.inflate(
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

        holder.searchedLocationsRvItemBinding.addressTextView.setOnClickListener {
            onClick?.invoke(place)
        }
        holder.searchedLocationsRvItemBinding.deleteSearchedLocationIcon.setOnClickListener {
            onDeleteClick?.invoke(place)
        }
    }

    var onClick: ((Place) -> Unit)? = null
    var onDeleteClick: ((Place) -> Unit)? = null

    companion object {
        private const val TAG = "HourlyWeatherRVAdapter"
    }
}