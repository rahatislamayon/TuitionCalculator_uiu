package com.example.tuitioncalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tuitioncalculator.data.AppDatabase
import com.example.tuitioncalculator.databinding.FragmentPaymentsBinding
import com.example.tuitioncalculator.viewmodel.TuitionViewModel
import com.example.tuitioncalculator.viewmodel.TuitionViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class PaymentsFragment : Fragment() {
    private var _binding: FragmentPaymentsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TuitionViewModel
    private lateinit var adapter: InstallmentAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPaymentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val dao = AppDatabase.getDatabase(requireContext()).tuitionDao()
        val factory = TuitionViewModelFactory(dao)
        viewModel = ViewModelProvider(requireActivity(), factory)[TuitionViewModel::class.java]

        adapter = InstallmentAdapter { installment ->
            viewModel.toggleInstallmentPaid(installment)
        }
        
        binding.installmentsRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.installmentsRecycler.adapter = adapter
        
        binding.btnGenerateQuote.setOnClickListener {
            generateOfficialQuote()
        }
        
        observeData()
    }

    private fun generateOfficialQuote() {
        val profile = viewModel.latestProfile.value
        val installments = viewModel.latestInstallments.value

        if (profile == null || installments.isEmpty()) {
            android.widget.Toast.makeText(requireContext(), "No payment data available to generate quote.", android.widget.Toast.LENGTH_SHORT).show()
            return
        }

        val sb = StringBuilder()
        sb.append("🎓 OFFICIAL TUITION QUOTE\n")
        sb.append("=========================\n")
        sb.append("University: ${profile.universityCode}\n")
        sb.append("Semester: ${profile.semesterName}\n\n")
        
        sb.append(String.format(java.util.Locale.US, "Base Tuition: ৳ %.2f\n", profile.totalTuition))
        val scholarshipInt = (profile.scholarshipPercent * 100).roundToInt()
        if (scholarshipInt > 0) {
            sb.append("Scholarship Applied: $scholarshipInt%\n")
        }
        sb.append("-------------------------\n")
        sb.append(String.format(java.util.Locale.US, "FINAL PAYABLE: ৳ %.2f\n\n", profile.finalPayable))
        
        sb.append("INSTALLMENT BREAKDOWN:\n")
        installments.forEach {
            val status = if (it.isPaid) "[PAID]" else "[PENDING]"
            sb.append(String.format(java.util.Locale.US, "- Installment %d: ৳ %.2f %s\n", it.installmentNumber, it.amountDue, status))
        }
        
        sb.append("\nGenerated via Tuition Calculator App")

        val intent = android.content.Intent(android.content.Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(android.content.Intent.EXTRA_SUBJECT, "Tuition Quote - ${profile.universityCode}")
            putExtra(android.content.Intent.EXTRA_TEXT, sb.toString())
        }
        startActivity(android.content.Intent.createChooser(intent, "Share Official Quote"))
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.latestInstallments.collectLatest { installments ->
                adapter.submitList(installments)
                
                if (installments.isNotEmpty()) {
                    binding.paymentsContent.visibility = View.VISIBLE
                    binding.emptyState.visibility = View.GONE
                    val totalPayable = installments.sumOf { it.amountDue }
                    binding.finalPayableAmount.text = String.format(java.util.Locale.US, "৳ %.2f", totalPayable)
                } else {
                    binding.paymentsContent.visibility = View.GONE
                    binding.emptyState.visibility = View.VISIBLE
                }
            }
        }
        
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.latestProfile.collectLatest { profile ->
                if (profile != null) {
                    val scholarshipInt = (profile.scholarshipPercent * 100).roundToInt()
                    if (scholarshipInt > 0) {
                        binding.scholarshipApplied.visibility = View.VISIBLE
                        binding.scholarshipApplied.text = "✨ $scholarshipInt% Merit Scholarship Applied"
                    } else {
                        binding.scholarshipApplied.visibility = View.GONE
                    }
                    binding.baseFeeAmount.text = String.format(java.util.Locale.US, "Base: ৳ %.2f", profile.totalTuition)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

