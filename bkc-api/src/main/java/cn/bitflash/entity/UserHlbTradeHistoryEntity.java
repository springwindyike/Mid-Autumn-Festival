package cn.bitflash.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * hlb兑换历史实体类
 */
public class UserHlbTradeHistoryEntity {

    private String id;

    private String uid;

    private BigDecimal totalHlb;

    private BigDecimal totalNpc;

    private BigDecimal frozenNpc;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public BigDecimal getTotalHlb() {
        return totalHlb;
    }

    public void setTotalHlb(BigDecimal totalHlb) {
        this.totalHlb = totalHlb;
    }

    public BigDecimal getTotalNpc() {
        return totalNpc;
    }

    public void setTotalNpc(BigDecimal totalNpc) {
        this.totalNpc = totalNpc;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getFrozenNpc() {
        return frozenNpc;
    }

    public void setFrozenNpc(BigDecimal frozenNpc) {
        this.frozenNpc = frozenNpc;
    }
}
