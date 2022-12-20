# Launcher Minecraft Custom

Ce projet vise à créer et distribuer un launcher personnalisé minecraft possédant plusieurs modpacks pour le serveur UniverseCorp.
Des changements dans la structure de base du Launcher peuvent amener une redistribution de celui-ci.


## Librairies utilisées

- [OpenAuth](https://github.com/Litarvan/OpenAuth) pour l'authentification Mojang/Microsoft
- [FontAwesomeFX](https://github.com/Jerady/fontawesomefx-jigsaw-modules) pour ajouter de beaux icônes aux boutons, labels, ...
- [FlowUpdater](https://github.com/FlowArg/FlowUpdater) pour installer, mettre à jour et lancer minecraft vanilla/forge/fabric
- [OpenLauncherLib](https://github.com/FlowArg/OpenLauncherLib) pour écrire et sauvegarder des logs, configs
- [Oshi-Core](https://github.com/oshi/oshi) pour la sélection de RAM et l'analyse de l'OS afin de déterminer une arborescence adaptée


## OS Supportés

Windows • Mac • Linux (Debian, Ubuntu)


## Comment utiliser ce launcher ?

- Ouvrir IntelliJ et cloner le projet :
```shell
git clone https://github.com/MaximeBro/Launcher.git
```


- Build le projet pour importer toutes les librairies
```shell
Gradle -> Tasks -> build -> build
```


- Exporter le projet en .jar
```shell
Gradle -> Tasks -> build -> jar
```


- Exporter le projet en installateur windows + exécutable .msi, .jar et mac
```shell
Gradle -> Tasks -> javapackager -> packageMyAppForWindows
```



## Aperçu du menu principal du launcher

![UniverseCorp Launcher Custom](http://mc.universecorp.fr/images/uclauncher.png "Aperçu du Launcher d'UniverseCorp")
