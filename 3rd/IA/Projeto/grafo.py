from nodo import nodo
import queue as q
import carro


class grafo :

    def __init__ (self) :
        self.nodos = {} # Dict de nodo com o id, tipo de objeto e coords do mesmo
        self.nodos_list = []
        self.comprimento = 0
        self.largura = 0
        self.meta = -1 # string com o id dos nodos correspondetes a meta 
        self.partida = -1 # string com id da linha de partida
        self.path = []
        self.custo = 0 # TODO: lista com os custos de cada um; secalhar associar custi ao carro ???
        self.carros = {} # mapa com os players  
        self.chegada = [] #lista de id de carros por ordem de chegada à meta

    def calculaId(self,coordx,coordy):
        x = int(coordx)
        y = int(coordy)
        largura = int(self.largura)
        return largura * y + x

    def calculaCoords(self,id):
        largura = int(self.largura)
        y = int(id)//largura
        x = int(id) - (y * largura)
        return (x,y)
    # id = 17 (3,1)

    def setLargura(self, largura):
        self.largura = largura

    def getLargura(self):
        return self.largura

    def setComprimento(self,comprimento):
        self.comprimento = comprimento
        

    def getPartida (self) :
        return self.partida

    def getMeta(self) : 
        return self.meta

    def setPartida (self,coordx,coordy):
        idNodo = self.calculaId(coordx,coordy)
        aux = nodo()
        aux = self.nodos.get(idNodo)
        aux.setTipo("Partida")
        aux.setCoords(coordx,coordy)
        self.nodos[idNodo] = aux
        self.partida = idNodo

    def setCost(self,carro : carro):
        custo = 0 
        for id in self.path:
            custo += self.heuristica(id)
        if(custo > 0):
            custo -= 1
        carro.setCusto(custo)
        
        
    def setMeta(self,coordx,coordy):
        idNodo = self.calculaId(coordx,coordy)
        aux = nodo()
        aux = self.nodos.get(idNodo)
        aux.setTipo("Meta")
        aux.setCoords(coordx,coordy)        
        self.nodos[idNodo] = aux
        self.meta = idNodo

    def __str__(self):
        res = ""
        for obj in self.path:
            res = res + obj + "\n"
        return res
    
    def getPath(self):
        #return self.path
        largura = self.largura
        res = []
        for pos in self.path:
            aux = pos
            x = 0
            y = 0
            while aux > 0:
                if aux >= largura:
                    aux -= largura
                    y += 1
                else:
                    x += aux
                    aux = 0
                string =  "(" + str(x) + "," + str(y) + ")" 

            res.append(string)
        
        return res

    def txt_para_grafo (self,pista) : 
        y=0
        x=0
        fx=0
        fy=0
        # Para identificar a largura e comprimento para usar no ID
        with open(pista, 'r') as txt:
            lines = txt.readlines() 
        for l in lines:  
            x= 0
            for o in l :
                x+=1
            y+=1
        self.comprimento = y
        self.largura = x 


