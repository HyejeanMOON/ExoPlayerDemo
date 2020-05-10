package com.hyejeanmoon.exoplayerdemo

import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.PlaybackPreparer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerControlView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.hyejeanmoon.exoplayerdemo.databinding.ActivityVideoBinding

class VideoActivity : AppCompatActivity(), PlayerControlView.VisibilityListener, PlaybackPreparer {

    lateinit var binding: ActivityVideoBinding
    lateinit var path: Uri
    private var isDialogShowing = false

    private var simpleExoPlayer: SimpleExoPlayer? = null
    private var videoSource: MediaSource? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_video)

        // set the activity to fullscreen and hide it's actionbar
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar?.hide()

        path = Uri.parse(intent.getStringExtra(SELECTED_VIDEO_PATH))

        binding.btnTrackSelection.setOnClickListener {
            TrackSelectionDialog().show(supportFragmentManager, null)
        }

        initPlayer()
        playVideo()
    }

    private fun initPlayer() {

        // build a player
        simpleExoPlayer = SimpleExoPlayer.Builder(this).build()

        // Attaching the player to a view
        binding.videoPlayer.player = simpleExoPlayer

    }

    private fun playVideo() {

        // Produces DataSource instances through which media data is loaded.
        val dataSourceFactory =
            DefaultDataSourceFactory(this, Util.getUserAgent(this, "ExoPlayerDemo"))

        // 1. DASH -> DashMediaSource
        // 2. SmoothStreaming -> SsMediaSource
        // 3. HLS -> HlsMediaSource
        // 4. regular media files -> ProgressiveMediaSource
        videoSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(path)

        // Prepare the player with the source.
        videoSource?.also {
            simpleExoPlayer?.prepare(it)
        }
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun onPause() {
        super.onPause()
        releasePlayer()
    }

    // when activity is in onStop or onPause or newIntent of states, the player must to be released.
    private fun releasePlayer() {
        if (simpleExoPlayer != null) {
            (simpleExoPlayer as ExoPlayer).release()
            simpleExoPlayer = null
            videoSource = null
        }

    }

    override fun onResume() {
        super.onResume()
        
        initPlayer()
        playVideo()
    }

    override fun onVisibilityChange(visibility: Int) {
        binding.btnTrackSelection.visibility = visibility
    }

    override fun preparePlayback() {
        simpleExoPlayer?.retry()
    }

    companion object {
        const val SELECTED_VIDEO_PATH = "VIDEO_PATH"
    }

}