package com.example.tuitioncalculator.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TuitionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: SemesterProfile): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInstallments(installments: List<Installment>)

    @Transaction
    suspend fun insertProfileWithInstallments(profile: SemesterProfile, installments: List<Installment>) {
        val id = insertProfile(profile)
        val installmentsWithId = installments.map { it.copy(profileId = id) }
        insertInstallments(installmentsWithId)
    }

    @Query("SELECT * FROM semester_profiles ORDER BY id DESC")
    fun getAllProfiles(): Flow<List<SemesterProfile>>

    @Query("SELECT * FROM installments WHERE profileId = :profileId ORDER BY installmentNumber ASC")
    fun getInstallmentsForProfile(profileId: Long): Flow<List<Installment>>

    @Update
    suspend fun updateInstallment(installment: Installment)

    @Delete
    suspend fun deleteProfile(profile: SemesterProfile)
}
