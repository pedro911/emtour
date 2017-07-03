package de.wwu.wfm.group12.emtour2;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

@Entity
public class FunsparkRecommendationEntity implements Serializable {

	private static  final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	protected int idfunspark_recommendation;
	
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	protected EmtourRecommendationEntity emtourRecommendation;
	
	@Version
	protected long version;
	
	protected Double price;
	protected String description;

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}	

}