package Controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import Entidades.*;
import Devices.*;
import UI.*;

public class ControllerCasa
{
    public static void run(CasaInteligenteFacade facade)
    {
        boolean exit = false;
        while (!exit)
        {
            int option = -1;
            while (option < 0 || option > 5)
            {
                option = Menu.menuCasa();
                switch (option)
                {
                    case 1:
                    {
                        String nif = Menu.getNIF();
                        if (facade.checkCasa(nif))
                        {
                            System.out.println(facade.getCasa(nif));
                        }
                        else
                        {
                            System.out.println("NIF inválido!\n");
                        }
                        break;
                    }
                    case 2:
                    {
                        String nif = Menu.getNIF();
                        if (facade.checkCasa(nif))
                        {
                            System.out.println(facade.getDispositivos(nif));
                        }
                        else
                        {
                            System.out.println("NIF inválido!\n");
                        }
                        break;
                    }
                    case 3:
                    {
                        String nif = Menu.getNIF();
                        String divisao = Menu.getDivisao();

                        int id = facade.getMapDevices().size();
                        int aux = Menu.menuCriarDispositivo();
                        if (aux == 1)
                        {
                            SmartDevice res = Menu.getSmartBulb(id);
                            facade.addDispositivoCasa(nif, divisao, res);
                        }
                        else if (aux == 2)
                        {
                            SmartDevice res = Menu.getSmartCamera(id);
                            facade.addDispositivoCasa(nif, divisao, res);
                        }
                        else
                        {
                            SmartDevice res = Menu.getSmartSpeaker(id);
                            facade.addDispositivoCasa(nif, divisao, res);
                        }
                    }
                    case 4:
                    {
                        String nif = Menu.getNIF();
                        String divisao = Menu.getDivisao();
                        if (facade.ligaDispositivoDivisao(nif, divisao))
                        {
                            System.out.println("Dispositivos ligados com sucesso!\n");
                        }
                        else
                        {
                            System.out.print("NIF inválido!");
                        }
                        break;
                    }
                    case 5:
                    {
                        String nif = Menu.getNIF();
                        String divisao = Menu.getDivisao();
                        if (facade.desligaDispositivoDivisao(nif, divisao))
                        {
                            System.out.println("Dispositivos desligados com sucesso!\n");
                        }
                        else
                        {
                            System.out.print("NIF inválido!");
                        }
                        break;
                    }
                    case 0:
                    {
                        exit = true;
                        Menu.clearWindow();
                        break;
                    }
                }
            }
        }
    }
}