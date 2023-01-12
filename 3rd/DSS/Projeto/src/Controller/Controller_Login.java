package src.Controller;

import src.RMFacade;
import src.UI.UI;
import src.Utilizadores.Utilizador;

//import java.sql.SQLException;
import java.util.ArrayList;

//import javax.swing.text.Utilities;

public class Controller_Login {
    public static void run(RMFacade rm) throws Exception
    {
        //boolean exit = false;
        while (true)
        {
            ArrayList<String> opcao;
                opcao = UI.menuLogin(); 

                if (rm.utilizadores.checkUsername(opcao.get(0))) {
                    if (rm.utilizadores.checkPassword(opcao.get(0), opcao.get(1))) {
                        Utilizador u = rm.utilizadores.geUtilizador(opcao.get(0));
                        char tipo = u.getTipoDeUser();
                        if (tipo=='a') {
                            Controller_menuPrincipal_admin.run(rm,u);
                        }
                        else {
                            Controller_menuPrincipal_normal.run(rm,u);
                        }
                    }
                }
            }
        }

}
