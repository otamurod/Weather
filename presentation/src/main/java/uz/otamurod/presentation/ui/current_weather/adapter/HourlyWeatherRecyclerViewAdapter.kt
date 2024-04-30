package uz.otamurod.presentation.ui.current_weather.adapter

import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import uz.otamurod.domain.model.HourlyItem
import uz.otamurod.presentation.R
import uz.otamurod.presentation.databinding.HourlyWeatherRvItemBinding
import uz.otamurod.presentation.utils.weather.WeatherType

class HourlyWeatherRecyclerViewAdapter :
    RecyclerView.Adapter<HourlyWeatherRecyclerViewAdapter.ViewHolder>() {

    private var selectedHourPosition = -1

    private val diffCallback = object : DiffUtil.ItemCallback<HourlyItem>() {
        override fun areItemsTheSame(oldItem: HourlyItem, newItem: HourlyItem): Boolean {
            return oldItem.time == newItem.time
        }

        override fun areContentsTheSame(oldItem: HourlyItem, newItem: HourlyItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    inner class ViewHolder(val hourlyWeatherRvItemBinding: HourlyWeatherRvItemBinding) :
        RecyclerView.ViewHolder(hourlyWeatherRvItemBinding.root) {
        fun onBind(hourlyItem: HourlyItem, isSelected: Boolean) {
            hourlyWeatherRvItemBinding.apply {
                hourTimeTextView.text = extractHour(hourlyItem.time)
                hourlyTemperatureTextView.text =
                    String.format("%d °C", hourlyItem.temperature2m.toInt())
                val weatherTypeAnimationName =
                    WeatherType.fromWMO(hourlyItem.weatherCode).animationAssetFileName
                try {
                    hourlyWeatherCodeAnimation.setAnimation(weatherTypeAnimationName)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d(TAG, "onBind: $weatherTypeAnimationName")
                }
                hourlyWeatherCodeAnimation.playAnimation()

                if (isSelected) {
                    hourlyWeatherRvItemContainer.background =
                        ColorDrawable(itemView.context.resources.getColor(R.color.hourly_weather_rv_item_bg_color))
                } else {
                    hourlyWeatherRvItemContainer.setBackgroundResource(R.drawable.current_weather_probabilities_bg)
                }
            }
        }
    }

    private fun extractHour(dateTimeString: String): String {
        val dateTimeFormat = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm")
        val dateTime = DateTime.parse(dateTimeString, dateTimeFormat)
        return dateTime.toString("HH:mm")
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): HourlyWeatherRecyclerViewAdapter.ViewHolder {
        return ViewHolder(
            HourlyWeatherRvItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hourlyItem = differ.currentList[position]
        holder.onBind(hourlyItem, selectedHourPosition == position)

        holder.hourlyWeatherRvItemBinding.hourlyWeatherRvItemContainer.setOnClickListener {
            if (selectedHourPosition >= 0) {
                notifyItemChanged(selectedHourPosition)
            }
            selectedHourPosition = holder.adapterPosition
            notifyItemChanged(selectedHourPosition)
            onClick?.invoke(hourlyItem)
        }
    }

    var onClick: ((HourlyItem) -> Unit)? = null

    companion object {
        private const val TAG = "HourlyWeatherRVAdapter"
    }
}