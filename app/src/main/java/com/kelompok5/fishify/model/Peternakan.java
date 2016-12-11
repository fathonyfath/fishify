package com.kelompok5.fishify.model;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

/**
 * Created by bradhawk on 12/6/2016.
 */

@Entity
public class Peternakan {
    @Id
    private Long idPeternakan;

    private String namaPeternakan;
    private float lebar;
    private float tinggi;

    private Long idUser;

    @ToMany(referencedJoinProperty = "idPeternakan")
    private List<IkanTernak> ikanTernakList;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1278146887)
    private transient PeternakanDao myDao;

    @Generated(hash = 1924298123)
    public Peternakan(Long idPeternakan, String namaPeternakan, float lebar,
            float tinggi, Long idUser) {
        this.idPeternakan = idPeternakan;
        this.namaPeternakan = namaPeternakan;
        this.lebar = lebar;
        this.tinggi = tinggi;
        this.idUser = idUser;
    }

    @Generated(hash = 758993934)
    public Peternakan() {
    }

    public Long getIdPeternakan() {
        return this.idPeternakan;
    }

    public void setIdPeternakan(Long idPeternakan) {
        this.idPeternakan = idPeternakan;
    }

    public String getNamaPeternakan() {
        return this.namaPeternakan;
    }

    public void setNamaPeternakan(String namaPeternakan) {
        this.namaPeternakan = namaPeternakan;
    }

    public float getLebar() {
        return this.lebar;
    }

    public void setLebar(float lebar) {
        this.lebar = lebar;
    }

    public float getTinggi() {
        return this.tinggi;
    }

    public void setTinggi(float tinggi) {
        this.tinggi = tinggi;
    }

    public Long getIdUser() {
        return this.idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 2068969772)
    public List<IkanTernak> getIkanTernakList() {
        if (ikanTernakList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            IkanTernakDao targetDao = daoSession.getIkanTernakDao();
            List<IkanTernak> ikanTernakListNew = targetDao
                    ._queryPeternakan_IkanTernakList(idPeternakan);
            synchronized (this) {
                if (ikanTernakList == null) {
                    ikanTernakList = ikanTernakListNew;
                }
            }
        }
        return ikanTernakList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1890770173)
    public synchronized void resetIkanTernakList() {
        ikanTernakList = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 565251268)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPeternakanDao() : null;
    }
}
