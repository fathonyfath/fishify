package com.kelompok5.fishify.controller;

import com.kelompok5.fishify.FishifyApplication;
import com.kelompok5.fishify.model.Peternakan;
import com.kelompok5.fishify.model.PeternakanDao;
import com.kelompok5.fishify.model.User;
import com.kelompok5.fishify.model.UserDao;
import com.kelompok5.fishify.utils.mvc.BaseController;

import java.util.List;

/**
 * Created by bradhawk on 12/11/2016.
 */

public class PeternakanController extends BaseController {

    public User getLoginUser() {
        UserDao dao = FishifyApplication.getDaoSession().getUserDao();
        return dao.queryBuilder().unique();
    }

    public boolean addPeternakan(String namaPeternakan, float panjangPeternakan, float lebarPeternakan) {
        User loginUser = getLoginUser();
        Peternakan peternakan = new Peternakan(null, namaPeternakan, panjangPeternakan, lebarPeternakan, loginUser.getIdPengguna());
        PeternakanDao dao = FishifyApplication.getDaoSession().getPeternakanDao();
        dao.insert(peternakan);
        return true;
    }

    public boolean updatePeternakan(Long idPeternakan, String namaPeternakan, float panjangPeternakan, float lebarPeternakan) {
        Peternakan peternakan = fetchPeternakan(idPeternakan);
        peternakan.setNamaPeternakan(namaPeternakan);
        peternakan.setPanjang(panjangPeternakan);
        peternakan.setLebar(lebarPeternakan);
        PeternakanDao dao = FishifyApplication.getDaoSession().getPeternakanDao();
        dao.update(peternakan);
        return true;
    }

    public Peternakan fetchPeternakan(Long idPeternakan) {
        PeternakanDao dao = FishifyApplication.getDaoSession().getPeternakanDao();
        return dao.queryBuilder().where(PeternakanDao.Properties.IdPeternakan.eq(idPeternakan)).unique();
    }

    public List<Peternakan> fetchAllPeternakan() {
        PeternakanDao dao = FishifyApplication.getDaoSession().getPeternakanDao();
        return dao.queryBuilder().list();
    }
}
