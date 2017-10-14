/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.uesocc.ingenieria.prn335_2017.web.controladores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.primefaces.event.SelectEvent;
import sv.edu.uesocc.ingenieria.prn335_2017.datos.acceso.UsuarioFacadeLocal;
import sv.edu.uesocc.ingenieria.prn335_2017.datos.definiciones.Usuario;

/**
 *
 * @author bryan
 */
@Named(value = "usuarioBean")
@ViewScoped
public class UsuarioBean implements Serializable {

    /**
     * Creates a new instance of UsuarioBean
     */
    public UsuarioBean() {
    }

    boolean chkFitro, btnEdit = false;
    @EJB
    UsuarioFacadeLocal usuario;
    List<Usuario> usuarioData = new ArrayList<>();
    Usuario data = new Usuario();

    public boolean isChkFitro() {
        return chkFitro;
    }

    public void setChkFitro(boolean chkFitro) {
        this.chkFitro = chkFitro;
    }

    public boolean isBtnEdit() {
        return btnEdit;
    }

    public void setBtnEdit(boolean btnEdit) {
        this.btnEdit = btnEdit;
    }

    public UsuarioFacadeLocal getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioFacadeLocal usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getUsuarioData() {
        return usuarioData;
    }

    public void setUsuarioData(List<Usuario> usuarioData) {
        this.usuarioData = usuarioData;
    }

    public Usuario getData() {
        return data;
    }

    public void setData(Usuario data) {
        this.data = data;
    }

    /**
     * Este metodo sirve para poder agregar un nuevo registro a la base de datos
     */
    public void crear() {
        try {
            usuario.create(data);
            init();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro agregado correctamente"));
            data = new Usuario();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al agregar un nuego registro."));
        }
    }

    public void editar() {
        try {
            usuario.edit(data);
            init();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Edicion correcta."));
            btnEdit = false;
            data = new Usuario();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al editar registro."));
        }
    }
    
    public void eliminar() {
        try {
            usuario.remove(data);
            init();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro removido correctamente."));
            btnEdit = false;
            data = new Usuario();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al eliminar."));
        }
    }

    /**
     * Es metodo sirve para detectar el cambio de un checkbox alojado en el jsf
     */
    public void chkCambio() {
        if (chkFitro == true) {
            this.usuarioData = obtenerUtilizados();
        } else {
            init();
        }
    }

    /**
     * Este metodo sirve para filtrar usuarios no utilizados
     *
     * @return una lista con los usuarios que no han sido utiizados
     */
    public List<Usuario> obtenerUtilizados() {
        List salida;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("sv.edu.uesocc.ingenieria.prn335_2017_WebProject_war_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        Query c = em.createNamedQuery("Usuario.noUtilizados");
        salida = c.getResultList();

        if (salida != null) {
            return salida;
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    public void onRowSelect(SelectEvent event) {
        btnEdit = true;
    }

    public void btnCancelar() {
        data = new Usuario();
        btnEdit = false;
    }

    @PostConstruct
    /**
     * Este metodo llena una lista con los dato de la base de datos
     */
    public void init() {
        if (usuarioData != null) {
            this.usuarioData = usuario.findAll();
        } else {
            this.usuarioData = Collections.EMPTY_LIST;
        }
    }
}
