package com.example.tuitioncalculator

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.example.tuitioncalculator.databinding.ActivityIntroBinding

@Suppress("DEPRECATION")
class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding
    private val handler = Handler(Looper.getMainLooper())
    private var splashRunnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Hide ActionBar if present
        supportActionBar?.hide()
        
        // Animate the splash progress bar
        val progressAnimator = ObjectAnimator.ofInt(binding.pbSplash, "progress", 0, 100)
        progressAnimator.duration = 2000
        progressAnimator.interpolator = DecelerateInterpolator()
        progressAnimator.start()
        
        // Setup Onboarding Buttons
        binding.btnNext.setOnClickListener { finishIntro() }
        binding.btnSkip.setOnClickListener { finishIntro() }
        
        val prefs = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val hasSeenIntro = prefs.getBoolean("has_seen_intro", false)
        
        splashRunnable = Runnable {
            if (hasSeenIntro) {
                // If they've seen the intro, go straight to Main
                startActivity(Intent(this, MainActivity::class.java))
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            } else {
                // Otherwise, fade out splash to reveal onboarding
                binding.layoutSplash.animate()
                    .alpha(0f)
                    .setDuration(500)
                    .withEndAction {
                        binding.layoutSplash.visibility = View.GONE
                        binding.layoutOnboarding.animate().alpha(1f).setDuration(500).start()
                    }
                    .start()
            }
        }
        
        splashRunnable?.let { handler.postDelayed(it, 2200) }
    }
    
    private fun finishIntro() {
        val prefs = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("has_seen_intro", true).apply()
        
        startActivity(Intent(this, MainActivity::class.java))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        splashRunnable?.let { handler.removeCallbacks(it) }
    }
}
