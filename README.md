# ReptiGuide — projet Android (version expert, socle initial)

Application Android native en Kotlin + Jetpack Compose. Cette première base comprend :
- une bibliothèque locale de démonstration avec recherche ;
- des fiches espèce et leurs rubriques techniques ;
- des pages Terrarium, Chauffage/Éclairage et Suivi ;
- une interface en français et une navigation inférieure.

## Ouvrir et générer l'APK
1. Installer Android Studio (version récente) et le JDK 17 ou 21.
2. Ouvrir le dossier `ReptiGuide_Android` dans Android Studio.
3. Laisser Gradle synchroniser les dépendances.
4. Brancher un téléphone Android avec le débogage USB ou créer un émulateur.
5. Pour générer l'APK : **Build > Build Bundle(s) / APK(s) > Build APK(s)**.
6. L'APK debug sera généralement créé dans `app/build/outputs/apk/debug/app-debug.apk`.

## État du projet
Version 0.1.0 — socle de navigation et données d'exemple. Ce ZIP contient le code source, pas un APK compilé.

## Prochaines étapes techniques
- remplacer les exemples par une base de données documentée et versionnée ;
- ajouter Room pour les données locales et les animaux personnels ;
- ajouter filtres par groupe, biotope, difficulté et statut de conservation ;
- construire les calculateurs de terrarium, photopériode et chauffage ;
- ajouter les sources, dates de révision et avertissements par fiche ;
- tests sur appareils réels et validation des recommandations par un vétérinaire NAC / spécialiste reptiles.

## Sécurité et bien-être animal
Les valeurs des fiches actuelles sont des exemples de démonstration, pas un protocole universel. Les paramètres doivent être vérifiés selon l'espèce, la sous-espèce, l'âge, l'état de santé, l'installation et les sources spécialisées à jour.
