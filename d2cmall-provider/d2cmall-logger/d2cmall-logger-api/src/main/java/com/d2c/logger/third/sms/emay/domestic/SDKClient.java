/**
 * SDKClient.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */
package com.d2c.logger.third.sms.emay.domestic;

public interface SDKClient extends java.rmi.Remote {

    public java.lang.String getVersion() throws java.rmi.RemoteException;

    public StatusReport[] getReport(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException;

    public int cancelMOForward(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException;

    public int chargeUp(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2, java.lang.String arg3)
            throws java.rmi.RemoteException;

    public double getBalance(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException;

    public double getEachFee(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException;

    public Mo[] getMO(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException;

    public int logout(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException;

    public int registDetailInfo(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2,
                                java.lang.String arg3, java.lang.String arg4, java.lang.String arg5, java.lang.String arg6,
                                java.lang.String arg7, java.lang.String arg8, java.lang.String arg9) throws java.rmi.RemoteException;

    public int registEx(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2)
            throws java.rmi.RemoteException;

    public int sendSMS(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2, java.lang.String[] arg3,
                       java.lang.String arg4, java.lang.String arg5, java.lang.String arg6, int arg7, long arg8)
            throws java.rmi.RemoteException;

    public int serialPwdUpd(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2, java.lang.String arg3)
            throws java.rmi.RemoteException;

    public int setMOForward(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2)
            throws java.rmi.RemoteException;

    public int setMOForwardEx(java.lang.String arg0, java.lang.String arg1, java.lang.String[] arg2)
            throws java.rmi.RemoteException;

}
