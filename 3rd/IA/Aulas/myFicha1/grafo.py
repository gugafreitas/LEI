# biblioteca necessária para se poder utilizar o valor math.inf  (infinito)
import math

# Importar a classe nodo
from nodo import Node


# Biblioteca de tratamento de grafos necessária para desenhar graficamente o grafo
# Biblioteca de tratamento de grafos necessária para desenhar graficamente o grafo


# Definição da classe grafo:
# Um grafo tem uma lista de nodos,
# um dicionário:  nome_nodo -> lista de tuplos (nome_nodo,peso)
# para representar as arestas
# uma flag para indicar se é direcionado ou não
class Graph:
    # Construtor da classe
    def __init__(self, directed=False):
        self.m_nodes = []  # lista de nodos do grafo
        self.m_directed = directed  # se o grafo é direcionado ou não
        self.m_graph = {}  # dicionario para armazenar os nodos, arestas e pesos

    # escrever o grafo como string
    def __str__(self):
        out = ""
        for key in self.m_graph.keys():
            out = out + "node " + str(key) + ": " + str(self.m_graph[key]) + "\n"
        return out

    # Adicionar aresta no grafo, com peso
    def add_edge(self, node1, node2, weight):
        n1 = Node(node1)
        n2 = Node(node2)
        if (n1 not in self.m_nodes):
            self.m_nodes.append(n1)
            self.m_graph[node1] = set()

        else:
            n1 = self.get_node_by_name(node1)

        if (n2 not in self.m_nodes):
            self.m_nodes.append(n2)
            self.m_graph[node2] = set()

        else:
            n2 = self.get_node_by_name(node1)

        self.m_graph[node1].add((node2, weight))

        # se o grafo for nao direcionado, colocar a aresta inversa
        if not self.m_directed:
            self.m_graph[node2].add((node1, weight))

    # encontrar o nodo pelo nome
    def get_node_by_name(self, name):
        search_node = Node(name)
        for node in self.m_nodes:
            if node == search_node:
                return node
            else:
                return None

    #imprimir arestas
    def imprime_arestas(self):
        listaA = ""
        for nodo in self.m_graph.keys():
            for (nodo2, custo) in self.m_graph[nodo]:
                listaA = listaA + nodo + " ->" + nodo2 + " custo:" + str(custo) + "\n"
        return listaA


    #devolver o custo de uma aresta
    def get_arc_cost(self, node1, node2):
        custoT = math.inf
        a = self.m_graph[node1]
        for(nodo, custo) in a:
            if nodo == node2:
                custoT = custo

        return custoT

    # Dado um caminho calcula o seu custo
    def calcula_custo(self, caminho):
        teste = caminho
        custo = 0
        i = 0
        while i+1 < len(teste):
            custo = custo + self.get_arc_cost(teste[i], teste[i+1])
            i = i+1
        return custo


    #procura DFS
    def procura_DFS(self, start, end, path=[], visited = set()):
        path.append(start)
        visited.add(start)

        if start == end:
            custoT = self.calcula_custo(path)
            return (path, custoT)
        for (adjacente, peso) in self.m_graph[start]:
            if adjacente not in visited:
                resultado = self.procura_DFS(adjacente, end, path, visited)
                if resultado is not None:
                    return resultado
        path.pop()
        return None