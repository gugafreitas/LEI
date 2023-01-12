class nodo  :

    def __init__(self) : 
        self.id = ""
        self.tipo = "" # Parede, posição jogador, área jogavel 
        self.coords = (-1,-1)


    def setCoords(self,x,y):
        self.coords = (x,y)
        
    def setId(self, id):
        self.id = id

    def setNodo(self, id, tipo, x, y):
        self.coords = (x,y)
        self.id = id
        self.tipo = tipo

    def get_Coords(self): 
        return self.coords
    
    def getX(self):
        return self.coords[0]
    
    def getY(self):
        return self.coords[1]

    def getId(self):
        return self.id

    def getTipo(self) : 
        return self.tipo

    def setTipo(self,tipo) : 
        self.tipo = tipo