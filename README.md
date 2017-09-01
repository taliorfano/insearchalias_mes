# insearchalias_mes
A aplicação 'In Search of Alias' tem como objetivo encontrar alias entre os contribuidores de um projeto no GitHub.

<h2> Sobre </h2>

<h3> Coleta </h3>
Coleta do nome e e-mail de cada contribuidor do repositório analisado. Nesta etapa, o nome e o e-mail são tratados para que os registros fiquem consistentes. Por exemplo, se no campo do nome, possui um e-mail, gravamos apenas o prefixo como send o nome.
<br/> 

<!---<ul>
      <li> Se no campo do nome, possui um e-mail, gravamos apenas o prefixo como nome </li>
      <li> Se no campo do e-mail, existe um código hash acoplado ao e-mail, consideramos apenas a parte válida do e-mail, deixando-o consistente, por exemplo: <br/><i>sberlin@gmail.com@d779f126-a31b-0410-b53b-1d3aecad763e é registrado como sberlin@gmail.com </i> </li>
   </ul> -->

<h3> Heurística </h3>

<br/> Inicialmente é verificada a similaridade entre os nomes, seguindo os seguintes critérios:
   <ul>
      <li> Comparação entre os nomes ignorando caracteres especiais </li>
      <li> Comparação entre os nomes observando se o primeiro e ultimo nome são iguais </li>
      <li> Comparação entre os nomes pela similaridade de Jaro-Winkler de 0.85*
         </br> <i> * Valor adotado após testes realizados pela autora </i>
      </li>
   </ul>

<br/>Caso não tenha similaridade entre os nomes, é verificada a similaridade entre os e-mails dos usuários, seguindo os seguintes critérios:
   <ul>
      <li> Comparação entre os prefixos </li>
      <li> Comparação entre os prefixos desconsiderando caracteres como '.', '-' e '_' </li>
      <li> Não foi adotada a similaridade de Jaro-Winkler na comparação de e-mails para evitar falsos positivos </li>
   </ul>

<br/> Portanto, as heurística implementadas permitem detectar similaridades tanto entre o nome, quanto entre os e-mails dos desenvolvedores contribuidores.
<br/>
<h3> Modo de usar
<br/> Basta clonar o projeto e executar a classe App , que se encontra em 'br.ufmg.mes.DisambiguationHeuristic'.
<br/> Após a execução, a aplicação irá solicitar como entrada a URL do repositório que será analisado.
</br> Depois de informada a URL, basta aguardar alguns instantes e o resultado contendo a lista de alias será gerada.

<h3> Exemplo </h3> 
<b> Guice</b>
<b> Entrada </b>
<br/>URL: https://github.com/google/guice.git
<br/>
<br/>
<b> Resultado </b>
<br/>Alias 1
<br/>   limpbizkit - limpbizkit@d779f126-a31b-0410-b53b-1d3aecad763e
<br/>   limpbizkit - limpbizkit@gmail.com
<br/>
<br/> Alias 2
<br/>    Colin Decker - cgdecker@gmail.com
<br/>    Colin Decker - cgdecker@google.com
<br/>
<br/>Alias 3
<br/>    chrisn - chrisn@google.com
<br/>    christophf - christophf@google.com
<br/>    chris.nokleberg - chris.nokleberg@d779f126-a31b-0410-b53b-1d3aecad763e
<br/>
<br/>Alias 4
<br/>    sberlin - sberlin@d779f126-a31b-0410-b53b-1d3aecad763e
<br/>    sberlin - sberlin@gmail.com
<br/>    Sam Berlin - sberlin@gmail.com
<br/>    Sam Berlin - sameb@google.com
<br/>    sameb - sameb@google.com
<br/>    sam - sberlin@gmail.com
<br/>    sameb - sberlin@gmail.com
<br/> 
<br/> OBS> O Guice possui um total de 72 autores.
