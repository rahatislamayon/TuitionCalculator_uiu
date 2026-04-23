<div align="center">

# 🎓 Tuition Calculator

**The Smartest Way to Plan & Track University Finances in Bangladesh**

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.22-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![Android Min SDK](https://img.shields.io/badge/Min%20SDK-24-green.svg)](https://android-arsenal.com/api?level=24)
[![Material Design 3](https://img.shields.io/badge/Material-3-orange.svg)](https://m3.material.io/)
[![Architecture](https://img.shields.io/badge/Architecture-MVVM-lightgrey.svg)](https://developer.android.com/topic/architecture)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

A premium, offline-first Android application designed to help university students estimate, compare, and track their tuition fees and installments across 20+ top private universities in Bangladesh.

</div>

---

## 🌟 Overview

Navigating university finances can be overwhelming. **Tuition Calculator** simplifies this by bringing real-world billing algorithms of major Bangladeshi universities (NSU, BRAC, UIU, AIUB, etc.) directly to your fingertips. 

Whether you are a prospective student comparing degrees or a current student tracking your semester installments, this app provides precise, atomic calculations down to the last decimal, wrapped in a stunning "Sophisticated Fluidity" design language.

<br>

## ✨ Key Features

*   **🧮 Smart Installment Engine**: Instantly generate accurate payment breakdowns based on specific university billing structures and scholarship percentages.
*   **⚖️ Cross-University Comparison**: Compare per-credit rates and total semester fees side-by-side between different universities and departments.
*   **📊 Payment Tracking**: Keep a detailed, persistent log of your past and upcoming installments. Mark them as paid/unpaid with real-time UI updates.
*   **📄 Official Quote Generation**: Export and share your calculated tuition breakdowns as formatted text via Android's native share sheet.
*   **🎨 Premium Material 3 Design**: Experience a gorgeous, fluid UI featuring custom gradients, smooth navigation transitions, and dynamic empty states.
*   **⚡ 100% Offline**: All algorithms and databases run entirely locally on your device for maximum speed and privacy.

<br>

## 🛠️ Tech Stack & Architecture

Built with modern Android development standards to ensure scalability, performance, and maintainability.

*   **Language**: [Kotlin](https://kotlinlang.org/) (100%)
*   **Architecture**: **MVVM** (Model-View-ViewModel) + Repository Pattern
*   **UI Toolkit**: XML + **Material Design 3** (M3) + ViewBinding
*   **Database**: **Room** (SQLite) using Coroutines and `StateFlow` for reactive UI updates
*   **Navigation**: **Jetpack Navigation Component** with custom animations
*   **Concurrency**: Kotlin Coroutines & `viewModelScope`

### Project Structure Highlights
*   **`domain/BillingAlgorithm.kt`**: Contains the core logic and university-specific data models for calculating precise installment percentages and handling edge cases.
*   **`data/TuitionDao.kt`**: Utilizes Room `@Transaction` to ensure atomicity when saving semester profiles and their associated installment chunks.

<br>

## 🚀 Getting Started

### Prerequisites
*   [Android Studio](https://developer.android.com/studio) (Iguana or newer recommended)
*   Android SDK 36
*   Minimum Device requirement: Android 7.0 (API level 24)

### Installation
1.  **Clone the repository**
    ```bash
    git clone https://github.com/yourusername/TuitionCalculator.git
    ```
2.  **Open the project** in Android Studio.
3.  **Sync Gradle** to fetch all required dependencies.
4.  **Run the app** on an emulator or a physical device.

<br>

## 📸 Screenshots

<div align="center">
  <img src="https://lh3.googleusercontent.com/aida/ADBb0uh3MbFYHLinpgipkXVRUDxImPY81JEppNyqbffBCDK1d29YQw8SNm03Lxy6Ew0effiTfJtfFRsoG-pTvO0b9Y3Wcz4XHsLDfhy88pGGo6jxafTi-tNRqZVYmLU1j6jdWdwhL_dH5oVH2by9pJzjni3xyaf_f_UmCC7gkbF4i5fxcttIRO49fWZ1Xa9g59znWenJkS5SMpn05IsxC5J8SXDkjvLymH2TnLwO8CyMftDyotXSIbPW6WIMx1ht" width="30%">
  <img src="https://lh3.googleusercontent.com/aida/ADBb0ui9iRlqikZi8jtQ0UHfVggzFgna-217nwIOyLizjzIQ9uCbw7UC8n3iukkGgNDzhnJl7mjKnr3LpZ4mb1Z3up8VTMNwiGsuDCABjOuKnESmBbRbBaR6UEBYAITyAWX8xZSNq6_hmxTXxv_tLiiwWPVeyJzAiccjmeK9OnFnrwqpl6zgwjoBwsuZy_aGtf5LdM9Rmnm1HB7a7grVi7gZqJstvO8bPFGB6H_glaL2xiFYQXpQN1atfGXu-pVY" width="30%">
  <img src="https://lh3.googleusercontent.com/aida/ADBb0uhhEiNAChtZMMosfaImNUb6PxDyRyrKWTygxbUgMc-6k2SoR-W9nhqUOE9UUaV1IBiovk2Gx4m-IfTy6c4Lo5_pHQVWzo9GIclnR8NpTXJQrK47DdZA6m-290Ml0soQsrjAB4_wX6HS0Xg4HPWUR12dZ_osPBx1SFt7lc7JEfROIQbfY2jZAgb0abh5tRFZseIWo4opfiSaKFym_vRjnzNbhoaLMrIlz1Gure2RrtctInKMTjF1ME6iq7k" width="30%">
</div>

<br>

## 🤝 Contributing

Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<br>

## 📄 License

Distributed under the MIT License. See `LICENSE` for more information.

<br>

<div align="center">
  <b>Built by ayon 👾.</b>
</div>
