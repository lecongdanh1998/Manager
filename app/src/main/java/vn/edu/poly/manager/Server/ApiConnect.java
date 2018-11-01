package vn.edu.poly.manager.Server;

public class ApiConnect {

    //API USER
    public static final String URL_REGISTER(String url) {
        return url + "/api/register";
    }
    public static final String URL_GET_USER_INFOR(String url) {
        return url + "/api/nks/user";
    }
    public static final String URL_SIGNIN(String url) {
        return url + "/oauth/token";
    }
    public static final String URL_UPDATE_USER_INFOR(String url) {
        return url + "/api/nks/users/updateInfo";
    }
    public static final String URL_UPDATE_USER_AVATAR(String url) {
        return url + "/api/nks/users/updateAvatar";
    }
    public static final String URL_CONNECT_AVATAR( String url) {
        return url + "/storage/app/public/";
    }
    //





    //API POST
    public static final String URL_CONNECT_WEBSITE( String url) {
        return url + "/api/nks/posts";
    }
    public static final String URL_CONNECT_DETAILS(String url) {
        return url + "/api/nks/post";
    }
    public static final String URL_CONNECT_DRAFF_PENDING(String url) {
        return url + "/api/nks/posts/pending";
    }
    public static final String URL_CONNECT_PENDING_PUBLIC(String url) {
        return url + "/api/nks/posts/public";
    }
    public static final String URL_CONNECT_UPDATE(String url) {
        return url + "/api/nks/posts/update";
    }
    public static final String URL_CONNECT_CATEGORIES(String url) {
        return url + "/api/nks/categories-dropdown";
    }
    public static final String URL_CONNECT_UPDATE_IMAGES(String url) {
        return url + "/api/nks/posts/updateImg";
    }
    //






    //API GALLERY
    public static final String URL_CONNECT_GALLERYS(String url) {
        return url + "/api/nks/galleries";
    }
    public static final String URL_CONNECT_GALLERYS_IMAGES(String url) {
        return url;
    }
    public static final String URL_UPDATE_IMAGES(String url) {
        return url + "/api/nks/galleries/add";
    }
    public static final String URL_DELETE_IMAGES(String url) {
        return url + "/api/nks/galleries/delete";
    }
    //






    //API CONTACTS
    public static final String URL_GET_POST_CONTACT(String url) {
        return url + "/api/contacts";
    }
    public static final String URL_GET_POST_CONTACT_DETAIL(String url, String id) {
        return url + "/api/contacts/" + id;
    }

    //





    //API SETTINGS
    public static final String URL_GET_SETTING(String url) {
        return url + "/api/settings";
    }
    public static final String URL_GET_POST_SETTING_DETAIL(String url, String id) {
        return url + "/api/settings/" + id;
    }
    public static final String URL_UPDATE_SETTING(String url) {
        return url + "/api/settings/update";
    }
    //
}
