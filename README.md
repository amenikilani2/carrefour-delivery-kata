# Projet Spring Boot avec Java 21

## Description

Ce projet est une application Spring Boot construite avec Java 21. L'application fournit une API RESTful pour gérer Les clients et les commandes. Elle inclut des fonctionnalités de base comme la création, la lecture, la mise à jour et la suppression (CRUD).

## Prérequis

Avant de commencer, assurez-vous d'avoir les éléments suivants installés sur votre machine :

- [Java 21 JDK](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
- [Maven](https://maven.apache.org/install.html)
- [Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)

## Installation

1. Clonez le dépôt :
   bash
   git clone https://github.com/votre-utilisateur/votre-repo.git
   cd votre-repo


2. Compilez et packagez l'application avec Maven :
   bash
   mvn clean install


## Utilisation

1. Démarrez l'application :
   bash
   mvn spring-boot:run


2. L'application sera accessible à l'adresse suivante :

   http://localhost:8080


## API Endpoints

L'application expose les endpoints suivants :

- `GET /api/customers` : Récupère tous les clients.
- `GET /api/customers/{id}` : Récupère un client par son ID.
- `POST /api/customers` : Crée un nouvel client.
- `DELETE /api/customers/{id}` : Supprime un client.
- - `GET /api/orders` : Récupère toutes les commandes.
- `GET /api/orders/{id}` : Récupère un commande par son ID.
- `POST /api/orders` : Crée un nouvel commande.
- `DELETE /api/orders/{id}` : Supprime un commande.

## Configuration

Les configurations de l'application sont définies dans le fichier `application.properties` situé dans le répertoire `src/main/resources`.

## Tests

Pour exécuter les tests unitaires et les tests end-to-end, utilisez les commandes suivantes :

- Tests unitaires :
  bash
  mvn test


## Licence

Ce projet est sous licence MIT. Voir le fichier [LICENSE](LICENSE) pour plus de détails.

## Auteurs

- **Ameni Kilani** - *Développeur principal* - [Votre Profil](https://github.com/votre-utilisateur)