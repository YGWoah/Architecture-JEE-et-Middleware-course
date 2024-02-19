package part1.DAO;

import java.util.Date;

public class DaoImplWBS implements IDao{
    @Override
    public Date getDate() {
        System.out.println("WBS version");
        Date currentDate = new Date();
        return new Date(currentDate.getTime() - 1000000000);
    }
}
