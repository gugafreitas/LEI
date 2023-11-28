package Controllers;

import Entidades.*;
import Devices.*;
import UI.*;

public class ControllerEntidades
{
    public static void run(CasaInteligenteFacade facade)
    {
        boolean exit = false;
        while (!exit)
        {
            int opcao = -1;
            while (opcao < 0 || opcao > 10)
            {
                opcao = Menu.menuRequisitosBase();
                switch (opcao)
                {
                    case 1:
                    {
                        int id = facade.getMapDevices().size();
                        SmartDevice bulb = Menu.getSmartBulb(id);
                        facade.adicionaDispositivo(bulb);
                        break;
                    }
                    case 2:
                    {
                        int id = facade.getMapDevices().size();
                        SmartDevice speaker = Menu.getSmartSpeaker(id);
                        facade.adicionaDispositivo(speaker);
                        break;
                    }
                    case 3:
                    {
                        int id = facade.getMapDevices().size();
                        SmartDevice camera = Menu.getSmartCamera(id);
                        facade.adicionaDispositivo(camera);
                        break;
                    }
                    case 4:
                    {
                        Casa casa = Menu.getCasa();
                        facade.adicionaCasa(casa);
                        facade.adicionaFornecedor(casa.getFornecedor());
                        break;
                    }
                    case 5:
                    {
                        Fornecedor fornecedor = Menu.getFornecedor();
                        facade.adicionaFornecedor(fornecedor);
                        break;
                    }
                    case 6:
                    {
                        System.out.println(facade.listaForncedores());
                        break;
                    }
                    case 7:
                    {
                        System.out.println(facade.listaCasa());
                        break;
                    }
                    case 8:
                    {
                        System.out.println(facade.listaDevices());
                        break;
                    }
                    case 9:
                    {
                        String nif = Menu.getNIF();
                        int id = Menu.getID();
                        if (facade.ligaDispositivo(nif, id))
                        {
                            System.out.println("Dispositivo ligado com sucesso!\n");
                        }
                        else
                        {
                            System.out.print("NIF inválido ou Divisão inválida!");
                        }
                        break;
                    }
                    case 10:
                    {
                        String nif = Menu.getNIF();
                        int id = Menu.getID();
                        if (facade.desligaDispositivo(nif, id))
                        {
                            System.out.println("Dispositivo desligado com sucesso!\n");
                        }
                        else
                        {
                            System.out.print("NIF inválido ou Divisão inválida!");
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
