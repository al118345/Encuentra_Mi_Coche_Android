package com.energia.electra.geolocation.database.model;

/**
 * Created by Ruben on 18/09/2015.
 */
public class User {

    private long id;



    private String _name;
    private String _user;
    private String _pass;

    public User(long id, String name, String user,String pass) {
        this.id = id;
        this._name = name;
        this._pass=pass;
        this._user=user;
    }

    /**
     * Method to get the id of the item
     *
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Method to get the name of the item
     *
     * @return item name
     */
    public String getName() {
        return _name;
    }

    /**
     * Method to set the name of the item
     *
     * @param name
     */
    public void setName(String name) {
        this._name = name;
    }

    public void setId(long id) {
        this.id = id;
    }
    public String get_pass() {
        return _pass;
    }

    public void set_pass(String _pass) {
        this._pass = _pass;
    }

    public String get_user() {
        return _user;
    }

    public void set_user(String _user) {
        this._user = _user;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

}
