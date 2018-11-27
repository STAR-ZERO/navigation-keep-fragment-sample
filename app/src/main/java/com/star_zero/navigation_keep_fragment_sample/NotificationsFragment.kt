package com.star_zero.navigation_keep_fragment_sample

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.star_zero.navigation_keep_fragment_sample.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private lateinit var binding: FragmentNotificationsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("NotificationsFragment", "onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }
}
