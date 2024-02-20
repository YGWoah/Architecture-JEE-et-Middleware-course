package ma.enset.presentation;

import ma.enset.IOC.ClassPathXmlApplicationContext;

public class MiniFrameWorkPresentation {
    public static void main(String[] args) {
        // try the mini framework that i created
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
        ma.enset.Metier.IMetier metier = (ma.enset.Metier.IMetier) context.getBean("metier");
        System.out.println("resultat => " + metier.caculate());

    }
}
