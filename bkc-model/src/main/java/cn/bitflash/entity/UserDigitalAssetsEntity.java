package cn.bitflash.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author gaoyuguo
 * @date 2018年9月22日
 */
@TableName("user_digital_assets")
public class UserDigitalAssetsEntity implements Serializable {
    @TableId(type = IdType.INPUT)
    private String uid;

    private BigDecimal purchase;

    private BigDecimal frozen;

    private BigDecimal release;

    private BigDecimal available;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public BigDecimal getPurchase() {
        return purchase;
    }

    public void setPurchase(BigDecimal purchase) {
        this.purchase = purchase;
    }

    public BigDecimal getFrozen() {
        return frozen;
    }

    public void setFrozen(BigDecimal frozen) {
        this.frozen = frozen;
    }

    public BigDecimal getRelease() {
        return release;
    }

    public void setRelease(BigDecimal release) {
        this.release = release;
    }

    public BigDecimal getAvailable() {
        return available;
    }

    public void setAvailable(BigDecimal available) {
        this.available = available;
    }
}
