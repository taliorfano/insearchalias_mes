package br.ufmg.mes.DisambiguationHeuristic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.gitective.core.CommitUtils;
import org.apache.commons.io.FileUtils;

public class DataCollectGit {
	
	/***
	 * 	Clona o repositorio do Git na maquina
	 * @return
	 * @throws IOException 
	 */
	public Git CollectGit(String url) throws IOException{

		//url = "https://github.com/google/guice.git"; // TODO Tirar 
		
		Git git = null;
		//File dir = new File("C:\\Users\\Talita\\Teste");
		
		File dir = new File("tmp_git");
		
		boolean hasDirectory = dir.isDirectory();
		
		if(!hasDirectory){
			hasDirectory =  dir.mkdir();
			if(!hasDirectory){
				return null;
			}
		}
		else{
			
			FileUtils.cleanDirectory(dir); 
		}
		
		System.out.println("Aguarde clonando o repositorio...");
		git = Git.cloneRepository()
				.setURI(url)
				.setDirectory(dir)
				.call();
		System.out.println("Repositorio clonado. Em analise...");
		return git;
	}
	
	/***
	 * 	Coleta autores do repositorio a partir do master
	 * @param authorsList
	 */
	public void CollectAuthors(ArrayList<Author> authorsList, Git git){
		try {
			/*
			String pathRepository = "C:/Users/Talita/git/guice/.git";
			FileRepositoryBuilder builder = new FileRepositoryBuilder();
			Repository repository = builder.setGitDir(new File((pathRepository))).readEnvironment()
					.findGitDir().build();
			*/
			
			Repository repository = git.getRepository();			
			RevCommit base = CommitUtils.getBase(repository, "/origin/master");

			// Caminha sobre os commits
			RevWalk walk = new RevWalk(repository);
			walk.markStart(walk.parseCommit(base));
			
			for (RevCommit commit : walk) {
				/*System.out.println("Author: "+ 
						commit.getAuthorIdent().getName() + "--  " +
						commit.getAuthorIdent().getEmailAddress());*/
				
				Author author = new Author(DealName(commit.getAuthorIdent().getName()), DealEmail(commit.getAuthorIdent().getEmailAddress()),
											commit.getAuthorIdent().getWhen());
				
				if(!authorsList.contains(author)){
					authorsList.add(author);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Trata e-mail deixando-o consistente, exemplo sberlin@gmail.com@d779f126-a31b-0410-b53b-1d3aecad763e fica sberlin@gmail.com 
	 * @param email
	 * @return
	 */
	public String DealEmail(String email){
		String newEmail = email;
		int qtd = org.apache.commons.lang3.StringUtils.countMatches(email, '@');
		if(qtd > 1){
			String[] split = newEmail.split("@");
		    newEmail = split[0]+'@'+split[1];
		    return newEmail;
		}
		return newEmail;
	}
	
	/**
	 * Trata e-mail deixando-o consistente, exemplo sberlin@gmail.com@d779f126-a31b-0410-b53b-1d3aecad763e fica sberlin@gmail.com 
	 * @param email
	 * @return
	 */
	public String DealName(String name){
		if(name.contains("@")){
			String[] split = name.split("@");
		    name = split[0];
		}
		return name;
	}
	
}
