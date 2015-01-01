StoveRock
=========

Programmeerimiskeelte aine projekt

#Ülesande püstitus

Pliidikivi kaardimäng (lihtsustatud versioon)

* Kaks mängijat: punane ja sinine.
* Mängijad käivad kordamööda, kusjuures punane alustab.
* Igal mängijal on
- oma kaardipakk,
- käes mingi arv kaarte ja
- mänguväljal mingi arv olendeid.

* Olendite hulgas on mõlemal mängija üks kangelane.
* Mängu alguses on väljakul ainsateks olenditeks kangelased 
ning igal kangelasel on 30 elupunkti.
* Mäng on võidetud, kui vastase kangelasel on <=0 punkte ja 
enda kangelasel >0 elupunkti.

* Mõlema mängija esimesel käigul saab tegutseda ühe tegutsemispunkti jagu.
* Tegutsemispunktid kasvavad iga käigupaari tagant ühe võrra, kuid ainult 
kuni kümne punktini.

* Igal kaart vajab käimiseks vastava arvu tegutsemispunkte. 
* Kaarte on kahte tüüpi: olendikaardid ja loitsud.
* Loitsudel on käies mingi kindele toime; olendikaardi mängimisel ilmub see 
olend mänguväljale, kaardi väljamängija poolele.

* Olendikaardi väljakäimist loetakse tema esimeseks käiguks, mis tähendab et rünnata ta samal käigul enam ei saa. (täiendus 19. nov -- vabatahtlik reegel)

* Mängija käik kestab senikaua kuni ta otsustab järje teisele üle anda.
* Iga käigu algul saab mängija ühe kaardi enda pakist endale kätte.
* Kui mangija peab võtma kaardi, aga tema pakk on tühi, siis tema kangelase elult lahutatakse 10 punkti
* Enda olenditega saab rünnata teisi olendeid, aga iga olend saab rünnata
ainult üks kord käigu jooksul.
* Juhul kui vähemalt ühel vastase olendil on mõnitav omadus, ei saa sa rünnata 
ühtegi vastase olendit kellel mõnitavat omadust pole.

/////////////////////////////////////////////


Mängijate kaardipakid tuleb lugeda failist, mille süntaks on järgmine:

-- fail on list kolmikutest
File ::== [(Name, Cost, CardType)] 
Name ::== <String>
Cost ::== <Int>

-- on kahte tüüpi kaarte: olendid ja loitsud
CardType ::== "MinionCard" [Effect] Health Attack Taunt ("Just" MinionType | "Nothing")
           |  "SpellCard" [Effect]
-- elupunktide arv
Health ::== <Int>
-- ründepunktide arv
Attack ::== <Int>
-- mõnitav olend
Taunt ::== <Bool>

-- olenditel võivad olla sellised tüübid
MinionType ::== "Beast" | "Murloc"

Effect ::== "OnPlay"     [EventEffect]  -- effekt kaardi käimisel
         |  "UntilDeath" [EventEffect]  -- effekt mis kestab välja käimisest kuni olendi surmani
         |  "OnDamage"   [EventEffect]  -- effekt mis tehakse olendi vigastamisel
         |  "OnDeath"    [EventEffect]  -- effekt mis tehakse olendi tapmisel (elupunktid <= 0)


-- toime on kaardi võtmine või olendite mõjutamine --- vastavalt filtrile
EventEffect ::== "All" [Filter] [CreatureEffect] 
                             -- mõjutatake kõiki filtrile vastavaid olendeid
               | "Choose" [Filter] [CreatureEffect]       
                             -- mõjutatake üht kasutaja valitud filtrile vastavat olendeit
               | "Random" [Filter] [CreatureEffect]       
                             -- mõjutatake üht juhuslikku filtrile vastavat olendeit
               | "DrawCard"  -- toime olendi omanik võtab kaardi


CreatureEffect ::== "Health" Type Health  -- elupunktide muutmine
                 |  "Attack" Type Attack  -- ründepunktide muutmine
                 |  "Taunt" Taunt         -- mõnituse muutmine

-- muutuse tüüp
Type ::== "Relative" -- negatiivne relatiivne muutmine on vigastamine 
        | "Absolute" -- absoluutne negatiivne muutmine ei ole vigastamine

