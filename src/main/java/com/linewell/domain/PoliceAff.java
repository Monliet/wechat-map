package com.linewell.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "police_aff", schema = "wechat_map", catalog = "")
public class PoliceAff {
    private String id;
    private String userId;
    private String pushId;
    private Date createTime;
    private String filePath;
    private String affMsg;
    private Integer code;
    private String affAddress;
    private String fbMsg;
    private String fbfilePath;

    @Id
    @Column(name = "id",length = 32)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "push_id")
    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    @Basic
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "file_path")
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PoliceAff policeAff = (PoliceAff) o;

        if (id != null ? !id.equals(policeAff.id) : policeAff.id != null) return false;
        if (userId != null ? !userId.equals(policeAff.userId) : policeAff.userId != null) return false;
        if (pushId != null ? !pushId.equals(policeAff.pushId) : policeAff.pushId != null) return false;
        if (createTime != null ? !createTime.equals(policeAff.createTime) : policeAff.createTime != null) return false;
        if (filePath != null ? !filePath.equals(policeAff.filePath) : policeAff.filePath != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (pushId != null ? pushId.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (filePath != null ? filePath.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "aff_msg")
    public String getAffMsg() {
        return affMsg;
    }

    public void setAffMsg(String affMsg) {
        this.affMsg = affMsg;
    }

    @Basic
    @Column(name = "code")
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Basic
    @Column(name = "aff_address")
    public String getAffAddress() {
        return affAddress;
    }

    public void setAffAddress(String affAddress) {
        this.affAddress = affAddress;
    }

    @Basic
    @Column(name = "fb_msg")
    public String getFbMsg() {
        return fbMsg;
    }

    public void setFbMsg(String fbMsg) {
        this.fbMsg = fbMsg;
    }

    @Basic
    @Column(name = "fbfile_path")
    public String getFbfilePath() {
        return fbfilePath;
    }

    public void setFbfilePath(String fbfilePath) {
        this.fbfilePath = fbfilePath;
    }
}
