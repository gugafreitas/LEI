package src.Controller;

import src.RMFacade;
import src.UI.UI;

// import java.sql.SQLException;
// import java.util.ArrayList;

import src.Utilizadores.Utilizador;
import src.Carros.Carro;



public class Controller_Escolher_Carro {
    public static void run(RMFacade rm,Utilizador u) throws Exception
    {
        //boolean exit = false;
        System.out.println(rm.carros);//Da print do map de todos os carros com as respetivas infos

        int opcao = UI.menuEscolherCarro(); 

        Carro c1 = rm.carros.get(opcao);

        u.setCarro(c1);

        Controller_Jogar.run(rm,u);
    }
}