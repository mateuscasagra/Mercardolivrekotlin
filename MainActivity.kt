@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.mercardolivre
import com.example.mercardolivre.PromosActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mercardolivre.ui.theme.MercardoLivreTheme
import kotlin.math.min

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MercardoLivreTheme {
                Tela()
            }
        }
    }
}

data class NavItem(
    val label: String,
    val icon: ImageVector,
)

data class Produto(
    val icone: ImageVector,
    val titulo: String,
    val preco: Double,
)
@Composable
fun Tela() {
    var textPesquisa by remember { mutableStateOf("")}
    var itemSelecionado by remember { mutableStateOf(1)}

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Yellow,
                    titleContentColor = Color.Black
                ),
                title = {
                    TextField(
                        value = textPesquisa,
                        onValueChange = { novoTexto -> textPesquisa = novoTexto },
                        placeholder = { Text("Buscar no Mercado Livre") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            //.height(50.dp)
                            .clip(RoundedCornerShape(percent = 50)),
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Ícone de busca") },
                        colors = TextFieldDefaults.colors(
                            // Cores do Conteúdo
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Gray,
                            cursorColor = Color(0xFF00A650),
                            // Cores do Container
                            focusedContainerColor = Color(0xFFF5F5F5),
                            unfocusedContainerColor = Color(0xFFF5F5F5),
                            disabledContainerColor = Color.LightGray,
                            // "Cores" para remover a linha indicadora
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        )
                    )
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Carrinho")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                val itens = listOf(
                    NavItem("Favoritos", Icons.Default.FavoriteBorder),
                    NavItem("Minhas compras", Icons.Default.List),
                    NavItem("Notificações", Icons.Default.Notifications)
                )

                itens.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = itemSelecionado == index,
                        onClick = { itemSelecionado = index },
                        label = { Text(item.label) },
                        icon = {Icon(item.icon, contentDescription = item.label)}
                    )
                }
            }
        }
    ) { innerPadding ->
        Column (
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color.White)
        ) {
            BannerPromocional()
            SecaoIcons()
            SecaoProdutos()
        }
    }
}

fun trocaTela(context: Context){
    val intent = Intent(context, PromosActivity::class.java);
    context.startActivity(intent);
}

@Composable
fun BannerPromocional() {
    val context =  LocalContext.current;
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable {trocaTela(context)}
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Blue),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("LIQUIDAÇÃO PARA COMPRAR AGORA",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp)
        }
    }
}

@Composable
fun SecaoIcons(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Icone(icone = Icons.Default.Home, texto = "Entregar")
        Icone(icone = Icons.Default.Check, texto = "Ofertas")
        Icone(icone = Icons.Default.ThumbUp, texto = "Cupons")
        Icone(icone = Icons.Default.Phone, texto = "Celulares")
        Icone(icone = Icons.Default.AddCircle, texto = "Moda")
    }
}

@Composable
fun Icone(icone: ImageVector, texto: String){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Icon(icone, contentDescription = texto, modifier = Modifier.size(30.dp), tint = Color.Black)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(texto, fontSize = 12.sp, textAlign = TextAlign.Center)
    }
}

@Composable
fun SecaoProdutos(){
    val listaProdutos = listOf(
        Produto(Icons.Default.Favorite, "Produto", 100.00),
        Produto(Icons.Default.Favorite, "Produto", 100.00),
        Produto(Icons.Default.Favorite, "Produto", 100.00),
        Produto(Icons.Default.Favorite, "Produto", 100.00),
        Produto(Icons.Default.Favorite, "Produto", 100.00),
        Produto(Icons.Default.Favorite, "Produto", 100.00)
    )
    Column(modifier = Modifier.padding(horizontal = 16.dp)){
        Text("Também te interessa", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        for(i in listaProdutos.indices step 3) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)){
                for(j in i until min(i + 3, listaProdutos.size)){
                    val produto = listaProdutos[j]
                    CardProduto(
                        icone = produto.icone,
                        titulo = produto.titulo,
                        preco = produto.preco,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun CardProduto(icone: ImageVector, titulo: String, preco: Double, modifier: Modifier = Modifier){
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icone,
                    contentDescription = titulo,
                    modifier = Modifier.size(72.dp),
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier.height(40.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = titulo,
                    fontSize = 15.sp,
                    maxLines = 2,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "R$$preco",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    MercardoLivreTheme {
        Tela()
    }
}
