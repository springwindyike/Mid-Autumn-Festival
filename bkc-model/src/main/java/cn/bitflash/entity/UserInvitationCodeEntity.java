package cn.bitflash.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * @author gaoyuguo
 * @date 2018年9月22日
 */
@TableName("user_invitation_code")
public class UserInvitationCodeEntity implements Serializable {
    @TableId(type = IdType.INPUT)
    private String uid;

    private String code;

    private String lftCode;

    private String rgtCode;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLftCode() {
        return lftCode;
    }

    public void setLftCode(String lftCode) {
        this.lftCode = lftCode;
    }

    public String getRgtCode() {
        return rgtCode;
    }

    public void setRgtCode(String rgtCode) {
        this.rgtCode = rgtCode;
    }
}
