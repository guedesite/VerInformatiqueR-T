# VersInformatiqueR-T
[![Release](https://jitpack.io/v/guedesite/VersInformatiqueR-T.svg)](https://jitpack.io/#guedesite/VersInformatiqueR-T)

#### Programme de simulation de la reproduction d'un [ver informatique](https://fr.wikipedia.org/wiki/Ver_informatique) dans un réseau SSH non protégé.
---
### Commandes:
| Nom    | Description | argument(s) |
| ----    | ---------- | -----------  |
| [timer](https://github.com/guedesite/VersInformatiqueR-T/blob/main/src/fr/guedesite/vinfo/cmd/timer.java)               | Affiche le temps d'éxécution des commandes | &lt;on/off/status&gt; |
| [clear](https://github.com/guedesite/VersInformatiqueR-T/blob/main/src/fr/guedesite/vinfo/cmd/clear.java)               | Vide la console | &lt;&gt; |
| [scan](https://github.com/guedesite/VersInformatiqueR-T/blob/main/src/fr/guedesite/vinfo/cmd/scan.java)                 | Scan le réseau et index les hôtes disponible | &lt;show&gt; |
| [ssh](https://github.com/guedesite/VersInformatiqueR-T/blob/main/src/fr/guedesite/vinfo/cmd/ssh.java)                   | Initialiser la connexion d'un hôte | &lt;id (int)&gt; &lt;open/close&gt; &lt;user (String)&gt; &lt;password (String)&gt; |
| [brute](https://github.com/guedesite/VersInformatiqueR-T/blob/main/src/fr/guedesite/vinfo/cmd/brute.java)               | brute force le mot de passe d'une connection | &lt;id (int)&gt; &lt;user (String)&gt; &lt;true/false (logging)&gt; |
| [openTerminal](https://github.com/guedesite/VersInformatiqueR-T/blob/main/src/fr/guedesite/vinfo/cmd/openTerminal.java) | Ouvre le terminal d'une connection | &lt;id (int)&gt; |
| [sendFile](https://github.com/guedesite/VersInformatiqueR-T/blob/main/src/fr/guedesite/vinfo/cmd/sendFile.java)         | Envois un fichier à une connexion SSH | &lt;id (int)&gt; &lt;Fichier source OU filechooser pour séléctionner le fichier&gt; &lt;Destination&gt;|
| [logger](https://github.com/guedesite/VersInformatiqueR-T/blob/main/src/fr/guedesite/vinfo/cmd/logger.java)             | Log les activité de l'utilisateur | &lt;&gt; |