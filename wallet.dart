// ============================================================================
// SÉQUENCE 1 : Développement Procédural du Wallet (Moderne & Asynchrone)
// ----------------------------------------------------------------------------
// Prototype de "Menu Distributeur" en mode console.
// Écrit de façon PROCÉDURALE (pas de classes métier), mais en s'appuyant
// sur les nouveautés de Dart 3+ (Records, Extension Methods, switch
// expressions) et sur l'asynchronisme (Future / async / await).
// ============================================================================

import 'dart:io';

// ----------------------------------------------------------------------------
// 1. VARIABLES GLOBALES (COLLECTIONS)
// ----------------------------------------------------------------------------

/// Liste des wallets (portefeuilles) enregistrés dans le système.
/// Chaque wallet est représenté par une Map clé/valeur :
/// { "telephone": ..., "nom": ..., "solde": ..., "code": ... }
final List<Map<String, dynamic>> wallets = <Map<String, dynamic>>[];

/// Historique de toutes les opérations (Dépôts, Retraits, Frais).
/// Chaque transaction est une Map :
/// { "telephone": ..., "type": ..., "montant": ..., "date": ... }
final List<Map<String, dynamic>> transactions = <Map<String, dynamic>>[];

// ----------------------------------------------------------------------------
// 2. MÉTHODE D'EXTENSION : FormateurMonnaie
// ----------------------------------------------------------------------------

/// Extension sur le type `double` permettant de formater un montant en CFA.
/// Exemple : 15000.0.enCfa => "15000 CFA"
extension FormateurMonnaie on double {
  String get enCfa {
    // On utilise toStringAsFixed(0) pour éviter d'afficher les décimales
    // inutiles (ex: 15000.0 -> "15000" et non "15000.0").
    return "${toStringAsFixed(0)} CFA";
  }
}

// ----------------------------------------------------------------------------
// CONSTANTES MÉTIER
// ----------------------------------------------------------------------------

/// Taux de frais appliqué sur les retraits : 1%
const double tauxFrais = 0.01;

/// Plafond maximum des frais par transaction : 5 000 CFA
const double plafondFrais = 5000.0;

// ----------------------------------------------------------------------------
// FONCTIONS UTILITAIRES
// ----------------------------------------------------------------------------

/// Recherche un wallet par numéro de téléphone.
/// Retourne `null` si aucun wallet n'est trouvé (dynamic pour rester simple
/// dans un style procédural, sans introduire de valeur "magique").
Map<String, dynamic>? trouverWalletParTelephone(String telephone) {
  for (final w in wallets) {
    if (w["telephone"] == telephone) {
      return w;
    }
  }
  return null;
}

/// Vérifie si un code secret est déjà utilisé par un autre wallet.
bool codeDejaUtilise(String code) {
  return wallets.any((w) => w["code"] == code);
}

/// Calcule les frais de retrait (1%, plafonnés à 5000 CFA).
double calculerFrais(double montant) {
  final double frais = montant * tauxFrais;
  return frais > plafondFrais ? plafondFrais : frais;
}

// ----------------------------------------------------------------------------
// 3. FONCTIONS MÉTIER ASYNCHRONES (Future + Record)
// ----------------------------------------------------------------------------

/// RG 1.1 / RG 1.2 : Création d'un nouveau wallet.
/// Retourne un Record (bool success, String message).
Future<(bool, String)> insererWallet({
  required String telephone,
  required String nom,
  required double solde,
  required String code,
}) async {
  // Simulation d'une latence réseau / base de données (1 seconde).
  await Future.delayed(const Duration(seconds: 1));

  // RG 1.1 : tous les champs sont obligatoires (on vérifie qu'ils ne sont
  // pas vides après avoir été saisis).
  if (telephone.isEmpty || nom.isEmpty || code.isEmpty) {
    return (false, "❌ Tous les champs (téléphone, nom, code) sont obligatoires.");
  }

  // RG 1.1 : le solde initial doit être >= 0.
  if (solde < 0) {
    return (false, "❌ Le solde initial doit être supérieur ou égal à 0.");
  }

  // RG 1.2 : unicité du numéro de téléphone.
  if (trouverWalletParTelephone(telephone) != null) {
    return (false, "❌ Ce numéro de téléphone est déjà associé à un wallet.");
  }

  // RG 1.2 : unicité du code secret.
  if (codeDejaUtilise(code)) {
    return (false, "❌ Ce code secret est déjà utilisé par un autre client.");
  }

  // Toutes les règles sont respectées : on enregistre le wallet.
  wallets.add({
    "telephone": telephone,
    "nom": nom,
    "solde": solde,
    "code": code,
  });

  return (true, "✅ Wallet créé avec succès pour $nom (Solde initial : ${solde.enCfa}).");
}

