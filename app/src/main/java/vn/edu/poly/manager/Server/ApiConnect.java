package vn.edu.poly.manager.Server;

public class ApiConnect {
    public static final String URL_REGISTER = "http://demo.vnlead.webstarterz.com/api/register";
    public static final String URL_SIGNIN = "http://demo.vnlead.webstarterz.com/oauth/token";
    public static final String URL_GET_USER_INFOR = "http://demo.vnlead.webstarterz.com/api/user/info";
    public static final String URL_UPDATE_USER_INFOR(String id){
        return "http://demo.vnlead.webstarterz.com/api/users/" + id + "/updateInfo";
    }
    public static final String URL_UPDATE_USER_AVATAR(String id){
        return "http://demo.vnlead.webstarterz.com/api/users/" + id + "/updateAvatar";
    }
    public static final String URL_GET_POST_CONTACT = "demo.vnlead.webstarterz.com/api/contacts";
}
