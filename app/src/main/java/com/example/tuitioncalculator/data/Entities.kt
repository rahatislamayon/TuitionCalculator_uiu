package com.example.tuitioncalculator.data

import androidx.room.*

@Entity(tableName = "semester_profiles")
data class SemesterProfile(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val universityCode: String,
    val semesterName: String,
    val totalTuition: Double,
    val scholarshipPercent: Double,
    val finalPayable: Double
)

@Entity(
    tableName = "installments",
    foreignKeys = [
        ForeignKey(
            entity = SemesterProfile::class,
            parentColumns = ["id"],
            childColumns = ["profileId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("profileId")]
)
data class Installment(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val profileId: Long,
    val installmentNumber: Int,
    val amountDue: Double,
    val isPaid: Boolean = false,
    val dueDateText: String = "" 
)
