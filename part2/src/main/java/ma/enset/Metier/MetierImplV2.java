package ma.enset.Metier;

import ma.enset.DAO.DaoImpl;
import ma.enset.DAO.IDao;

import java.util.Date;

public class MetierImplV2 implements IMetier{

    private IDao dao;

    public MetierImplV2(DaoImpl dao){
        this.dao=dao;
    }

    @Override
    public double caculate() {
        System.out.println("this is the new verson of the metier ");

        Date date = dao.getDate();

        if(date==new Date())
            return 1;
        else
            return 0;
    }
}
