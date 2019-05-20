package com.linewell.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_area", schema = "wechat_map", catalog = "")
public class UserArea implements Serializable {

    private static final long serialVersionUID = 8229743542144603775L;
    private String id;
    private String userId;
    private String areaCode;
    private int code;
    private String address;

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
    @Column(name = "area_code")
    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserArea userArea = (UserArea) o;

        if (id != null ? !id.equals(userArea.id) : userArea.id != null) return false;
        if (userId != null ? !userId.equals(userArea.userId) : userArea.userId != null) return false;
        if (areaCode != null ? !areaCode.equals(userArea.areaCode) : userArea.areaCode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (areaCode != null ? areaCode.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "code")
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
