package com.leoman.carrental.entity;

import com.leoman.bus.entity.CarType;
import com.leoman.bussend.entity.BusSend;
import com.leoman.city.entity.City;
import com.leoman.entity.BaseEntity;
import com.leoman.order.entity.Order;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * 租车
 * Created by 史龙 on 2016/9/7.
 */
@Entity
@Table(name = "t_car_rental_order")
public class CarRental extends BaseEntity{

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "contact_id")
    @Where(clause = "type = '2'")
    private List<BusSend> busSends;

    //订单号
    @ManyToOne
    @JoinColumn(name= "order_id")
    private Order order;

    //巴士类型
    @ManyToOne
    @JoinColumn(name= "car_type_id")
    private CarType carType;

    //出发城市
    @ManyToOne
    @JoinColumn(name= "city_id")
    private City city;

    //起点
    @Column(name= "start_point")
    private String startPoint;

    //终点
    @Column(name= "end_point")
    private String endPoint;

    //租车方式: 0:单程 1:返程 2:往返
    @Column(name= "rental_way")
    private Integer rentalWay;

    //出发时间
    @Column(name= "start_date")
    private Long startDate;

    //返回时间
    @Column(name= "end_date")
    private Long endDate;

    //巴士数量
    @Column(name= "bus_num")
    private Integer busNum;

    //总人数
    @Column(name= "total_number")
    private Integer totalNumber;

    //是否要发票 0:不要 1:要
    @Column(name= "is_invoice")
    private Integer isInvoice;

    //发票抬头
    @Column(name= "invoice")
    private String invoice;

    //退订原因
    @Column(name= "unsubscribe")
    private String unsubscribe;

    //已收金额
    @Column(name= "income")
    private Double income;

    //退款金额
    @Column(name= "refund")
    private Double refund;

    //是否改签过了 0:否 1:是 每个订单只能改签一次
    @Column(name = "is_rewrite")
    private Integer isRewrite;

    //是否用户删除 0:否 1:是
    @Column(name = "is_del")
    private Integer isDel;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "rental_id")
    private Set<CarRentalOffer> carRentalOffers;

    @Transient
    private Double totalAmount = 0.0;

    public Double getTotalAmount() {
        if(totalAmount == 0.0){
            if(carRentalOffers != null){
                for(CarRentalOffer c : carRentalOffers){
                    totalAmount += c.getAmount();
                }
            }
        }
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Set<CarRentalOffer> getCarRentalOffers() {
        return carRentalOffers;
    }

    public void setCarRentalOffers(Set<CarRentalOffer> carRentalOffers) {
        this.carRentalOffers = carRentalOffers;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public Integer getRentalWay() {
        return rentalWay;
    }

    public void setRentalWay(Integer rentalWay) {
        this.rentalWay = rentalWay;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public Integer getBusNum() {
        return busNum;
    }

    public void setBusNum(Integer busNum) {
        this.busNum = busNum;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public Integer getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(Integer isInvoice) {
        this.isInvoice = isInvoice;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getUnsubscribe() {
        return unsubscribe;
    }

    public void setUnsubscribe(String unsubscribe) {
        this.unsubscribe = unsubscribe;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public Double getRefund() {
        return refund;
    }

    public void setRefund(Double refund) {
        this.refund = refund;
    }

    public List<BusSend> getBusSends() {
        return busSends;
    }

    public void setBusSends(List<BusSend> busSends) {
        this.busSends = busSends;
    }

    public Integer getIsRewrite() {
        return isRewrite;
    }

    public void setIsRewrite(Integer isRewrite) {
        this.isRewrite = isRewrite;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}
