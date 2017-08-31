package br.ufmg.mes.DisambiguationHeuristic;

import java.text.Normalizer;

import info.debatty.java.stringsimilarity.JaroWinkler;
import info.debatty.java.stringsimilarity.Levenshtein;
import info.debatty.java.stringsimilarity.NormalizedLevenshtein;

public class Heuristic {
	/***
	 * Heuristicas que comparam dois nomes
	 * @param name1
	 * @param name2
	 * @return True se são similares
	 */
	public boolean CompareName(String name1, String name2){
		
		// Ignora case
		if(name1.equalsIgnoreCase(name2)){
			return true;
		}
		
		// Ignora caracteres especiais
		if(NoSpecialCaracthers(name1).equalsIgnoreCase(NoSpecialCaracthers(name2))){
			return true;
		}
		
		// Compara primeiro e ultimo nome
		if(FirstName(name1).equalsIgnoreCase(FirstName(name2)) && LastName(name1).equalsIgnoreCase(LastName(name2))){
			return true;
		}
		
		// Compara a similaridade de Jaro-Winkler
		if(SimilarityJaroWinkler(name1, name2) > 0.85){
			return true;
		}
		
		// TODO others heuristics
		
		return false;
	}
	
	
	/***
	 * Heuristicas que comparam dois e-mails 
	 * @param email1
	 * @param email2
	 * @return True se sao similares
	 */
	public boolean CompareEmail(String email1, String email2){
		// Ignora case
		if(email1.equalsIgnoreCase(email2)){
			return true;
		}
		
		String prefix1 = PrefixEmail(email1);
		String prefix2 = PrefixEmail(email2);
		
		// Verifica se os prefixos sao iguais
		if(prefix1.equalsIgnoreCase(prefix2)){
			return true;
		}
		
		// Compara prefixos sem caracteres ponto, hifen, etc...
		if(OnlyAlphabetCharacters(prefix1).equalsIgnoreCase(OnlyAlphabetCharacters(prefix2))){
			return true;
		}
		
		// TODO others heuristics
		
		return false;
	}
	

	/**
	 * Retira caracteres especiais da String
	 * @param name
	 * @return
	 */
	public static String NoSpecialCaracthers(String name){
		name = Normalizer.normalize(name, Normalizer.Form.NFD);
   		name = name.replaceAll("[^\\p{ASCII}]", "");   		
		return name;
	}
	
	/***
	 * First name
	 * @param name
	 * @return first name
	 */
	public String FirstName(String name){
        String[] split = name.split(" ");
        String first = split[0];
        return first;
	}
			
	/***
	 * Last name
	 * @param name
	 * @return last name
	 */
	public String LastName(String name){
		String[] split = name.split(" ");
        String last =  split[split.length - 1];
        return last;
	}
	
	/***
	 * Get prefix de e-mail
	 * @param email
	 * @return
	 */
	public String PrefixEmail(String email){
        String[] split = email.split("@");
        String newEmail = split[0];
        return newEmail;
	}
    
    /***
     * Only alphabet characters
     * @param name
     * @return
     */
    public static String OnlyAlphabetCharacters(String prefix){
    	prefix = Normalizer.normalize(prefix, Normalizer.Form.NFD);
    	prefix = prefix.replaceAll("[^A-Za-z]", "");
   		
    	/*name = name.replaceAll("_", "");
   		name = name.replaceAll(".", "");
   		name = name.replaceAll("-", "");*/
   		return prefix;
    }
	
	/***
	 * Retorna a similaridade de Jaro-Winkler de 0 a 1
	 * @param name1
	 * @param name2
	 * @return valor da similaridade
	 */
	public double SimilarityJaroWinkler(String name1, String name2){
     	JaroWinkler jw = new JaroWinkler();
        return jw.similarity(name1, name2);
	}
	
	/***
	 *  Distancia de Levenshtein - nao adotada
	 */
	public void DistanceLevenshtein(String name1, String name2){
  		 NormalizedLevenshtein lv = new NormalizedLevenshtein();
  		 Levenshtein l = new Levenshtein();

        System.out.println(l.distance(name1, name2));
        System.out.println(lv.distance(name1, name2));
	}
}
