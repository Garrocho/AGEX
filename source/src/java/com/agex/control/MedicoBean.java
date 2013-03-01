package com.agex.control;

import com.agex.dao.MedicoDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

/**
 * Responsável por tratar as regras de negócio da entidade Medico.
 * 
 * @author Charles Garrocho
 */
public class MedicoBean {

    private Integer idMedico;
    private String nome;
    private String crm;
    private List<MedicoBean> medicosBean = new ArrayList<MedicoBean>();
    private List<SelectItem> medicos = new ArrayList<SelectItem>();

    public MedicoBean() {
    }

    public MedicoBean(Integer idMedico, String nome, String crm) {
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

    public List<SelectItem> getMedicosBean() {
        MedicoDAO medico = new MedicoDAO();
        medicos.removeAll(medicos);
        for (MedicoDAO e : medico.getMedicos()) {
            medicos.add(new SelectItem(e.getIdMedico(), e.getNome()));

        }
        return medicos;
    }

    public void setMedicosBean(List<MedicoBean> medicosBean) {
        this.medicosBean = medicosBean;
    }

    public String cadastrar() {
        if (!nome.isEmpty() && !crm.isEmpty()) {
            MedicoDAO medico = new MedicoDAO(null, nome, crm);
            FacesContext contexto = FacesContext.getCurrentInstance();
            if (medico.cadastrar()) {
                FacesMessage mensagem = new FacesMessage("Cadastro realizado com sucesso!");
                contexto.addMessage("cadastro", mensagem);
                novo();
            } else {
                FacesMessage mensagem = new FacesMessage("Erro ao cadastrar!");
                contexto.addMessage("cadastro", mensagem);
            }
        }
        return "";
    }

    public DataModel<MedicoBean> listar() {
        MedicoDAO medico = new MedicoDAO();
        if (medico.getMedicos() != null) {
            medicosBean.removeAll(medicosBean);
            for (MedicoDAO e : medico.getMedicos()) {
                medicosBean.add(new MedicoBean(e.getIdMedico(), e.getNome(), e.getCrm()));
            }
            return new ListDataModel(medicosBean);
        }
        return null;
    }

    public String excluir() {
        Map parametros = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String idMed = parametros.get("idMedico").toString();
        Integer id = Integer.parseInt(idMed);
        MedicoDAO medico = new MedicoDAO(id);
        medico.remove();
        return "";
    }

    public String alterar() {
        MedicoDAO medico = new MedicoDAO(idMedico, nome, crm);
        FacesContext contexto = FacesContext.getCurrentInstance();
        if (medico.alterar()) {
            FacesMessage mensagem = new FacesMessage("Cadastro alterado com sucesso!");
            contexto.addMessage("cadastro", mensagem);
        } else {
            FacesMessage mensagem = new FacesMessage("Erro ao alterar!");
            contexto.addMessage("cadastro", mensagem);
        }
        return "";
    }

    public String buscar() {
        Map parametros = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String idMed = parametros.get("idMedico").toString();
        Integer id = Integer.parseInt(idMed);

        int i;
        for (i = 0; i < medicosBean.size(); i++) {
            if (medicosBean.get(i).getIdMedico().equals(id)) {
                break;
            }
        }

        this.idMedico = id;
        this.nome = medicosBean.get(i).getNome();
        this.crm = medicosBean.get(i).getCrm();

        return "carrega";
    }

    public MedicoBean getMedico() {
        MedicoDAO medicoDAO = new MedicoDAO(idMedico);
        medicoDAO = medicoDAO.getMedico();
        MedicoBean medico = new MedicoBean(medicoDAO.getIdMedico(), medicoDAO.getNome(), medicoDAO.getCrm());
        return medico;
    }

    public void novo() {
        setIdMedico(null);
        setNome("");
        setCrm("");
    }
}
