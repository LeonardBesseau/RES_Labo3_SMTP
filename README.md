# RES_Labo3_SMTP
Voici le repo du laboratoire 3 sur le protocole SMTP pour le cours RES à l'HEIG-VD.

Auteurs : Besseau Léonard, Ogi Nicolas
Date : 23.04.2021

## Description
Ce programme vous permet de faire des farces à vos amis en leur envoyant de faux e-mails. Il vous permet également de configurer un faux serveur SMTP (un faux serveur qui simule le comportement d'un serveur de messagerie mais n'envoie pas les messages aux destinataires).



## Instructions d'installation
Après avoir cloné ce repo à l'endroit de votre choix, il faut démarrer le faux serveur SMTP afin qu'il intercepte les farces que vous envoyez.

Dans le dossier *docker*, lancez le script *build-image.sh* (en ayant au préalable démarré Docker) qui va permettre de construire l'image selon le Dockerfile puis lancez le script *run-container.sh* qui va créer un conteneur et directement démarrer le faux serveur SMTP. Il écoute sur le port 8282 (pour HTTP) et le port 25 (pour SMTP) par défaut.

Une fois le serveur démarré, vous pourrez consulter les mails qu'il a reçu en tapant `localhost:8282` dans votre navigateur : 

![image-20210423171350889](figures/image-20210423171350889.png)



**TODO** : Lancement de l'app avec le .jar



## Utilisation

1. Pour commencer cette campagne de farces, il vous faut tout d'abord remplir le fichier *config/victimes.utf8* avec les adresses mails des personnes qui en seront victimes :

![image-20210423162301552](figures/image-20210423162301552.png)

​	**Attention** : Une adresse mail par ligne.

2. Ensuite, il faut configurer le fichier *config/config.properties*  :

   ![image-20210429144328135](figures/image-20210429144328135.png)

	
	
	- **smtpServerAddress** : correspond à l'adresse du serveur SMTP auquel vous voulez vous connecter. Pour expérimenter le programme avec le faux serveur SMTP MockMock, vous pouvez renseigner `localhost`.
	- **smtpServerPort** : correspond au port sur lequel écoute le serveur SMTP.
	- **numberOfGroups** : vous permet de renseigner combien de groupes de victimes sont à créer pour votre campagne de farces. Chaque groupe sera composé **au minimum** de **1 expéditeur** et de **2 destinataires** dont les rôles seront attribués aléatoirement au sein du groupe. Le groupe est également composé de victimes choisies aléatoirement parmi la liste des victimes.
	- **BCC** : vous permet d'ajouter une adresse mail qui recevra aussi la farce mais en *cci* (blind copy) afin qu'elle n'apparaisse pas dans le mail. Ceci à des fins de tests ou pour voir discrètement que votre campagne de farce se déroule comme prévu.

3. Finalement, il faut écrire vos farces dans le fichier *config/messages.utf8* :

   ![image-20210423164718030](figures/image-20210423164718030.png)

**Attention** : Les farces doivent être séparées par **3 underscores** : "___"

Après avoir configuré ces 3 fichiers, vous devriez être capable de lancer votre campagne de farces en exécutant le fichier .jar que vous avez téléchargé au préalable.



## Description de l'implémentation

