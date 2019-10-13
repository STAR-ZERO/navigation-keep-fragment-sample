package com.star_zero.navigation_keep_fragment_sample

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.star_zero.navigation_keep_fragment_sample.databinding.FragmentNotificationsBinding
import com.star_zero.navigation_keep_fragment_sample.util.NavigationViewModel

class NotificationsFragment : Fragment() {

    private lateinit var binding: FragmentNotificationsBinding

    private val navigationViewModel: NavigationViewModel by lazy {
        ViewModelProviders.of(requireActivity()).get(NavigationViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("NotificationsFragment", "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        binding.handler = this
        return binding.root
    }

    fun navigateToDetail(view: View) {
        navigationViewModel.navigateToDetail()
    }
}
