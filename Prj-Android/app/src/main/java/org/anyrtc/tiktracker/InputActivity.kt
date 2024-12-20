package org.anyrtc.tiktracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.doAfterTextChanged
import org.anyrtc.tiktracker.R
import org.anyrtc.tiktracker.databinding.ActivityInputBinding

class InputActivity : AppCompatActivity() {
    private val binding by lazy { ActivityInputBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.run {
            etUrl.doAfterTextChanged {
              btnOpen.isSelected = !it.isNullOrEmpty()
              btnOpen.isEnabled = !it.isNullOrEmpty()
            }
            etUrl.setText("https://gcalic.v.myalicdn.com/gc/zyqcdx01_1/index.m3u8?contentid=2820180516001&useLivePlayer=true")
            btnOpen.setOnClickListener {
                startActivity(Intent(this@InputActivity,MainActivity::class.java).apply {
                    putExtra("playUrl",etUrl.text.toString())
                    putExtra("player",if (rgPlayer.checkedRadioButtonId== R.id.rb_exo){"exo"}else{"ijk"})
                })
            }
        }
    }
}