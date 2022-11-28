package com.OOP.FeslenderApp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.OOP.FeslenderApp.databinding.FesListBinding
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.type.DateTime
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class FesAdapter(val fes: LiveData<ArrayList<FesData>>) : RecyclerView.Adapter<FesAdapter.Holder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = FesListBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(fes.value?.getOrNull(position))
    }

    override fun getItemCount(): Int {
        return fes.value?.size ?: 0
    }

    class Holder(private val binding: FesListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(fes: FesData?) {
            val storage: FirebaseStorage = FirebaseStorage.getInstance()
            val storageRef: StorageReference = storage.reference
            fes?.let {
                storageRef.child(it.poster).downloadUrl.addOnSuccessListener { uri ->
                    Glide.with(binding.root).load(uri).into(binding.imageView)
                }.addOnFailureListener {
                    Glide.with(binding.root).load(R.drawable.error_img).into(binding.imageView)
                }
                binding.txtTitle.text = it.name
                binding.txtDate.text = it.date
                binding.txtEnddate.text = it.end_date
                binding.txtLocation.text = it.location
            }
        }

    }
}