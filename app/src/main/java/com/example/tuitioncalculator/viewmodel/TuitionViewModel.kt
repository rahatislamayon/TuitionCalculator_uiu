package com.example.tuitioncalculator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tuitioncalculator.data.Installment
import com.example.tuitioncalculator.data.SemesterProfile
import com.example.tuitioncalculator.data.TuitionDao
import com.example.tuitioncalculator.domain.BillingAlgorithm
import com.example.tuitioncalculator.domain.UniversityBillingModels
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class TuitionViewModel(private val dao: TuitionDao) : ViewModel() {

    val allProfiles: StateFlow<List<SemesterProfile>> = dao.getAllProfiles()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val latestProfile: StateFlow<SemesterProfile?> = dao.getAllProfiles()
        .map { profiles -> profiles.firstOrNull() }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    /** Flat-mapped flow: automatically emits installments for the latest profile. */
    val latestInstallments: StateFlow<List<Installment>> = dao.getAllProfiles()
        .map { profiles -> profiles.firstOrNull() }
        .flatMapLatest { profile ->
            if (profile != null) {
                dao.getInstallmentsForProfile(profile.id)
            } else {
                flowOf(emptyList())
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun calculateAndSave(universityCode: String, semesterName: String, totalTuition: Double, scholarshipPercent: Double) {
        viewModelScope.launch {
            val model = UniversityBillingModels.models.find { it.universityCode == universityCode } ?: return@launch
            val finalPayable = totalTuition * (1.0 - scholarshipPercent)

            val profile = SemesterProfile(
                universityCode = universityCode,
                semesterName = semesterName,
                totalTuition = totalTuition,
                scholarshipPercent = scholarshipPercent,
                finalPayable = finalPayable
            )

            val installmentsAmounts = BillingAlgorithm.calculateInstallments(totalTuition, scholarshipPercent, model)
            val installments = installmentsAmounts.mapIndexed { index, amount ->
                Installment(
                    profileId = 0, // Will be set by DAO transaction
                    installmentNumber = index + 1,
                    amountDue = amount,
                    isPaid = false,
                    dueDateText = "Installment ${index + 1}"
                )
            }

            dao.insertProfileWithInstallments(profile, installments)
        }
    }

    fun toggleInstallmentPaid(installment: Installment) {
        viewModelScope.launch {
            dao.updateInstallment(installment.copy(isPaid = !installment.isPaid))
        }
    }
}

class TuitionViewModelFactory(private val dao: TuitionDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TuitionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TuitionViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

