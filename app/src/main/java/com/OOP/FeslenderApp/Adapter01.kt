package com.OOP.FeslenderApp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.OOP.FeslenderApp.databinding.CalendarCellBinding

class Adapter01: RecyclerView.Adapter<Adapter01.ViewHolder>() {

    private lateinit var items: ArrayList<DateEvents>

    fun build(i:ArrayList<DateEvents>):Adapter01{
        items = i
        return this
    }

    class ViewHolder(private val binding: CalendarCellBinding, val context: Context):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DateEvents) {
            with(binding)
            {
                txtDay.text = item.date

                rvEventInCalendar.apply {
                    adapter = Adapter02().build(ArrayList(item.events),ArrayList(item.colors))
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                }
            }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            CalendarCellBinding.inflate(LayoutInflater.from(parent.context),parent,false),
            parent.context
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size


}