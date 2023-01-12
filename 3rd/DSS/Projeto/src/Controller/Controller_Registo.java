package src.Controller;

import src.RMFacade;
import src.UI.UI;
import src.Utilizadores.Utilizador;

import java.util.ArrayList;

public class Controller_Registo {
    public static void run(RMFacade rm) throws Exception
    {
        //boolean exit = false;
        while (true)
        {
            ArrayList<String> opcao;
                opcao = UI.menuRegisto(); 

                if (rm.utilizadores.checkUsername(opcao.get(0))==false) {
                    rm.utilizadores.regUser(opcao.get(0), opcao.get(1), 'n');
                    char tipo = rm.utilizadores.geUtilizador(opcao.get(0)).getTipoDeUser();
                    if (tipo=='a') {
                        Utilizador u = rm.utilizadores.geUtilizador(opcao.get(0));
                        Controller_menuPrincipal_admin.run(rm,u);
                    }
                    else {
                        Utilizador u = rm.utilizadores.geUtilizador(opcao.get(0));
                        Controller_menuPrincipal_normal.run(rm,u);
                    }
                }
            }

        }
}

