%aluno()
aluno(1,joao,m).
aluno(2,antonio,m).
aluno(3,carlos,m).
aluno(4,luisa,f).
aluno(5,maria,f).
aluno(6,isabel,f).

%curso()
curso(1,lei).
curso(2,miei).
curso(3,lcc).

%disciplina(cod,sigla,ano,curso)
disciplina(1,ed,2,1).
disciplina(2,ia,3,1).
disciplina(3,fp,1,2).

%inscrito(aluno,disciplina)
inscrito(1,1).
inscrito(1,2).
inscrito(5,3).
inscrito(5,5).
inscrito(2,5).

%nota(aluno,disciplina,nota)
nota(1,1,15).
nota(1,2,16).
nota(1,5,20).
nota(2,5,10).
nota(3,5,8).

%copia
copia(1,2).
copia(2,3).
copia(3,4).


%ex1
filter(_, [], []).
filter(Predicado, [X | Xs], [X | Ys]) :-
    call(Predicado, X),
    filter(Predicado, Xs, Ys).
filter(Predicado, [_ | Xs], Ys) :-
    filter(Predicado, Xs, Ys).

nao_inscrito(Aluno) :-
    aluno(Aluno, _, _),
    not(inscrito(Aluno, _)).

alunos_nao_inscritos(Alunos) :-
    findall(Aluno, aluno(Aluno, _, _), AlunosTodos),
    filter(nao_inscrito, AlunosTodos, Alunos).


%ex2
nao_inscrito(Aluno) :-
    aluno(Aluno, _, _),
    not(inscrito(Aluno, _)) ; 
    inscrito(Aluno,X), 
    not(disciplina(X,_,_,_)).



%ex3
media(Aluno,R) :- not(member(Aluno,alunos_nao_inscritos(Alunos))) -> 
    findall(N,nota(Aluno,_,N),List) , 
    sumList(List,R1) , 
    length(List,L), 
    R is (R1/L).


%ex4
mediaNotasTotal(R) :- findall(N,nota(_,_,N),List), sumlist(List,R1), length(List,L), R is (R1/L).

acimaMedia(Aluno) :- mediaNotasTotal(M), media(Aluno, Me), (Me>M).

alunosInscritos(R) :- findall(N,aluno(N,_,_),L1), findall(N,inscrito(N,_),L2), findall(Elem, (member(Elem, L1), member(Elem, L2)), L3), R is L3.
