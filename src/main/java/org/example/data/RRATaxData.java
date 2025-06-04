package org.example.data;

public class RRATaxData {
    public String taxpayerId;
    public String name;
    public double taxAmount;
    public String paymentStatus;

    public RRATaxData(){}

    //Getters and Setters
    public String getTaxpayerId(){
        return taxpayerId;
    }
    public void setTaxpayerId(String taxpayerId){
        this.taxpayerId =taxpayerId;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public double getTaxAmount(){
        return taxAmount;
    }
    public void setTaxAmount(double taxAmount){
        this.taxAmount=taxAmount;
    }
    public String getPaymentStatus(){
        return paymentStatus;
    }
    public void setPaymentStatus(String paymentStatus){
        this.paymentStatus=paymentStatus;
    }


}
