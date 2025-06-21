package dev.savila.pc2_dam_vila.Conversion

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dev.savila.pc2_dam_vila.auth.AuthViewModel
import dev.savila.pc2_dam_vila.conversion.ConversionViewModel

@Composable
fun ConversionScreen() {
    val viewModel = ConversionViewModel()
    val context = LocalContext.current
    val currencies = listOf("USD", "EUR", "PEN", "GBP", "JPY")

    var amount by remember { mutableStateOf("") }
    var fromCurrency by remember { mutableStateOf(currencies[0]) }
    var toCurrency by remember { mutableStateOf(currencies[1]) }
    var resultText by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text("Conversor de Divisas", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Monto") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            CurrencyDropdown("De", currencies, fromCurrency) { fromCurrency = it }
            CurrencyDropdown("A", currencies, toCurrency) { toCurrency = it }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val amountDouble = amount.toDoubleOrNull()
                if (amountDouble != null && fromCurrency != toCurrency) {
                    val result = viewModel.convert(amountDouble, fromCurrency, toCurrency)
                    resultText = "$amountDouble $fromCurrency equivalen a $result $toCurrency"
                    viewModel.saveConversion(amountDouble, fromCurrency, toCurrency, result)
                } else {
                    Toast.makeText(context, "Verifica los campos", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Convertir")
        }

        Spacer(modifier = Modifier.height(16.dp))

        resultText?.let {
            Text(it, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

fun viewModel(): AuthViewModel {
    return AuthViewModel()
}

@Composable
    fun CurrencyDropdown(
        label: String,
        options: List<String>,
        selected: String,
        onSelectedChange: (String) -> Unit
    ) {
        var expanded by remember { mutableStateOf(false) }

        Column {
            Text(label)
            Box{
                OutlinedTextField(
                    value = selected,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth().clickable { expanded = true }
                )
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    options.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                onSelectedChange(option)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }