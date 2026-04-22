/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.atm.transaction.system.model;

/**
 *
 * @author HQ
 */
public class UserItem {
    private int id;
    private String name;

    public UserItem(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    
    @Override
    public String toString() {
        return name; // This is what shows in the ComboBox
    }
}