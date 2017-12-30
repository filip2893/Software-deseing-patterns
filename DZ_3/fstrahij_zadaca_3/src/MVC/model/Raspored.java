/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC.model;

/**
 *
 * @author Filip
 */
public class Raspored {
    private int vrstaZapisa, mjestoId, vrsta, modelUredajajId, uredjajId;
    private int aktuatorId, senzorId;

    public Raspored(int vrstaZapisa, int mjestoId, int vrsta, int modelUredajajId, int uredjajId) {
        this.vrstaZapisa = vrstaZapisa;
        this.mjestoId = mjestoId;
        this.vrsta = vrsta;
        this.modelUredajajId = modelUredajajId;
        this.uredjajId = uredjajId;
    }

    public Raspored(int vrstaZapisa, int aktuatorId, int senzorId) {
        this.vrstaZapisa = vrstaZapisa;
        this.aktuatorId = aktuatorId;
        this.senzorId = senzorId;
    }

    public int getVrstaZapisa() {
        return vrstaZapisa;
    }
    
    public int getMjestoId() {
        return mjestoId;
    }

    public int getVrsta() {
        return vrsta;
    }

    public int getModelUredajajId() {
        return modelUredajajId;
    }

    public int getUredjajId() {
        return uredjajId;
    }

    public int getAktuatorId() {
        return aktuatorId;
    }

    public int getSenzorId() {
        return senzorId;
    }

    public void setMjestoId(int mjestoId) {
        this.mjestoId = mjestoId;
    }

    public void setVrstaZapisa(int vrstaZapisa) {
        this.vrstaZapisa = vrstaZapisa;
    }    

    public void setVrsta(int vrsta) {
        this.vrsta = vrsta;
    }

    public void setModelUredajajId(int modelUredajajId) {
        this.modelUredajajId = modelUredajajId;
    }

    public void setUredjajId(int uredjajId) {
        this.uredjajId = uredjajId;
    }

    public void setAktuatorId(int aktuatorId) {
        this.aktuatorId = aktuatorId;
    }

    public void setSenzorId(int senzorId) {
        this.senzorId = senzorId;
    }    
}
