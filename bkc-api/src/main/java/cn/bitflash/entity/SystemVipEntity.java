package cn.bitflash.entity;

public class SystemVipEntity {

    private int id;

    private float vipCash;

    private String vipLevel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getVipCash() {
        return vipCash;
    }

    public void setVipCash(float vipCash) {
        this.vipCash = vipCash;
    }

    public String getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(String vipLevel) {
        this.vipLevel = vipLevel;
    }
}