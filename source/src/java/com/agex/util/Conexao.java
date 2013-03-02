package com.agex.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Charles Garrocho
 */
public class Conexao {

    private static EntityManagerFactory emf = null;
    private static EntityManager em = null;

    public static EntityManager getManager() {

        try{
            if (emf==null){
                emf = Persistence.createEntityManagerFactory("AGEXPU");
                em = emf.createEntityManager();
                System.out.println("Criando o gerenciador de Entidade");
            } else
                if (em==null){
                    em = emf.createEntityManager();
            } else {
                System.out.println("A entidade já foi criada!");
            }
            return em;
            
        } catch (Exception e){
            return null;
        }
    }
}