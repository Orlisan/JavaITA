package JavaITA;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.nio.file.*;

public class Main {
    static String percorso; 
    static File file;
    static char[] caratteri;
    static int puntatore = 0;
    static char charCorrente;
    static FileReader fr;
    static FileWriter fw;
    static String fileName;
    static File tempFile;
    static HashMap<String, String> mappaInvertita;
    static boolean isString = false;
    static boolean isComment = false;
    static boolean isComment2 = false;
    
    static boolean eseguiSubito = false;
    static boolean mantieniFile = false;
    static boolean invertiTranspiler = false;
    
    static final HashMap<String, String> parole = new HashMap<>();
    static {
    	  parole.put("classe", "class");
          parole.put("Principale", "Main");
          parole.put("principale", "main");
          parole.put("Sistema", "System");
          parole.put("uscita", "out");
          parole.put("stampa", "print");
          parole.put("stampaANuovaRiga", "println");
          parole.put("se è vero che", "if(");
          parole.put("se è vero che la", "if(");
          parole.put("se è vero che il", "if(");
          parole.put("se è vero che i", "if(");
          parole.put("se è vero che gli", "if(");
          parole.put("se è vero che le", "if(");
          parole.put("se è vero che lo", "if(");
          parole.put("allora", ") {");
          parole.put(", allora", ") {");
          parole.put("allora fai", ") {");
          parole.put(", allora fai", ") {");
          parole.put(".", " } ");
          parole.put("assegnaProprietà", "setProperty");
          parole.put("ottieniProprietà", "getProperty");
          parole.put("pulisciProprietà", "clearProperty");
          parole.put("ottieni", "get");
          parole.put("prendi", "get");
          parole.put("assegna", "set");
          parole.put("aggiungi", "add");
          parole.put("intero", "int");
          parole.put("Intero", "Integer");
          parole.put("Doppio", "Double");
          parole.put("doppio", "double");
          parole.put("Booleano", "Boolean");
          parole.put("booleano", "boolean");
          parole.put("Decimale", "Float");
          parole.put("decimale", "float");
          parole.put("immetti", "put");
          parole.put("metti", "put");
          parole.put("Stringa", "String");
          parole.put("interfaccia", "interface");
          parole.put("Sovrascrivi", "Override");
          parole.put("pubblico", "public");
          parole.put("pubblica", "public");
          parole.put("protetto", "protected");
          parole.put("protetta", "protected");
          parole.put("predefinito", "default");
          parole.put("predefinita", "default");
          parole.put("privato", "private");
          parole.put("privata", "private");
          parole.put("carattere", "char");
          parole.put("Carattere", "Character");
          parole.put("vuoto", "void");
          parole.put("vuota", "void");
          parole.put("vuoti", "void");
          parole.put("vuote", "void");
          parole.put("restituisci", "return");
          parole.put("registro", "record");
          parole.put("se", "if");
          parole.put("altrimenti", "else");
          parole.put("prova", "try");
          parole.put("cattura", "catch");
          parole.put("infine", "finally");
          parole.put("alla fine", "finally");
          parole.put("immutabile", "final");
          parole.put("finale", "final");
          parole.put("statico", "static");
          parole.put("statica", "static");
          parole.put("sincronizzato", "synchronized");
          parole.put("sincronizzata", "synchronized");
          parole.put("questo", "this");
          parole.put("questa", "this");
          parole.put("questo oggetto", "this");
          parole.put("Filo", "Thread");
          parole.put("Processo", "Thread");
          parole.put("Esecuzione", "Thread");
          parole.put("padre", "super");
          parole.put("madre", "super");
          parole.put("superiore", "super");
          parole.put("genitore", "super");
          parole.put("Scannerizzatore", "Scanner");
          parole.put("Documento", "File");
          parole.put("Archivio", "File");
          parole.put("ScrittoreDocumenti", "FileWriter");
          parole.put("LettoreDocumenti", "FileReader");
          parole.put("scrivi", "write");
          parole.put("dormi", "sleep");
          parole.put("corri", "run");
          parole.put("avvia", "start");
          parole.put("chiudi", "close");
          parole.put("Lista", "List");
          parole.put("Vettore", "Array");
          parole.put("ListaDiVettori", "ArrayList");
          parole.put("senza parametri", "()");
          parole.put("nuovo", "new");
          parole.put("nuova", "new");
          parole.put("nuovi", "new");
          parole.put("nuove", "new");
          parole.put("un nuovo", "new");
          parole.put("una nuova", "new");
          parole.put("dei nuovi", "new");
          parole.put("dellenuove", "new");
          parole.put("Mappa", "Map");
          parole.put("MappaIndicizzata", "HashMap");
          parole.put("Dizionario", "HashMap");
          parole.put("falso", "false");
          parole.put("vero", "true");
          parole.put("diventa", " = ");
          parole.put("è uguale a", " == ");
          parole.put("è maggiore o uguale a", " >= ");
          parole.put("è minore o uguale a", " <= ");
          parole.put("è minore a", " < ");
          parole.put("è maggiore a", " > ");
          parole.put("non è uguale a", " != ");
          parole.put("diventano", " = ");
          parole.put("sono uguali a", " == ");
          parole.put("sono maggiori o uguali a", " >= ");
          parole.put("sono minori o uguali a", " <= ");
          parole.put("sono minori a", " < ");
          parole.put("sono maggiori a", " > ");
          parole.put("non sono uguali a", " != ");
          parole.put("non", " ! ");
          parole.put("assieme a", " && "); 
          parole.put("oppure", " || "); 
          parole.put("per", "for( ");
          parole.put("per ogni", "for( ");
          parole.put("finchè", "while( ");
          parole.put("finché", "while( ");
          parole.put("finche", "while( ");
          parole.put("fai", ") {");
          parole.put(", fai", ") {");
          parole.put("in vettori", "[]");
          parole.put("contenente", "[");
          parole.put("nella posizione", "[");
          parole.put("del vettore", "]");
          parole.put("fatta da", "[");
          parole.put("fatto da", "[");
          parole.put("valori", "]");
          parole.put("fatta di", "<");
          parole.put("e di", " , ");
          parole.put("e basta", ">");
          parole.put("cioe", " { ");
          parole.put("cioé", " { ");
          parole.put("cioè", " { ");
          parole.put(", cioe", " { ");
          parole.put(", cioé", " { ");
          parole.put(", cioè", " { ");
          parole.put("che richiede", " ( ");
          parole.put(", fine", " ) ");
          parole.put("fine", " ) ");
          parole.put("fino a quando", "while( ");
          parole.put("interrompi il ciclo", "break");
          parole.put("spacca", "break");
          parole.put("scegli con", "switch");
          parole.put("scegli", "switch");
          parole.put("scegli tramite", "switch");
          parole.put("caso", "case");
          parole.put("salta", "continue");
          parole.put("continua", "continue");
          parole.put("sommato a", " + ");
          parole.put("sottratto a", " - ");
          parole.put("sommata a", " + ");
          parole.put("sottratta a", " - ");
          parole.put("moltiplicato per", " * ");
          parole.put("diviso per", " / ");
          parole.put("moltiplicata per", " * ");
          parole.put("divisa per", " / ");
          parole.put("fratto per", " * ");
          parole.put("fratta per", " / ");
          parole.put("incrementa", " ++ ");
          parole.put("Lunghezza", "length");
          parole.put("lunghezza", "length");
          parole.put("modulo", " % ");
          parole.put("diviso", "split");
          parole.put("indice di", "indexOf");
          parole.put("Divisa in caratteri", "toCharArray");
          parole.put("Diviso in caratteri", "toCharArray");
          parole.put("aumenta di", " += ");
          parole.put("diminuisce di", " -= ");
          parole.put("si moltiplica di", " *= ");
          parole.put("si divide di", " /= ");
          parole.put("contiene", "contains");
          parole.put("equivale a", "equals");
          parole.put("carattre al posto", "charAt");
          parole.put("inMaiuscolo", "toUpperCase");
          parole.put("inmaiuscolo", "toUpperCase");
          parole.put("inMinuscolo", "toLowerCase");
          parole.put("inminuscolo", "toLowerCase");
          parole.put("dentro a", " : ");
          parole.put("interno a", " : ");
          parole.put("iniziaCon", "startsWith");
          parole.put("finisceCon", "endsWith");
          parole.put("lungo", "long");
          parole.put("largo", "long");
          parole.put("inTesto", "toString");
          parole.put("Matematica", "Math");
          parole.put("inIntero", "parseInt");
          parole.put("inDoppio", "parseDouble");
          parole.put("RadiceQuadrata", "sqrt");
          parole.put("potenza", "pow");
          parole.put("assoluto", "abs");
          parole.put("casuale", "random");
          parole.put("prossimaRiga", "nextLine");
          parole.put("prossimoIntero", "nextInt");
          parole.put("nullo", "null");
          parole.put("nulla", "null");
          parole.put("nulli", "null");
          parole.put("nulle", "null");
          parole.put("è vuota", "isEmpty");
          parole.put("è vuoto", "isEmpty");
          parole.put("sono vuoti", "isEmpty");
          parole.put("sono vuote", "isEmpty");
          parole.put("estende", "extends"); 
          parole.put("implementa", "implements"); 
          parole.put("lancia", "throw"); 
          parole.put("lancia Errori", "throws"); 
          parole.put("pacchetto", "package"); 
          parole.put("importa", "import");
          parole.put("sigillata", "sealed");
          parole.put("blindata", "sealed");
          parole.put("sigillato", "sealed");
          parole.put("blindato", "sealed");
          parole.put("permette a", "permits");
          parole.put("consente a", "permits");
          parole.put("variabile", "var");
          parole.put("astratto", "abstract");
          parole.put("transitorio", "transient");
          parole.put("nativo", "native");
          parole.put("astratta", "abstract");
          parole.put("transitoria", "transient");
          parole.put("nativa", "native");
          parole.put("Errore Di Runtime", "RuntimeException");
          parole.put("prendiMessaggio", "getMessage");
          parole.put("stampaTraccia", "printStackTrace");
          parole.put("è un", "instanceof");
          parole.put("è una", "instanceof");
          parole.put("entrata", "in");
          parole.put("errore", "err");
          parole.put("esci", "exit");
          parole.put("esciDalSistema", "exit");
          parole.put("termina", "exit");
          parole.put("tempoCorrente", "currentTimeMillis");
          parole.put("tempoPreciso", "nanoTime");
          parole.put("proprietà", "getProperties");
          parole.put("pulisci", "gc");
          parole.put("copiaVettore", "arraycopy");
          parole.put("stampaFormattata", "printf");
          parole.put("massimo", "max");
          parole.put("massima", "max");
          parole.put("minimo", "min");
          parole.put("minima", "min");
          parole.put("arrotondaPerEccesso", "ceil");
          parole.put("arrotondataPerEccesso", "ceil");
          parole.put("arrotondaPerDifetto", "floor");
          parole.put("arrotondataPerDifetto", "floor");
          parole.put("arrotonda", "round");
          parole.put("arrotondata", "round");
          parole.put("seno", "sin");
          parole.put("coseno", "cos");
          parole.put("tangente", "tan");
          parole.put("arcoSeno", "asin");
          parole.put("arcoCoseno", "acos");
          parole.put("arcoTangente", "atan");
          parole.put("esponenziale", "exp");
          parole.put("logaritmo", "log");
          parole.put("logaritmoBaseDieci", "log10");
          parole.put("segno", "signum");
          parole.put("radiceCubica", "cbrt");
          parole.put("sottostringa", "substring");
          parole.put("rimpiazza", "replace");
          parole.put("sostituisci", "replace");
          parole.put("sostituita", "replace");
          parole.put("rimpiazzaTutto", "replaceAll");
          parole.put("sostituisciTutto", "replaceAll");
          parole.put("confronta", "compareTo");
          parole.put("confrontata", "compareTo");
          parole.put("confrontaIgnorandoMaiuscole", "compareToIgnoreCase");
          parole.put("equivaleIgnorandoMaiuscole", "equalsIgnoreCase");
          parole.put("unisci", "join");
          parole.put("unito", "join");
          parole.put("unita", "join");
          parole.put("ripeti", "repeat");
          parole.put("ripetuta", "repeat");
          parole.put("pulisciBordi", "trim");
          parole.put("senzaSpazi", "trim");
          parole.put("ultimoIndiceDi", "lastIndexOf");
          parole.put("valoreDi", "valueOf");
          parole.put("èVuoto", "isEmpty");
          parole.put("èVuota", "isEmpty");
          parole.put("èBianco", "isBlank");
          parole.put("èBianca", "isBlank");
          parole.put("esiste", "exists");
          parole.put("creaNuovoFile", "createNewFile");
          parole.put("cancella", "delete");
          parole.put("cancellata", "delete");
          parole.put("percorso", "getPath");
          parole.put("percorsoAssoluto", "getAbsolutePath");
          parole.put("nome", "getName");
          parole.put("TrovaPadre", "getParent");
          parole.put("èUnDocumento", "isFile");
          parole.put("èUnaCartella", "isDirectory");
          parole.put("puòLeggere", "canRead");
          parole.put("puòScrivere", "canWrite");
          parole.put("puòEseguire", "canExecute");
          parole.put("leggi", "read");
          parole.put("svuota", "flush");
          parole.put("pulisciFlusso", "flush");
          parole.put("esegui", "do");
          parole.put("mentre", "while");
          parole.put("costruttore", "constructor");
          parole.put("costruttrice", "constructor");
          parole.put("volatile", "volatile");
          parole.put("modulistica", "module");
          parole.put("esporta", "exports");
          parole.put("richiede", "requires");
          parole.put("dimensione", "size");
          parole.put("svuotaTutto", "clear");
          parole.put("svuotaTutta", "clear");
          parole.put("Breve", "Short");
          parole.put("breve", "short");
          parole.put("Numero", "Number");
          parole.put("Oggetto", "Object");
          parole.put("Enumerazione", "Enum");
          parole.put("SequenzaDiCaratteri", "CharSequence");
          parole.put("CostruttoreDiStringhe", "StringBuilder");
          parole.put("BufferDiStringhe", "StringBuffer");
          parole.put("clona", "clone");
          parole.put("clonata", "clone");
          parole.put("codiceHash", "hashCode");
          parole.put("notifica", "notify");
          parole.put("notificaTutti", "notifyAll");
          parole.put("aspetta", "wait");
          parole.put("finalizza", "finalize");
          parole.put("Ambiente", "Runtime");
          parole.put("ottieniAmbiente", "getRuntime");
          parole.put("memoriaLibera", "freeMemory");
          parole.put("memoriaTotale", "totalMemory");
          parole.put("memoriaMassima", "maxMemory");
          parole.put("processori disponibili", "availableProcessors");
          parole.put("priorità", "setPriority");
          parole.put("unisciti", "join");
          parole.put("interrompi", "interrupt");
          parole.put("èVivo", "isAlive");
          parole.put("èViva", "isAlive");
          parole.put("cediPasso", "yield");
          parole.put("filoCorrente", "currentThread");
          parole.put("processoCorrente", "currentThread");
          parole.put("Eccezione", "Exception");
          parole.put("Errore", "Error");
          parole.put("Lanciabile", "Throwable");
          parole.put("Argomento Illegale", "IllegalArgumentException");
          parole.put("Stato Illegale", "IllegalStateException");
          parole.put("Indice Fuori Limite", "IndexOutOfBoundsException");
          parole.put("Classe Non Trovata", "ClassNotFoundException");
          parole.put("Operazione Non Supportata", "UnsupportedOperationException");
          parole.put("Aritmetica Fallita", "ArithmeticException");
          parole.put("Interruzione Improvvisa", "InterruptedException");
          parole.put("PI_GRECO", "PI");
          parole.put("E_DI_EULERO", "E");
          parole.put("tangenteIperbolica", "tanh");
          parole.put("senoIperbolico", "sinh");
          parole.put("cosenoIperbolico", "cosh");
          parole.put("ipotenusa", "hypot");
          parole.put("approssima", "rint");
          parole.put("approssimata", "rint");
          parole.put("è lettera", "isLetter");
          parole.put("èLettera", "isLetter");
          parole.put("è numero", "isDigit");
          parole.put("èNumero", "isDigit");
          parole.put("è spazio", "isWhitespace");
          parole.put("èSpazio", "isWhitespace");
          parole.put("èMaiuscolo", "isUpperCase");
          parole.put("èMaiuscola", "isUpperCase");
          parole.put("èMinuscolo", "isLowerCase");
          parole.put("èMinuscola", "isLowerCase");
          parole.put("concatena", "concat");
          parole.put("concatenata", "concat");
          parole.put("formato", "format");
          parole.put("formattata", "format");
          parole.put("corrispondeA", "matches");
          parole.put("proprietàDiSistema", "getProperties");
          parole.put("variabileDAmbiente", "getenv");
          parole.put("identitàHash", "identityHashCode");
          parole.put("lineaSeparatrice", "lineSeparator");
          parole.put("contieneChiave", "containsKey");
          parole.put("che", "");
          
          mappaInvertita = parole.entrySet()
				    .stream()
				    .collect(Collectors.toMap(
				        Map.Entry::getValue, 
				        Map.Entry::getKey,
				        (oldValue, newValue) -> newValue, 
				        HashMap::new                     
				    ));
    }
    public static void main(String[] args) throws Exception { 
        if (args.length >= 1) {
            percorso = args[0];
            file = new File(percorso);
            if(percorso.equals("-?") || percorso.equals("help") || percorso.equals("/?")) {
            	System.out.println("UTILIZZO DI JAVAITA:"
        				+ "\njavaita <percorso> <parametri>"
        				+ "\n\n\nPARAMETRI DISPONIBILI:"
        				+ "\n-s: non esegue il programma transpilato, consigliato l'uso assieme a -t"
        				+ "\n\n-t: mantiene il file transpilato e quello compilato nella cartella dati di JavaITA"
        				+ "\n\n-r: esegue il processo opposto a quello di default: transpila il codice Java in JavaITA"
        				+ "\n Attenzione, l'utilizzo dello strumento di revert\n è sconsigliato per progetti medio-complicati,\nData l'imprevedibilità dei termini su concetti avanzati"
        				+ "\n\n-? Mostra questa legenda");
        				return;
            }
            for(String arg: args) {
            	if(arg.equals("-s") || arg.equals("/s")) {
            		eseguiSubito = true;
            	}else if(arg.equals("-t") || arg.equals("/t")) {
            		mantieniFile = true;
            	}else if(arg.equals("-r") || arg.equals("/r")) {
            		System.out.println("Attenzione, l'utilizzo dello strumento di revert\n è sconsigliato per progetti medio-complicati,\nData l'imprevedibilità dei termini su concetti avanzati");
            		invertiTranspiler = true;
            	}else if(arg.equals("-?") || arg.equals("help") || arg.equals("/?")) {
            		System.out.println("UTILIZZO DI JAVAITA:"
            				+ "\njavaita <percorso> <parametri>"
            				+ "\n\n\nPARAMETRI DISPONIBILI:"
            				+ "\n-s: non esegue il programma transpilato, consigliato l'uso assieme a -t"
            				+ "\n\n-t: mantiene il file transpilato e quello compilato nella cartella dati di JavaITA"
            				+ "\n\n-r: esegue il processo opposto a quello di default: transpila il codice Java in JavaITA"
            				+ "\n Attenzione, l'utilizzo dello strumento di revert\n è sconsigliato per progetti medio-complicati,\nData l'imprevedibilità dei termini su concetti avanzati"
            				+ "\n\n-? Mostra questa legenda");
            				return;
            	}
            }
        } else {
            throw new Exception("Errore di sintassi, uso: javaita <percorso>");
        }

        caratteri = new char[(int) file.length()];
        String nomeConEstensione = file.getName();
        
        fileName = "";
        int puntoIndex = nomeConEstensione.lastIndexOf('.');
        
        if (puntoIndex > 0) {
            fileName = nomeConEstensione.substring(0, puntoIndex);
        } else {
            fileName = nomeConEstensione;
        }
        String homeUtente = System.getProperty("user.home");
        String cartellaBase;

        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            cartellaBase = homeUtente + "/AppData/Local/JavaITA/output";
        } else {
            cartellaBase = homeUtente + "/.javaita/output";
        }

