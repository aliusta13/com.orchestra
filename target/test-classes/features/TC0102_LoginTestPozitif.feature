


Feature: Connexion utilisateur
  @login
Scenario: Tentative de connexion échouée avec email correct et mot de passe correct

Given L'utilisateur accède à la page d'accueil
And   L'utilisateur attend 1 secondes
And   Il clique sur le bouton de "compte"
And   L'utilisateur attend 1 secondes
When  L'utilisateur entre le nom d'utilisateur "emailUtilisateurCorrect" et le mot de passe "motDePasseCorrect"
And   L'utilisateur attend 1 secondes
And   Il clique sur le bouton de "connexion"
And   L'utilisateur attend 1 secondes
Then  l'utilisateur "peut" accéder au compte
