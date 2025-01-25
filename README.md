# Projet de Compilateur Mini Python

## Présentation du Projet
Développement d'un compilateur pour un sous-ensemble de Python (Mini Python). Les phases présentées ici sont l'analyse lexicale et l'analyse syntaxique. Ce projet a été implémenté en Java.

## Exécution du Lexer/Parser

Lancez le lexer ou le parser via Gradle :

```bash
gradle run --args="Lexer|Parser src/main/resources/fichiertest.mpy --stack"
```

Options :
- Choisissez entre `Lexer` et `Parser`
- Spécifiez le fichier source Mini Python depuis `src/main/resources`
- Utilisez `--stack` pour afficher les tokens renvoyés par le lexer

## Spécifications du Langage
- Les commentaires commencent par `#`
- Syntaxe et structures de contrôle similaires à Python
- Utilisation de l'indentation pour définir les blocs