#---------------------------------------------------------------------------------------------------------------------------------------------------------------
        with open(pista, 'r') as txt:
            lines = txt.readlines() 

        for l in lines:
            fx = 0
            for o in l :
                id_nodo = self.calculaId(fx,fy)
                nodo_aux = nodo() 
                if o == 'X' :  
                    nodo_aux.setNodo(id_nodo,"Parede",x,y)
                elif o == '-' : 
                    nodo_aux.setNodo(id_nodo,"Pista",x,y)
                elif o == 'P' :    
                    nodo_aux.setNodo(id_nodo,"Partida",x,y)
                    self.partida = id_nodo
                elif o == 'J' :  
                    nodo_aux.setNodo(id_nodo,"Jogador",x,y)
                    aux_carro = carro.carro()
                    aux_carro.setCarro(id_nodo, (x,y), (0,0), (0,0))
                    self.carros[id_nodo] = aux_carro
                elif o == 'F' :
                    nodo_aux.setNodo(id_nodo,"Meta",x,y)
                    self.meta = id_nodo 
                self.nodos[id_nodo] = nodo_aux
                fx+=1
            fy+=1
        

    def search_vizinhos(self,id):
        # 0<=y<=7 0<=x<=13
        largura = self.largura
        comprimento = self.comprimento
        vizinhos = []
        (idX, idY) = self.calculaCoords(id)
        
        if idY>0 and idX>0 and idY<comprimento and idX<largura:
            auxVizinhos = [id-largura, id+largura, id+1, id-1, id-largura+1, id-largura-1, id+largura-1, id+largura+1]
            for n in auxVizinhos:
                (nX, nY) = self.calculaCoords(n)
                if nY>0 and nX>0 and nY<comprimento and nX<largura and self.nodos.get(n).getTipo() != 'Parede':
                    vizinhos.append(n)

        return vizinhos


    def isVizinho(self, idNodo, idVizinho):
        '''
        idAcima = nodo - self.largura
        idAbaixo = nodo + self.largura
        if self.nodos[vizinho].getTipo() == "Parede":
            return False
        elif vizinho == nodo+1 or vizinho == nodo-1:
            return True
        elif vizinho == idAcima or vizinho == idAcima+1 or vizinho == idAcima-1:
            return True
        elif vizinho == idAbaixo or vizinho == idAbaixo+1 or vizinho == idAbaixo-1:
            return True
    ''' 
        return None
    # --------------------------------------------------------------------------------------------------------------------------------------------------------


    def procura_DFS(self, start, end, path=[], visited=set()):
        path.append(start)
        visited.add(start)
        if start == end:
            self.path = list(path)
            #print(self.path)
        else:
            for neighbour in self.search_vizinhos(start):
                #print(str(neighbour) + ' -> ' + str(self.search_vizinhos(start)))
                #print(path)
                if neighbour not in visited:
                    result = self.procura_DFS(neighbour, end, path, visited)
                    if result is not None:
                        return result
            path.pop()
            return None

    # --------------------------------------------------------------------------------------------------------------------------------------------------------


    def procura_BFS(self, partida):

        fila = q.Queue()  
        visited = set()

        pai = dict()
        pai[partida] = None

        visited.add(partida)
        fila.put(partida)

        vizinhos = []

        aux = 0 

        while fila: # loop visita todos os nodos
            m = fila.get() 
            if m == self.meta: #encontra um nodo que faz parte da meta
                aux = 1
                break

            vizinhos = self.search_vizinhos(m)

            for vizinho in vizinhos:
                if self.nodos[vizinho].getTipo()!="Parede":
                    if vizinho not in visited:
                        visited.add(vizinho)
                        fila.put(vizinho)
                        pai[vizinho] = m
            
        if aux == 1 : 
            self.path.append(m)
            while m != partida : 
                self.path.append(pai[m])
                m = pai[m]
                self.custo = self.setCost(self.carros.get(partida))
        

    

    def heuristica(self, id):
        aux = nodo()
        aux = self.nodos[id]
        type = aux.getTipo()
        
        H = { #TODO: ver custos, eu acho que e isto, mas temos de ver as questoes dos jogadores
            'Parede': 25,
            'Pista': 1,
            'Jogador': 1,
            'Meta': 1,
            'Partida' : 1
        }
    
        return H[type]

    # --------------------------------------------------------------------------------------------------------------------------------------------------------



    def procura_a_star(self, start, stop):
            # In this open_lst is a lisy of nodes which have been visited, but who's 
            # neighbours haven't all been always inspected, It starts off with the start node
            # And closedList is a list of nodes which have been visited
            # and who's neighbors have been always inspected
            openList = set([start])
            closedList = set([])
    
            # poo has present distances from start to all other nodes
            # the default value is +infinity
            poo = {}
            poo[start] = 0
    
            # par contains an adjac mapping of all nodes
            par = {}
            par[start] = start
    
            while len(openList) > 0:
                n = None
    
                # it will find a node with the lowest value of f() -
                for v in openList:
                    if n == None or poo[v] + self.heuristica(v) < poo[n] + self.heuristica(n):
                        n = v
    
                if n == None:
                    print('Path does not exist!')
                    return None
    
                # if the current node is the stop
                # then we start again from start
                if n == stop:
                    reconst_path = []
    
                    while par[n] != n:
                        reconst_path.append(n)
                        n = par[n]
    
                    reconst_path.append(start)
    
                    reconst_path.reverse()
    
                    self.path = reconst_path
                    break
    
                # for all the neighbors of the current node do
                for m in self.search_vizinhos(n):
                # if the current node is not presentin both openList and closedList
                    # add it to openList and note n as it's par
                    if m not in openList and m not in closedList:
                        openList.add(m)
                        par[m] = n
                        poo[m] = poo[n] + self.heuristica(m)
    
                    # otherwise, check if it's quicker to first visit n, then m
                    # and if it is, update par data and poo data
                    # and if the node was in the closedList, move it to openList
                    else:
                        if poo[m] > poo[n] + self.heuristica(m):
                            poo[m] = poo[n] + self.heuristica(m)
                            par[m] = n
    
                            if m in closedList:
                                closedList.remove(m)
                                openList.add(m)
    
                # remove n from the openList, and add it to closedList
                # because all of his neighbors were inspected
                if n in openList:  
                    openList.remove(n)
                    closedList.add(n)
    
            return None
        


    # --------------------------------------------------------------------------------------------------------------------------------------------------------


    def greedy(self, start, end):
        # open_list é uma lista de nodos visitados, mas com vizinhos
        # que ainda não foram todos visitados, começa com o  start
        # closed_list é uma lista de nodos visitados
        # e todos os seus vizinhos também já o foram
        open_list = set([start])
        closed_list = set([])

        # parents é um dicionário que mantém o antecessor de um nodo
        # começa com start
        parents = {}
        parents[start] = start

        while len(open_list) > 0:
            n = None

            # encontraf nodo com a menor heuristica
            for v in open_list:
                if n == None or self.heuristica(v) < self.heuristica(n):
                    n = v

            if n == None:
                print('Path does not exist!')
                return None

            # se o nodo corrente é o destino
            # reconstruir o caminho a partir desse nodo até ao start
            # seguindo o antecessor
            if n == end:
                reconst_path = []

                while parents[n] != n:
                    reconst_path.append(n)
                    n = parents[n]

                reconst_path.append(start)

                reconst_path.reverse()
                self.path = reconst_path

                return (reconst_path, self.setCost(self.carros.get(start)))

            # para todos os vizinhos  do nodo corrente
            for m in self.search_vizinhos(n):
                # Se o nodo corrente nao esta na open nem na closed list
                # adiciona-lo à open_list e marcar o antecessor
                if m not in open_list and m not in closed_list:
                    open_list.add(m)
                    parents[m] = n

            # remover n da open_list e adiciona-lo à closed_list
            # porque todos os seus vizinhos foram inspecionados
            open_list.remove(n)
            closed_list.add(n)

        print('Path does not exist!')
        return None


    
    def chooseAlg(self, choice, partida, chegada):
        if choice == '1':
            self.procura_BFS(partida)
        elif choice == '2':
            self.procura_a_star(partida, chegada)
        elif choice ==  '3':
            self.procura_DFS(partida,chegada,path=[],visited=set())
        elif choice == '4': 
            self.greedy(partida,chegada)
        else:
            print('noti noti')
            self.chooseAlg(choice, partida, chegada)
            
        

    
        