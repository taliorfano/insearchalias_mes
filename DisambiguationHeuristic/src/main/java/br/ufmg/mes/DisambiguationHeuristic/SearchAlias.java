package br.ufmg.mes.DisambiguationHeuristic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class SearchAlias {
	
	/***
	 * 	Explora lista de autores em busca de Alias
	 * @return Lista com alias
	 */
	public HashMap<String, ArrayList<Author>>  ExploreAuthors(ArrayList<Author> authorsList){
		HashMap<String, ArrayList<Author>> aliasName = new HashMap<String, ArrayList<Author>>();
		HashMap<String, ArrayList<Author>> aliasEmail = new HashMap<String, ArrayList<Author>>();
		HashMap<String, ArrayList<Author>> aliasIdentified = new HashMap<String, ArrayList<Author>>();
		
		Heuristic heuristic = new Heuristic();
		boolean heuristicEmail = false;
		boolean heuristicName = false;
		
		Author[] authors = authorsList.toArray(new Author[0]);
    	
    	for(int i = 0; i < authors.length; i++){
    		for(int j = i +1; j < authors.length; j++){
    			// Verifica heuristica de similaridade pelo nome e depois pelo e-mail
    			heuristicName = heuristic.CompareName(authors[i].getName(), authors[j].getName());
    			if(heuristicName){
    				ArrayList<Author> authorsAliasList = aliasName.get(authors[i].getName());
    				if(authorsAliasList == null)
    				{
    					authorsAliasList = new ArrayList<Author>();
    				}
    				AddAuthorsToList(authors[i], authors[j], authorsAliasList);
    				aliasName.put(authors[i].getName(), authorsAliasList);
    			}
    			else{
    				heuristicEmail = heuristic.CompareEmail(authors[i].getEmail(), authors[j].getEmail());
        			if(heuristicEmail){
        				ArrayList<Author> authorsAliasList = aliasEmail.get(authors[i].getEmail());
        				if(authorsAliasList == null)
        				{
        					authorsAliasList = new ArrayList<Author>();
        				}
        				AddAuthorsToList(authors[i], authors[j], authorsAliasList);
        				aliasEmail.put(authors[i].getEmail(), authorsAliasList);
        			}
    			}
    		}
    	}
    	
    	/*
    	// Visualiza Resultado
    	System.out.println("Resultado alias name");
    	for (Entry<String, ArrayList<Author>> key : aliasName.entrySet()) {
			 System.out.println("Key:"+ key.getKey());
			 for (Author a : key.getValue()) {
				 System.out.println("    " + a.getName()+" - "+ a.getEmail());
			 }
		}
    	
    	System.out.println("Resultado alias email");
    	for (Entry<String, ArrayList<Author>> key : aliasEmail.entrySet()) {
			 System.out.println("Key:"+ key.getKey());
			 for (Author a : key.getValue()) {
				 System.out.println("    " + a.getName()+" - "+ a.getEmail());
			 }
		}*/
    	
    	aliasIdentified = MergeAliasList(aliasName, aliasEmail);
    	return aliasIdentified;
	}
	
	/**
	 * Adiciona autores na lista de alias quando eles sao similares
	 * @param a1 Author 1
	 * @param a2 Author 2
	 * @param authorsAliasList
	 */
	public void AddAuthorsToList(Author a1, Author a2, ArrayList<Author> authorsAliasList){
		if(!authorsAliasList.contains(a1)){
			authorsAliasList.add(a1);
		}
		if(!authorsAliasList.contains(a2)){
			authorsAliasList.add(a2);
		}
	}
	
	/**
	 * Realiza merge das listas de alias de nome e e-mail
	 */
	public HashMap<String, ArrayList<Author>> MergeAliasList(HashMap<String, ArrayList<Author>> aliasName, HashMap<String, ArrayList<Author>> aliasEmail){
		HashMap<String, ArrayList<Author>> alias = new HashMap<String, ArrayList<Author>>();
		alias = (HashMap<String, ArrayList<Author>>) aliasName.clone();
		
		for (Entry<String, ArrayList<Author>> key : aliasName.entrySet()) {
			 //System.out.println("Key:"+ key.getKey());
			 String name = key.getKey(); // joao joao@gmail.com
			 							// j     joao@gmai.com
			 
			 for (Entry<String, ArrayList<Author>> key2 : aliasEmail.entrySet()) {
				 boolean hasAliasEquals = false;
				 for (Author a : key.getValue()) {
					 if(a.getName().equals(name)){
						 hasAliasEquals = true;
					 }
				 }
				 
				 // deve fazer merge
				 if(hasAliasEquals){
					 ArrayList<Author> array = alias.get(name);
					 for (Author a : key.getValue()) {
						 if(!array.contains(a)){
							 array.add(a);
						 }
						 
					 }
					 alias.put(name, array);
				 }
			}
			 
		}
		
		HashMap<String, ArrayList<Author>> aliasFinal = (HashMap<String, ArrayList<Author>>) alias.clone();
		HashMap<String, ArrayList<Author>> alias2 = (HashMap<String, ArrayList<Author>>)alias.clone();
		ArrayList<String> removedKeys = new ArrayList<String>();		
		
		for (Entry<String, ArrayList<Author>> key : alias.entrySet()) {
			 //System.out.println("Key:"+ key.getKey());
			 String keyName = key.getKey(); // joao joao@gmail.com
			 							 // joao joaom@gmail.com
			 
			 if(removedKeys.contains(key.getKey())){
				 continue;
			 }
			 
			 // Coloca num array os e-mails desse usuario
			 ArrayList<String> emails = new ArrayList<String>();
			 for(Author a : key.getValue()){
				 if(!emails.contains(a.getEmail())){
					 emails.add(a.getEmail());
				 }
			 }
			 
			 // Verifica se dentre os outros usuarios possui alguem com o mesmo e-mail desse se houver faz merge
			 for (Entry<String, ArrayList<Author>> key2 : alias2.entrySet()) {
				 if(key2.getKey() == keyName || removedKeys.contains(key2.getKey())){ // ele mesmo
					 continue;
				 }
				 boolean hasEqual = false;
				 
				 // Verifica se existe email em comum entre os usuarios
				 for(Author a : key2.getValue()){
					 if(emails.contains(a.getEmail())){
						 hasEqual = true;
						 break;
					 }
				 }
				 
				 if(hasEqual){
					 JoinAuthor(aliasFinal, key2.getValue(), keyName);
					 aliasFinal.remove(key2.getKey());
					 removedKeys.add(key2.getKey());
				 }
			 }
			 
			 /*for (Entry<String, ArrayList<Author>> key2 : alias.entrySet()) {
				 boolean hasAliasEquals = false;
				 for (Author a : key.getValue()) {
					 if(a.getEmail().equals(email)){
						 hasAliasEquals = true;
					 }
				 }
				 
				 // deve fazer merge
				 if(hasAliasEquals){
					 ArrayList<Author> array = alias.get(email);
					 for (Author a : key.getValue()) {
						 if(!array.contains(a)){
							 array.add(a);
						 }
						 
					 }
					 alias.put(email, array);
				 }
			}*/
		}
		return aliasFinal;
	}
	
	/**
	 * 
	 * @param alias
	 * @param author1
	 * @param author2
	 * @param key
	 */
	public void JoinAuthor(HashMap<String, ArrayList<Author>> alias, ArrayList<Author> authorsList, String key){
		ArrayList<Author> authors = alias.get(key);
		
		for(Author a : authorsList){
			if(!authors.contains(a)){
				authors.add(a);
			}
		}
		
		alias.put(key, authors);
	}
	
	public void AddAliasMap(HashMap<Integer, ArrayList<Author>> alias, Author author){
		int tam = alias.size();
		
		/*for (Entry<Integer, ArrayList<Author>> user : alias.entrySet()) {
			 boolean hasAliasEquals = false;
			 for (Author a : user.getValue()) {
				 if(a.getEmail().equals(email)){
					 hasAliasEquals = true;
				 }
			 }
		}
		
		alias.put(tam+1, value)
		
		*/
		
		
			
	}
	
	
}
