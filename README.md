# insearchalias_mes
A aplicação 'In Search of Alias' tem como objetivo de encontrar alias entre os contribuidores de um projeto no GitHub.

<h2> Metodologia e Heurística </h2>

<h3> Coleta </h3>
Coleta do nome e e-mail de cada contribuidor do repositório analisado. Nesta etapa, o nome e o e-mail são tratados para que os registros fiquem consistentes.
<br/>   
   <ul>
      <li> Se no campo do nome, possui um e-mail, gravamos apenas o prefixo como nome </li>
      <li> Se no campo do e-mail, existe um código hash acoplado ao e-mail, consideramos a penas a parte válida do e-mail, deixando-o consistente, exemplo: <br/>sberlin@gmail.com@d779f126-a31b-0410-b53b-1d3aecad763e é registrado como sberlin@gmail.com </li>
   </ul>


<h2> Exemplo </h2> 
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

