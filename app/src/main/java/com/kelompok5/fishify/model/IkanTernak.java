package com.kelompok5.fishify.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by bradhawk on 12/6/2016.
 */

@Entity
public class IkanTernak {
    @Id
    private Long idIkanTernak;

    private String namaIkan;
    private String jenisIkan;

    private Long idPeternakan;

    @Generated(hash = 1208592062)
    public IkanTernak(Long idIkanTernak, String namaIkan, String jenisIkan,
            Long idPeternakan) {
        this.idIkanTernak = idIkanTernak;
        this.namaIkan = namaIkan;
        this.jenisIkan = jenisIkan;
        this.idPeternakan = idPeternakan;
    }

    @Generated(hash = 935811710)
    public IkanTernak() {
    }

    public Long getIdIkanTernak() {
        return this.idIkanTernak;
    }

    public void setIdIkanTernak(Long idIkanTernak) {
        this.idIkanTernak = idIkanTernak;
    }

    public String getNamaIkan() {
        return this.namaIkan;
    }

    public void setNamaIkan(String namaIkan) {
        this.namaIkan = namaIkan;
    }

    public String getJenisIkan() {
        return this.jenisIkan;
    }

    public void setJenisIkan(String jenisIkan) {
        this.jenisIkan = jenisIkan;
    }

    public Long getIdPeternakan() {
        return this.idPeternakan;
    }

    public void setIdPeternakan(Long idPeternakan) {
        this.idPeternakan = idPeternakan;
    }
}
