package com.spark.model;

import java.io.Serializable;

public class TrackNew implements Serializable{
	

		   String head_ref_code;
		   String sub_id ;
		   long customer_id; 
		   String channel ;
		   long head_id ;
		   String last_updated_time;
		   String trigger_date ;
		   String updated_time ;
		   long value ;
		   String position ;
		public String getHead_ref_code() {
			return head_ref_code;
		}
		public void setHead_ref_code(String head_ref_code) {
			this.head_ref_code = head_ref_code;
		}
		public String getSub_id() {
			return sub_id;
		}
		public void setSub_id(String sub_id) {
			this.sub_id = sub_id;
		}
		public long getCustomer_id() {
			return customer_id;
		}
		public void setCustomer_id(long customer_id) {
			this.customer_id = customer_id;
		}
		public String getChannel() {
			return channel;
		}
		public void setChannel(String channel) {
			this.channel = channel;
		}
		public long getHead_id() {
			return head_id;
		}
		public void setHead_id(long head_id) {
			this.head_id = head_id;
		}
		public String getLast_updated_time() {
			return last_updated_time;
		}
		public void setLast_updated_time(String last_updated_time) {
			this.last_updated_time = last_updated_time;
		}
		public String getTrigger_date() {
			return trigger_date;
		}
		public void setTrigger_date(String trigger_date) {
			this.trigger_date = trigger_date;
		}
		public String getUpdated_time() {
			return updated_time;
		}
		public void setUpdated_time(String updated_time) {
			this.updated_time = updated_time;
		}
		public long getValue() {
			return value;
		}
		public void setValue(long value) {
			this.value = value;
		}
		public String getPosition() {
			return position;
		}
		public void setPosition(String position) {
			this.position = position;
		}
	  
		

}
