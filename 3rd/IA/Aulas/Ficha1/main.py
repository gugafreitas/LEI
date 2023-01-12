#procura DFS
def procura_DFS(self,start,end,path=[],visited=set()):
    path.append(start)
    visited.add(start)

    if start==end:
        #calcular o custo do caminho função calcula custo
        custoT=self.calcula_custo(path)
        return(path,custoT)
    for(adjacente,peso) in self.m_graph[start]:
        if adjacente not in visited:
            resultado=self.procura_DFS(adjacente,end,path,visited)
            if resultado is not None:
                return resultado
    path.pop() #se nao encontra remover o que está no caminho...
    return None



def main():
    n1=Node("teste")
    n2=Node("teste2")
    g=Graph()
    g.add.edge()