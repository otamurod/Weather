package uz.otamurod.presentation.ui.weekly_forecast.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.joda.time.DateTime
import uz.otamurod.domain.model.DailyItem
import uz.otamurod.presentation.databinding.WeeklyForecastRvItemBinding
import uz.otamurod.presentation.utils.weather.WeatherType
import java.util.*

class WeeklyForecastRecyclerViewAdapter :
    RecyclerView.Adapter<WeeklyForecastRecyclerViewAdapter.ViewHolder>() {
    private val diffCallback = object : DiffUtil.ItemCallback<DailyItem>() {
        override fun areItemsTheSame(oldItem: DailyItem, newItem: DailyItem): Boolean {
            return oldItem.time == newItem.time
        }

        override fun areContentsTheSame(oldItem: DailyItem, newItem: DailyItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    inner class ViewHolder(val weeklyForecastRvItemBinding: WeeklyForecastRvItemBinding) :
        RecyclerView.ViewHolder(weeklyForecastRvItemBinding.root) {
        fun onBind(dailyItem: DailyItem) {
            weeklyForecastRvItemBinding.apply {
                val formattedDate = extractFormattedDate(dailyItem.time)
                weekDayTextView.text = formattedDate[0]
                weekDateTextView.text = formattedDate[1]

                val weatherTypeAnimationName =
                    WeatherType.fromWMO(dailyItem.weatherCode).animationAssetFileName
                try {
                    weekDayWeatherCodeAnimation.setAnimation(weatherTypeAnimationName)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d(TAG, "onBind: $weatherTypeAnimationName")
                }
                weekDayWeatherCodeAnimation.playAnimation()

                weekDayTemperatureMaxTextView.text =
                    String.format("%d", dailyItem.temperature2mMax.toInt())
                weekDayTemperatureMinTextView.text =
                    String.format("/%d °C", dailyItem.temperature2mMin.toInt())
            }
        }
    }

    private fun extractFormattedDate(dateString: String): List<String> {
        val dateTime = DateTime.parse(dateString)

        val dayOfWeek = dateTime.dayOfWeek().asText.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.ROOT
            ) else it.toString()
        }
        val dayOfMonth = dateTime.dayOfMonth().asText
        val monthName = dateTime.monthOfYear().asText

        return listOf(dayOfWeek, "$dayOfMonth-$monthName")
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): WeeklyForecastRecyclerViewAdapter.ViewHolder {
        return ViewHolder(
            WeeklyForecastRvItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dailyItem = differ.currentList[position]
        holder.onBind(dailyItem)

        holder.weeklyForecastRvItemBinding.weekDayItemContainer.setOnClickListener {
            onClick?.invoke(dailyItem)
        }
    }

    var onClick: ((DailyItem) -> Unit)? = null

    companion object {
        private const val TAG = "HourlyWeatherRVAdapter"
    }
}