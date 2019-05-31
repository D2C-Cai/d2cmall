package com.d2c.logger.third.email.common;

public class EmailEntity {

    private String api_user;
    private String api_key;
    private String from;
    private String to;
    private String subject;
    private String html;
    private String fromname;
    private String bcc;
    private String cc;
    private String replyto;
    private int label;
    private String headers;
    private String files;
    private String x_smtpapi;
    private String resp_email_id;
    private String use_maillist;
    private String gzip_compress;
    private String template_invoke_name;
    private String substitution_vars;

    public String getApi_user() {
        return api_user;
    }

    public void setApi_user(String api_user) {
        this.api_user = api_user;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getFromname() {
        return fromname;
    }

    public void setFromname(String fromname) {
        this.fromname = fromname;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getReplyto() {
        return replyto;
    }

    public void setReplyto(String replyto) {
        this.replyto = replyto;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getX_smtpapi() {
        return x_smtpapi;
    }

    public void setX_smtpapi(String x_smtpapi) {
        this.x_smtpapi = x_smtpapi;
    }

    public String getResp_email_id() {
        return resp_email_id;
    }

    public void setResp_email_id(String resp_email_id) {
        this.resp_email_id = resp_email_id;
    }

    public String getUse_maillist() {
        return use_maillist;
    }

    public void setUse_maillist(String use_maillist) {
        this.use_maillist = use_maillist;
    }

    public String getGzip_compress() {
        return gzip_compress;
    }

    public void setGzip_compress(String gzip_compress) {
        this.gzip_compress = gzip_compress;
    }

    public String getTemplate_invoke_name() {
        return template_invoke_name;
    }

    public void setTemplate_invoke_name(String template_invoke_name) {
        this.template_invoke_name = template_invoke_name;
    }

    public String getSubstitution_vars() {
        return substitution_vars;
    }

    public void setSubstitution_vars(String substitution_vars) {
        this.substitution_vars = substitution_vars;
    }

    public String getXML() {
        StringBuilder xml = new StringBuilder("");
        if (this.api_user != null)
            xml.append("api_user=" + this.api_user);
        if (this.api_key != null)
            xml.append("&api_key=" + this.api_key);
        if (this.from != null)
            xml.append("&from=" + this.from);
        if (this.to != null)
            xml.append("&to=" + this.to);
        if (this.subject != null)
            xml.append("&subject=" + this.subject);
        if (this.html != null)
            xml.append("&html=" + this.html);
        if (this.fromname != null)
            xml.append("&fromname=" + this.fromname);
        if (this.bcc != null)
            xml.append("&bcc=" + this.bcc);
        if (this.cc != null)
            xml.append("&cc=" + this.cc);
        if (this.replyto != null)
            xml.append("&replyto=" + this.replyto);
        if (this.label != 0)
            xml.append("&label=" + this.label);
        if (this.headers != null)
            xml.append("&headers=" + this.headers);
        if (this.files != null)
            xml.append("&files=" + this.files);
        if (this.x_smtpapi != null)
            xml.append("&x_smtpapi=" + this.x_smtpapi);
        if (this.resp_email_id != null)
            xml.append("&resp_email_id=" + this.resp_email_id);
        if (this.use_maillist != null)
            xml.append("&use_maillist=" + this.use_maillist);
        if (this.gzip_compress != null)
            xml.append("&gzip_compress=" + this.gzip_compress);
        if (this.template_invoke_name != null)
            xml.append("&template_invoke_name=" + this.template_invoke_name);
        if (this.substitution_vars != null)
            xml.append("&substitution_vars=" + this.substitution_vars);
        return xml.toString();
    }

}