-- filtri list on konjunktsioon, välja arvatud "Any" puhul
Filter ::== "AnyCreature"     -- olendid
          | "AnyHero"         -- kangelased
          | "AnyFriendly"     -- "omad" 
          | "Type" MinionType -- kindlat tüüpi olendid
          | "Self"            -- mõjutav olend ise
          | "Not" [Filter]    -- eitus
          | "Any" [Filter]    -- disjunktsioon: kui üks tingimus on taidetud


Näiteks:

[ ("Boulderfist Ogre"
, 6
, MinionCard [] 6 7 False Nothing),

("Elven Archer"
, 4
, MinionCard [OnPlay [Choose [] [Health Relative (-1)]]] 4 5 False Nothing),

("Gnomish Inventor"
, 4
, MinionCard [OnPlay [DrawCard]] 2 4 False Nothing)]


\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/

Projekt:

Kirjutada selline programm, mis võimaldab kirjeldatud kaardimängu mängida.
Piisav ja soovitatav on teha tekstuaalne liides, kus valikud tehakse numbriklahvide ja enteri abil.
Programm peab tundma reegleid, mängustrateegia implementeerimine pole oluline: 
mõlemad mängijad on inimesed kes mängivad sama arvuti taga.
Nagu eelnevalt mainitud, mängijate kaardipakid tuleb lugeda failist.

~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-

Projektitöö hindamine:

1) Punktide saamiseks tuleb oma programmi tööd ja lähtekoodi esitleda (~10 kuni 15min).
2) Hindan ka programmeerimise heade tavade täitmist: koodi loetavust, kommenteerimist, jms.
3) Teretulnud on ka poolikud lahendused: hindan töö mahu järgi.
4) Projekti lahendamisel kasutatud programmeerimiskeel võiks olla valitud loengus esitletavate keelte hulgas. Juhul, kui valitakse mingi muu keel, peab ilmtingimata Kalmerilt luba küsima ja oskama vastata, kuidas see projekt sel juhul ainega üldse seostub. (F# või SML on ehk ok OCaml asemel etc.)
5) Graafilise kasutajaliidese eest lisapunkte ei anta!

ja loomulikut

6) Kõik töö autorid tuleb selgelt välja tuua --- koodi kopeerimine teistelt isikutel pole lubatud.
Soovituslik reegel on, et enda koodi ei tohiks teistele näidata ja teiste lahendusi ei tohi lugeda.

=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+

Vihjed:

Ma lisan siia peatselt vihjeid, mis võivad sind ülesande lahendamisel aidata.

    Esmalt võiks deklareerida kõik vajalikud andmestruktuurid mängu seisudi hoidmiseks. (Minu lahenduses on neid üsna palju --- umbes 20 erinevat definitsiooni)
    Haskellis saab andmestruktuuridele automaatselt genereerida sõnest andmete lugemist. ('Read' tüübiklass) 
    Kõik mänguvälja oledid võiks hoida ühes listis---siis saab nendele defineerida filtreerimist vastavalt Filter avaldisele.
        Minul on defineeritud funktsioon filterApplies :: Creature -> Filter -> Creature -> Bool, nii et (filterApplies a f c) on tõeväärtus, kus a on olend, kelle efektis on filter f, ja c on mingi muu laual olev olend. Tagastatakse True, kui c vastab filtri tingimustele.
    Lahendus jaotada paljude väikeste funktsioonide/protseduuride vahel!

    Minu lahenduses on protseduur chooseCreature :: [Creature] -> IO (Creature,[Creature]) 
    (ja randomCreature :: [Creature] -> IO (Creature,[Creature])) mis võimaldab mängijal  interaktiivselt(/juhuslikult) olendite vahel valida.
    Olenditel võiks olla staatilised elu, ründe ja taunt-punktid ja dünaamilised elu, ründe ja taunt-punktid. Dünaamilised punktid muutuvad UntilDeath efektiga, staatilised teiste efektidega. Kokku kehtib alati nende summa. Niimoodi saab UntilDeath punktid uuesti arvutada, ilma teisi mõjutamata. 
