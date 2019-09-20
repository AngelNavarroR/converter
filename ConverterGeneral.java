/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angel.dev.converter;

import angel.dev.utils.EjbsCaller;
import angel.dev.utils.ReflexionEntity;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Angel Navarro
 */
@FacesConverter("entityConverter")
public class ConverterGeneral implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext context, UIComponent c, String value) {
        if (value == null || value.isEmpty() == true || value.equals("")) {
            return null;
        }
        try {
            String[] p = value.split(":");
            if (p == null || p.length < 3) {
                return null;
            }
            Object find = EjbsCaller.getTransactionManager().find(Class.forName(p[0]), ReflexionEntity.instanceConsString(p[2], p[1]));
            return find;
        } catch (NullPointerException | ELException | NumberFormatException e) {
            Logger.getLogger(ConverterGeneral.class.getName()).log(Level.SEVERE, value, e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConverterGeneral.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent c, Object value) {
        if (value == null) {
            return null;
        }
        String temp = ReflexionEntity.getIdEntity(value);
        return temp;
    }
}
