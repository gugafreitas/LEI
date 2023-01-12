package src.Controller;

import src.RMFacade;
import src.UI.UI;
import src.Utilizadores.Utilizador;

// import java.sql.SQLException;
// import java.util.ArrayList;

public class Controller_menuPrincipal_normal {
    public static void run(RMFacade rm,Utilizador u) throws Exception
    {
        //boolean exit = false;
        while (true)
        {
            int opcao = UI.menuPrincipal_normal(); 

            if (opcao == 1) {
                Controller_Jogar.run(rm,u);
            }
            else break;         
        }
    }
}
