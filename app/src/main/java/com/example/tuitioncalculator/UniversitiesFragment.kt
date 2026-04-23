package com.example.tuitioncalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tuitioncalculator.databinding.FragmentUniversitiesBinding
import com.example.tuitioncalculator.databinding.ItemUniversityBinding
import com.example.tuitioncalculator.domain.BillingModel
import com.example.tuitioncalculator.domain.UniversityBillingModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class UniversitiesFragment : Fragment() {

    private var _binding: FragmentUniversitiesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentUniversitiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = UniversityAdapter { uni ->
            showUniversityInfoDialog(uni)
        }
        binding.universitiesRecycler.adapter = adapter
        adapter.submitList(UniversityBillingModels.models)
    }

    private fun showUniversityInfoDialog(uni: BillingModel) {
        val message = StringBuilder()
        
        if (uni.programPrices.isNotEmpty()) {
            message.append("Per-Credit Prices:\n")
            for ((program, price) in uni.programPrices) {
                message.append("• $program: ৳${price.toLong()}\n")
            }
            message.append("\n")
        } else {
            message.append("Base Per-Credit Price: ৳${uni.perCreditPrice.toLong()}\n\n")
        }

        if (uni.otherFees.isNotEmpty()) {
            message.append("Other Information:\n${uni.otherFees}")
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(uni.universityName)
            .setMessage(message.toString().trim())
            .setPositiveButton("Close", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class UniversityAdapter(
    private val onInfoClick: (BillingModel) -> Unit
) : ListAdapter<BillingModel, UniversityAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(val binding: ItemUniversityBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUniversityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @android.annotation.SuppressLint("DiscouragedApi")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val uni = getItem(position)
        holder.binding.tvUniCode.text = uni.universityCode
        holder.binding.tvUniName.text = uni.universityName
        
        val context = holder.binding.root.context
        val logoName = "logo_" + uni.universityCode.lowercase()
        val resId = context.resources.getIdentifier(logoName, "drawable", context.packageName)
        
        if (resId != 0) {
            holder.binding.tvLogoText.visibility = android.view.View.GONE
            holder.binding.ivLogoImage.visibility = android.view.View.VISIBLE
            holder.binding.ivLogoImage.setImageResource(resId)
        } else {
            holder.binding.ivLogoImage.visibility = android.view.View.GONE
            holder.binding.tvLogoText.visibility = android.view.View.VISIBLE
            holder.binding.tvLogoText.text = uni.universityCode.firstOrNull()?.toString() ?: "U"
        }

        holder.binding.btnInfo.setOnClickListener {
            onInfoClick(uni)
        }
        
        // Also make the root clickable for better UX
        holder.binding.root.setOnClickListener {
            onInfoClick(uni)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<BillingModel>() {
        override fun areItemsTheSame(oldItem: BillingModel, newItem: BillingModel) = oldItem.universityCode == newItem.universityCode
        override fun areContentsTheSame(oldItem: BillingModel, newItem: BillingModel) = oldItem == newItem
    }
}
