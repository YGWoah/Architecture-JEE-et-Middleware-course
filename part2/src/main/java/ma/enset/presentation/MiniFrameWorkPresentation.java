package ma.enset.presentation;

import jakarta.xml.bind.JAXBException;
import ma.enset.IOC.ClassPathXmlApplicationContext;


public class MiniFrameWorkPresentation {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = null;
        try {
            context = new ClassPathXmlApplicationContext("config.xml");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ma.enset.Metier.IMetier metier = (ma.enset.Metier.IMetier) context.getBean("metier");
        System.out.println("resultat => " + metier.caculate());

    }
}
