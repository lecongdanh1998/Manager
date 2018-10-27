package vn.edu.poly.manager.Server;

public class ApiConnect {
    public static final String URL_REGISTER = "http://demo.vnlead.webstarterz.com/api/register";
    public static final String URL_SIGNIN = "http://demo.vnlead.webstarterz.com/oauth/token";
    public static final String URL_GET_USER_INFOR = "http://demo.vnlead.webstarterz.com/api/user/info";

    public static final String URL_UPDATE_USER_INFOR(String id, String site, String url) {
        return "http://" + site + "." + url + "/api/users/" + id + "/updateInfo";
    }

    public static final String URL_UPDATE_USER_AVATAR(String id, String site, String url) {
        return "http://" + site + "." + url + "/api/users/" + id + "/updateAvatar";
    }

    public static final String URL_CONNECT_WEBSITE(String site, String url) {
        return "http://" + site + "." + url + "/api/posts";
    }

    public static final String URL_CONNECT_AVATAR(String site, String url) {
        return "http://" + site + "." + url + "/storage/app/public/";
    }

    public static final String URL_CONNECT_DETAILS(String id, String site, String url) {
        return "http://" + site + "." + url + "/api/posts/" + id;
    }

    public static final String URL_CONNECT_UPDATE(String site, String url) {
        return "http://" + site + "." + url + "/api/posts/update";
    }

    public static final String URL_CONNECT_DRAFF_PENDING(String site, String url) {
        return "http://" + site + "." + url + "/api/posts/pending";
    }

    public static final String URL_CONNECT_PENDING_PUBLIC(String site, String url) {
        return "http://" + site + "." + url + "/api/posts/public";
    }

    public static final String URL_CONNECT_GALLERYS(String site, String url) {
        return "http://" + site + "." + url + "/api/galleries";
    }

    public static final String URL_CONNECT_GALLERYS_IMAGES(String site, String url) {
        return "http://" + site + "." + url + "/";
    }

    public static final String URL_GET_POST_CONTACT(String site, String url) {
        return "http://" + site + "." + url + "/api/contacts";
    }

    public static final String URL_GET_POST_CONTACT_DETAIL(String site, String url, String id) {
        return "http://" + site + "." + url + "/api/contacts/" + id;
    }

    public static final String URL_GET_SETTING(String site, String url) {
        return "http://" + site + "." + url + "/api/settings";
    }

    public static final String URL_GET_POST_SETTING_DETAIL(String site, String url, String id) {
        return "http://" + site + "." + url + "/api/settings/" + id;
    }

    public static final String URL_UPDATE_SETTING(String site, String url) {
        return "http://" + site + "." + url + "/api/settings/update";
    }

}
