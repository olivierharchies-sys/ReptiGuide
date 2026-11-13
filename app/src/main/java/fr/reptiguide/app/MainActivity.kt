package fr.reptiguide.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Forest = Color(0xFF174D3A)
private val Leaf = Color(0xFF3E8062)
private val Cream = Color(0xFFF4F3EA)

data class Species(
    val name: String, val latin: String, val group: String, val difficulty: String,
    val hot: String, val cool: String, val humidity: String, val enclosure: String,
    val heat: String, val uvb: String, val diet: String, val note: String
)

private val speciesData = listOf(
    Species("Gecko léopard", "Eublepharis macularius", "Gecko", "Débutant averti",
        "31–33 °C (zone chaude)", "24–26 °C (zone fraîche)", "Gradient sec; cachette humide dédiée",
        "Au moins 90 × 45 × 45 cm pour un adulte, à adapter à l'individu",
        "Source de chaleur contrôlée par thermostat; gradient mesuré au niveau du sol",
        "UVB faible recommandé avec zone d'ombre et choix de retrait",
        "Insectes nourriciers variés, supplémentation adaptée",
        "Valeurs indicatives : vérifier les recommandations actuelles et la réglementation locale."),
    Species("Pogona vitticeps", "Pogona vitticeps", "Agamidé", "Intermédiaire",
        "Point de basking à définir par mesure de surface", "Zone de retrait fraîche distincte",
        "Environnement globalement aride avec hydratation appropriée",
        "Grand terrarium horizontal; dimensions à dimensionner selon taille adulte",
        "Lampe de basking + thermostat/dimming compatible; mesurer la surface",
        "UVB linéaire T5 adapté à la distance et au réflecteur",
        "Végétaux adaptés et insectes selon âge",
        "Ne pas appliquer une température unique : mesurer plusieurs zones et la température de surface."),
    Species("Python royal", "Python regius", "Serpent", "Intermédiaire",
        "Zone chaude généralement autour de 31–33 °C", "Zone fraîche autour de 25–27 °C",
        "Humidité contrôlée, ventilation et substrat adaptés",
        "Enclos permettant extension complète et thermorégulation",
        "Panneau radiant ou autre source sécurisée pilotée par thermostat",
        "UVB faible optionnel/recommandé selon installation; cycle lumineux régulier",
        "Rongeurs de taille appropriée, décongelés et réchauffés correctement",
        "Surveiller les variations saisonnières, la ventilation et les risques de brûlure."),
    Species("Gecko à crête", "Correlophus ciliatus", "Gecko", "Débutant averti",
        "Éviter les surchauffes; zone tempérée", "Gradient doux avec possibilité de retrait",
        "Humidité cyclique avec séchage partiel et ventilation",
        "Terrarium vertical, hauteur et volume adaptés à l'adulte",
        "Chauffage souvent limité; thermostat indispensable si nécessaire",
        "UVB faible possible avec zones ombragées",
        "Aliment complet formulé pour geckos frugivores + insectes selon programme",
        "Espèce sensible aux températures trop élevées : priorité à la prévention de la surchauffe."),
    Species("Tortue d'Hermann", "Testudo hermanni", "Tortue terrestre", "Intermédiaire",
        "Basking et gradient selon saison et installation", "Zone de retrait fraîche",
        "Gradient de microclimats; accès à eau peu profonde",
        "Enclos extérieur sécurisé privilégié si climat et réglementation le permettent",
        "Basking sécurisé et adapté; hibernation uniquement avec protocole compétent",
        "UVB indispensable en installation intérieure",
        "Végétaux fibreux adaptés à l'espèce",
        "Les besoins varient selon sous-espèce, saison, âge et conditions locales."),
    Species("Caméléon casqué", "Chamaeleo calyptratus", "Caméléon", "Avancé",
        "Basking mesuré et contrôlé selon âge/sexe", "Zone fraîche ventilée",
        "Cycles de brumisation avec séchage et drainage",
        "Grand terrarium haut, très ventilé, avec végétation et perchoirs",
        "Basking localisé, sans surchauffer l'ensemble du volume",
        "UVB linéaire adapté à la distance et aux branches",
        "Insectes variés supplémentés selon protocole",
        "Maintenance exigeante : ventilation, hydratation et paramètres doivent être suivis de près.")
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ReptiGuideApp() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReptiGuideApp() {
    var tab by remember { mutableStateOf(0) }
    var query by remember { mutableStateOf("") }
    var selected by remember { mutableStateOf<Species?>(null) }
    MaterialTheme(colorScheme = lightColorScheme(primary = Forest, secondary = Leaf, background = Cream)) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text("ReptiGuide", fontWeight = FontWeight.Bold, fontSize = 22.sp)
                            Text("GUIDE EXPERT • REPTILES", fontSize = 10.sp, letterSpacing = 1.5.sp)
                        }
                    },
                    actions = { Icon(Icons.Default.Tune, contentDescription = "Paramètres", modifier = Modifier.padding(16.dp)) },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Forest, titleContentColor = Color.White, actionIconContentColor = Color.White)
                )
            },
            bottomBar = {
                NavigationBar(containerColor = Color.White) {
                    listOf("Espèces", "Terrarium", "Chauffage", "Suivi").forEachIndexed { i, label ->
                        NavigationBarItem(selected = tab == i, onClick = { tab = i; selected = null },
                            icon = { Icon(listOf(Icons.Default.Pets, Icons.Default.Home, Icons.Default.DeviceThermostat, Icons.Default.Checklist)[i], contentDescription = label) },
                            label = { Text(label) })
                    }
                }
            },
            containerColor = Cream
        ) { padding ->
            Box(Modifier.padding(padding).fillMaxSize()) {
                when {
                    selected != null -> SpeciesDetail(selected!!) { selected = null }
                    tab == 0 -> SpeciesList(query, { query = it }, { selected = it })
                    tab == 1 -> InfoPage("Terrariums recommandés", "Choisir le biotope avant le matériel.",
                        listOf("Désertique : gradient thermique, abris et substrat adapté",
                            "Tropical : drainage, ventilation et humidité cyclique",
                            "Arboricole : hauteur, perchoirs et circulation d'air",
                            "Semi-aquatique : zone terrestre, filtration et qualité de l'eau",
                            "Bioactif : drainage, microfaune et substrat validé pour l'espèce"))
                    tab == 2 -> InfoPage("Chauffage & éclairage", "La sécurité thermique est prioritaire.",
                        listOf("Thermostat adapté à la technologie de chauffe",
                            "Sonde fixée et positionnée selon les instructions du fabricant",
                            "Protection physique contre tout contact avec la source chaude",
                            "Mesurer les surfaces avec un thermomètre infrarouge",
                            "Contrôler le gradient avec plusieurs thermomètres",
                            "UVB : choisir selon indice UVI cible, distance, réflecteur et obstacles"))
                    else -> InfoPage("Suivi de maintenance", "Le carnet personnel sera enrichi dans les prochaines versions.",
                        listOf("Enregistrer les animaux et leurs identifiants",
                            "Noter poids, mues, repas et observations",
                            "Programmer nettoyage et remplacement des lampes",
                            "Consigner les températures et l'humidité mesurées",
                            "Exporter un historique pour le vétérinaire spécialisé"))
                }
            }
        }
    }
}

