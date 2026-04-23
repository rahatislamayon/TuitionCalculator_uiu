package com.example.tuitioncalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tuitioncalculator.databinding.FragmentCompareBinding
import com.example.tuitioncalculator.domain.BillingAlgorithm
import com.example.tuitioncalculator.domain.BillingModel
import com.example.tuitioncalculator.domain.UniversityBillingModels

class CompareFragment : Fragment() {

    private var _binding: FragmentCompareBinding? = null
    private val binding get() = _binding!!

    companion object {
        // Common department categories used for matching against each university's programPrices
        val DEPARTMENTS = listOf(
            "General (Base Rate)",
            "CSE (Computer Science)",
            "EEE (Electrical Engineering)",
            "Civil Engineering",
            "BBA (Business)",
            "Pharmacy",
            "LLB (Law)",
            "Architecture",
            "English",
            "Economics",
            "Data Science",
            "Textile Engineering",
            "Media / Journalism"
        )

        // Keywords for fuzzy-matching against programPrices map keys
        private val DEPT_KEYWORDS = mapOf(
            "General (Base Rate)" to emptyList<String>(),
            "CSE (Computer Science)" to listOf("cse", "computer", "cybersecurity"),
            "EEE (Electrical Engineering)" to listOf("eee", "electrical"),
            "Civil Engineering" to listOf("civil"),
            "BBA (Business)" to listOf("bba", "business"),
            "Pharmacy" to listOf("pharm"),
            "LLB (Law)" to listOf("llb", "law"),
            "Architecture" to listOf("arch"),
            "English" to listOf("english"),
            "Economics" to listOf("econ"),
            "Data Science" to listOf("data science", "ai", "artificial"),
            "Textile Engineering" to listOf("textile"),
            "Media / Journalism" to listOf("media", "jmc", "msj", "journalism")
        )

        /**
         * Finds the best matching per-credit price for a given department.
         * Falls back to the university's base perCreditPrice if no match is found.
         */
        fun findPriceForDepartment(uni: BillingModel, department: String): Double {
            if (department == "General (Base Rate)" || uni.programPrices.isEmpty()) {
                return uni.perCreditPrice
            }
            val keywords = DEPT_KEYWORDS[department] ?: return uni.perCreditPrice
            for ((programKey, price) in uni.programPrices) {
                val keyLower = programKey.lowercase()
                for (keyword in keywords) {
                    if (keyLower.contains(keyword)) {
                        return price
                    }
                }
            }
            // No match found — fall back to base rate
            return uni.perCreditPrice
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCompareBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val universities = UniversityBillingModels.models
        val uniNames = universities.map { it.universityName }
        
        val uniAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, uniNames)
        binding.spinnerUniA.adapter = uniAdapter
        binding.spinnerUniB.adapter = uniAdapter

        // Department spinner
        val deptAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, DEPARTMENTS)
        binding.spinnerDepartment.adapter = deptAdapter
        
        // Select second item for B if available
        if (uniNames.size > 1) {
            binding.spinnerUniB.setSelection(1)
        }

        binding.btnCompare.setOnClickListener { v ->
            // Simple button click bounce animation
            v.animate()
                .scaleX(0.95f)
                .scaleY(0.95f)
                .setDuration(100)
                .withEndAction {
                    v.animate().scaleX(1f).scaleY(1f).setDuration(100).start()
                }
                .start()

            val totalCreditsText = binding.etTotalCredits.text.toString()
            val scholarshipText = binding.etScholarship.text.toString()
            
            val totalCredits = totalCreditsText.toDoubleOrNull() ?: 130.0
            val scholarship = (scholarshipText.toDoubleOrNull() ?: 0.0) / 100.0
            
            val uniA = universities[binding.spinnerUniA.selectedItemPosition]
            val uniB = universities[binding.spinnerUniB.selectedItemPosition]
            val selectedDept = DEPARTMENTS[binding.spinnerDepartment.selectedItemPosition]

            // Get department-specific per-credit prices
            val priceA = findPriceForDepartment(uniA, selectedDept)
            val priceB = findPriceForDepartment(uniB, selectedDept)
            
            val totalFeeA = totalCredits * priceA
            val totalFeeB = totalCredits * priceB
            
            val breakdownA = BillingAlgorithm.calculateInstallments(totalFeeA, scholarship, uniA)
            val breakdownB = BillingAlgorithm.calculateInstallments(totalFeeB, scholarship, uniB)
            
            val finalTotalA = breakdownA.sum()
            val finalTotalB = breakdownB.sum()

            val deptLabel = if (selectedDept == "General (Base Rate)") "Base" else selectedDept.substringBefore(" (")
            
            binding.tvResUniA.text = "${uniA.universityCode}\n$deptLabel: ৳${priceA.toLong()}/cr"
            binding.tvResUniB.text = "${uniB.universityCode}\n$deptLabel: ৳${priceB.toLong()}/cr"
            
            val comparisonText = if (finalTotalA < finalTotalB) {
                "${uniA.universityCode} is ৳ ${String.format(java.util.Locale.US, "%.2f", finalTotalB - finalTotalA)} cheaper."
            } else if (finalTotalB < finalTotalA) {
                "${uniB.universityCode} is ৳ ${String.format(java.util.Locale.US, "%.2f", finalTotalA - finalTotalB)} cheaper."
            } else {
                "Both cost exactly the same."
            }
            
            binding.tvBreakdownA.text = String.format(java.util.Locale.US, "Total Tuition: ৳ %.2f\n\n%s", totalFeeA, buildBreakdownText(breakdownA))
            binding.tvBreakdownB.text = String.format(java.util.Locale.US, "Total Tuition: ৳ %.2f\n\n%s\n\n--- \nResult: %s", totalFeeB, buildBreakdownText(breakdownB), comparisonText)
            
            // Animate results container
            if (binding.resultsContainer.visibility == View.GONE) {
                binding.resultsContainer.visibility = View.VISIBLE
                binding.resultsContainer.alpha = 0f
                binding.resultsContainer.translationY = 50f
                binding.resultsContainer.animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setDuration(400)
                    .start()
            } else {
                // Already visible, flash it to show update
                binding.resultsContainer.alpha = 0.5f
                binding.resultsContainer.animate().alpha(1f).setDuration(300).start()
            }
        }
    }
    
    private fun buildBreakdownText(installments: List<Double>): String {
        val sb = StringBuilder()
        for ((index, amount) in installments.withIndex()) {
            sb.append(String.format(java.util.Locale.US, "Installment %d:\n৳ %.2f\n\n", index + 1, amount))
        }
        sb.append(String.format(java.util.Locale.US, "Total:\n৳ %.2f", installments.sum()))
        return sb.toString().trim()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
