#  Mon Application POOC ChatBox pour OpenClassroom

Cette application est une API web développée avec **Spring Boot**, utilisant une base de données **H2**, et intégrant un système d'authentification via **Spring Security** (avec JWT ou session classique selon configuration).

---

##  Lancer l'application

1. **Cloner le projet :**

```bash
git clone https://github.com/joselct17/chatbox_projet_13
cd chatbox


## L'application utilise H2 en mémoire (par défaut).

 Accès à la console H2 :
URL : http://localhost:8080/h2-console

JDBC URL : jdbc:h2:mem:testdb

Utilisateur : sa

Mot de passe : (laisser vide)

Fichier SQL backup : /chatbox/backup.sql


Pour se connecter à l'application, vous pouvez utiliser les identifiants suivants :

| Identifiant | Mot de passe | Rôle (exemple) |
| ----------- | ------------ | -------------- |
| `user1`     | `password`   | ROLE\_USER     |
| `user2`     | `password`   | ROLE\_USER     |
| `sav`       | `password`   | ROLE\_SAV      |



