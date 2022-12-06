package com.uottawa.colbytodd.mealer;

public class PurchaseHistory {

    private String DocumentID;
    private String Status;
    private String Cook;

    public PurchaseHistory(String DocumentID, String Status, String Cook){
        this.DocumentID = DocumentID;
        this.Status = Status;
        this.Cook = Cook;
    }

    public String getDocumentID(){
        return this.DocumentID;
    }

    public String getStatus(){
        return this.Status;
    }

    public String getCook() { return this.Cook; }
}
