package part1.DAO;

import java.util.Date;

public class DaoImpl implements IDao {
    @Override
    public Date getDate() {
        System.out.println("normal version");
        Date currentDate = new Date();
        return currentDate;
    }
}
