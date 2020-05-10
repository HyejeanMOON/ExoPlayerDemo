package com.hyejeanmoon.exoplayerdemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.MappingTrackSelector
import com.google.android.exoplayer2.ui.TrackSelectionView.TrackSelectionListener
import com.hyejeanmoon.exoplayerdemo.databinding.FragmentTrackSelectionBinding

class TrackSelectionViewFragment : Fragment(), TrackSelectionListener {

    init {
        // Retain instance across activity re-creation to prevent losing access to init data.
        retainInstance = true
    }

    private lateinit var binding: FragmentTrackSelectionBinding
    private lateinit var mappedTrackInfo: MappingTrackSelector.MappedTrackInfo
    private var rendererIndex = 0
    private var allowAdaptiveSelections = false
    private var allowMultipleOverrides = false
    private var isDisabled = false
    private lateinit var overrides: List<DefaultTrackSelector.SelectionOverride>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_track_selection, container, true)

        // Sets whether an option is available for disabling the renderer.
        binding.exoTrackSelectionView.setShowDisableOption(true)

        // Sets whether tracks from multiple track groups can be selected.
        binding.exoTrackSelectionView.setAllowMultipleOverrides(allowMultipleOverrides)

        // Sets whether adaptive selections (consisting of more than one track) can be made using this selection view.
        binding.exoTrackSelectionView.setAllowAdaptiveSelections(allowAdaptiveSelections)

        // Initialize the view to select tracks for a specified renderer.
        binding.exoTrackSelectionView.init(
            mappedTrackInfo,
            rendererIndex,
            isDisabled,
            overrides,
            this
        )

        return binding.root
    }

    fun initParam(
        mappedTrackInfo: MappingTrackSelector.MappedTrackInfo,
        rendererIndex: Int,
        allowAdaptiveSelections: Boolean,
        allowMultipleOverrides: Boolean,
        isDisabled: Boolean,
        overrides: DefaultTrackSelector.SelectionOverride?
    ) {
        this.mappedTrackInfo = mappedTrackInfo
        this.rendererIndex = rendererIndex
        this.isDisabled = isDisabled
        this.overrides = if (overrides == null) emptyList() else listOf(overrides)
        this.allowAdaptiveSelections = allowAdaptiveSelections
        this.allowMultipleOverrides = allowMultipleOverrides
    }

    override fun onTrackSelectionChanged(
        isDisabled: Boolean,
        overrides: MutableList<DefaultTrackSelector.SelectionOverride>
    ) {
        this.isDisabled = isDisabled
        this.overrides = overrides
    }


}