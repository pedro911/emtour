package de.wwu.wfm.group12.emtour2;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;
import java.io.Serializable;
import java.sql.Date;

@Entity
public class EmtourRecommendationEntity implements Serializable {

	private static  final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	protected int idemtour_recommendation;
	
	@OneToOne(cascade=CascadeType.ALL)
	protected CustomerEntity customer;
	
	@Version
	protected long version;
	
	protected Double price;
	protected String description;
	protected Enum<?> recomendedDestination; 
	
	protected boolean paymentStatus;

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}	
	

}