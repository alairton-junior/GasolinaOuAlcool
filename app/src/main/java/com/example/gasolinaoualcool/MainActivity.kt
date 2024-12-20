package com.example.gasolinaoualcool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gasolinaoualcool.ui.theme.ScreenFunction

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScreenFunction(dynamicColor = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FuelCalculatorApp()
                }
            }
        }
    }
}

@Preview
@Composable
fun FuelCalculatorApp() {
    FuelCalculatorScreen(modifier = Modifier.wrapContentSize(Alignment.Center))
}

@Composable
fun FuelCalculatorScreen(modifier: Modifier = Modifier) {
    var percentage by remember { mutableStateOf(0.75f) }
    var gasolinePriceInput by remember { mutableStateOf(TextFieldValue("")) }
    var alcoholPriceInput by remember { mutableStateOf(TextFieldValue("")) }
    var resultText by remember { mutableStateOf("") }
    var isSeventyPercent by remember { mutableStateOf(true) }

    Column(
        modifier = modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.logo_horizontal),
            contentDescription = "Logo",
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Selecione o rendimento do carro em relação ao álcool:",
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "70%",
                color = MaterialTheme.colorScheme.primary
            )
            Switch(
                checked = isSeventyPercent,
                onCheckedChange = {
                    isSeventyPercent = it
                    percentage = if (!it) 0.7f else 0.75f
                },
                modifier = Modifier.padding(16.dp),
                colors = SwitchDefaults.colors(checkedThumbColor = MaterialTheme.colorScheme.secondary)
            )
            Text(
                "75%",
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = gasolinePriceInput,
            onValueChange = { gasolinePriceInput = it },
            label = { Text("Preço da Gasolina (R$)") },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp)),
        )

        TextField(
            value = alcoholPriceInput,
            onValueChange = { alcoholPriceInput = it },
            label = { Text("Preço do Álcool (R$)") },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp)),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val gasolinePrice = gasolinePriceInput.text.toFloatOrNull() ?: 0f
                val alcoholPrice = alcoholPriceInput.text.toFloatOrNull() ?: 0f

                val threshold = gasolinePrice * percentage
                resultText = if (alcoholPrice <= threshold) {
                    "✅ Álcool é a melhor escolha!\n\n" +
                            "💡 Rendimento do álcool selecionado: ${"%.0f".format(percentage * 100)}%\n" +
                            "💰 Preço máximo do álcool para ser vantajoso: R$${"%.2f".format(threshold)}\n"
                } else {
                    "✅ Gasolina é a melhor escolha!\n\n" +
                            "💡 Rendimento do álcool selecionado: ${"%.0f".format(percentage * 100)}%\n" +
                            "💰 Preço máximo do álcool para ser vantajoso: R$${"%.2f".format(threshold)}\n"
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary, // Cor de fundo do botão
                contentColor = MaterialTheme.colorScheme.primary // Cor do texto no botão
            )
        ) {
            Text("Calcular")
        }


        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = resultText,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview
@Composable
fun FuelCalculatorScreenPreview() {
    ScreenFunction {
        FuelCalculatorScreen()
    }
}
