package com.uottawa.colbytodd.mealer;

public class menuList {

    private String documentId;
    private Boolean isChecked;

    public menuList(String documentID, Boolean isChecked){
        this.documentId = documentID;
        this.isChecked = isChecked;
    }

    public String getDocumentId() {
        return documentId;
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
