package com.example.tuitioncalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.tuitioncalculator.data.AppDatabase
import com.example.tuitioncalculator.databinding.FragmentCalculatorBinding
import com.example.tuitioncalculator.domain.UniversityBillingModels
import com.example.tuitioncalculator.viewmodel.TuitionViewModel
import com.example.tuitioncalculator.viewmodel.TuitionViewModelFactory

class CalculatorFragment : Fragment() {
    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TuitionViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val dao = AppDatabase.getDatabase(requireContext()).tuitionDao()
        val factory = TuitionViewModelFactory(dao)
        viewModel = ViewModelProvider(requireActivity(), factory)[TuitionViewModel::class.java]
        
        val uiAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            UniversityBillingModels.models.map { it.universityCode }
        )
        binding.universitySpinner.adapter = uiAdapter

        binding.calculateBtn.setOnClickListener {
            val totalFeeText = binding.totalFee.text.toString()
            val scholarshipText = binding.scholarship.text.toString()

            if (totalFeeText.isEmpty()) {
                binding.totalFee.error = "Total fee is required"
                return@setOnClickListener
            }

            val totalFee = totalFeeText.toDoubleOrNull() ?: 0.0
            val scholarshipStr = scholarshipText.ifEmpty { "0" }
            val rawScholarship = scholarshipStr.toDoubleOrNull() ?: 0.0

            if (rawScholarship !in 0.0..100.0) {
                binding.scholarship.error = "Must be between 0 and 100"
                Toast.makeText(requireContext(), "Scholarship must be between 0% and 100%", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val scholarshipPercent = rawScholarship / 100.0
            val selectedUniversityCode = binding.universitySpinner.selectedItem.toString()

            viewModel.calculateAndSave(selectedUniversityCode, "Current Semester", totalFee, scholarshipPercent)
            
            Toast.makeText(requireContext(), "Calculation Generated!", Toast.LENGTH_SHORT).show()
            
            // Navigate to Payments tab after calculation with smooth transition
            findNavController().navigate(R.id.action_calculator_to_payments)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

