class pista : 

    def __init__(self):
        self.meta = {}
        self.linhadepartida = {}
        self.comprimento = 0
        self.largura = 0 
        self.obstaculos = [] #Lista de todos os obstaculos, seria melhor fazer um dict??
        self.livres = []

    def setpista(self,meta,partida,comprimento,largura,obstaculos,livres) : 
        self.meta = meta
        self.linhadepartida = partida
        self.comprimento = comprimento
        self.largura = largura
        self.obstaculos.append(obstaculos)
        self.livres.append(livres)

    def getpista(self) :
        dict = {} 
        dict["meta"] = self.meta
        dict["partida"] = self.linhadepartida
        dict["comprimento"] = self.comprimento
        dict["largura"] = self.largura
        dict["obstaculos"] = self.obstaculos
        dict["livres"] = self.livres
        return dict 

    def getpartida(self) :
        return self.linhadepartida

    def getmeta(self):
        return self.meta

    def setmeta(self,posicao) :
        self.meta[posicao] = posicao

    def setpartida(self,posicao) :
        self.linhadepartida[posicao] = posicao

    def setobstaculos(self,posicao) :
        self.obstaculos.append(posicao)
    
    def setlivres(self,posicao) : 
        self.livres.append(posicao)

def read_txt (ficheiro,pista1 : pista) : #TODO: por os x e y's direitos
    f1 = open(ficheiro,"r")
    x=0
    y=0
    while (1):
        word = f1.read(1)
        if not word:
            break
        if word == '\n' :
            pista1.largura = x 
            x = 0
            y+= 1
        if (word == 'X') :
            pista1.setobstaculos((x,y))
            x+=1
        if (word == '-') :
            pista1.setlivres((x,y))
            x+=1
    pista1.comprimento = y+1
    f1.close


def print_txt (ficheiro) : 
    f1 = open(ficheiro,"r")
    for line in f1:
        for word in line.split():
            print(word)
        