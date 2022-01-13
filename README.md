# VersInformatiqueR-T
[![Release](https://jitpack.io/v/guedesite/VersInformatiqueR-T.svg)](https://jitpack.io/#guedesite/VersInformatiqueR-T)

#### Programme de simulation de la reproduction d'un [ver informatique](https://fr.wikipedia.org/wiki/Ver_informatique) dans un réseau SSH non protégé.
---
### Commandes:
| Nom    | Description | argument(s) |
| ----    | ---------- | -----------  |
| [timer](https://github.com/guedesite/VersInformatiqueR-T/blob/main/src/fr/guedesite/vinfo/cmd/timer.java)               | Affiche le temps d'éxécution des commandes | <on/off/status> |
| [clear](https://github.com/guedesite/VersInformatiqueR-T/blob/main/src/fr/guedesite/vinfo/cmd/clear.java)               | Vide la console | <> |
| [scan](https://github.com/guedesite/VersInformatiqueR-T/blob/main/src/fr/guedesite/vinfo/cmd/scan.java)                 | Scan le réseau et index les hôtes disponible | <show> |
| [ssh](https://github.com/guedesite/VersInformatiqueR-T/blob/main/src/fr/guedesite/vinfo/cmd/ssh.java)                   | Initialiser la connexion d'un hôte | <id (int)> <open/close> <user (String)> <password (String)> |
| [brute](https://github.com/guedesite/VersInformatiqueR-T/blob/main/src/fr/guedesite/vinfo/cmd/brute.java)               | brute force le mot de passe d'une connection | <id (int)> <user (String)> <true/false (logging)> |
| [openTerminal](https://github.com/guedesite/VersInformatiqueR-T/blob/main/src/fr/guedesite/vinfo/cmd/openTerminal.java) | Ouvre le terminal d'une connection | <id (int)> |
| [sendFile](https://github.com/guedesite/VersInformatiqueR-T/blob/main/src/fr/guedesite/vinfo/cmd/sendFile.java)         | Envois un fichier à une connexion SSH | <id (int)> <Fichier source OU filechooser pour ouvrir séléctionner le fichier> <Destination>|
| [logger](https://github.com/guedesite/VersInformatiqueR-T/blob/main/src/fr/guedesite/vinfo/cmd/logger.java)             | Log les activité de l'utilisateur | <> |