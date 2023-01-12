%LICENCIATURA EM ENGENHARIA INFORMÃTICA
%MESTRADO integrado EM ENGENHARIA INFORMÃTICA

%InteligÃªncia Artificial
%2022/23

%Draft Ficha 7


% Parte I
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Operacoes aritmeticas

%--------------------------------- - - - - - - - - - -  -  -  -  -   -

%--------------------------------- - - - - - - - - - -  -  -  -  -   -

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado soma: X,Y,Z,Soma -> {V,F}

soma(X,Y,Z,Soma ) :- Soma is X+Y+Z.

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado somaL: L ,Soma -> {V,F}

somaL([],0).
somaL([X|L],R) :- somaL(T,R1), R is H+R1. 

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado maior: X,Y,R -> {V,F}

maior(X,Y,R) :- ((X>Y) -> (R is X) ; (R is Y)).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado maior: Lista, M, Resultado -> {V,}

maiorL([],0).
maiorL([X|L],R) :- maiorL(L,R1), maiorL(X,R1,R).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Quantidade de elementos de uma lista.

quantidade([],0).
quantidade([X|Y],R) :- quantidade(Y,R1) , R is R1+1.

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Tamanho de uma Lista

tamanhoL([],0).
tamanhoL([X|Y],R) :- tamanhoL(Y,R1) , R is 1+R1.

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Média aritmética de uma lista

mediaL([X],X).
mediaL([X|Y],R) :- somaL([X|Y],S) , tamanhoL([X|Y],T), R is (S/T).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% verificar se um numero é par

checkPar(X) :- (X mod 2) =:= 0.

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado que ordene de modo crescente uma sequência de valores

crescente(X,R) :- sort(X,R).

% Parte II--------------------------------------------------------- - - - - -



%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado pertence: Elemento,Lista -> {V,F}

pertence( X,[X|L] ) pertence( X,[Y|L] ) :- X \= Y, pertence( X,L ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado comprimento: Lista,Comprimento -> {V,F}

comprimento([],0).
comprimento([X|Y],R) :- tamanhoL(Y,R1) , R is 1+R1.


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado quantos: Lista,Comprimento -> {V,F}

diferentes([],0).
diferentes([H|T], R) :- (not(pertence(H,T))) -> (diferentes(T,R1), R is R1+1) ; (diferentes(T,R1), R is R1 + 0).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado apagar: Elemento,Lista,Resultado -> {V,F}

apaga1(X,[],[]).
apaga1(X,[Y],R) :- (X=:=Y -> R is []) ; R is [Y].
apaga1(X,[Y,Z],R) :- (not(pertence(X,[Y,Z])) -> R is [Y,Z]) ; X=:=Y -> R is [Z] ; apaga1(X,Z,R).
          
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado apagatudo: Elemento,Lista,Resultado -> {V,F}




%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado adicionar: Elemento,Lista,Resultado -> {V,F}


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado concatenar: Lista1,Lista2,Resultado -> {V,F}


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado inverter: Lista,Resultado -> {V,F}


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado sublista: SubLista,Lista -> {V,F}
