# JavaEvaluation
Java Evaluation est un test technique pour un recrutement chez supralog (CF **[>> le document en pdf <<](Test technique.pdf)**)

Pré-requis
==========

Afin de faire tourner l'application, il faut : 
**[>> Java JDK 21 <<](https://www.oracle.com/fr/java/technologies/downloads/#java21)**

Lancer l'application
====================
L'application se lance sur le port 8070 (vous pouvez le changer dans application.properties)

Une collection postman est disponible également : **[>> Collection Postman <<](./src/main/resources/postman/supralog.postman_collection.json)**

Lancer les tests
=============
L'application comprend un ensemble de tests unitaires pour assurer la qualité et la fiabilité du code. Les tests sont écrits en utilisant le framework **Mockito** pour mocker les dépendances et **JUnit5** pour les assertions et les vérifications. 

Pour exécuter tous les tests, vous pouvez utiliser la commande `mvn test` dans la ligne de commande.

Si vous souhaitez exécuter les tests d'une classe spécifique, vous pouvez utiliser la commande `mvn -DfailIfNoTests=false -Dtest={NomDeLaClassTest} test`. 

Par exemple, pour exécuter les tests de la classe `ProductServiceTest`, vous pouvez utiliser la commande `mvn -DfailIfNoTests=false -Dtest=ProductServiceTest test`.