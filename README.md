# Vizsgaremek - Receptgyűjtemény

##Az alkalmazás célja

Sokan találkozhattunk a nehezen megválaszolható kérdéssel: Mit főzzünk ma?
A receptgyűjtemény alkalmazás célja ennek a kérdésnek a megválaszolása, illetve a különféle receptgyűjtéssel 
és tárolással kapcsolatos fogyasztói igények kielégítése. 

##Az alkalmazás konténerét létrehozó utasítások:

Csak az adatbázis van konténerben (ez működik):
* docker network create vizsgaremeknetwork
* docker run --name vizsgaremekdb --network vizsgaremeknetwork -e MYSQL_ROOT_PASSWORD=1234 -e MYSQL_DATABASE=vizsgaremek -d -p 3307:3306 mysql:latest

Alkalmazás és az adatbázis is konténerben fut (nem működik, azonnal exitel):
* docker build -t vizsgaremekapp .
* docker run --name vizsgaremekapp --network vizsgaremeknetwork -p 8080:8080 -d vizsgaremekapp
