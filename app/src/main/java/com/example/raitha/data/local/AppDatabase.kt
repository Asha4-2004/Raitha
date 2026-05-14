package com.example.raitha.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TipEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tipDao(): TipDao

    companion object {
        fun seedTips(): List<TipEntity> {
            return listOf(
                TipEntity(title = "Rice Blast Control", description = "Spray Tricyclazole 75 WP at 0.6 g/l for early protection.", cropType = "Paddy", imageUrl = null, isKannada = false, district = "Mandya", season = "Kharif"),
                TipEntity(title = "ಭತ್ತದ ಬೆಂಕಿ ರೋಗ", description = "ಟ್ರೈಸೈಕ್ಲಾಜೋಲ್ 75 ಡಬ್ಲ್ಯುಪಿ ಸಿಂಪಡಿಸಿ.", cropType = "Paddy", imageUrl = null, isKannada = true, district = "Mandya", season = "Kharif"),
                TipEntity(title = "Areca Nut Yellowing", description = "Apply 100g Urea, 40g Rock Phosphate, and 140g MOP per palm.", cropType = "Areca Nut", imageUrl = null, isKannada = false, district = "Shivamogga", season = "Monsoon"),
                TipEntity(title = "ಅಡಿಕೆ ಹಳದಿ ರೋಗ", description = "ಮರಕ್ಕೆ ಸಮತೋಲಿತ ರಸಗೊಬ್ಬರ ನೀಡಿ.", cropType = "Areca Nut", imageUrl = null, isKannada = true, district = "Shivamogga", season = "Monsoon"),
                TipEntity(title = "Coconut Button Shedding", description = "Spray 1% Borax on the crown once every six months.", cropType = "Coconut", imageUrl = null, isKannada = false, district = "Udupi", season = "Summer"),
                TipEntity(title = "ತೆಂಗಿನ ಕಾಯಿ ಉದುರುವಿಕೆ", description = "ಬೋರಾಕ್ಸ್ ದ್ರಾವಣ ಸಿಂಪಡಿಸಿ.", cropType = "Coconut", imageUrl = null, isKannada = true, district = "Udupi", season = "Summer"),
                TipEntity(title = "Tomato Early Blight", description = "Use Mancozeb 75 WP at 2 g/l to control leaf spotting.", cropType = "Tomato", imageUrl = null, isKannada = false, district = "Kolar", season = "Monsoon"),
                TipEntity(title = "ಟೊಮೇಟೊ ಎಲೆ ಮಚ್ಚೆ", description = "ಮ್ಯಾಂಕೋಜೆಬ್ ಸಿಂಪಡಿಸಿ.", cropType = "Tomato", imageUrl = null, isKannada = true, district = "Kolar", season = "Monsoon"),
                TipEntity(title = "Coffee Rust Control", description = "Apply 0.5% Bordeaux mixture during pre-monsoon showers.", cropType = "Coffee", imageUrl = null, isKannada = false, district = "Chikkamagaluru", season = "Pre-monsoon"),
                TipEntity(title = "ಕಾಫಿ ತುಕ್ಕು ರೋಗ", description = "ಬೋರ್ಡೋ ದ್ರಾವಣ ಬಳಸಿ.", cropType = "Coffee", imageUrl = null, isKannada = true, district = "Chikkamagaluru", season = "Pre-monsoon"),
                TipEntity(title = "Mango Fruit Fly", description = "Install Methyl Eugenol traps to monitor and control flies.", cropType = "Mango", imageUrl = null, isKannada = false, district = "Dharwad", season = "Summer"),
                TipEntity(title = "ಮಾವಿನ ಹಣ್ಣಿನ ನೊಣ", description = "ಮಿಥೈಲ್ ಯುಜೆನಾಲ್ ಬಲೆಗಳನ್ನು ಬಳಸಿ.", cropType = "Mango", imageUrl = null, isKannada = true, district = "Dharwad", season = "Summer"),
                TipEntity(title = "Silk Worm Care", description = "Maintain 25-27 C and 75% humidity in the rearing room.", cropType = "Sericulture", imageUrl = null, isKannada = false, district = "Ramanagara", season = "Year-round"),
                TipEntity(title = "ರೇಷ್ಮೆ ಹುಳು ಸಾಕಣೆ", description = "ಕೋಣೆಯ ತಾಪಮಾನ ಕಾಪಾಡಿ.", cropType = "Sericulture", imageUrl = null, isKannada = true, district = "Ramanagara", season = "Year-round"),
                TipEntity(title = "Sugarcane Borer", description = "Release Trichogramma chilonis at 2.5 lakh per hectare.", cropType = "Sugarcane", imageUrl = null, isKannada = false, district = "Belagavi", season = "Kharif"),
                TipEntity(title = "ಕಬ್ಬಿನ ಕೊರಕ", description = "ಟ್ರೈಕೋಗ್ರಾಮಾ ಬಳಸಿ.", cropType = "Sugarcane", imageUrl = null, isKannada = true, district = "Belagavi", season = "Kharif"),
                TipEntity(title = "Cotton Bollworm", description = "Plant trap crops like marigold or castor around the field.", cropType = "Cotton", imageUrl = null, isKannada = false, district = "Haveri", season = "Rabi"),
                TipEntity(title = "ಹತ್ತಿ ಕಾಯಿಕೊರಕ", description = "ಚೆಂಡು ಹೂವಿನ ಗಿಡಗಳನ್ನು ಬೆಳೆಸಿ.", cropType = "Cotton", imageUrl = null, isKannada = true, district = "Haveri", season = "Rabi"),
                TipEntity(title = "Maize Fall Armyworm", description = "Sow seeds early and apply sand-lime mixture in leaf whorls.", cropType = "Maize", imageUrl = null, isKannada = false, district = "Davanagere", season = "Kharif"),
                TipEntity(title = "ಮೆಕ್ಕೆಜೋಳದ ಸೈನಿಕ ಹುಳು", description = "ಬೇಗನೆ ಬಿತ್ತನೆ ಮಾಡಿ.", cropType = "Maize", imageUrl = null, isKannada = true, district = "Davanagere", season = "Kharif"),
                TipEntity(title = "Paddy Zinc Deficiency", description = "Apply 25 kg zinc sulphate per hectare before transplanting.", cropType = "Paddy", imageUrl = null, isKannada = false, district = "Raichur", season = "Rabi"),
                TipEntity(title = "ಭತ್ತದ ಸತು ಕೊರತೆ", description = "ಜಿಂಕ್ ಸಲ್ಫೇಟ್ ಬಳಸಿ.", cropType = "Paddy", imageUrl = null, isKannada = true, district = "Raichur", season = "Rabi"),
                TipEntity(title = "Areca Nut Cracking", description = "Ensure regular irrigation and apply Borax 20 g per palm.", cropType = "Areca Nut", imageUrl = null, isKannada = false, district = "Uttara Kannada", season = "Summer"),
                TipEntity(title = "ಅಡಿಕೆ ಸಿಪ್ಪೆ ಒಡೆಯುವಿಕೆ", description = "ನಿಯಮಿತ ನೀರಾವರಿ ಒದಗಿಸಿ.", cropType = "Areca Nut", imageUrl = null, isKannada = true, district = "Uttara Kannada", season = "Summer"),
                TipEntity(title = "Banana Wilt Control", description = "Drench soil with Carbendazim 2 g/l near the root zone.", cropType = "Banana", imageUrl = null, isKannada = false, district = "Mysuru", season = "Year-round"),
                TipEntity(title = "ಬಾಳೆ ಬಾಡು ರೋಗ", description = "ರೋಗನಿರೋಧಕ ದ್ರಾವಣ ಬಳಸಿ.", cropType = "Banana", imageUrl = null, isKannada = true, district = "Mysuru", season = "Year-round"),
                TipEntity(title = "Groundnut Seed Treatment", description = "Treat seeds with Trichoderma viride at 4 g per kg seed.", cropType = "Groundnut", imageUrl = null, isKannada = false, district = "Tumakuru", season = "Kharif"),
                TipEntity(title = "ನೆಲಗಡಲೆ ಬೀಜೋಪಚಾರ", description = "ಜೈವಿಕ ಶಿಲೀಂಧ್ರನಾಶಕ ಬಳಸಿ.", cropType = "Groundnut", imageUrl = null, isKannada = true, district = "Tumakuru", season = "Kharif"),
                TipEntity(title = "Pomegranate Bacterial Blight", description = "Prune infected branches and spray Streptocycline 0.5 g/l.", cropType = "Pomegranate", imageUrl = null, isKannada = false, district = "Bagalkote", season = "Rabi"),
                TipEntity(title = "ದಾಳಿಂಬೆ ಸೊರಗು ರೋಗ", description = "ರೋಗಗ್ರಸ್ತ ಕೊಂಬೆಗಳನ್ನು ಕತ್ತರಿಸಿ.", cropType = "Pomegranate", imageUrl = null, isKannada = true, district = "Bagalkote", season = "Rabi"),
                TipEntity(title = "Onion Purple Blotch", description = "Spray Mancozeb 2 g/l or Hexaconazole 1 ml/l of water.", cropType = "Onion", imageUrl = null, isKannada = false, district = "Chitradurga", season = "Kharif"),
                TipEntity(title = "ಈರುಳ್ಳಿ ನೇರಳೆ ಮಚ್ಚೆ", description = "ಮ್ಯಾಂಕೋಜೆಬ್ ಬಳಸಿ.", cropType = "Onion", imageUrl = null, isKannada = true, district = "Chitradurga", season = "Kharif")
            )
        }
    }
}
