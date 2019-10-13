package com.star_zero.navigation_keep_fragment_sample

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.star_zero.navigation_keep_fragment_sample.databinding.FragmentDetailBinding
import com.star_zero.navigation_keep_fragment_sample.util.NavigationViewModel

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    private val navigationViewModel: NavigationViewModel by lazy {
        ViewModelProviders.of(requireActivity()).get(NavigationViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("DetailFragment", "onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationViewModel.isShowDetail = false
    }
}