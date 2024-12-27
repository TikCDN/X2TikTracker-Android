package org.anyrtc.tiktracker

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.graphics.drawable.Drawable
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import io.anyrtc.x2tiktracker.*
import org.anyrtc.tiktracker.databinding.ActivityMainBinding
import java.security.MessageDigest
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate


import java.security.interfaces.RSAPublicKey
import java.util.HexFormat



class MainActivity : AppCompatActivity() {

    private val x2HsrEngine by lazy { X2TikTrackerEngine(this,Config.appId) }

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.run {
            val playerUrl = intent.getStringExtra("playUrl").orEmpty()
            x2HsrEngine.registerListener(object :X2TikTrackerEventHandler {


                override fun onShareResult(code: TKT_CODE?) {
                    setTitle(code?.name)
                }

                override fun onLoadDataStats(stats: DataStats) {
                    binding.m = stats
                }

                override fun onPeerOff(peerId: String, peerData: String) {
                }

                override fun onPeerOn(peerId: String, peerData: String) {
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