package com.leoman.bus.entity;

import com.leoman.entity.BaseEntity;
import com.leoman.image.entity.Image;

import javax.persistence.*;

/**
 *
 * 班车实体类
 * Created by Daisy on 2016/9/6.
 */
@Entity
@Table(name = "t_bus")
public class Bus extends BaseEntity{

    @Column(name = "uuid")
    private String uuid;//唯一标识

    @Column(name = "vkey")
    private String vkey;//车辆授权码

    @Column(name = "car_no")
    private String carNo;//车牌号

    @Column(name = "model_no")
    private String modelNo;//车型

    @Column(name = "brand")
    private String brand;//品牌

    @Column(name = "seat_num")
    private Integer seatNum;//座位数

    @ManyToOne
    @JoinColumn(name = "car_type_id")
    private CarType carType;//用车类型

    @Column(name = "policy_no")
    private String policyNo;//保险单号

    @Column(name = "driver_name")
    private String driverName;//司机名称

    @Column(name = "driver_phone")
    private String driverPhone;//司机手机号

    @Column(name = "driver_sex")
    private String driverSex;//司机性别

    @Column(name = "driver_id_card")
    private String driverIDCard;//司机身份证号

    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;//图片

    @Column(name = "cur_lat")
    private Double curLat;//当前纬度

    @Column(name = "cur_lng")
    private Double curLng;//当前经度

    @Transient
    private Long stationId;//当前站点id

    @Transient
    private String stationName;//当前站点名称

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(Integer seatNum) {
        this.seatNum = seatNum;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getDriverSex() {
        return driverSex;
    }

    public void setDriverSex(String driverSex) {
        this.driverSex = driverSex;
    }

    public String getDriverIDCard() {
        return driverIDCard;
    }

    public void setDriverIDCard(String driverIDCard) {
        this.driverIDCard = driverIDCard;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Double getCurLat() {
        return curLat;
    }

    public void setCurLat(Double curLat) {
        this.curLat = curLat;
    }

    public Double getCurLng() {
        return curLng;
    }

    public void setCurLng(Double curLng) {
        this.curLng = curLng;
    }

    public Bus(){}

    public Bus(Long id) {
        this.setId(id);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getVkey() {
        return vkey;
    }

    public void setVkey(String vkey) {
        this.vkey = vkey;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
}
