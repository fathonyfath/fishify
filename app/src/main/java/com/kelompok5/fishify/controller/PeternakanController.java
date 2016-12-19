package com.kelompok5.fishify.controller;

import android.graphics.Bitmap;

import com.kelompok5.fishify.FishifyApplication;
import com.kelompok5.fishify.model.IkanTernak;
import com.kelompok5.fishify.model.IkanTernakDao;
import com.kelompok5.fishify.model.Peternakan;
import com.kelompok5.fishify.model.PeternakanDao;
import com.kelompok5.fishify.model.User;
import com.kelompok5.fishify.model.UserDao;
import com.kelompok5.fishify.utils.Constant;
import com.kelompok5.fishify.utils.mvc.BaseController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
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
        if (panjangPeternakan <= 0.0f || lebarPeternakan <= 0.0f) return false;
        User loginUser = getLoginUser();
        Peternakan peternakan = new Peternakan(null, namaPeternakan, panjangPeternakan, lebarPeternakan, loginUser.getIdPengguna());
        PeternakanDao dao = FishifyApplication.getDaoSession().getPeternakanDao();
        dao.insert(peternakan);
        return true;
    }

    public boolean updatePeternakan(Long idPeternakan, String namaPeternakan, float panjangPeternakan, float lebarPeternakan) {
        if (panjangPeternakan <= 0.0f || lebarPeternakan <= 0.0f) return false;
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

    public boolean deletePeternakan(Long idPeternakan) {
        PeternakanDao dao = FishifyApplication.getDaoSession().getPeternakanDao();
        dao.deleteByKey(idPeternakan);
        return true;
    }

    public List<IkanTernak> fetchIkanTernakByPeternakan(Long idPeternakan) {
        Peternakan peternakan = fetchPeternakan(idPeternakan);
        peternakan.resetIkanTernakList();
        return peternakan.getIkanTernakList();
    }

    public IkanTernak fetchIkanTernak(Long idIkanTernak) {
        IkanTernakDao dao = FishifyApplication.getDaoSession().getIkanTernakDao();
        return dao.queryBuilder().where(IkanTernakDao.Properties.IdIkanTernak.eq(idIkanTernak)).unique();
    }

    public Long addIkanTernak(String namaIkan, String jenisIkan, Long idPeternakan) {
        IkanTernakDao dao = FishifyApplication.getDaoSession().getIkanTernakDao();
        return dao.insert(new IkanTernak(null, namaIkan, jenisIkan, idPeternakan));
    }

    public boolean updateIkanTernak(Long idIkanTernak, String namaIkan, String jenisIkan, Long idPeternakan) {
        IkanTernak ikanTernak = fetchIkanTernak(idIkanTernak);
        ikanTernak.setNamaIkan(namaIkan);
        ikanTernak.setJenisIkan(jenisIkan);
        ikanTernak.setIdPeternakan(idPeternakan);
        IkanTernakDao dao = FishifyApplication.getDaoSession().getIkanTernakDao();
        dao.update(ikanTernak);
        return true;
    }

    public List<IkanTernak> getDataIkanBiasaDiTernakList() {
        return Arrays.asList(Constant.DATA_IKAN_TERNAK);
    }

    public boolean deleteIkanTernak(Long idIkanTernak) {
        IkanTernakDao dao = FishifyApplication.getDaoSession().getIkanTernakDao();
        dao.deleteByKey(idIkanTernak);
        return true;
    }

    public boolean saveImage(File file, Bitmap bitmap) {
        FileOutputStream fos = null;
        if (file.exists()) file.delete();
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            return false;
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }
}
