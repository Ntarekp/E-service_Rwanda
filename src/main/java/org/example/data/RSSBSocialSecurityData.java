package org.example.data;

public class RSSBSocialSecurityData {
    private String employeeId;
    private String employerName;
    private double ContributionAmount;

public RSSBSocialSecurityData(){

}

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public double getContributionAmount() {
        return ContributionAmount;
    }

    public void setContributionAmount(double contributionAmount) {
        ContributionAmount = contributionAmount;
    }
}
