package com.example.tuitioncalculator

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.tuitioncalculator.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPrefs: SharedPreferences

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            try {
                // Copy image to app local storage to persist without requiring complex permissions
                val inputStream = requireContext().contentResolver.openInputStream(uri)
                val file = java.io.File(requireContext().filesDir, "profile_avatar.jpg")
                val outputStream = java.io.FileOutputStream(file)
                inputStream?.copyTo(outputStream)
                inputStream?.close()
                outputStream.close()
                
                val localUri = Uri.fromFile(file).toString()
                sharedPrefs.edit().putString("USER_AVATAR_URI", localUri).apply()
                binding.ivProfileImage.setImageURI(Uri.parse(localUri))
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "Failed to save image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        sharedPrefs = requireContext().getSharedPreferences("UserProfilePrefs", Context.MODE_PRIVATE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        loadProfileStats()

        binding.ivProfileImage.setOnClickListener {
            pickMedia.launch(androidx.activity.result.PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        
        binding.ivEditProfile.setOnClickListener {
            showEditDialog()
        }
    }
    
    private fun loadProfileStats() {
        val name = sharedPrefs.getString("USER_NAME", "John Doe")
        val studentId = sharedPrefs.getString("USER_STUDENT_ID", "2012020202")
        val dept = sharedPrefs.getString("USER_DEPARTMENT", "Computer Science & Eng.")
        val avatarUri = sharedPrefs.getString("USER_AVATAR_URI", null)
        
        binding.tvProfileName.text = name
        binding.tvProfileId.text = "ID: $studentId"
        binding.tvProfileDept.text = dept
        
        if (avatarUri != null) {
            try {
                binding.ivProfileImage.setImageURI(Uri.parse(avatarUri))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    private fun showEditDialog() {
        val context = requireContext()
        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 40, 50, 10)
        }

        val nameInput = EditText(context).apply {
            hint = "Full Name"
            setText(sharedPrefs.getString("USER_NAME", "John Doe"))
        }
        val idInput = EditText(context).apply {
            hint = "Student ID"
            setText(sharedPrefs.getString("USER_STUDENT_ID", "2012020202"))
        }
        val deptInput = EditText(context).apply {
            hint = "Department"
            setText(sharedPrefs.getString("USER_DEPARTMENT", "Computer Science & Eng."))
        }

        layout.addView(nameInput)
        layout.addView(idInput)
        layout.addView(deptInput)

        AlertDialog.Builder(context)
            .setTitle("Edit Profile")
            .setView(layout)
            .setPositiveButton("Save") { _, _ ->
                sharedPrefs.edit().apply {
                    putString("USER_NAME", nameInput.text.toString().trim())
                    putString("USER_STUDENT_ID", idInput.text.toString().trim())
                    putString("USER_DEPARTMENT", deptInput.text.toString().trim())
                }.apply()
                loadProfileStats()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
