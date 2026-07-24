# Wallet API

API REST de gestion de portefeuilles electroniques (Utilisateurs, Portefeuilles, Transactions),
construite avec une architecture en couches propre (Controller / Service / Repository / DTO / Mapper).

## Stack technique

- Java 21
- Spring Boot 3.5.x
- Spring Web
- Spring Data JPA
- PostgreSQL
- Lombok
- Bean Validation
- SpringDoc OpenAPI (Swagger)
- Maven
- Docker / Docker Compose

## Architecture

```
com.marieme.wallet
├── config          → configuration Swagger, JPA Auditing
├── controller       → endpoints REST
├── dto
│   ├── request       → objets d'entree (validation)
│   └── response       → objets de sortie
├── entity            → entites JPA (User, Wallet, Transaction)
├── enums             → TransactionType, TransactionStatus
├── exception         → exceptions metier + handler global
├── mapper            → conversion entite <-> DTO
├── repository        → interfaces Spring Data JPA
├── service           → interfaces metier
├── service/impl      → implementations metier
└── utils             → constantes
```

## Modele de donnees

```
User (1) ───< Wallet (1) ───< Transaction
```

- Un **User** possede plusieurs **Wallet**
- Un **Wallet** possede plusieurs **Transaction**
- Chaque transaction impacte automatiquement le solde du portefeuille

## Demarrage rapide

### 1. Avec Docker (recommande)

```bash
docker compose up --build
```

L'API est alors disponible sur `http://localhost:8080`.

### 2. En local

Prerequis : Java 21, Maven 3.9+, PostgreSQL 16 en local (base `walletdb`).

```bash
mvn clean install
mvn spring-boot:run
```

Adapter au besoin `src/main/resources/application.yml` (url, username, password).

## Documentation API (Swagger)

Une fois l'application demarree :

```
http://localhost:8080/swagger-ui.html
```

## Exemples d'endpoints

| Methode | URL                                    | Description                          |
|---------|-----------------------------------------|---------------------------------------|
| POST    | /api/v1/users                          | Creer un utilisateur                 |
| GET     | /api/v1/users/{id}                     | Recuperer un utilisateur             |
| GET     | /api/v1/users                          | Lister les utilisateurs (pagine)     |
| POST    | /api/v1/wallets                        | Creer un portefeuille                |
| GET     | /api/v1/wallets/user/{userId}          | Portefeuilles d'un utilisateur       |
| POST    | /api/v1/transactions                   | Creer une transaction (depot/retrait)|
| GET     | /api/v1/transactions/wallet/{walletId} | Transactions d'un portefeuille       |
| PATCH   | /api/v1/transactions/{id}/annuler      | Annuler une transaction              |

Une collection Postman prete a l'emploi est disponible dans `postman/wallet-api.postman_collection.json`.

## Tests

```bash
mvn test
```

Les tests utilisent une base H2 en memoire (profil `test`).

## Regles metier notables

- Impossible de creer deux utilisateurs avec le meme email
- Un retrait ou transfert sortant est refuse si le solde est insuffisant
- L'annulation d'une transaction restaure automatiquement le solde du portefeuille
- Les entites ne sont jamais exposees directement : uniquement via des DTO

## Pistes d'evolution

- Authentification JWT + Spring Security
- Audit utilisateur (qui a cree/modifie quoi)
- Recherche multicritere et filtres avances
- Tests d'integration complets (Testcontainers PostgreSQL)
- CI/CD (GitHub Actions)
