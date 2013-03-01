package com.agex.dao;

import com.agex.dao.model.Medico;
import com.agex.util.Conexao;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.HibernateException;

/**
 * Responsável por persistir e buscar objetos da entidade Medico no banco de dados.
 * 
 * @author Charles Garrocho
 */
public class MedicoDAO {

    private Integer idMedico;
    private String nome;
    private String crm;

    public MedicoDAO() {
    }
    
    public MedicoDAO(Integer id){
        this.idMedico = id;
    }

    public MedicoDAO(Integer idMedico, String nome, String crm) {
        this.idMedico = idMedico;
        this.nome = nome;
        this.crm = crm;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public Integer getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Integer idMedico) {
        this.idMedico = idMedico;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public EntityManager conecta() {
        EntityManager em = Conexao.getManager();
        return em;
    }

    public boolean cadastrar() {
        EntityManager em = conecta();
        try {
            if (em != null) {
                Medico medico = new Medico();
                medico.setIdMedico(null);
                medico.setNome(nome);
                medico.setCrm(crm);

                EntityTransaction tx = em.getTransaction();
                tx.begin();
                em.persist(medico);
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
    
    public List<MedicoDAO> getMedicos(){
        try{
            EntityManager em = conecta();
            if (em!=null){
                Query q = em.createQuery("SELECT m FROM Medico m");
                List<Medico> resultado = q.getResultList();
                List<MedicoDAO> medicos = new ArrayList<MedicoDAO>();
                for (Medico p: resultado){
                    medicos.add(new MedicoDAO(p.getIdMedico(), p.getNome(), p.getCrm()));
                }
                return medicos;
            }
            return null;
        } catch (Exception e){
            return null;
        }
    }
    
    public MedicoDAO getMedico(){
        try{
            EntityManager em = conecta();
            if (em!=null){
                String consulta = "SELECT m FROM Medico m WHERE m.idMedico="+idMedico;
                //System.out.println(consulta);
                Query q = em.createQuery(consulta);
                List<Medico> resultado = q.getResultList();
                //System.out.println(resultado.toString());
                MedicoDAO medico = new MedicoDAO();
                for (Medico m: resultado){
                    medico = new MedicoDAO(m.getIdMedico(), m.getNome(), m.getCrm());
                }
                return medico;
            }
            return null;
        } catch (Exception e){
            return null;
        }
    }
    
    public void remove(){
        EntityManager em = conecta();
        try {
            Medico m = em.find(Medico.class, idMedico);
            em.getTransaction().begin();
            em.remove(m);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive())
                em.getTransaction().rollback();
        }
    }
    
    public boolean alterar(){
        EntityManager em = conecta();
        try {
            Medico medico = em.find(Medico.class, idMedico);
            medico.setNome(nome);
            medico.setCrm(crm);
            
            em.getTransaction().begin();
            em.persist(medico);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive())
                em.getTransaction().rollback();
            return false;
        }
    }
}
