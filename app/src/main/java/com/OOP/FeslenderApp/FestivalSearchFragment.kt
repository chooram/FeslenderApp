package com.OOP.FeslenderApp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.OOP.FeslenderApp.databinding.FragmentFestivalSearchBinding

class FestivalSearchFragment : Fragment() {
    var binding: FragmentFestivalSearchBinding? = null

    override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFestivalSearchBinding.inflate(inflater)

        return binding?.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.btnFestival?.setOnClickListener{
            val result = "festival"
            val bundle = bundleOf("click" to result)
            findNavController().navigate(R.id.action_festival_search_to_festival_list,bundle)
        }
        binding?.btnConcert?.setOnClickListener{
            val result = "concert"
            val bundle = bundleOf("click" to result)
            findNavController().navigate(R.id.action_festival_search_to_festival_list,bundle)
        }
        binding?.btnMatch?.setOnClickListener{
            val result = "match"
            val bundle = bundleOf("click" to result)
            findNavController().navigate(R.id.action_festival_search_to_festival_list,bundle)
        }
        binding?.btnSoon?.setOnClickListener{
            val result = "soon"
            val bundle = bundleOf("click" to result)
            findNavController().navigate(R.id.action_festival_search_to_festival_list,bundle)
        }
        binding?.btnArea?.setOnClickListener{
            findNavController().navigate(R.id.action_festival_search_to_festival_area_list)
        }
        binding?.btnSearch?.setOnClickListener{
            val result = binding?.searchText?.text
            Log.e("SearchText:",result.toString())
            val bundle = bundleOf("search" to result.toString())
            findNavController().navigate(R.id.action_festival_search_to_festival_list,bundle)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}