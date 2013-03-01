package com.agex.control;

import com.agex.dao.PacienteDAO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

/**
 * Responsável por tratar as regras de negócio da entidade Paciente.
 * 
 * @author Charles Garrocho
 */
public class PacienteBean {

    private Integer id;
    private String nome;
    private Date dataNasc;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String uf;
    private List<PacienteBean> pacientesBean = new ArrayList<PacienteBean>();
    private List<SelectItem> pacientes = new ArrayList<SelectItem>();

    public PacienteBean() {
    }

    public PacienteBean(Integer id, String nome, Date dataNasc, String logradouro, String numero, String bairro, String cidade, String uf) {
        this.id = id;
        this.nome = nome;
        this.dataNasc = dataNasc;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Date getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(Date dataNasc) {
        this.dataNasc = dataNasc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public List<SelectItem> getPacientesBean() {
        PacienteDAO paciente = new PacienteDAO();
        pacientes.removeAll(pacientes);
        for (PacienteDAO p : paciente.getPacientes()) {
            pacientes.add(new SelectItem(p.getId(), p.getNome()));
        }
        return pacientes;
    }

    public void setPacientesBean(List<PacienteBean> pacientesBean) {
        this.pacientesBean = pacientesBean;
    }

    @Override
    public String toString() {
        return "PacienteBean{" + "id=" + id + ", nome=" + nome + ", dataNasc=" + dataNasc + ", logradouro=" + logradouro + ", numero=" + numero + ", bairro=" + bairro + ", cidade=" + cidade + ", uf=" + uf + '}';
    }

    public String cadastrar() {
        if (!nome.isEmpty() && dataNasc != null) {
            PacienteDAO paciente = new PacienteDAO(null, nome, dataNasc, logradouro, numero, bairro, cidade, uf);
            FacesContext contexto = FacesContext.getCurrentInstance();
            if (paciente.cadastrar()) {
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

    public DataModel<PacienteBean> listar() {
        PacienteDAO paciente = new PacienteDAO();
        if (paciente.getPacientes() != null) {
            pacientesBean.removeAll(pacientesBean);
            for (PacienteDAO p : paciente.getPacientes()) {
                pacientesBean.add(new PacienteBean(p.getId(), p.getNome(), p.getDataNasc(),
                        p.getLogradouro(), p.getNumero(), p.getBairro(), p.getCidade(), p.getUf()));
            }
            return new ListDataModel(pacientesBean);
        }
        return null;
    }

    public String excluir() {
        Map parametros = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String idPac = parametros.get("idPaciente").toString();
        Integer id = Integer.parseInt(idPac);
        PacienteDAO paciente = new PacienteDAO(id);
        paciente.remove();
        novo();
        return "";
    }

    public String alterar() {
        PacienteDAO paciente = new PacienteDAO(id, nome, dataNasc, logradouro, numero, bairro, cidade, uf);
        FacesContext contexto = FacesContext.getCurrentInstance();
        if (paciente.alterar()) {
            novo();
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
        String idPac = parametros.get("idPaciente").toString();
        Integer id = Integer.parseInt(idPac);
        
        int i;
        for (i = 0; i < pacientesBean.size(); i++) {
            if (pacientesBean.get(i).getId().equals(id)) {
                break;
            }
        }

        this.id = id;
        this.nome = pacientesBean.get(i).nome;
        this.dataNasc = pacientesBean.get(i).dataNasc;
        this.logradouro = pacientesBean.get(i).logradouro;
        this.numero = pacientesBean.get(i).numero;
        this.bairro = pacientesBean.get(i).bairro;
        this.cidade = pacientesBean.get(i).cidade;
        this.uf = pacientesBean.get(i).uf;
        
        return "carrega";
    }

    public PacienteBean getPaciente() {
        PacienteDAO pacienteDAO = new PacienteDAO(id);
        pacienteDAO = pacienteDAO.getPaciente();
        PacienteBean paciente = new PacienteBean(pacienteDAO.getId(), pacienteDAO.getNome(), pacienteDAO.getDataNasc(),
                pacienteDAO.getLogradouro(), pacienteDAO.getNumero(), pacienteDAO.getBairro(),
                pacienteDAO.getCidade(), pacienteDAO.getUf());
        return paciente;
    }

    public void novo() {
        setNome("");
        setDataNasc(null);
        setId(null);
        setLogradouro("");
        setNumero(null);
        setBairro("");
        setCidade("");
        setUf("");
    }
}
