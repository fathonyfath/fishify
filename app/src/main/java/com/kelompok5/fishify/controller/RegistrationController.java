package com.kelompok5.fishify.controller;

import com.kelompok5.fishify.FishifyApplication;
import com.kelompok5.fishify.model.User;
import com.kelompok5.fishify.model.UserDao;
import com.kelompok5.fishify.utils.mvc.BaseController;

import java.util.Date;

/**
 * Created by bradhawk on 11/30/2016.
 */

public class RegistrationController extends BaseController {

    public boolean register(String namaLengkap, String jenisKelamin, Date tanggalLahir, String email) {
        User newUser = new User(null, namaLengkap, jenisKelamin, tanggalLahir, email);
        UserDao dao = FishifyApplication.getDaoSession().getUserDao();
        dao.insert(newUser);
        return true;
    }

    public boolean isUserRegistered() {
        UserDao dao = FishifyApplication.getDaoSession().getUserDao();
        User availableUser = dao.queryBuilder().unique();
        return availableUser != null;
    }

}
