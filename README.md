# JavaITA è un transpilatore il cui obiettivo è rendere java semplice e in italiano, quasi a sembrare un discorso


**È Importante fare presente che non tutto il JRE è stato mappato, e il linguaggio JavaITA è perfettamente mischiabile con il java standard**

Il transpilatore gestisce commenti e stringhe, non traducendole

È possibile usare più forme grammaticali per scrivere il codice, a seconda del contesto/preferenze, si può quindi crearsi il proprio livello di discorsivita, ad esempio se conservare

```se(x è uguale a y) {<codice> }``` 

oppure renderlo

```se è vero che x è uguale a y allora fai <codice> .```

il programma si usa da terminale, bisogna cliccare due volte sullo script corrispondente al proprio sistema operativo: ```.bat``` per Windows, ```.sh``` per Linux/MacOS, e attivarlo con privilegi elevati, esso aggiungerà il programma al PATH ed eseguirà il file ```.jar``` premettendo automaticamente ```java -cp``` e cose simili, fatto questo si potrà liberamente andare sul proprio terminale da qualsiase directory e usare il programma, con la sintassi spiegata qua sotto

## Utilizzo JavaITA
Dopo aver seguito i passaggi sopraelencati per usare il transpilatore bisognerà aprire il terminale e digitare ```javaita <percorso> <parametri>``` come percorso il percorso del file (con qualsiasi estensione) che si vuole transpilare e come parametri:

```-s``` o ```/s``` : traduce soltanto il file javaita in linguaggio java ma non compila ed esegue, come è invece di default, è normalmente abbinabile a -t

```-t``` o ```/t``` : mantiene i file temporanei (.java e .class) tenendoli nella cartella output che si trova su windows nelle AppData e su Linux/Mac in una cartella apposita sulla home

```-r``` o ```/r``` : compie il processo opposto: trasforma un file.java in un file in javaita, funziona solo con programmi semplici, data la varietà di termini italiani per eseguire un solo termine, per ovvie ragioni, non compila nè esegue

```-?```, ```/?``` o ```help``` : mostra una una lista simile a questa
```-p``` o ```/p``` : pulisce tutte le cache contenute nelle AppData

### Requisiti

Un computer con Windows, Linux o MacOS

JavaSE e jdk installato sul dispositivo 17 o superiore, java configurato correttamente nel PATH
