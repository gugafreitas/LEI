package Controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import Entidades.*;
import UI.*;

public class Controller
{
    public static void run()
    {
        CasaInteligenteFacade facade = new CasaInteligenteFacade();

        boolean exit = false;
        while (true)
        {
            int opcao = -1;
            while (opcao < 0 || opcao > 6)
            {
                opcao = Menu.menuInicial();
                switch (opcao)
                {
                    case 1:
                    {
                        ControllerEntidades.run(facade);
                        Menu.pressEnter();
                        break;
                    }
                    case 2:
                    {
                        ControllerEstatisticas.run(facade);
                        Menu.pressEnter();
                        break;
                    }
                    case 3:
                    {
                        ControllerCasa.run(facade);
                        Menu.pressEnter();
                        break;
                    }
                    case 4:
                    {
                        facade.loadLogs();
                        Menu.pressEnter();
                        break;
                    }
                    case 5:
                    {
                        // Passar caminho relativo
                        try {facade.loadEstadoObj("Estado.obj");
                            System.out.println("Ficheiros carregados com sucesso!!!\n");}
                        catch (FileNotFoundException e) {Menu.errors(1);}
                        catch (IOException e) {Menu.errors(4);}
                        catch (ClassNotFoundException e) {Menu.errors(3);}
                        Menu.pressEnter();
                        break;
                    }
                    case 6:
                    {
                        try{facade.saveEstado();System.out.println("Ficheiros guardados com sucesso!!!\n");}
                        catch (FileNotFoundException e) {Menu.errors(1);}
                        catch (IOException e) {Menu.errors(2);}
                        Menu.pressEnter();
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
