# Graph Report - TuitionCalculator  (2026-04-24)

## Corpus Check
- 19 files · ~57,200 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 98 nodes · 79 edges · 16 communities detected
- Extraction: 100% EXTRACTED · 0% INFERRED · 0% AMBIGUOUS
- Token cost: 0 input · 0 output

## Community Hubs (Navigation)
- [[_COMMUNITY_Community 0|Community 0]]
- [[_COMMUNITY_Community 1|Community 1]]
- [[_COMMUNITY_Community 2|Community 2]]
- [[_COMMUNITY_Community 3|Community 3]]
- [[_COMMUNITY_Community 4|Community 4]]
- [[_COMMUNITY_Community 5|Community 5]]
- [[_COMMUNITY_Community 6|Community 6]]
- [[_COMMUNITY_Community 7|Community 7]]
- [[_COMMUNITY_Community 8|Community 8]]
- [[_COMMUNITY_Community 9|Community 9]]
- [[_COMMUNITY_Community 10|Community 10]]
- [[_COMMUNITY_Community 11|Community 11]]
- [[_COMMUNITY_Community 12|Community 12]]
- [[_COMMUNITY_Community 13|Community 13]]
- [[_COMMUNITY_Community 14|Community 14]]
- [[_COMMUNITY_Community 15|Community 15]]

## God Nodes (most connected - your core abstractions)
1. `TuitionDao` - 8 edges
2. `PaymentsFragment` - 6 edges
3. `ProfileFragment` - 6 edges
4. `CompareFragment` - 5 edges
5. `HomeFragment` - 5 edges
6. `UniversitiesFragment` - 5 edges
7. `CalculatorFragment` - 4 edges
8. `InstallmentAdapter` - 3 edges
9. `InstallmentDiffCallback` - 3 edges
10. `IntroActivity` - 3 edges

## Surprising Connections (you probably didn't know these)
- None detected - all connections are within the same source files.

## Communities

### Community 0 - "Community 0"
Cohesion: 0.15
Nodes (4): DiffCallback, UniversitiesFragment, UniversityAdapter, ViewHolder

### Community 1 - "Community 1"
Cohesion: 0.22
Nodes (1): TuitionDao

### Community 2 - "Community 2"
Cohesion: 0.25
Nodes (3): InstallmentAdapter, InstallmentDiffCallback, ViewHolder

### Community 3 - "Community 3"
Cohesion: 0.29
Nodes (1): CompareFragment

### Community 4 - "Community 4"
Cohesion: 0.29
Nodes (1): PaymentsFragment

### Community 5 - "Community 5"
Cohesion: 0.29
Nodes (1): ProfileFragment

### Community 6 - "Community 6"
Cohesion: 0.33
Nodes (1): HomeFragment

### Community 7 - "Community 7"
Cohesion: 0.33
Nodes (3): BillingAlgorithm, BillingModel, UniversityBillingModels

### Community 8 - "Community 8"
Cohesion: 0.33
Nodes (2): TuitionViewModel, TuitionViewModelFactory

### Community 9 - "Community 9"
Cohesion: 0.4
Nodes (1): CalculatorFragment

### Community 10 - "Community 10"
Cohesion: 0.5
Nodes (1): IntroActivity

### Community 11 - "Community 11"
Cohesion: 0.5
Nodes (1): MainActivity

### Community 12 - "Community 12"
Cohesion: 0.5
Nodes (1): AppDatabase

### Community 13 - "Community 13"
Cohesion: 0.67
Nodes (1): ExampleInstrumentedTest

### Community 14 - "Community 14"
Cohesion: 0.67
Nodes (2): Installment, SemesterProfile

### Community 15 - "Community 15"
Cohesion: 0.67
Nodes (1): ExampleUnitTest

## Knowledge Gaps
- **6 isolated node(s):** `ViewHolder`, `ViewHolder`, `SemesterProfile`, `Installment`, `BillingModel` (+1 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **Thin community `Community 1`** (9 nodes): `TuitionDao.kt`, `TuitionDao`, `.deleteProfile()`, `.getAllProfiles()`, `.getInstallmentsForProfile()`, `.insertInstallments()`, `.insertProfile()`, `.insertProfileWithInstallments()`, `.updateInstallment()`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 3`** (7 nodes): `CompareFragment.kt`, `CompareFragment`, `.buildBreakdownText()`, `.onCreateView()`, `.onDestroyView()`, `.onViewCreated()`, `findPriceForDepartment()`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 4`** (7 nodes): `PaymentsFragment.kt`, `PaymentsFragment`, `.generateOfficialQuote()`, `.observeData()`, `.onCreateView()`, `.onDestroyView()`, `.onViewCreated()`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 5`** (7 nodes): `ProfileFragment.kt`, `ProfileFragment`, `.loadProfileStats()`, `.onCreateView()`, `.onDestroyView()`, `.onViewCreated()`, `.showEditDialog()`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 6`** (6 nodes): `HomeFragment.kt`, `HomeFragment`, `.loadFeaturedLogos()`, `.onCreateView()`, `.onDestroyView()`, `.onViewCreated()`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 8`** (6 nodes): `TuitionViewModel.kt`, `TuitionViewModel`, `.calculateAndSave()`, `.toggleInstallmentPaid()`, `TuitionViewModelFactory`, `.create()`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 9`** (5 nodes): `CalculatorFragment.kt`, `CalculatorFragment`, `.onCreateView()`, `.onDestroyView()`, `.onViewCreated()`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 10`** (4 nodes): `IntroActivity.kt`, `IntroActivity`, `.finishIntro()`, `.onCreate()`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 11`** (4 nodes): `MainActivity.kt`, `MainActivity`, `.onCreate()`, `.setupBottomNavigation()`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 12`** (4 nodes): `AppDatabase.kt`, `AppDatabase`, `.tuitionDao()`, `getDatabase()`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 13`** (3 nodes): `ExampleInstrumentedTest.java`, `ExampleInstrumentedTest`, `.useAppContext()`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 14`** (3 nodes): `Entities.kt`, `Installment`, `SemesterProfile`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 15`** (3 nodes): `ExampleUnitTest.java`, `ExampleUnitTest`, `.addition_isCorrect()`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **What connects `ViewHolder`, `ViewHolder`, `SemesterProfile` to the rest of the system?**
  _6 weakly-connected nodes found - possible documentation gaps or missing edges._