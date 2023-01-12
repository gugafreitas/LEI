
class carro:

    def __init__(self):
        self.id = -1
        self.posicao = (0,0)
        self.velocidade = (0,0)
        self.aceleracao = (0,0)
        self.custo = 0 # custo que o carro tem para chegar a meta 

    def setCarro(self,id,posicao,velocidade,acel):
        self.id = id
        self.posicao = posicao
        self.velocidade = velocidade
        self.aceleracao = acel

    def getCarro(self):
        res = {}
        res["id"] = self.id
        res["posicao"] = self.posicao
        res["velocidade"] = self.velocidade
        res["aceleracao"] = self.aceleracao
        return res

    def getID(self):
        return  self.id

    def getPosicao(self):
        return self.posicao
    
    def getPosicao_X(self):
        return self.posicao[0]
    
    def getPosicao_Y(self):
        return self.posicao[1]
    
    def setPosicao(self, pos):
        self.posicao = pos 
    
    def setPosicao_X(self, X):
        self.posicao = (X,self.posicao[1])
    
    def setPosicao_Y(self, Y):
        self.posicao = (self.posicao[0],Y)

    def setCusto(self,custo) : 
        self.custo = custo

    def getCusto (self) : 
        return self.custo

    def novaPosicao(self):
        a1 = self.posicao[0] + self.velocidade[0] + self.aceleracao[0]
        a2 = self.posicao[1] + self.velocidade[1] + self.aceleracao[1]
        self.posicao = (a1,a2)



    def getVel(self):
        return self.velocidade

    def setVel(self,vel):
        self.velocidade = vel

    def getVelx(self):
        return self.velocidade[0]

    def setVelx(self,x):
        self.velocidade = (x,self.velocidade[1])

    def getVely(self):
        return self.velocidade[1]

    def setVely(self,y):
        self.velocidade = (self.velocidade[0],y)

    def novaVelocidade(self):
        a1 = self.velocidade[0] + self.aceleracao[0]
        a2 = self.velocidade[1] + self.aceleracao[1]
        self.velocidade = (a1,a2)


    
    def getAccel(self):
        return self.aceleracao

    def setAccel(self,accel):
        self.aceleracao = accel

    def getAccelx(self):
        return self.aceleracao[0]

    def setAccelx(self,x):
        self.aceleracao = (x,self.aceleracao[1])

    def getAccely(self):
        return self.aceleracao[1]

    def setAccely(self,y):
        self.aceleracao = (self.aceleracao[0],y)

#pode escolher:
#1.0-calcular o nodo a que chega se não mudar nada
#1.1-aumentar/diminuir aceleração no x
#1.1-aumentar/diminuir aceleração no y
#1.2-calcular o nodo a que chega se não mudar nada
#2.0-manter o estado e andar para a frente
#
#