package com.example.tuitioncalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tuitioncalculator.databinding.FragmentHomeBinding

import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val bottomNav = requireActivity().findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottom_navigation)
        
        binding.btnCalculate.setOnClickListener {
            bottomNav.selectedItemId = R.id.calculatorFragment
        }
        
        binding.tvGoToHistory.setOnClickListener {
            bottomNav.selectedItemId = R.id.paymentsFragment
        }
        
        binding.btnLearnMore.setOnClickListener {
            android.widget.Toast.makeText(context, "Coming Soon...", android.widget.Toast.LENGTH_SHORT).show()
        }
        
        binding.tvViewAllUniversities.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_universities)
        }
        
        binding.btnStartComparing.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_compare)
        }
        
        loadFeaturedLogos()
    }
    
    @android.annotation.SuppressLint("DiscouragedApi")
    private fun loadFeaturedLogos() {
        val context = requireContext()
        val nsuId = context.resources.getIdentifier("logo_nsu", "drawable", context.packageName)
        if (nsuId != 0) {
            binding.ivNsuLogo.visibility = android.view.View.VISIBLE
            binding.tvNsuFallback.visibility = android.view.View.GONE
            binding.ivNsuLogo.setImageResource(nsuId)
            binding.ivNsuLogo.imageTintList = null
            binding.ivNsuLogo.setPadding(0, 0, 0, 0)
        }
        
        val uiuId = context.resources.getIdentifier("logo_uiu", "drawable", context.packageName)
        if (uiuId != 0) {
            binding.ivUiuLogo.visibility = android.view.View.VISIBLE
            binding.tvUiuFallback.visibility = android.view.View.GONE
            binding.ivUiuLogo.setImageResource(uiuId)
            binding.ivUiuLogo.imageTintList = null
            binding.ivUiuLogo.setPadding(0, 0, 0, 0)
        }
        
        val aiubId = context.resources.getIdentifier("logo_aiub", "drawable", context.packageName)
        if (aiubId != 0) {
            binding.ivAiubLogo.visibility = android.view.View.VISIBLE
            binding.tvAiubFallback.visibility = android.view.View.GONE
            binding.ivAiubLogo.setImageResource(aiubId)
            binding.ivAiubLogo.imageTintList = null
            binding.ivAiubLogo.setPadding(0, 0, 0, 0)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
