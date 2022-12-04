package com.OOP.FeslenderApp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.OOP.FeslenderApp.databinding.EventInCalendarCellBinding


class Adapter02: RecyclerView.Adapter<Adapter02.ViewHolder02>() {

    private lateinit var events: ArrayList<String>
    private lateinit var colors: ArrayList<String>

    fun build(i: ArrayList<String>, j: ArrayList<String>): Adapter02{
        events = i
        colors = j
        return this
    }

    class ViewHolder02(private val binding: EventInCalendarCellBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind02(event: String, color: String) {
            with(binding) {
                txtEvent.text = event
                txtEvent.setBackgroundColor(Color.parseColor(color))
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder02 =
        ViewHolder02(EventInCalendarCellBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: ViewHolder02, position: Int) {

        holder.bind02(events[position], colors[position])

    }
    override fun getItemCount(): Int = colors.size
}


