package vn.edu.poly.manager.Util;

import java.io.Serializable;

public class ValidateForm implements Serializable {

    public boolean validateTextEmpty(String email){
        if (email.matches("")){
            return true;
        } else {
            return false;
        }
    }
}
