package com.agex.dao;

import com.agex.dao.model.Agenda;
import com.agex.dao.model.AgendaPK;
import com.agex.util.Conexao;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Charles Garrocho
 */
public class AgendaDAO {

    private Date dataHora;
    private Integer idPaciente;
    private Integer idMedico;
    private Integer idExame;
    private String obs;
    private String resultado;

    public AgendaDAO() {
    }

    public AgendaDAO(Date dataHora, Integer idPaciente, Integer idMedico, Integer idExame) {
        this.dataHora = dataHora;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.idExame = idExame;
    }

    public AgendaDAO(Date dataHora, Integer idPaciente, Integer idMedico, Integer idExame, String obs, String resultado) {
        this.dataHora = dataHora;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.idExame = idExame;
        this.obs = obs;
        this.resultado = resultado;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public Integer getIdExame() {
        return idExame;
    }

    public void setIdExame(Integer idExame) {
        this.idExame = idExame;
    }

    public Integer getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Integer idMedico) {
        this.idMedico = idMedico;
    }

    public Integer getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public EntityManager conectar() {
        EntityManager em = Conexao.getManager();
        return em;
    }

    public boolean cadastrar() {
        EntityManager em = conectar();
        try {
            if (em != null) {
                AgendaPK a = new AgendaPK(dataHora, idMedico, idExame, idPaciente);
                Agenda agenda = new Agenda();
                agenda.setAgendaPK(a);
                agenda.setObs(obs);
                agenda.setResultado(resultado);

                em.getTransaction().begin();
                em.persist(agenda);
                em.getTransaction().commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        }
    }

    public List<AgendaDAO> getAgendamentos(String dataInicio, String dataFinal) {
        EntityManager em = conectar();
        try {
            String consulta;
            Query q;
            if (dataInicio != null) {
                consulta = "SELECT a FROM Agenda a WHERE dataHora BETWEEN :dataInicio AND :dataFinal";
                q = em.createQuery(consulta).setParameter("dataInicio", dataInicio).setParameter("dataFinal", dataFinal);
            } else {
                consulta = "SELECT a FROM Agenda a";
                q = em.createQuery(consulta);
            }
            List<Agenda> a = q.getResultList();
            List<AgendaDAO> agenda = new ArrayList<AgendaDAO>();
            for (Agenda ag : a) {
                AgendaPK apk = ag.getAgendaPK();
                agenda.add(new AgendaDAO(apk.getDataHora(), apk.getIdPaciente(), apk.getIdMedico(), apk.getIdExame(),
                        ag.getObs(), ag.getResultado()));
            }
            return agenda;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return null;
        }
    }

    public void remove() {
        EntityManager em = conectar();
        try {
            AgendaPK agendaPK = new AgendaPK(dataHora, idMedico, idExame, idPaciente);
            Agenda agenda = em.find(Agenda.class, agendaPK);

            em.getTransaction().begin();
            em.remove(agenda);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }

    public boolean alterar() {
        EntityManager em = conectar();
        try {
            AgendaPK agendaPK = new AgendaPK(dataHora, idMedico, idExame, idPaciente);
            Agenda agenda = em.find(Agenda.class, agendaPK);
            agenda.setObs(obs);
            agenda.setResultado(resultado);

            em.getTransaction().begin();
            em.persist(agenda);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        }
    }
}
