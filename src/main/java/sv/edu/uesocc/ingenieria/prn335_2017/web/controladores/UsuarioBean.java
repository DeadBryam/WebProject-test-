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
import javafx.scene.chart.PieChart;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
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
    
    public void showMessage(String Mensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(Mensaje));
    }
    
    @PostConstruct
    public void init() {
        if (usuarioData != null) {
            this.usuarioData = usuario.findAll();
        } else {
            this.usuarioData = Collections.EMPTY_LIST;
        }
    }
}
