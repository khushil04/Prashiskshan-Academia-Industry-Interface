package com.prashiskshan.presentation.common

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.prashiskshan.databinding.ActivityProfileManagementBinding
import com.prashiskshan.presentation.faculty.FacultyDashboardActivity
import com.prashiskshan.presentation.industry.IndustryDashboardActivity
import com.prashiskshan.presentation.student.ProfileActivity

class ProfileManagementActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileManagementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileManagementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Profile Management"

        binding.btnStudent.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        binding.btnFaculty.setOnClickListener {
            startActivity(Intent(this, FacultyDashboardActivity::class.java))
        }

        binding.btnCompany.setOnClickListener {
            startActivity(Intent(this, IndustryDashboardActivity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, ProfileManagementActivity::class.java)
    }
}