@Composable
fun SpeciesList(query: String, onQuery: (String) -> Unit, onSelect: (Species) -> Unit) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Bibliothèque des espèces", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Forest)
        Text("Fiches techniques • paramètres • maintenance", color = Color.Gray, fontSize = 13.sp)
        Spacer(Modifier.height(14.dp))
        OutlinedTextField(value = query, onValueChange = onQuery, modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Rechercher une espèce…") }, leadingIcon = { Icon(Icons.Default.Search, null) },
            shape = RoundedCornerShape(16.dp), singleLine = true)
        Spacer(Modifier.height(12.dp))
        Text("${speciesData.count { it.name.contains(query, true) || it.latin.contains(query, true) || it.group.contains(query, true) }} espèces dans cette version de démonstration",
            color = Leaf, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp), contentPadding = PaddingValues(bottom = 16.dp)) {
            items(speciesData.filter { it.name.contains(query, true) || it.latin.contains(query, true) || it.group.contains(query, true) }) { item ->
                Card(Modifier.fillMaxWidth().clickable { onSelect(item) }, shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(2.dp)) {
                    Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(48.dp).background(Color(0xFFE5EFE8), RoundedCornerShape(14.dp)), contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Pets, contentDescription = null, tint = Forest)
                        }
                        Spacer(Modifier.width(12.dp))
                        Column(Modifier.weight(1f)) {
                            Text(item.name, fontWeight = FontWeight.Bold, color = Forest)
                            Text(item.latin, fontSize = 12.sp, color = Color.Gray)
                            Text("${item.group} • ${item.difficulty}", fontSize = 11.sp, color = Leaf)
                        }
                        Icon(Icons.Default.ChevronRight, null, tint = Leaf)
                    }
                }
            }
        }
    }
}

@Composable
fun SpeciesDetail(s: Species, onBack: () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        Row(Modifier.fillMaxWidth().background(Forest).padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null, tint = Color.White) }
            Column {
                Text(s.name, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(s.latin, color = Color.White.copy(alpha = .8f), fontSize = 12.sp)
            }
        }
        LazyColumn(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            item { Text("Fiche de maintenance", fontWeight = FontWeight.Bold, fontSize = 22.sp, color = Forest) }
            item { DetailCard("Gradient thermique", "Zone chaude : ${s.hot}\nZone fraîche : ${s.cool}") }
            item { DetailCard("Humidité", s.humidity) }
            item { DetailCard("Terrarium", s.enclosure) }
            item { DetailCard("Chauffage", s.heat) }
            item { DetailCard("UVB & éclairage", s.uvb) }
            item { DetailCard("Alimentation", s.diet) }
            item {
                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF2D8))) {
                    Row(Modifier.padding(14.dp), verticalAlignment = Alignment.Top) {
                        Icon(Icons.Default.WarningAmber, null, tint = Color(0xFF8B5A00))
                        Spacer(Modifier.width(8.dp))
                        Text(s.note, fontSize = 12.sp, color = Color(0xFF654600))
                    }
                }
            }
        }
    }
}

@Composable
fun DetailCard(title: String, body: String) {
    Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(16.dp)) {
        Column(Modifier.fillMaxWidth().padding(16.dp)) {
            Text(title, color = Forest, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(6.dp))
            Text(body, color = Color.DarkGray, fontSize = 14.sp)
        }
    }
}

@Composable
fun InfoPage(title: String, subtitle: String, points: List<String>) {
    LazyColumn(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Text(title, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Forest)
            Text(subtitle, color = Color.Gray, fontSize = 13.sp)
        }
        items(points) { point ->
            Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(16.dp)) {
                Row(Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
                    Icon(Icons.Default.CheckCircle, null, tint = Leaf)
                    Spacer(Modifier.width(10.dp))
                    Text(point, fontSize = 14.sp)
                }
            }
        }
        item {
            Text("Les recommandations doivent être validées espèce par espèce avec des sources vétérinaires et herpétologiques reconnues. Les valeurs génériques ne remplacent pas une mesure réelle ni un avis spécialisé.",
                color = Color.Gray, fontSize = 11.sp)
        }
    }
}
