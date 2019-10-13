package com.star_zero.navigation_keep_fragment_sample

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.star_zero.navigation_keep_fragment_sample.databinding.FragmentHomeBinding
import com.star_zero.navigation_keep_fragment_sample.util.NavigationViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val navigationViewModel: NavigationViewModel by lazy {
        ViewModelProviders.of(requireActivity()).get(NavigationViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("HomeFragment", "onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.handler = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (navigationViewModel.isShowDetail) {
            findNavController().navigate(R.id.action_home_to_detail)
        }
    }

    fun navigateToDetail(view: View) {
        findNavController().navigate(R.id.action_home_to_detail)
    }
}