        new File(cartellaBase).mkdirs();
        File tempFile = new File(cartellaBase + "/" + fileName + ".java"); fw = new FileWriter(tempFile);

        try {
            String contenuto = Files.readString(file.toPath());
            caratteri = contenuto.toCharArray();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while (puntatore != caratteri.length) {
            charCorrente = caratteri[puntatore];
            
            if (charCorrente == '\'' || charCorrente == '\"') {
                isString = !isString; 
            } 
            if(charCorrente == '/' && caratteri[puntatore +1] == '/' && !isComment) {
            	isComment = true;
            }
            if(charCorrente == '\n' && isComment) {
            	isComment = false;
            }
            if(charCorrente == '/' && caratteri[puntatore +1] == '*' && !isComment2) {
            	isComment2 = true;
            }
            if(charCorrente == '*' && caratteri[puntatore +1] == '/' && isComment2) {
            	isComment2 = false;
            }
            if (!isString && !isComment && !isComment2) {
                if (scanChar(puntatore)) continue; 
            }
            fw.write(charCorrente);
            puntatore++;
        }
        fw.close(); 
        try {
        	if (!eseguiSubito) {
        	    String separator = File.pathSeparator; 
        	    String binPath = cartellaBase;
        	    String javaFile = binPath + "/" + fileName + ".java";

        	    try {
        	        Process compile = new ProcessBuilder("javac", javaFile)
        	                .inheritIO()
        	                .start();
        	        int exitCode = compile.waitFor();

        	        if (exitCode == 0) {
        	            new ProcessBuilder("java", "-cp", binPath, fileName)
        	                    .inheritIO()
        	                    .start()
        	                    .waitFor();
        	        }
        	    } catch (Exception e) {
        	        e.printStackTrace();
        	    }
        	}
        	if (!mantieniFile) {
                File dir = new File(cartellaBase);
                File[] files = dir.listFiles();
                if (files != null) {
                    for (File f : files) {
                        if (f.getName().startsWith(fileName)) {
                            f.delete();
                        }
                    }
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static boolean scanChar(int index) throws IOException {
        char[] delimitatori = {
        	    ' ', '\n', '\t', '\r',
        	    '(', ')', '{', '}', '[', ']',
        	    '.', ',', ';', ':', 
        	    '+', '-', '*', '/', '%', '=', '!', '>', '<', '&', '|', '^', '@'
        	};
        
       
        for (String parola : paroleCorrenti().keySet().stream().sorted((a, b) -> b.length() - a.length()).toList()) {
        	 boolean isDelimitedAfter = false;
             boolean isDelimitedThen = false;
        	
            int lParola = parola.length();
            char[] pezziParola = parola.toCharArray();

            int contatoreCharAppaiati = 0;

            for (int i = 0; i < lParola; i++) {
               if(index +lParola <= caratteri.length) { //Te lo controlla già qua, se non passa, il contatore non aumenta e non arriva al blocco dopo
            	if (index + i < caratteri.length && pezziParola[i] == caratteri[index + i]) {
                    contatoreCharAppaiati++;
                }
               }
            }
           
            if (contatoreCharAppaiati == lParola) {
                
            	for(char del: delimitatori) {
                	if(index -1 != -1) {
	                	if( caratteri[index -1] == del ) {
	                		isDelimitedAfter = true;
	                	
	                	}
                	}else {
                		isDelimitedAfter = true;
                		
                	}
                	if (index + lParola >= caratteri.length) { 
                        isDelimitedThen = true; 
                    } else {
                	if(index+lParola < caratteri.length && caratteri[index + lParola] == del) {
                		isDelimitedThen = true;
                	}
                    }
                	
                }
            	if(isDelimitedAfter && isDelimitedThen) {
	            	fw.write(paroleCorrenti().get(parola));
	                puntatore += lParola;
	                return true;
            	}
            }
           }
        return false;
    }
    static HashMap<String, String> paroleCorrenti() {
		if(invertiTranspiler) {
			return mappaInvertita;
		}else {
			return parole;
		}
    }
    }
