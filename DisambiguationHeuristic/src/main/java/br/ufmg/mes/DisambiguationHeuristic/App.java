package br.ufmg.mes.DisambiguationHeuristic;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map.Entry;

import org.eclipse.jgit.api.Git;

/**
 *	Disambiguation Heuristics - Talita Santana Orfano
 *	Agosto, 2017
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {         
   		String URL = null;
    	ArrayList<Author> authorsList = new ArrayList<Author>();
    	DataCollectGit dataCollectGit = new DataCollectGit();
    	System.out.print("Digite a URL do repositorio: ");
    	Scanner scanner = new Scanner(System.in);
    	URL = scanner.next();
    	
    	Git git = dataCollectGit.CollectGit(URL);

    	if(git == null)
    	{
    		System.out.println("Erro na criação do repositório para analise.");
    		return;
    	}
    	else{
	    	dataCollectGit.CollectAuthors(authorsList, git);
	    	/*System.out.println("Autores do repositorio");
	    	for(Author a : authorsList){
	    		System.out.println(a.getName() + " "+a.getEmail());
	    	}*/
    	}
    	
    	HashMap<String, ArrayList<Author>> aliasIdentified = new HashMap<String, ArrayList<Author>>();
    	SearchAlias search = new SearchAlias();
    	
    	// Busca alias entre os autores
    	aliasIdentified = search.ExploreAuthors(authorsList);
    	
    	// Exibe resultado
    	int count = 1;
    	System.out.println("Alias presentes no repositorio:");
    	for (Entry<String, ArrayList<Author>> key : aliasIdentified.entrySet()) {
    		System.out.println("Alias " + count);
			 for (Author a : key.getValue()) {
				 System.out.println("    " + a.getName()+" - "+ a.getEmail());
			 }
			 count++;
		}
    }
}
