package de.wwu.wfm.group12.emtour;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;
import java.io.Serializable;
import java.sql.Date;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class BillEntity implements Serializable {

	private static  final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	protected int idbill;
	
	@OneToOne(cascade=CascadeType.ALL)
	protected CustomerEntity customer;
	
	@OneToMany(targetEntity=EmtourRecommendationEntity.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch=FetchType.EAGER)
	protected Collection<EmtourRecommendationEntity> emtourRecommendations;
	
	@OneToMany(targetEntity=FunsparkRecommendationEntity.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch=FetchType.EAGER)
	protected Collection<FunsparkRecommendationEntity> funsparkRecommendations;
	
	@Version
	protected long version;
	
	protected boolean paymentStatus;

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public int getIdbill() {
		return idbill;
	}

	public void setIdbill(int idbill) {
		this.idbill = idbill;
	}

	public boolean isPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(boolean paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public CustomerEntity getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}

	public Collection<EmtourRecommendationEntity> getEmtourRecommendations() {
		if(emtourRecommendations == null) emtourRecommendations = new HashSet<EmtourRecommendationEntity>();
		return emtourRecommendations;
	}

	public void setEmtourRecommendations(Collection<EmtourRecommendationEntity> emtourRecommendations) {
		this.emtourRecommendations = emtourRecommendations;
	}

	public Collection<FunsparkRecommendationEntity> getFunsparkRecommendations() {
		if(funsparkRecommendations == null) funsparkRecommendations = new HashSet<FunsparkRecommendationEntity>();
		return funsparkRecommendations;
	}

	public void setFunsparkRecommendations(Collection<FunsparkRecommendationEntity> funsparkRecommendations) {
		this.funsparkRecommendations = funsparkRecommendations;
	}	

}