package au.com.unico.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="GCD")
public class GCD implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8944222219978111726L;

	@Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

	public Long getId() {
		return id;
	}

	@Column(name = "gcd_value")
	private Integer gcd;
	
	@Column(name = "entered_date")
	private Date enteredDate;
	
	
	public Date getEnteredDate() {
		return enteredDate;
	}

	public void setEnteredDate(Date enteredDate) {
		this.enteredDate = enteredDate;
	}

	public Integer getGcd() {
		return gcd;
	}

	public void setGcd(Integer gcd) {
		this.gcd = gcd;
	}

}
