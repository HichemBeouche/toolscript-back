# Projet ToolScript

## Table des Matières
1. [Technologies](#technologies)
2. [Installation](#installation)
3. [API](#api)

***
## Technologies
***

La partie Back est rédigée en java avec l'utilisation du framework Spring.

***
## Installation
***

Introduction à l'installation du projet :

Pour la base de donnée :

* Pour pouvoir accéder à la base de données, il faut se connecter au VPN Cisco

Récuperer le code :

* Créer un nouveau dossier, et ouvrir un invite de commande à l'intérieur

* Executer la commande suivante
```
$ git clone https://github.com/dengler5u/ToolScript-Back.git
```
Executer le Back :

* Importer dans Eclipse ou IntelliJ le dossier ToolScript-Back 
* Se rendre dans Java Ressources -> src/main/java -> com.api.toolscript et executer ToolScriptApp.java

***
## API
***

## Table des Controllers
1. [AuthController](#authcontroller)
2. [ModuleController](#modulecontroller)
3. [StoryController](#storycontroller)
4. [SubmoduleController](#submodulecontroller)
5. [UserController](#usercontroller)

***
## AuthController
***

Post
  - path="/login"
  - path="/register"

***
## ModuleController
***

Get
  - path="/module/{id_module}"
  - path="/modules/{id_story}"

Post
  - path="/module/{id_story}/create"

Put
  - path="/module/{id_module}/edit"

Delete
  - path="/module/{id_module}/delete"

***
## StoryController
***

Get
  - path="/usernameAuthors/{id_story}"
  - path="/modules/{id_story}"
  - path="/sharedstories/{id_user}"
  - path="/story/{id_story}/modulesAndSubmodules"
  - path="/story/{id_story}"

Post
  - path="/story/{id_user}/create"
  - path="/story/addReaderOrCoAuthor/{id_story}"

Put
  - path="/story/saveModulesAndSubmodules"
  - path="/story/{id_story}/edit"

Delete
  - path="/story/{id_story}/delete"

***
## SubmoduleController
***

Get
  - path="/submodule/{id_submodule}"
  - path="/submodule/{idStory}/story_submodules"

Post
  - path="/submodule/{id_module}/createSubmodule"

Put
  - path="/submodule/changeName"
  - path="/submodule/changeNote"

Delete
  - path="/submodule/{id_submodule}/delete"
  - path="/submodule/{id_submodule}/deleteAllByStory"

***
## UserController
***

Get
  - path="/user/{id_user}"
  - path="/user/users"

Put
  - path="/user/changePassword"
  - path="/user/changeUsername"
  - path="/user/changeMail"
