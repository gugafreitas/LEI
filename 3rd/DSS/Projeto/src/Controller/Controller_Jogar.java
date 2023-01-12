package src.Controller;

import src.RMFacade;
import src.UI.UI;
import src.Utilizadores.Utilizador;

public class Controller_Jogar {
    public static void run(RMFacade rm,Utilizador u) throws Exception
    {
        //boolean exit = false;
        while (true)
        {
            int opcao = -1;
            while (opcao < 0 || opcao > 6)
            {
                opcao = UI.menuInicial();
                switch (opcao)
                {
                    case 1:
                    {
                        Controller_Escolher_Carro.run(rm,u);
                        UI.pressEnter();
                        break;
                    }
                    case 2:
                    {
                        Controller_Escolher_Campeonato.run(rm, u);
                        UI.pressEnter();
                        break;
                    }
                    case 3 : 
                    {
                        Controller_Escolher_Piloto.run(rm,u);
                        UI.pressEnter();
                        break;
                        
                    }
                    case 0:
                    {
                        System.exit(0);
                        break;
                    }
                }
            }

        }

    }
}
