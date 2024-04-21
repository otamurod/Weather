package uz.otamurod.presentation.ui.weekly_forecast.adapter

import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.joda.time.DateTime
import uz.otamurod.domain.model.DailyItem
import uz.otamurod.presentation.R
import uz.otamurod.presentation.databinding.DayRvItemBinding
import uz.otamurod.presentation.utils.weather.WeatherType
import java.util.*

class WeekDaysRecyclerViewAdapter :
    RecyclerView.Adapter<WeekDaysRecyclerViewAdapter.ViewHolder>() {

    private var selectedDayPosition = -1

    private val diffCallback = object : DiffUtil.ItemCallback<DailyItem>() {
        override fun areItemsTheSame(oldItem: DailyItem, newItem: DailyItem): Boolean {
            return oldItem.time == newItem.time
        }

        override fun areContentsTheSame(oldItem: DailyItem, newItem: DailyItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    inner class ViewHolder(val dayRvItemBinding: DayRvItemBinding) :
        RecyclerView.ViewHolder(dayRvItemBinding.root) {
        fun onBind(dailyItem: DailyItem, isSelected: Boolean) {
            dayRvItemBinding.apply {
                val formattedDate = extractFormattedDate(dailyItem.time)
                dayOfMonthTextView.text = formattedDate[0]
                dayOfWeekShortTextView.text = formattedDate[1]

                val weatherTypeAnimationName =
                    WeatherType.fromWMO(dailyItem.weatherCode).animationAssetFileName
                try {
                    dailyWeatherCodeAnimation.setAnimation(weatherTypeAnimationName)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d(TAG, "onBind: $weatherTypeAnimationName")
                }
                dailyWeatherCodeAnimation.playAnimation()

                if (isSelected) {
                    weekDayItemContainer.background =
                        ColorDrawable(itemView.context.resources.getColor(R.color.hourly_weather_rv_item_bg_color))
                } else {
                    weekDayItemContainer.setBackgroundResource(R.drawable.current_weather_probabilities_bg)
                }
            }
        }
    }

    private fun extractFormattedDate(dateString: String): List<String> {
        val dateTime = DateTime.parse(dateString)

        val dayOfMonth = dateTime.dayOfMonth().asText
        val dayOfWeek = dateTime.dayOfWeek().asText.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.ROOT
            ) else it.toString()
        }.substring(0, 3)

        return listOf(dayOfMonth, dayOfWeek)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): WeekDaysRecyclerViewAdapter.ViewHolder {
        return ViewHolder(
            DayRvItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dailyItem = differ.currentList[position]
        holder.onBind(dailyItem, selectedDayPosition == position)

        holder.dayRvItemBinding.weekDayItemContainer.setOnClickListener {
            if (selectedDayPosition >= 0) {
                notifyItemChanged(selectedDayPosition)
            }
            selectedDayPosition = holder.adapterPosition
            notifyItemChanged(selectedDayPosition)
            onClick?.invoke(dailyItem)
        }
    }

    var onClick: ((DailyItem) -> Unit)? = null

    companion object {
        private const val TAG = "WeekDaysRVAdapter"
    }
}