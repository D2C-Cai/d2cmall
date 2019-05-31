/**
 * StatusReport.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */
package com.d2c.logger.third.sms.emay.global;

public class StatusReport implements java.io.Serializable {

    private static final long serialVersionUID = 4695536490122969530L;
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
            StatusReport.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sdkhttp.eucp.b2m.cn/", "statusReport"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errorCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "errorCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("memo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "memo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mobile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mobile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("receiveDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "receiveDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reportStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "reportStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("seqID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "seqID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceCodeAdd");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serviceCodeAdd"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("submitDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "submitDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    private java.lang.String errorCode;
    private java.lang.String memo;
    private java.lang.String mobile;
    private java.lang.String receiveDate;
    private int reportStatus;
    private long seqID;
    private java.lang.String serviceCodeAdd;
    private java.lang.String submitDate;
    private java.lang.Object __equalsCalc = null;
    private boolean __hashCodeCalc = false;

    public StatusReport() {
    }

    public StatusReport(java.lang.String errorCode, java.lang.String memo, java.lang.String mobile,
                        java.lang.String receiveDate, int reportStatus, long seqID, java.lang.String serviceCodeAdd,
                        java.lang.String submitDate) {
        this.errorCode = errorCode;
        this.memo = memo;
        this.mobile = mobile;
        this.receiveDate = receiveDate;
        this.reportStatus = reportStatus;
        this.seqID = seqID;
        this.serviceCodeAdd = serviceCodeAdd;
        this.submitDate = submitDate;
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(java.lang.String mechType,
                                                                    java.lang.Class<?> _javaType, javax.xml.namespace.QName _xmlType) {
        return new org.apache.axis.encoding.ser.BeanSerializer(_javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(java.lang.String mechType,
                                                                        java.lang.Class<?> _javaType, javax.xml.namespace.QName _xmlType) {
        return new org.apache.axis.encoding.ser.BeanDeserializer(_javaType, _xmlType, typeDesc);
    }

    /**
     * Gets the errorCode value for this StatusReport.
     *
     * @return errorCode
     */
    public java.lang.String getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the errorCode value for this StatusReport.
     *
     * @param errorCode
     */
    public void setErrorCode(java.lang.String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Gets the memo value for this StatusReport.
     *
     * @return memo
     */
    public java.lang.String getMemo() {
        return memo;
    }

    /**
     * Sets the memo value for this StatusReport.
     *
     * @param memo
     */
    public void setMemo(java.lang.String memo) {
        this.memo = memo;
    }

    /**
     * Gets the mobile value for this StatusReport.
     *
     * @return mobile
     */
    public java.lang.String getMobile() {
        return mobile;
    }

    /**
     * Sets the mobile value for this StatusReport.
     *
     * @param mobile
     */
    public void setMobile(java.lang.String mobile) {
        this.mobile = mobile;
    }

    /**
     * Gets the receiveDate value for this StatusReport.
     *
     * @return receiveDate
     */
    public java.lang.String getReceiveDate() {
        return receiveDate;
    }

    /**
     * Sets the receiveDate value for this StatusReport.
     *
     * @param receiveDate
     */
    public void setReceiveDate(java.lang.String receiveDate) {
        this.receiveDate = receiveDate;
    }

    /**
     * Gets the reportStatus value for this StatusReport.
     *
     * @return reportStatus
     */
    public int getReportStatus() {
        return reportStatus;
    }

    /**
     * Sets the reportStatus value for this StatusReport.
     *
     * @param reportStatus
     */
    public void setReportStatus(int reportStatus) {
        this.reportStatus = reportStatus;
    }

    /**
     * Gets the seqID value for this StatusReport.
     *
     * @return seqID
     */
    public long getSeqID() {
        return seqID;
    }

    /**
     * Sets the seqID value for this StatusReport.
     *
     * @param seqID
     */
    public void setSeqID(long seqID) {
        this.seqID = seqID;
    }

    /**
     * Gets the serviceCodeAdd value for this StatusReport.
     *
     * @return serviceCodeAdd
     */
    public java.lang.String getServiceCodeAdd() {
        return serviceCodeAdd;
    }

    /**
     * Sets the serviceCodeAdd value for this StatusReport.
     *
     * @param serviceCodeAdd
     */
    public void setServiceCodeAdd(java.lang.String serviceCodeAdd) {
        this.serviceCodeAdd = serviceCodeAdd;
    }

    /**
     * Gets the submitDate value for this StatusReport.
     *
     * @return submitDate
     */
    public java.lang.String getSubmitDate() {
        return submitDate;
    }

    /**
     * Sets the submitDate value for this StatusReport.
     *
     * @param submitDate
     */
    public void setSubmitDate(java.lang.String submitDate) {
        this.submitDate = submitDate;
    }

    public synchronized boolean equals(java.lang.Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof StatusReport))
            return false;
        StatusReport other = (StatusReport) obj;
        if (this == obj)
            return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true
                && ((this.errorCode == null && other.getErrorCode() == null)
                || (this.errorCode != null && this.errorCode.equals(other.getErrorCode())))
                && ((this.memo == null && other.getMemo() == null)
                || (this.memo != null && this.memo.equals(other.getMemo())))
                && ((this.mobile == null && other.getMobile() == null)
                || (this.mobile != null && this.mobile.equals(other.getMobile())))
                && ((this.receiveDate == null && other.getReceiveDate() == null)
                || (this.receiveDate != null && this.receiveDate.equals(other.getReceiveDate())))
                && this.reportStatus == other.getReportStatus() && this.seqID == other.getSeqID()
                && ((this.serviceCodeAdd == null && other.getServiceCodeAdd() == null)
                || (this.serviceCodeAdd != null && this.serviceCodeAdd.equals(other.getServiceCodeAdd())))
                && ((this.submitDate == null && other.getSubmitDate() == null)
                || (this.submitDate != null && this.submitDate.equals(other.getSubmitDate())));
        __equalsCalc = null;
        return _equals;
    }

    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getErrorCode() != null) {
            _hashCode += getErrorCode().hashCode();
        }
        if (getMemo() != null) {
            _hashCode += getMemo().hashCode();
        }
        if (getMobile() != null) {
            _hashCode += getMobile().hashCode();
        }
        if (getReceiveDate() != null) {
            _hashCode += getReceiveDate().hashCode();
        }
        _hashCode += getReportStatus();
        _hashCode += new Long(getSeqID()).hashCode();
        if (getServiceCodeAdd() != null) {
            _hashCode += getServiceCodeAdd().hashCode();
        }
        if (getSubmitDate() != null) {
            _hashCode += getSubmitDate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

}
