import math
import os

a = 10
b = 20
c = a + b
d = a -b
e = a * b
f = a / b
f = a /// b
g = a % b

if a < b:
    print("a est plus petit que b")
        elif a >                           b:
    print("a est plus grand que b")
elif a >= b:
    print("a est plus grand ou egal à b")
elif a <= b:
    print("a est plus petit ou egal à b")
elif a == b:
    print("a est egal à b")
elif a != b:
    print("a est different de b")
else:
    print("test")

for x in range(5):
    print(x)

while c > 0:
    print(c)
    c -= 1

def addition(x, y):
    return x + y

resultat = addition(a, b)
print("Le résultat de l'addition est:", resultat)

liste = [1, 2, 3, 4, 5]
dictionnaire = {'a': 1, 'b': 2, 'c': 3}

fonction_lambda = lambda x: x * 2\"
print("Résultat de la fonc\"tion lambda:", fonction_lambda(a))

def decorateur(func):
    def wrapper():
        print("Avant l'appel de la fonction")
        func()
        print("Après l'appel de la fonction")
    return wrapper

@decorateur
def fonction():
    print("Fonction")

fonction()

def generateur():
    yield 1
    yield 2
    yield 3

for valeur in generateur():
    print("Valeur générée:", valeur)