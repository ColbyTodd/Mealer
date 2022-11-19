package com.uottawa.colbytodd.mealer;

public class menuList {

    private String documentId;
    private Boolean isChecked;
    private String email;

    public menuList(String documentID, Boolean isChecked, String email){
        this.documentId = documentID;
        this.isChecked = isChecked;
        this.email = email;
    }

    public String getDocumentId() {
        return documentId;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }


}
