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
import sv.edu.uesocc.ingenieria.prn335_2017.datos.acceso.UsuarioFacadeLocal;
import sv.edu.uesocc.ingenieria.prn335_2017.datos.definiciones.Usuario;

/**
 *
 * @author bryan
 */
@Named(value = "usuarioBean")
@ViewScoped
public class UsuarioBean implements Serializable{

    /**
     * Creates a new instance of UsuarioBean
     */
    public UsuarioBean() {
    }
    
    boolean activo;

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    @EJB
    UsuarioFacadeLocal usuario;
    List<Usuario> usuarioData = new ArrayList<>();
    Usuario data = new Usuario();

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
                showMessage("Datos ingresado correctamente.");
                data = new Usuario();
            } catch (Exception e) {
                System.out.println("Error: " + e);
                showMessage("Error al ingresar los datos.");
            }
    }
    /**
     * Es metodo sirve para detectar el cambio de un checkbox alojado en el jsf
     */
    public void chkCambio(){
        if(activo == true){
            this.usuarioData = obtenerUtilizados();
        }else{
            init();
        }
        
    }
    
    /**
     * Este metodo sirve para mostrar un mensaje un el usuario
     * @param Mensaje espera el mensaje que sera mostrado al usuario
     */
    public void showMessage(String Mensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(Mensaje));
    }
    /**
     * Este metodo sirve para limpiar el formulario
     */
    public void btnLimpiar(){
        data = new Usuario();
    }
    
    /**
     * Este metodo sirve para filtrar usuarios no utilizados 
     * @return una lista con los usuarios que no han sido utiizados
     */
    public List<Usuario> obtenerUtilizados() {
        List salida;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("sv.edu.uesocc.ingenieria.prn335_2017_WebProject_war_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        Query c = em.createNamedQuery("Usuario.noUtilizados");
        salida = c.getResultList();
        
        if(salida != null){
        return salida;
        }else{
            return Collections.EMPTY_LIST;
        }
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
