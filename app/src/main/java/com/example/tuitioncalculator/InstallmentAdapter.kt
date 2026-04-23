this is the data of 2026 Bangladeshi University Per Credit Prices here is the Google docs link:
https://docs.google.com/document/d/1VTON_-wu1BQ2XzD0LGzbGTFSOvoMc99lEMgNm26pRR4/edit?usp=sharing

update all data and when user clicks this button it shows all Information About the University. 

item_university.xml
 
this is the data of 2026 Bangladeshi University Per Credit Prices here is the Google docs link:
https://docs.google.com/document/d/1VTON_-wu1BQ2XzD0LGzbGTFSOvoMc99lEMgNm26pRR4/edit?usp=sharing

update all data and when user clicks this button it shows all Information About the University. 

item_university.xml
 
package com.example.tuitioncalculator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tuitioncalculator.data.Installment
import com.example.tuitioncalculator.databinding.ItemInstallmentBinding

class InstallmentAdapter(
    private val onTogglePaid: (Installment) -> Unit
) : ListAdapter<Installment, InstallmentAdapter.ViewHolder>(InstallmentDiffCallback()) {

    class ViewHolder(val binding: ItemInstallmentBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemInstallmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val installment = getItem(position)
        // Show full simple text like "Installment 1"
        holder.binding.installmentName.text = "Installment ${installment.installmentNumber}"
        holder.binding.installmentAmount.text = String.format(java.util.Locale.US, "৳ %.2f", installment.amountDue)

        // Prevent trigger loop
        holder.binding.isPaidCheckbox.setOnCheckedChangeListener(null)
        holder.binding.isPaidCheckbox.isChecked = installment.isPaid
        // Show tick mark when paid
        holder.binding.isPaidCheckbox.text = if (installment.isPaid) "✓ Paid" else "Paid"

        holder.binding.isPaidCheckbox.setOnCheckedChangeListener { _, isChecked ->
            holder.binding.isPaidCheckbox.text = if (isChecked) "✓ Paid" else "Paid"
            onTogglePaid(installment)
        }
    }

    class InstallmentDiffCallback : DiffUtil.ItemCallback<Installment>() {
        override fun areItemsTheSame(oldItem: Installment, newItem: Installment): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Installment, newItem: Installment): Boolean {
            return oldItem == newItem
        }
    }
}

