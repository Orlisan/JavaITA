package JavaITA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.nio.file.*;

public class Main {
    static String percorsoJavaITAFile; 
    static String cartellaBase;
    static File file;
    static char[] caratteri;
    static int puntatore = 0;
    static char charCorrente;
    static FileReader fr;
    static FileWriter fw;
    static String fileName;
    static File tempFile;
    static HashMap<String, String> mappaInvertita;
    static ArrayList<String> jITArgs = new ArrayList<String>();
    static boolean isString = false;
    static boolean isComment = false;
    static boolean isComment2 = false;
    static boolean isAdjustedRevert = false;
    
    static boolean noPackage = false;
    
    static boolean eseguiSubito = false;
    static boolean mantieniFile = false;
    static boolean invertiTranspiler = false;
    static String pathCustomExtensions = null;
    static boolean mantieniEstensioni = false;
    static File ExternExtension;
    static String pathCodice = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath().concat("/..");
    static char[] delimitatori = {
    	    ' ', '\n', '\t', '\r',
    	    '(', ')', '{', '}', '[', ']',
    	    '.', ',', ';', ':', 
    	    '+', '-', '*', '/', '%', '=', '!', '>', '<', '&', '|', '^', '@'
    	};
    
    static char[] delimitatoriRevert = {
    	    ' ', '\n', '\t', '\r',
    	    '(', ')', '{', '}', '[', ']',
    	     ',', ';', ':', 
    	    '+', '-', '*', '/', '%', '=', '!', '>', '<', '&', '|', '^', '@'
    	};
    
    
    static final HashMap<String, String> parole = new HashMap<>();
    public static void main(String[] args) throws Exception { 
    	String homeUtente = System.getProperty("user.home");
    	if (System.getProperty("os.name").toLowerCase().contains("win")) {
            cartellaBase = homeUtente + "/AppData/Local/JavaITA/output";
        } else {
            cartellaBase = homeUtente + "/.javaita/output";
        }
    	if (args.length >= 1) {
            percorsoJavaITAFile = args[0];
            file = new File(percorsoJavaITAFile);
          
            if(percorsoJavaITAFile.equals("-?") || percorsoJavaITAFile.equals("help") || percorsoJavaITAFile.equals("/?")) {
            	System.out.println("UTILIZZO DI JAVAITA:"
        				+ "\njavaita <percorso> <parametri>"
        				+ "\n\n\nPARAMETRI DISPONIBILI:"
        				+ "\n-s: non esegue il programma transpilato, consigliato l'uso assieme a -t"
        				+ "\n\n-t: mantiene il file transpilato e quello compilato nella cartella dati di JavaITA"
        				+ "\n\n-r: esegue il processo opposto a quello di default: transpila il codice Java in JavaITA"
        				+ "\n Attenzione, l'utilizzo dello strumento di revert\n è sconsigliato per progetti medio-complicati,\nData l'imprevedibilità dei termini su concetti avanzati"
        				+ "\n\n-? Mostra questa legenda");
        				return;
            }else if(percorsoJavaITAFile.equals("-p") || percorsoJavaITAFile.equals("/p")) {
            	File dir = new File(cartellaBase);
                File[] files = dir.listFiles();
                if (files != null) {
                    for (File f : files) {
                            f.delete();
                    	}
                }
                File dirExt = new File(cartellaBase.concat("/.."));
                File[] filesExt = dirExt.listFiles();
                if (filesExt != null) {
                    for (File f : filesExt) {
                            f.delete();
                    	}
                }
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
            		mantieniFile = true;
            		eseguiSubito = true;
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
            	}else if(arg.equals("-p") || arg.equals("/p")) {
                	File dir = new File(cartellaBase);
                    File[] files = dir.listFiles();
                    if (files != null) {
                        for (File f : files) {
                                f.delete();
                        	}
                    }
                    File dirExt = new File(cartellaBase.concat("/.."));
                    File[] filesExt = dirExt.listFiles();
                    if (filesExt != null) {
                        for (File f : filesExt) {
                                f.delete();
                        	}
                    }
                }else if(arg.equals("-a") || arg.equals("/a")) {
                	for(int contatore = Arrays.asList(args).indexOf(arg)+1; contatore < args.length; contatore++) {
                		if(args[contatore].equals("-s") || args[contatore].equals("/s") || args[contatore].equals("-t") ||
                			    args[contatore].equals("/t") || args[contatore].equals("-r") || args[contatore].equals("/r") ||
                			    args[contatore].equals("-?") || args[contatore].equals("/?") || args[contatore].equals("help") ||
                			    args[contatore].equals("-p") || args[contatore].equals("/p") || args[contatore].equals("-np") ||
                			    args[contatore].equals("/np") || args[contatore].equals("-e") || args[contatore].equals("/e") ||
                			    args[contatore].equals("-te") || args[contatore].equals("/te")) {
                			break;
                			
                		}
                		jITArgs.add(args[contatore]);
                	}
                }else if(arg.equals("-np") || arg.equals(("/np"))) {
                	noPackage = true;
                }else if(arg.equals("-e") || arg.equals("/e")) {
                int contatore = Arrays.asList(args).indexOf(arg)+1; //Ho i miei motivi per cui si chiama contatore
                 if(contatore < args.length && !(args[contatore].equals("-s") || args[contatore].equals("/s") || args[contatore].equals("-t") ||
         			    args[contatore].equals("/t") || args[contatore].equals("-r") || args[contatore].equals("/r") ||
         			    args[contatore].equals("-?") || args[contatore].equals("/?") || args[contatore].equals("help") ||
         			    args[contatore].equals("-p") || args[contatore].equals("/p") || args[contatore].equals("-np") ||
         			    args[contatore].equals("/np") || args[contatore].equals("-e") || args[contatore].equals("/e") ||
         			    args[contatore].equals("-te") || args[contatore].equals("/te"))) {
                	pathCustomExtensions = args[contatore];
                 }
                }else if(arg.equals("-te") || arg.equals("/te")) {
                	mantieniEstensioni = true;
                }
            }
            if(!file.getName().endsWith(".javaita") && !invertiTranspiler) {throw new Exception("IL file allegato non è un file  JavaITA");}
        } else {
            throw new Exception("Errore di sintassi, uso: javaita <percorso>");
        }
    	//Parte estensioni
    	try {
    		File extension = null;
    		if(pathCustomExtensions != null) {
    			ExternExtension = new File(pathCustomExtensions);
    			extension = new File(cartellaBase + "/../extensions");
				extension.mkdirs();
    			if(ExternExtension.isDirectory()) {
    				for(File fileExt: ExternExtension.listFiles()) {
    					Path sorgente = Paths.get(ExternExtension.getAbsolutePath() +"/"+ fileExt.getName());
    					File f = new File(extension +"/"+ fileExt.getName());
    					Path destinazione = Paths.get(f.getAbsolutePath());
    					Files.copy(sorgente, destinazione, StandardCopyOption.REPLACE_EXISTING);
    				}
    			}else {
    				Path sorgente = Paths.get(ExternExtension.getAbsolutePath());
					File f = new File(extension +"/"+ ExternExtension.getName());
					Path destinazione = Paths.get(f.getAbsolutePath());
					Files.copy(sorgente, destinazione, StandardCopyOption.REPLACE_EXISTING);
    			}
    		}else {
	            extension = new File(pathCodice + "/extensions");
	            extension.mkdirs();
    		}
            if(!extension.isDirectory() || !extension.exists()) {
            	throw new Exception("la directory extensions non esiste");
            }
            if(extension.listFiles().length == 0) {
            	throw new Exception("nessuna estensione disponibile");
            }
            for (File f: extension.listFiles()) {
               if(f.getName().endsWith(".jitaext")) {
                FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);
               ArrayList<String> righeExt = new ArrayList<String>();
               String linea;
               while((linea = br.readLine()) != null) {
                    if(!linea.trim().startsWith("//")) {
                        righeExt.add(linea);
                       // System.out.println("Trovata linea: "+linea);
                    }
               }
               br.close();
               fr.close();
               for(String riga: righeExt) {
                String[] p = riga.split("-->");
                if(p.length >= 2) {
                parole.put(p[0], p[1]); 
            //    System.out.println(parole.get(p[0]));
                }
               }
               }
            }
    }catch(Exception  e) {
    	e.printStackTrace();
    }
        	
              
              mappaInvertita = parole.entrySet()
    				    .stream()
    				    .collect(Collectors.toMap(
    				        Map.Entry::getValue, 
    				        Map.Entry::getKey,
    				        (oldValue, newValue) -> newValue, 
    				        HashMap::new                     
    				    ));
        //Fine Parte Estensioni
        caratteri = new char[(int) file.length()];
        String nomeConEstensione = file.getName();
        
        fileName = "";
        int puntoIndex = nomeConEstensione.lastIndexOf('.');
        
        if (puntoIndex > 0) {
            fileName = nomeConEstensione.substring(0, puntoIndex);
        } else {
            fileName = nomeConEstensione;
        }
        new File(cartellaBase).mkdirs();
        if(!invertiTranspiler) {
        File tempFile = new File(cartellaBase + "/" + fileName + ".java"); fw = new FileWriter(tempFile);
        }else {
        	File tempFile = new File(cartellaBase + "/" + fileName + ".javaita"); fw = new FileWriter(tempFile);
        }

        try {
            String contenuto = Files.readString(file.toPath());
            caratteri = contenuto.toCharArray();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while (puntatore != caratteri.length) {
            charCorrente = caratteri[puntatore];
            if(!(puntatore-1 < 0) && !(puntatore-2 < 0)) {
	            if ((charCorrente == '\'' && !(caratteri[puntatore+1] == '\'' && caratteri[puntatore-1] == '\'' ) && !(caratteri[puntatore-1] == '\'' && caratteri[puntatore-2] == '\'' ))|| charCorrente == '\"' && !(caratteri[puntatore+1] == '\"' && caratteri[puntatore-1] == '\"' ) && !(caratteri[puntatore-1] == '\"' && caratteri[puntatore-2] == '\"' )) {
	                isString = !isString; 
	            } 
            }
            if(puntatore+1 < caratteri.length) {
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
        	    String binPath = cartellaBase;
        	    String javaFile = binPath + "/" + fileName + ".java";

        	    try {
        	        Process compile = new ProcessBuilder("javac", javaFile)
        	                .inheritIO()
        	                .start();
        	        int exitCode = compile.waitFor();

        	        if (exitCode == 0) {
        	        	ArrayList<String> cmd = new ArrayList<>(Arrays.asList("java", "-cp", binPath, fileName));
        	        	cmd.addAll(jITArgs);
        	        	new ProcessBuilder(cmd).inheritIO().start().waitFor();
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
        	if (!mantieniEstensioni) {
                File dir = new File(cartellaBase.concat("/.."));
                File[] files = dir.listFiles();
                    if(ExternExtension != null && ExternExtension.isDirectory()) {
                	for (File f : ExternExtension.listFiles()) {
                		for(File file: files) {
	                        if (file.getName().startsWith(f.getName())) {
	                            file.delete();
	                        }
                		}
                    }
                   
                }else if(ExternExtension != null && !ExternExtension.isDirectory()) {
                	for(File f: files) {
                		if(f.getName().startsWith(ExternExtension.getName())) {
                			f.delete();
                		}
                	}
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static boolean scanChar(int index) throws IOException {
       
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
                
            	for(char del: delimitatoriCorrenti()) {
	                if(charCorrente == del && invertiTranspiler) {
	                	isDelimitedAfter = true;
	                	isDelimitedThen = true; 
            			
	                } else {
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
                }
            	if(isDelimitedAfter && isDelimitedThen) {
            		if(paroleCorrenti().get(parola).equals("package") && noPackage) {
            			String s = new String(caratteri);
            			int indicePackage = s.indexOf('\n', s.indexOf(parola)) +1;
            			puntatore = indicePackage;
            			return true;
            		}
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
            if(!isAdjustedRevert) {
                for(String parola: mappaInvertita.keySet()) {
                    String parolaSpaziata = " ".concat(mappaInvertita.get(parola)).concat(" ");
                    mappaInvertita.put(parola, parolaSpaziata);
                }
                
                isAdjustedRevert = true;
            }
			return mappaInvertita;
		}else {
			return parole;
		}
    }
    
    static char[] delimitatoriCorrenti() {
    	if(invertiTranspiler) {
    		return delimitatoriRevert;
    	}else {
    		return delimitatori;
    	}
    }
    
    }