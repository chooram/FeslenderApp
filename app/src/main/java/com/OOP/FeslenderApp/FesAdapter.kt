package com.OOP.FeslenderApp

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.OOP.FeslenderApp.databinding.FesListBinding

class FesAdapter(val _fes: ArrayList<FesData>)
    :RecyclerView.Adapter<FesAdapter.Holder>() {
    var fes: ArrayList<FesData> = _fes

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = FesListBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(fes[position])
    }

    override fun getItemCount(): Int {
        return fes.size
    }

    class Holder(private val binding: FesListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(fes: FesData){
            binding.txtTitle.text = fes.name
            binding.txtDate.text = fes.date
            binding.txtEnddate.text = fes.end_date
            binding.txtLocation.text = fes.location
        }
    }
}