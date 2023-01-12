package src.Controller;

import src.Utilizadores.Utilizador;
import src.RMFacade;
import src.UI.UI;

public class Controller_menuPrincipal_admin {
    public static void run(RMFacade rm,Utilizador u ) throws Exception
    {
        //boolean exit = false;
        while (true)
        {
            int opcao = UI.menuPrincipal_admin(); 
            
            switch (opcao) 
            {
                case 1 : 
                    Controller_CriaCarro.run(rm);
                    UI.pressEnter();
                    break;
                case 2 : 
                    //Controller_CriaCarro.run(rm);
                    UI.pressEnter();
                    break;
                case 3 : 
                    Controller_CriaCampeonato.run(rm);
                    //Controller_CriaCarro.run(rm);
                    UI.pressEnter();
                    break;
                case 4 : 
                    //Controller_CriaCarro.run(rm);
                    UI.pressEnter();
                    break;
                case 5 : 
                    Controller_Jogar.run(rm,u);
                    UI.pressEnter();
                    break;
            }       
        }
    }
}
    
