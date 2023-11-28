package Controllers;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import Entidades.*;
import UI.*;

public class ControllerEstatisticas
{
    public static void run(CasaInteligenteFacade facade)
    {
        Scanner sc = new Scanner(System.in);
        boolean exit = false;
        while (!exit)
        {
            int option = -999;
            while (option < 0 || option > 4)
            {
                option = Menu.menuEstatisticas();
                switch (option)
                {
                    case 1:
                    {
                        System.out.println("Dia Inicial: ");
                        Date dateInicial = Menu.getDate();
                        System.out.println("Dia Final: ");
                        Date dateFinal = Menu.getDate();
                        Casa res = facade.maiorConsumidor(dateInicial, dateFinal);
                        System.out.println(res.toString());
                        break;
                    }
                    case 2:
                    {
                        Fornecedor f = facade.maiorfaturacao();
                        System.out.println(f.toString());
                        break;
                    }
                    case 3:
                    {
                        System.out.println("Nome da Empresa: ");
                        String nome = sc.next();
                        System.out.println(facade.listaFatura(nome));
                        break;
                    }
                    case 4:
                    {
                        System.out.println("Dia Inicial: ");
                        Date dateInicial = Menu.getDate();
                        System.out.println("Dia Final: ");
                        Date dateFinal = Menu.getDate();
                        List<Casa> res = facade.maioresConsumidores(dateInicial, dateFinal);
                        System.out.println(res);
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
