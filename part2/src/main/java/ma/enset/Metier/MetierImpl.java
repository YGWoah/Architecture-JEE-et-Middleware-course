package ma.enset.Metier;

import ma.enset.DAO.IDao;

import java.util.Date;

public class MetierImpl implements  IMetier{
    public IDao dao;

    @Override
    public double caculate() {
        Date date = dao.getDate();
        // do some calculation
        if (date.getTime() > 0)
            return 1;
        else
            return 0;
    }

    public void setDao(IDao dao) {
        this.dao = dao;
    }
}
