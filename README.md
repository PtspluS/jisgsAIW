# jisgsAIW

# Introduction

Pour ce tutoriel nous étudierons deux cas. Nous partons du principe que vous êtes en possessions d’un ordinateur et que Android studio est installé dessus.

Le premier est que vous souhaitez installer l’application depuis les fichiers présents sur la page du [GitHub](https://github.com/PtspluS/jigsAIw).

Le second est que vous êtes déjà en possession de le .apk et que vous souhaitez l’installer sur votre téléphone portable.

Si vous vous trouvez dans le second cas, nous vous invitons à vous rendre dans la seconde partie de ce tutoriel qui se trouve page 6.

Vous trouverez ci-dessous un sommaire détaillé pour vous aider dans vos recherches.

# Première étape 

## 1) Installation depuis GitHub

Le projet se trouvant à l’adresse GitHub précédemment indiqué dans l’introduction ([GitHub](https://github.com/PtspluS/jisgsAIW)), il vous faut aller le copier pour que vous puissiez créer le .apk de l’application.

Si vous souhaitez utiliser l’outil Git inclue dans Android Studio, je vous invite à sauter cette partie et à vous rendre à la partie 2.

Dans le cas contraire, deux possibilités s’offrent à vous.

#### Cloner depuis le GitHub via internet

Dans ce cas rendez-vous sur la page [GitHub du projet](https://github.com/PtspluS/jisgsAIW).
Une fois sur cette page assurez-vous d’être sur la bonne brache, c’est-à-dire sur la branche master.  
Par défaut GitHub vous met sur cette branche. Cependant si ce n’est pas le cas voici comment faire.

##### Changement de branche

Allez vers le haut de la page et cliquez sur le bouton branche.
Une fois cliqué sur le bouton, un menu s’offre à vous. Il vous suffit alors de cliquer sur le bouton master pour être positionné dans la branche master. Un tic viendra mettre en évidence la branche que vous avez sélectionné.

#### Clonage 
Une fois que vous êtes sur la bonne branche, il vous suffit de cliquer sur le bouton rectangulaire vert intitulé _Clone or download_.

Cliquez ensuite sur le bouton _Dowload ZIP_.  
Une fois le téléchargement effectué, déplacé le .zip dans le répertoire que vous voulez et extrayez les fichiers.

### Utiliser le terminal

Placez-vous dans le répertoire où vous souhaitez travailler et tapez la commande :

 > git clone_ https://github.com/PtspluS/jisgsAIW

Vous téléchargerez alors les fichiers dans votre répertoire courant.

## Ouverture dans Android Studio

Il est possible de créer directement un projet avec Android Studio en clonant le GitHub.

Pour se faire il vous suffit d’aller dans « File>New>Project from version control>Git »

Une nouvelle fenêtre s’ouvrira alors et il suffira de renseigner dans la case URL, l’url du projet GitHub.

Ensuite cliquez sur le bouton _Clone_ pour cloner le projet en créant un nouveau projet.

Si vous avez opéré de la sorte, vous pouvez passer le prochain chapitre.

### Nouveau projet sans passer par l’outil inclue

Dans le cas où vous ne souhaitez pas cloner le projet via l’outil Android studio et que vous avez déjà cloné le projet, voici comment je vous propose de procéder.

Dans Android studio allez dans « File>New>Import Project… ».

Il vous suffit alors d’indiquer le chemin jusqu’aux fichiers que vous avez clonés depuis GitHub.

## 3) Création du .apk

Maintenant que le projet est créé, il est conseiller de le build une première fois.

Pour ce faire allez dans « Build>Make project ». Une fois que le build s’est fait sans problème vous pouvez passer à l’étape suivante.

Pour générer un .apk non signé, il faut aller dans « Build>Build Bundle/APK>Build APK »

L’APK se trouvera alors dans « ./app/build/outputs/apk/debug/app-debug.apk »

Par défaut le .apk généré est considéré comme un .apk de débug.

Pour pallier cela il est possible de générer une APK signée.

Si tel est votre volonté nous vous invitons à suivre le tutoriel bien détaillé de la documentation officiel d’Android via ce lien [https://developer.android.com/studio/publish](https://developer.android.com/studio/publish).

Le build d’une APK signé sert pour une publication en ligne.

# Intallation de l’APK

## 1) Préparation du téléphone portable

Pour pouvoir installer l’APK sans passer par le marché officiel d’Android, il est obligatoire de toucher aux paramètres du téléphone.

L’étape la plus importante consiste à aller dans les paramètres du téléphone et de sélectionner l’option autorisant les sources inconnues.

## 1) Téléchargement

Une fois l’autorisation accordée, il suffit juste de transférer l’APK depuis votre ordinateur vers votre téléphone portable.

Cela peut se faire avec un câble.

## 2) Installation

Lorsque le téléchargement sera fini. Vous pouvez utiliser votre navigateur de fichier inclue dans le téléphone pour trouver l’APK. Cliquez dessus et suivez les instructions qui s’affichent alors à l’écran.

## 3) Après

Une fois que tout est finit, il est conseillé de revenir dans les paramètres et de décocher la case qui autorise les sources inconnues.
