
package org.employeeevent.model;

import java.io.Serializable;
import java.time.LocalDate;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Josh Juneau <juneau at fnal.gov>
 */

@XmlRootElement

public class AcmeEmployee implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    @Size(max = 50)
    private String firstName;
    //@Size(max = 50)  
    private String lastName;
    //@PastOrPresent
    private LocalDate startDate;
    private Integer age;
    private Integer jobId;
    private String status;

    public AcmeEmployee() {
    }

    public AcmeEmployee(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AcmeEmployee)) {
            return false;
        }
        AcmeEmployee other = (AcmeEmployee) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.fermi.employeeclient.AcmeEmployee[ id=" + id + " ]";
    }
    
}
