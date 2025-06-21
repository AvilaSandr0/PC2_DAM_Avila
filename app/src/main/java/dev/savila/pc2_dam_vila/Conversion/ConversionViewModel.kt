package dev.savila.pc2_dam_vila.conversion

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class ConversionViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val exchangeRates = mapOf(
        Pair("USD", "EUR") to 0.925,
        Pair("USD", "PEN") to 3.75,
        Pair("EUR", "USD") to 1.08,
        Pair("PEN", "USD") to 0.27,
        Pair("GBP", "JPY") to 183.2,
    )

    fun convert(amount: Double, from: String, to: String): Double {
        return if (from == to) {
            amount
        } else {
            val rate = exchangeRates[Pair(from, to)] ?: 1.0
            String.format("%.2f", amount * rate).toDouble()
        }
    }

    fun saveConversion(
        amount: Double,
        from: String,
        to: String,
        result: Double
    ) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        val data = hashMapOf(
            "usuario" to uid,
            "fechaHora" to FieldValue.serverTimestamp(),
            "monto" to amount,
            "from" to from,
            "to" to to,
            "resultado" to result
        )

        db.collection("conversiones").add(data)
    }
}
