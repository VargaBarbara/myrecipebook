#Vizsgaremek - Receptgyűjtemény

##Terv
(avagy ami mindenképpen benne lesz)

###_Receptek:_

- Integer id
- List<String> ingredients //hozzávalók VAGY List<Ingredient(mennyiség, mértékegység, hozzávaló)> -> aatbázisba toString()-gel lehet menteni(?)
- String preparation //elkészítés
- String note //megjegyzés (pl. nekem így meg úgy jobban megkel, stb.)
- Integer creatorId // létrehozó felhasználó id-ja
- LocalDateTime creationTime //létrehozás ideje
- LocalDateTime lastEditTime //utolsó módosítás dátuma

Metódusok:
- save(command)
- findAll()
- findById(id)
- findRandom() //véletlenszerű recept
- update(id, command)
- delete(id)

//TODO hozzávalók kérdése: új tábla adatbázisban? kódban új osztály vagy String?

###_Felhasználók:_

- Integer id
- String name
- String email

Metódusok:
- save(command)
- findAll()
- findById(id)
- update(id, command)
- delete(id)

###_Értékelések:_

- Integer id //értékelés sorszáma
- Integer userId //értékelést leadó felhasználó id-ja
- Integer receiptId //értékelt recept
- Integer fingers //értékelés azon az elven, hogy hány ujjadat nyalnád meg (feltételezzük, hogy mindenkinek pontosan 10 ujja van a kezein és legalább 1-et megnyalna :D)

Metódusok

- saveOrUpdate(userId, receiptId, fingers)
  - Új értékelés, ha a felhasználó még nem értékelte
  - vagy módosítás ha a felhasználó már értékelte az adott receptet
- delete(userId, receiptId) lehet törölni is.

//Controller, Service a Receipt-tel együtt, Repository külön
//Egyelőre legyen Command és Info is

##További ötletek
(avagy mivel lehet még bővíteni, ezeket nem biztos, hogy beleteszem)

- egy adott felhasználó által értékelt receptek listázása
- elkészítés (preparation) listaként ~ vázlatpontok/lépések
- receptek: 
  - érzékenységek/allergének,
  - ki szereti? (családtagok "létrehozása" és hozzárendelése a recepthez)
  - nehézségi fok
  - elkészítési idő
  - költségek // nem pontosan - drága, olcsó, közepes ~ enum? 
  - ratingeket is visszaadni double-ként find metódusokban
    
  - egy receptet csak az tud szerkeszteni, aki létrehozta.
  
- ha nincs megadva felhasználó recept létrehozásakor, akkor default guest felhasználó mentése
- jogosultságok (pl. meghívás szerkesztésre, ekkor új field: lastEditorId)