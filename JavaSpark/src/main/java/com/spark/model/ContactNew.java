package com.spark.model;

import java.io.Serializable;

public class ContactNew implements Serializable{
	
	
   String  head_ref_code ;
    long customer_id ;
    String sub_id ;
    long head_id ;
    String channel ;
   String  offer_desc ;
    long offer_id ;
   String  offer_text ;
   String offer_type ;
    String trigger_date ;
   long  position ;
public String getHead_ref_code() {
	return head_ref_code;
}
public void setHead_ref_code(String head_ref_code) {
	this.head_ref_code = head_ref_code;
}
public long getCustomer_id() {
	return customer_id;
}
public void setCustomer_id(long customer_id) {
	this.customer_id = customer_id;
}
public String getSub_id() {
	return sub_id;
}
public void setSub_id(String sub_id) {
	this.sub_id = sub_id;
}
public long getHead_id() {
	return head_id;
}
public void setHead_id(long head_id) {
	this.head_id = head_id;
}
public String getChannel() {
	return channel;
}
public void setChannel(String channel) {
	this.channel = channel;
}
public String getOffer_desc() {
	return offer_desc;
}
public void setOffer_desc(String offer_desc) {
	this.offer_desc = offer_desc;
}
public long getOffer_id() {
	return offer_id;
}
public void setOffer_id(long offer_id) {
	this.offer_id = offer_id;
}
public String getOffer_text() {
	return offer_text;
}
public void setOffer_text(String offer_text) {
	this.offer_text = offer_text;
}
public String getOffer_type() {
	return offer_type;
}
public void setOffer_type(String offer_type) {
	this.offer_type = offer_type;
}
public String getTrigger_date() {
	return trigger_date;
}
public void setTrigger_date(String trigger_date) {
	this.trigger_date = trigger_date;
}
public long getPosition() {
	return position;
}
public void setPosition(long position) {
	this.position = position;
}

   


   
   

}
