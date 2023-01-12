%LICENCIATURA EM ENGENHARIA INFORMÁTICA
%MESTRADO integrado EM ENGENHARIA INFORMÁTICA

%Inteligência Artificial
%2022/23

%Draft Ficha 6


% Extensao do predicado filho: Filho,Pai -> {V,F}

filho( joao,jose ).
filho( jose,manuel ).
filho( carlos,jose ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado pai: Pai,Filho -> {V,F}

pai( P,F ) :- filho( F,P ).
pai(paulo,filipe).
pai(paulo,maria).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado avo: Avo,Neto -> {V,F}

avo(antonio,nadia).
neto(nuno,ana).
sexo(joao,masculino).
sexo(jose,masculino).
sexo(maria,feminino).
sexo(joana,feminino).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -

% 12
pai(P,F) :- filho(F,P).


%13
avo(A,N) :- filho(N,X) , pai(A,X).

%14
avo(N,A) :- neto(A,N).

%15
descendente(X,Y) :- filho(X,Y) ; neto(X,Y) ; (filho(X,P) , descendente(P,Y)).

%16
descendente(X,Y,1) :- filho(X,Y).
descendente(X,Y,N) :- pai(Y,P), G is N-1, descendente(X,P,G).

%17


%18
bisavo(X,Y) :- avo(X,P) , pai(P,Y).

%19
trisavo(X,Y) :- (bisavo(P,Y) , pai(X,P)) ; (avo(X,Z) , avo(Z,Y)).

%20
tetraneto(X,Y) :- trisavo(Y,X).
