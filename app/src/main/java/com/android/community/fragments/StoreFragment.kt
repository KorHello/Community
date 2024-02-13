package com.android.community.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.android.community.R
import com.android.community.databinding.FragmentStoreBinding

class StoreFragment : Fragment() {

    private lateinit var binding : FragmentStoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_store, container, false)

        binding.homeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_storeFragment_to_homeFragment)
        }

        binding.talkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_storeFragment_to_homeFragment)
        }

        binding.bookmarkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_storeFragment_to_homeFragment)
        }

        binding.storeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_storeFragment_to_homeFragment)
        }

        return binding.root
    }


}