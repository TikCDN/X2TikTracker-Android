package org.anyrtc.tiktracker

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.media3.common.MediaItem
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import io.anyrtc.x2tiktracker.*
import org.anyrtc.tiktracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val x2HsrEngine by lazy { X2TikTrackerEngine(this,Config.appId) }

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.run {
            val playerUrl = intent.getStringExtra("playUrl").orEmpty()
            x2HsrEngine.registerListener(object :X2HlsShareEngineEventHandler{


                override fun onShareResult(code: TKT_CODE?) {
                }

                override fun onLoadDataStats(stats: DataStats) {
                    binding.m = stats
                }

                override fun onRenewTokenResult(token: String, errorCode: RenewTokenErrCode?) {
                }

                override fun onTokenWillExpire() {
                }

                override fun onPeerOff(peerId: String, peerData: String) {
                }

                override fun onPeerOn(peerId: String, peerData: String) {
                }

                override fun onTokenExpired() {
                }

            })
            x2HsrEngine.startPlay(playerUrl,true)
            val exUrl = x2HsrEngine.getExUrl()
            val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
            val hlsMediaSource =
                HlsMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(Uri.parse(exUrl)))
            val player = ExoPlayer.Builder(this@MainActivity).build()
            playerView.player = player
            player.setMediaSource(hlsMediaSource)
            player.prepare()
            player.play()

        }

    }

    override fun onBackPressed() {
        binding.playerView.player?.release()
        x2HsrEngine.stopPlay()
        x2HsrEngine.release()
        finish()
    }
}