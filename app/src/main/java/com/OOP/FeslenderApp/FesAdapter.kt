package com.OOP.FeslenderApp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.OOP.FeslenderApp.databinding.FesListBinding
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class FesAdapter(val _fes: ArrayList<FesData>) : RecyclerView.Adapter<FesAdapter.Holder>() {
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

    class Holder(private val binding: FesListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(fes: FesData) {
            val storage: FirebaseStorage = FirebaseStorage.getInstance()
            val storageRef: StorageReference = storage.reference
            storageRef.child(fes.poster).downloadUrl.addOnSuccessListener { uri ->
                Glide.with(binding.root).load(uri).into(binding.imageView)
            }.addOnFailureListener {
                Glide.with(binding.root).load(R.drawable.error_img).into(binding.imageView)
            }
            binding.txtTitle.text = fes.name
            binding.txtDate.text = fes.date
            binding.txtEnddate.text = fes.end_date
            binding.txtLocation.text = fes.location
        }

    }
}