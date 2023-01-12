import grafo

def main () : 
    graph = grafo.grafo()
#     print("Write down the width:")
#     graph.setLargura(input())
# #    print ("Main Largura: "+ str(graph.getLargura()))

#     print("Write down the height")
#     graph.setComprimento(input()) TODO: Mapa aleatorio??  INTERFACE GRAFICA


    print("Welcome, choose the map that you want to play")
    mapa_nr = input()
    if (int(mapa_nr)==1) :
        graph.txt_para_grafo("pista1.txt")
    elif(int(mapa_nr)==2) : 
        graph.txt_para_grafo("pista2.txt")  
    elif(int(mapa_nr)==3) : 
        graph.txt_para_grafo("pista3.txt")  
    elif(int(mapa_nr)==4) : 
        graph.txt_para_grafo("pista4.txt")  
    else :
        print("Sorry that map doesn't exist")
        

    print("Which algorithm would you like to use?")
    print("1 - BFS\n2 - A*\n3 - DFS\n4 - Greedy")
    nr = input()

    for key, value in graph.carros.items():
        graph.chooseAlg(nr,key,graph.getMeta())

        if (nr == '1') :
            print(graph.getPath()[::-1])

        else : 
            print(graph.getPath())
        
        graph.setCost(value)
        print("Custo de execução: " + str(value.getCusto()))
        



if __name__ == "__main__":
    main()
# TODO: Acrescentar mais jogadores e eles passam a ser os pontos de partida 
#       Velocidades 