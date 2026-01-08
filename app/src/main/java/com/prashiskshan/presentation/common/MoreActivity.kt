package com.prashiskshan.presentation.common

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.prashiskshan.R
import com.prashiskshan.databinding.ActivityMoreBinding
import com.prashiskshan.presentation.common.more.FeedbackFragment
import com.prashiskshan.presentation.common.more.HelpSupportFragment
import com.prashiskshan.presentation.common.more.SettingsFragment

class MoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoreBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.more)

        toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener { item ->
            onNavigationItemSelected(item)
            true
        }

        if (savedInstanceState == null) {
            openSettings()
            binding.navView.setCheckedItem(R.id.nav_settings)
        }
    }

    private fun onNavigationItemSelected(item: MenuItem) {
        when (item.itemId) {
            R.id.nav_settings -> openSettings()
            R.id.nav_profile_management -> startActivity(Intent(this, ProfileManagementActivity::class.java))
            R.id.nav_help -> openHelp()
            R.id.nav_feedback -> openFeedback()
            R.id.nav_about -> startActivity(Intent(this, AboutActivity::class.java))
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }

    private fun openSettings() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, SettingsFragment())
            .commit()
        supportActionBar?.title = getString(R.string.title_settings)
    }

    private fun openHelp() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, HelpSupportFragment())
            .commit()
        supportActionBar?.title = getString(R.string.title_help_support)
    }

    private fun openFeedback() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, FeedbackFragment())
            .commit()
        supportActionBar?.title = getString(R.string.title_feedback)
    }
}


