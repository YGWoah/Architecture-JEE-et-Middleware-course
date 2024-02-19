package part1.presentation;

import part1.DAO.DaoImpl;
import part1.DAO.IDao;
import part1.Metier.IMetier;
import part1.Metier.MetierImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("Hello World");
        // injection of dependencies by the static instantiation of the DaoImpl class
        // this is not a good practice

        IDao dao = new DaoImpl();
        IMetier metier = new MetierImpl();
        ((MetierImpl) metier).setDao(dao);

        System.out.println(metier.caculate());

        // injection of dependencies using the dynamic instantiation of the DaoImpl class(using a config file)
        // this is a better practice

        Scanner scanner = new Scanner(new File("config.txt"));
        String className = scanner.nextLine();

        Class cDao = Class.forName(className);
        IDao dao1 = (IDao) cDao.newInstance();

        className = scanner.nextLine();
        Class cMetier = Class.forName(className);
        IMetier metier1 = (IMetier) cMetier.newInstance();
        ((MetierImpl) metier1).setDao(dao1);

        System.out.println(metier1.caculate());




    }


}
