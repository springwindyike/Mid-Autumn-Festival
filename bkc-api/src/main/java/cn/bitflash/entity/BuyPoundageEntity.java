package cn.bitflash.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author soso
 * @date 2018年5月22日 下午4:00:45
 */

public class BuyPoundageEntity implements Serializable {

    private String userBuyId;

    private String sellUid;

    private BigDecimal poundage;

    private BigDecimal quantity;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    public String getUserBuyId() {
        return userBuyId;
    }

    public void setUserBuyId(String userBuyId) {
        this.userBuyId = userBuyId;
    }

    public String getSellUid() {
        return sellUid;
    }

    public void setSellUid(String sellUid) {
        this.sellUid = sellUid;
    }

    public BigDecimal getPoundage() {
        return poundage;
    }

    public void setPoundage(BigDecimal poundage) {
        this.poundage = poundage;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