/// RG 2.1 : Dépôt d'argent sur un wallet existant.
Future<(bool, String)> deposerArgent({
  required String telephone,
  required double montant,
}) async {
  // Simulation d'une latence (accès "base de données").
  await Future.delayed(const Duration(seconds: 1));

  // RG 2.1 : le wallet doit exister.
  final wallet = trouverWalletParTelephone(telephone);
  if (wallet == null) {
    return (false, "❌ Aucun wallet trouvé pour le numéro $telephone.");
  }

  // RG 2.1 : le montant doit être strictement positif.
  if (montant <= 0) {
    return (false, "❌ Le montant du dépôt doit être strictement positif.");
  }

  // Mise à jour du solde.
  wallet["solde"] = (wallet["solde"] as double) + montant;

  // Historisation de la transaction.
  transactions.add({
    "telephone": telephone,
    "type": "Dépôt",
    "montant": montant,
    "date": DateTime.now(),
  });

  return (
    true,
    "✅ Dépôt de ${montant.enCfa} effectué. Nouveau solde : ${(wallet["solde"] as double).enCfa}."
  );
}

/// RG 3.1 / RG 3.2 : Retrait d'argent avec vérification du code secret et
/// application des frais.
Future<(bool, String)> retirerArgent({
  required String telephone,
  required String code,
  required double montant,
}) async {
  // Simulation d'une latence.
  await Future.delayed(const Duration(seconds: 1));

  // RG 3.1 : le wallet doit exister.
  final wallet = trouverWalletParTelephone(telephone);
  if (wallet == null) {
    return (false, "❌ Aucun wallet trouvé pour le numéro $telephone.");
  }

  // RG 3.1 : le code secret doit être valide.
  if (wallet["code"] != code) {
    return (false, "❌ Code secret incorrect.");
  }

  // RG 3.1 : le montant doit être strictement positif.
  if (montant <= 0) {
    return (false, "❌ Le montant du retrait doit être strictement positif.");
  }

  // RG 3.2 : calcul des frais (1%, plafonnés à 5000 CFA).
  final double frais = calculerFrais(montant);
  final double montantTotal = montant + frais;

  // RG 3.1 : le solde doit couvrir le montant + les frais.
  final double soldeActuel = wallet["solde"] as double;
  if (soldeActuel < montantTotal) {
    return (
      false,
      "❌ Solde insuffisant. Solde actuel : ${soldeActuel.enCfa}, "
      "requis : ${montantTotal.enCfa} (dont ${frais.enCfa} de frais)."
    );
  }

  // Mise à jour du solde.
  wallet["solde"] = soldeActuel - montantTotal;

  // Historisation : une transaction pour le retrait, une pour les frais.
  transactions.add({
    "telephone": telephone,
    "type": "Retrait",
    "montant": montant,
    "date": DateTime.now(),
  });
  transactions.add({
    "telephone": telephone,
    "type": "Frais",
    "montant": frais,
    "date": DateTime.now(),
  });

  return (
    true,
    "✅ Retrait de ${montant.enCfa} effectué (frais : ${frais.enCfa}). "
    "Nouveau solde : ${(wallet["solde"] as double).enCfa}."
  );
}

// ----------------------------------------------------------------------------
// FONCTIONS D'AFFICHAGE (UI console)
// ----------------------------------------------------------------------------

