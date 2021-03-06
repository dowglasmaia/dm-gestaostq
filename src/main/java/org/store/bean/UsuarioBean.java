package org.store.bean;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.omnifaces.util.Messages;
import org.store.dao.UsuarioDAO;
import org.store.entity.Usuario;

@Model
@SessionScoped
public class UsuarioBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@EJB
	private UsuarioDAO uDao;

	@Inject
	private Usuario usuario;

	private String cpf;

	private String senha;

	// Logar
	public String logar() {
		usuario = uDao.getUsuario(cpf, senha); // Buscar Usuario com Base no cpf e senha cadastrado no Banco de Dados.
		if (usuario != null) {
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
					.getSession(false);
			session.setAttribute("usuarioLogado", usuario); // Cria uma Sessão Valida para o Usuario

			return "/pages/index?faces-redirect=true";
		}
		Messages.addGlobalError("Usuário não encontrado!");
		return "/login?faces-redirect=true";
	}

	public String logout() {
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/login?faces-redirect=true";
	}

	// ***//***//***//**
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
