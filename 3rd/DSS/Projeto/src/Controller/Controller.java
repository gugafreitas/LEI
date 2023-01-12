package src.Controller;

//import java.sql.SQLException;

import src.RMFacade;
import src.UI.UI;

public class Controller {

    public static void run() throws Exception
    {
        RMFacade rmFacade = new RMFacade();

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
                        Controller_Registo.run(rmFacade);
                        UI.pressEnter();
                        break;
                    }
                    case 2:
                    {
                        Controller_Login.run(rmFacade);
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

    public static void main (String [] args) throws Exception {
        run();
    }
}
