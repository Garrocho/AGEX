package com.agex.dao;

import com.agex.dao.model.Exame;
import com.agex.util.Conexao;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.HibernateException;

/**
 * Responsável por persistir e buscar objetos da entidade Exame no banco de dados.
 * 
 * @author Charles Garrocho
 */
public class ExameDAO {

    private Integer idExame;
    private String nome;
    private Float valor;

    public ExameDAO() {
    }

    public ExameDAO(Integer id) {
        this.idExame = id;
    }

    public ExameDAO(Integer idExame, String nome, Float valor) {
        this.idExame = idExame;
        this.nome = nome;
        this.valor = valor;
    }

    public Integer getIdExame() {
        return idExame;
    }

    public void setIdExame(Integer idExame) {
        this.idExame = idExame;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public EntityManager conecta() {
        EntityManager em = Conexao.getManager();
        return em;
    }

    public boolean cadastrar() {
        EntityManager em = conecta();
        try {
            if (em != null) {
                Exame exame = new Exame();
                exame.setIdExame(null);
                exame.setNome(nome);
                exame.setValor(valor);

                EntityTransaction tx = em.getTransaction();
                tx.begin();
                em.persist(exame);
                tx.commit();
                return true;
            }
            return false;
        } catch (HibernateException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        }
    }

    public List<ExameDAO> getExames() {
        try {
            EntityManager em = conecta();
            if (em != null) {
                Query q = em.createQuery("SELECT p FROM Exame p");
                List<Exame> resultado = q.getResultList();
                List<ExameDAO> exames = new ArrayList<ExameDAO>();
                for (Exame p : resultado) {
                    exames.add(new ExameDAO(p.getIdExame(), p.getNome(), p.getValor()));
                }
                return exames;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
    
    public ExameDAO getExame(){
        try{
            EntityManager em = conecta();
            if (em!=null){
                String consulta = "SELECT e FROM Exame e WHERE e.idExame="+idExame;
                //System.out.println(consulta);
                Query q = em.createQuery(consulta);
                List<Exame> resultado = q.getResultList();
                //System.out.println(resultado.toString());
                ExameDAO exame = new ExameDAO();
                for (Exame e: resultado){
                    exame = new ExameDAO(e.getIdExame(), e.getNome(), e.getValor());
                }
                return exame;
            }
            return null;
        } catch (Exception e){
            return null;
        }
    }
    
    public void remove() {
        EntityManager em = conecta();
        try {
            if (em != null) {
                Exame e = em.find(Exame.class, idExame);
                em.getTransaction().begin();
                em.remove(e);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }
    
    public boolean alterar(){
        EntityManager em = conecta();
        try {
            Exame exame = em.find(Exame.class, idExame);
            exame.setNome(nome);
            exame.setValor(valor);
            
            em.getTransaction().begin();
            em.persist(exame);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive())
                em.getTransaction().rollback();
            return false;
        }
    }
}
