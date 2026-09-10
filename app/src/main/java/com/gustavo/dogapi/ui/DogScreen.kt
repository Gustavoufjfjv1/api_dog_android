package com.gustavo.dogapi.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.gustavo.dogapi.viewmodel.DogUiState
import com.gustavo.dogapi.viewmodel.DogViewModel

private val CorTexto = Color(0xFF3A444D)
private val CorBorda = Color(0xFF333333)
private val CorBotao = Color(0xFF444444)

private const val TELA_ESQUERDA = 0.160f
private const val TELA_TOPO = 0.270f
private const val TELA_LARGURA = 0.588f
private const val TELA_ALTURA = 0.261f
private const val MOLDURA_BASE = 0.628f
private const val MOLDURA_ESQUERDA = 0.080f
private const val MOLDURA_LARGURA = 0.732f

@Composable
fun DogScreen(viewModel: DogViewModel = viewModel()) {
    var termoBusca by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxHeight().aspectRatio(425f / 637f)
        ) {
            val largura = maxWidth
            val altura = maxHeight

            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = largura * TELA_ESQUERDA, y = altura * TELA_TOPO)
                    .width(largura * TELA_LARGURA)
                    .height(altura * TELA_ALTURA),
                contentAlignment = Alignment.Center
            ) {
                when (val state = viewModel.uiState) {
                    is DogUiState.Loading ->
                        Text("Loading...", fontWeight = FontWeight.SemiBold, color = CorTexto)
                    is DogUiState.NotFound ->
                        Text("Not found!", fontWeight = FontWeight.SemiBold, color = CorTexto)
                    is DogUiState.Success -> AsyncImage(
                        model = state.dog.photoUrl, // Updated here
                        contentDescription = state.dog.breedName,
                        modifier = Modifier.size(largura * TELA_LARGURA * 0.55f)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = largura * MOLDURA_ESQUERDA, y = altura * (TELA_TOPO + TELA_ALTURA))
                    .width(largura * MOLDURA_LARGURA)
                    .height(altura * (MOLDURA_BASE - (TELA_TOPO + TELA_ALTURA))),
                contentAlignment = Alignment.Center
            ) {
                val state = viewModel.uiState
                if (state is DogUiState.Success) {
                    Text(
                        text = state.dog.breedName.replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = CorTexto
                    )
                }
            }

            OutlinedTextField(
                value = termoBusca,
                onValueChange = { termoBusca = it },
                label = { Text("Name") },
                singleLine = true,
                textStyle = TextStyle(fontWeight = FontWeight.SemiBold, color = CorTexto),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { viewModel.buscar(termoBusca) }),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = CorBorda,
                    unfocusedBorderColor = CorBorda,
                    focusedLabelColor = CorTexto,
                    unfocusedLabelColor = CorTexto,
                    cursorColor = CorTexto
                ),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = largura * MOLDURA_ESQUERDA, y = altura * 0.68f)
                    .width(largura * MOLDURA_LARGURA)
            )

            Row(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = largura * MOLDURA_ESQUERDA, y = altura * 0.80f)
                    .width(largura * MOLDURA_LARGURA),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
            }
        }
    }
}