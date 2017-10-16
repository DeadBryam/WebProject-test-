/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.uesocc.ingenieria.prn335_2017.web.controladores;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import sv.edu.uesocc.ingenieria.prn335_2017.datos.acceso.AbstractFacadeLocal;
import sv.edu.uesocc.ingenieria.prn335_2017.datos.definiciones.Usuario;

/**
 *
 * @author bryan
 */
public abstract class AbstractManagedBean<T>{

    private AbstractFacadeLocal fl;
    
    public void crear(T e) {
        try {
            fl.create(e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro agregado correctamente"));
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al agregar un nuego registro."));
        }
    }
    
}
