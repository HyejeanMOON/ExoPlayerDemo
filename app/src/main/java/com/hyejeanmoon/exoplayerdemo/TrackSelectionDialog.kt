package com.hyejeanmoon.exoplayerdemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.hyejeanmoon.exoplayerdemo.databinding.FragmentTrackSelectionDialogBinding

class TrackSelectionDialog : DialogFragment() {

    lateinit var binding: FragmentTrackSelectionDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_track_selection_dialog,
            container,
            true
        )

        return binding.root
    }

}