void afficherMenu() {
  print("");
  print("** Menu Distributeur (Asynchrone) **");
  print("1 - Créer Wallet");
  print("2 - Faire Dépôt");
  print("3 - Faire Retrait");
  print("4 - Lister les Transactions");
  print("0 - Quitter");
  stdout.write("Votre choix : ");
}

/// RG 4.1 : Liste l'historique complet ou filtré par numéro de téléphone.
void listerTransactions() {
  stdout.write("Filtrer par numéro de téléphone (laisser vide pour tout afficher) : ");
  final String? filtre = stdin.readLineSync();

  // On applique le filtre uniquement s'il n'est pas vide.
  final List<Map<String, dynamic>> resultats = (filtre == null || filtre.isEmpty)
      ? transactions
      : transactions.where((t) => t["telephone"] == filtre).toList();

  if (resultats.isEmpty) {
    print("ℹ️  Aucune transaction trouvée.");
    return;
  }

  print("\n--- Historique des transactions ---");
  for (final t in resultats) {
    final double montant = t["montant"] as double;
    print(
      "[${t["date"]}] ${t["telephone"]} | ${t["type"]} | ${montant.enCfa}"
    );
  }
}

// ----------------------------------------------------------------------------
// 4. INTERFACE CLI ASYNCHRONE (point d'entrée)
// ----------------------------------------------------------------------------

Future<void> main() async {
  // `continuer` pilote la boucle interactive du menu.
  bool continuer = true;

  while (continuer) {
    afficherMenu();
    final String? saisieChoix = stdin.readLineSync();

    // switch expression moderne (Dart 3+) pour parser le choix utilisateur.
    switch (saisieChoix) {
      case "1":
        // --- Création de wallet ---
        stdout.write("Numéro de téléphone : ");
        final String telephone = stdin.readLineSync() ?? "";

        stdout.write("Nom du client : ");
        final String nom = stdin.readLineSync() ?? "";

        stdout.write("Solde initial : ");
        final String soldeSaisi = stdin.readLineSync() ?? "0";
        // tryParse évite un crash si l'utilisateur saisit une valeur invalide.
        final double solde = double.tryParse(soldeSaisi) ?? -1;

        stdout.write("Code secret : ");
        final String code = stdin.readLineSync() ?? "";

        print("⏳ Création du wallet en cours...");
        // await : on attend le résultat du Future avant de continuer.
        final (bool success, String message) = await insererWallet(
          telephone: telephone,
          nom: nom,
          solde: solde,
          code: code,
        );
        print(message);

      case "2":
        // --- Dépôt ---
        stdout.write("Numéro de téléphone : ");
        final String telephone = stdin.readLineSync() ?? "";

        stdout.write("Montant à déposer : ");
        final String montantSaisi = stdin.readLineSync() ?? "0";
        final double montant = double.tryParse(montantSaisi) ?? -1;

        print("⏳ Dépôt en cours...");
        final (bool success, String message) = await deposerArgent(
          telephone: telephone,
          montant: montant,
        );
        print(message);

      case "3":
        // --- Retrait ---
        stdout.write("Numéro de téléphone : ");
        final String telephone = stdin.readLineSync() ?? "";

        stdout.write("Code secret : "); 
        final String code = stdin.readLineSync() ?? "";

        stdout.write("Montant à retirer : ");
        final String montantSaisi = stdin.readLineSync() ?? "0";
        final double montant = double.tryParse(montantSaisi) ?? -1;

        print("⏳ Retrait en cours...");
        final (bool success, String message) = await retirerArgent(
          telephone: telephone,
          code: code,
          montant: montant,
        );
        print(message);

      case "4":
        // --- Liste des transactions ---
        listerTransactions();

      case "0":
        // --- Sortie de la boucle ---
        print("👋 Merci d'avoir utilisé le distributeur. À bientôt !");
        continuer = false;

      default:
        // Choix non reconnu.
        print("⚠️  Choix invalide, veuillez réessayer.");
    }
  }
}
