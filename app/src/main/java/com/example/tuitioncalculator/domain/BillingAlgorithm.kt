package com.example.tuitioncalculator.domain

import kotlin.math.roundToLong

data class BillingModel(
    val universityCode: String,
    val universityName: String,
    val installmentPercentages: List<Double>,
    val perCreditPrice: Double = 0.0,
    val programPrices: Map<String, Double> = emptyMap(),
    val otherFees: String = ""
)

object UniversityBillingModels {
    val UIU = BillingModel("UIU", "United International University", listOf(0.40, 0.30, 0.30), 6500.0,
        mapOf("CSE / EEE / Civil" to 6500.0, "BBA / Econ" to 6500.0, "Pharmacy" to 6500.0, "English / JMC" to 5525.0),
        "Admission: ৳20,000. Semester/Trimester fees apply."
    )
    val UAP = BillingModel("UAP", "University of Asia Pacific", listOf(0.20, 0.40, 0.40), 5559.0,
        mapOf("Architecture" to 105000.0, "CSE/EEE/Civil" to 100000.0, "LLB" to 100000.0, "BBA" to 85000.0, "English" to 70000.0, "Pharmacy" to 125000.0),
        "Admission: ৳21,500. Prices above are per SEMESTER, not per credit."
    )
    val AIUB = BillingModel("AIUB", "American International University-Bangladesh", listOf(0.40, 0.30, 0.30), 7000.0,
        mapOf("CSE / Cybersecurity" to 7500.0, "EEE" to 6800.0, "Architecture" to 6500.0, "COE" to 5300.0, "IPE" to 5000.0, "BBA / Pharmacy" to 7000.0, "Biochemistry" to 6500.0, "LLB" to 7500.0, "English" to 5500.0, "JMC / Econ" to 4500.0),
        "Admission: ৳25,000. Activity Fee: ৳10,000/sem. Lab Fees extra."
    )
    val STAMFORD = BillingModel("STAMFORD", "Stamford University", listOf(1.0 / 6.0, 1.0 / 6.0, 1.0 / 6.0, 1.0 / 6.0, 1.0 / 6.0, 1.0 / 6.0), 4500.0,
        mapOf("BBA / MBA" to 4500.0, "CSE / Pharmacy" to 4000.0, "EEE" to 3500.0, "Civil" to 3800.0, "LLB" to 4800.0, "English" to 3000.0, "Econ" to 2500.0, "JMC" to 2800.0),
        "Admission: ৳26,000. Lab/Library: ৳5,250/sem."
    )
    val NSU = BillingModel("NSU", "North South University", listOf(0.35, 0.35, 0.30), 8000.0,
        mapOf("All Undergrad Programs" to 8000.0, "MBA / EMBA" to 7000.0, "MS (CSE/EEE)" to 5000.0),
        "Admission: ৳35,000 (incl. caution). Uniform undergrad pricing."
    )
    val BRAC = BillingModel("BRAC", "BRAC University", listOf(0.40, 0.30, 0.30), 8250.0,
        mapOf("Pharmacy" to 8250.0, "Law / Arch (Studio)" to 8800.0, "Arch (Lecture)" to 8250.0, "CSE" to 8250.0, "BBA / Econ" to 8250.0),
        "Admission: ৳33,900. RS: ৳88,000. Semester Fee: ৳9.3k-14k."
    )
    val EWU = BillingModel("EWU", "East West University", listOf(0.34, 0.33, 0.33), 6500.0,
        mapOf("CSE / EEE / Civil / BBA / LLB" to 6500.0, "Pharmacy" to 7000.0, "Data Science / Econ / English / Sociology" to 5500.0, "Info Studies" to 5000.0, "Math" to 4000.0),
        "Admission: ৳25,000. Lab/Library Fee: ৳5,000/sem."
    )
    val IUB = BillingModel("IUB", "Independent University Bangladesh", listOf(0.40, 0.30, 0.30), 6500.0,
        mapOf("Standard (BBA, CSE, EEE, English)" to 6500.0, "Professional (LLB, B.Pharm)" to 7500.0),
        "Admission: ৳25,000. Semester Fee: ৳8k-12k. 10% female discount."
    )
    val AUST = BillingModel("AUST", "Ahsanullah University of Science and Tech", listOf(0.50, 0.50), 7061.0, emptyMap(), "Estimated base pricing.")
    val DIU = BillingModel("DIU", "Daffodil International University", listOf(0.25, 0.25, 0.25, 0.25), 5853.0, emptyMap(), "Estimated base pricing.")
    val BUP = BillingModel("BUP", "Bangladesh University of Professionals", listOf(0.50, 0.50), 600.0, emptyMap(), "Public university pricing model.")
    val IUBAT = BillingModel("IUBAT", "Int. Univ. of Business Agriculture and Tech", listOf(0.20, 0.20, 0.20, 0.20, 0.20), 2600.0, emptyMap(), "Estimated base pricing.")
    val SEU = BillingModel("SEU", "Southeast University", listOf(0.40, 0.30, 0.30), 5500.0,
        mapOf("CSE" to 5500.0, "Pharmacy" to 5900.0, "Architecture" to 4800.0, "EEE" to 4300.0, "Textile" to 3850.0, "BBA" to 5350.0, "LLB" to 5400.0, "English" to 4200.0, "Econ" to 3800.0),
        "Per-credit pricing varies by program."
    )
    val GREEN = BillingModel("GREEN", "Green University of Bangladesh", listOf(0.40, 0.30, 0.30), 3350.0,
        mapOf("CSE" to 4000.0, "AI" to 3550.0, "SWE" to 3400.0, "EEE" to 3000.0, "LLB" to 4500.0, "BBA" to 3350.0, "English" to 2500.0, "Sociology" to 1800.0),
        "Focus on high-value, high-volume education."
    )
    val NUB = BillingModel("NUB", "Northern University Bangladesh", listOf(0.40, 0.60), 4500.0, emptyMap(), "Estimated base pricing.")
    val LU = BillingModel("LU", "Leading University", listOf(0.34, 0.33, 0.33), 2500.0,
        mapOf("CSE" to 2250.0, "BBA" to 2500.0, "English" to 1900.0, "LLB" to 2200.0, "Architecture" to 2550.0, "EEE" to 2100.0, "Civil" to 2415.0, "Bangla" to 840.0, "Islamic Studies" to 200.0),
        "Regional university in Sylhet."
    )
    val SUB = BillingModel("SUB", "State University of Bangladesh", listOf(0.40, 0.30, 0.30), 2300.0, emptyMap(), "Estimated base pricing.")
    val VARENDRA = BillingModel("VARENDRA", "Varendra University", listOf(0.30, 0.30, 0.40), 2500.0, emptyMap(), "Estimated base pricing.")
    val ULAB = BillingModel("ULAB", "University of Liberal Arts Bangladesh", listOf(0.40, 0.30, 0.30), 6500.0,
        mapOf("BBA" to 6500.0, "MSJ" to 6250.0, "Env. Science" to 5500.0, "CSE / EEE" to 5000.0, "English" to 4650.0, "Bangla" to 1800.0),
        "Reg Fee: ৳2,500/term. 10% female discount."
    )
    val WUB = BillingModel("WUB", "World University of Bangladesh", listOf(0.40, 0.30, 0.30), 4300.0,
        mapOf("BBA" to 4300.0, "LLB" to 4500.0, "Civil" to 3400.0, "CSE" to 3350.0, "Mechatronics" to 3100.0, "Arch" to 3600.0, "Pharmacy" to 4350.0, "Textile" to 3000.0),
        "Additional basic fees apply."
    )

    val models = listOf(
        UIU, UAP, AIUB, STAMFORD, NSU, BRAC, EWU, IUB, AUST, DIU, 
        BUP, IUBAT, SEU, GREEN, NUB, LU, SUB, VARENDRA, ULAB, WUB
    ).sortedBy { it.universityName }
}

object BillingAlgorithm {
    /** Rounds a Double to exactly 2 decimal places. */
    private fun Double.roundTo2Decimals(): Double =
        (this * 100.0).roundToLong() / 100.0

    fun calculateInstallments(
        totalTuition: Double,
        scholarshipPercent: Double,
        model: BillingModel
    ): List<Double> {
        val finalTuition = (totalTuition * (1.0 - scholarshipPercent)).roundTo2Decimals()
        val installments = model.installmentPercentages.dropLast(1).map { p_n ->
            (finalTuition * p_n).roundTo2Decimals()
        }.toMutableList()
        
        val currentSum = installments.sum().roundTo2Decimals()
        val lastInstallment = (finalTuition - currentSum).roundTo2Decimals()
        installments.add(lastInstallment)
        
        return installments
    }
}
