
package com.example.mercardolivre

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class PromosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            MaterialTheme {
                MainScreen()
            }
        }
    }
}


@Composable
fun MainScreen() {
    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavigationBar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF0F0F0))
        ) {
            BannerPrincipal()
            IconRow()
            OfertasSection()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = { Text("Festival de Grandes Marcas") },
        navigationIcon = {
            IconButton(onClick = { /* Ação do botão de voltar */ }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Voltar")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFFFEB3B), // Cor amarela
            titleContentColor = Color.Black,
            navigationIconContentColor = Color.Black
        )
    )
}


@Composable
fun BannerPrincipal() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color(0xFFFFEB3B))
            .padding(16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Column {
            Text(
                text = "FESTIVAL DE",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "GRANDES MARCAS",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "ATÉ 60% OFF",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0033A0) // Azul escuro
            )
        }
    }
}


@Composable
fun IconRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFEB3B))
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = "Imagem do produto",
            modifier = Modifier.size(50.dp),
            tint = Color.DarkGray
        )
    }
}


@Composable
fun ShortcutIcon(icon: ImageVector, text: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = Color(0xFF0033A0)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun OfertasSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Text(
            text = "OFERTAS PARA COMPRAR AGORA",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Usando LazyRow para uma lista rolável de produtos
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(5) { index -> // Criando 5 itens de exemplo
                ProdutoCard(item = index + 1)
            }
        }
    }
}


@Composable
fun ProdutoCard(item: Int) {
    Card(
        modifier = Modifier.width(150.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                // Em um app real, aqui iria a imagem do produto
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Imagem do produto",
                    modifier = Modifier.size(50.dp),
                    tint = Color.DarkGray
                )
            }
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = "Nome do Produto $item",
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(){
                    Text(text = "R$ 99,90", color = Color(0xFF0033A0), fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.width(38.dp))
                    BotaoFavorito()
                }

            }
        }
    }
}


@Composable
fun BotaoFavorito() {
    // 1. Crie uma variável de estado para controlar se o item é favorito ou não.
    // `remember` garante que o estado seja mantido quando a tela for redesenhada.
    var isFavorito by remember { mutableStateOf(false) }

    IconButton(
        onClick = {
            // 3. A ação: inverta o valor do estado.
            // Se for 'false', vira 'true'. Se for 'true', vira 'false'.
            isFavorito = !isFavorito
        }
    ) {
        Icon(
            // 2. O ícone exibido agora depende do estado da variável 'isFavorito'.
            imageVector = if (isFavorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = if (isFavorito) "Remover dos favoritos" else "Adicionar aos favoritos",
            modifier = Modifier.size(25.dp),
            // Bônus: Mude a cor também com base no estado!
            tint = if (isFavorito) Color.Red else Color.DarkGray
        )
    }
}
@Composable
fun BottomNavigationBar() {
    NavigationBar(
        containerColor = Color.White
    ) {
        NavigationBarItem(
            selected = true,
            onClick = { },
            icon = { Icon(Icons.Default.Home, contentDescription = "Início") },
            label = { Text("Início") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Favoritos") },
            label = { Text("Favoritos") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") }
        )
    }
}


// --- Preview ---

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        MainScreen()
    }
}


