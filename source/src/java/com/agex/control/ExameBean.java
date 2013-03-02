package com.agex.control;

import com.agex.dao.ExameDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

/**
 *
 * @author Charles Garrocho
 */
public class ExameBean {

    private Integer idExame;
    private String nome;
    private Float valor;
    private List<ExameBean> examesBean = new ArrayList();
    private List<SelectItem> exames = new ArrayList();

    public ExameBean() {
    }

    public ExameBean(Integer idExame, String nome, Float valor) {
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

    public List<SelectItem> getExamesBean() {
        ExameDAO exame = new ExameDAO();
        exames.removeAll(exames);
        for (ExameDAO e : exame.getExames()) {
            exames.add(new SelectItem(e.getIdExame(), e.getNome()));
        }
        return exames;
    }

    public void setExamesBean(List<ExameBean> examesBean) {
        this.examesBean = examesBean;
    }

    public String cadastrar() {
        if (!nome.isEmpty() && valor != null) {
            ExameDAO exame = new ExameDAO(null, nome, valor);
            FacesContext contexto = FacesContext.getCurrentInstance();
            if (exame.cadastrar()) {
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

    public DataModel<ExameBean> listar() {
        ExameDAO exame = new ExameDAO();
        if (exame.getExames() != null) {
            examesBean.removeAll(examesBean);
            for (ExameDAO e : exame.getExames()) {
                examesBean.add(new ExameBean(e.getIdExame(), e.getNome(), e.getValor()));
            }
            return new ListDataModel(examesBean);
        }
        return null;
    }

    public String excluir() {
        Map parametros = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String idExa = parametros.get("idExame").toString();
        Integer id = Integer.parseInt(idExa);
        ExameDAO exame = new ExameDAO(id);
        exame.remove();
        return "";
    }

    public String alterar() {
        ExameDAO exame = new ExameDAO(idExame, nome, valor);
        FacesContext contexto = FacesContext.getCurrentInstance();
        if (exame.alterar()) {
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
        String idExa = parametros.get("idExame").toString();
        Integer id = Integer.parseInt(idExa);

        int i;
        for (i = 0; i < examesBean.size(); i++) {
            if (examesBean.get(i).getIdExame().equals(id)) {
                break;
            }
        }

        this.idExame = id;
        this.nome = examesBean.get(i).getNome();
        this.valor = examesBean.get(i).getValor();

        return "carrega";
    }

    public ExameBean getExame() {
        ExameDAO exameDAO = new ExameDAO(idExame);
        exameDAO = exameDAO.getExame();
        ExameBean exame = new ExameBean(exameDAO.getIdExame(), exameDAO.getNome(), exameDAO.getValor());
        return exame;
    }

    public void novo() {
        setIdExame(null);
        setNome("");
        setValor(null);
    }
}
