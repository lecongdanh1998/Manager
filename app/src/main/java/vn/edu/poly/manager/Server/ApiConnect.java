package vn.edu.poly.manager.Server;

public class ApiConnect {

    public static final String URL_REGISTER(String url) {
        return "http://"+ url + "/api/register";
    }

    public static final String URL_GET_USER_INFOR(String url) {
        return "http://"+ url + "/api/user/info";
    }



    public static final String URL_SIGNIN(String url) {
        return "http://"+ url + "/oauth/token";
    }


    public static final String URL_UPDATE_USER_INFOR(String id, String url) {
        return "http://"+ url + "/api/users/" + id + "/updateInfo";
    }

    public static final String URL_UPDATE_USER_AVATAR(String id, String url) {
        return "http://"+ url + "/api/users/" + id + "/updateAvatar";
    }

    public static final String URL_CONNECT_WEBSITE( String url) {
        return "http://"+ url + "/api/posts";
    }

    public static final String URL_CONNECT_AVATAR( String url) {
        return "http://"+ url + "/storage/app/public/";
    }

    public static final String URL_CONNECT_DETAILS(String id, String url) {
        return "http://"+ url + "/api/posts/" + id;
    }

    public static final String URL_CONNECT_UPDATE(String url) {
        return "http://"+ url + "/api/posts/update";
    }

    public static final String URL_CONNECT_DRAFF_PENDING(String url) {
        return "http://"+ url + "/api/posts/pending";
    }

    public static final String URL_CONNECT_PENDING_PUBLIC(String url) {
        return "http://"+ url + "/api/posts/public";
    }

    public static final String URL_CONNECT_GALLERYS(String url) {
        return "http://"+ url + "/api/galleries";
    }

    public static final String URL_CONNECT_GALLERYS_IMAGES(String url) {
        return "http://"+url + "/";
    }

    public static final String URL_GET_POST_CONTACT(String url) {
        return "http://"+ url + "/api/contacts";
    }

    public static final String URL_GET_POST_CONTACT_DETAIL(String url, String id) {
        return "http://"+ url + "/api/contacts/" + id;
    }

    public static final String URL_GET_SETTING(String url) {
        return "http://"+ url + "/api/settings";
    }

    public static final String URL_GET_POST_SETTING_DETAIL(String url, String id) {
        return "http://"+ url + "/api/settings/" + id;
    }

    public static final String URL_UPDATE_SETTING(String url) {
        return "http://"+ url + "/api/settings/update";
    }

}
