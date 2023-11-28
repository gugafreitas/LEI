import re

def teste():
  linha = input()
  y = re.findall(r'[ ]+[^0-9]+\.',linha)
  if(len(y)>0):
    print ("existem ", len(y), " ocorrências, a primeira: '" + y[0] + "'")
  else:
    pass