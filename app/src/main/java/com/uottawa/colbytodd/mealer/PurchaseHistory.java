package com.uottawa.colbytodd.mealer;

public class PurchaseHistory {

    private String DocumentID;
    private String Status;

    public PurchaseHistory(String DocumentID, String Status){
        this.DocumentID = DocumentID;
        this.Status = Status;
    }

    public String getDocumentID(){
        return this.DocumentID;
    }

    public String getStatus(){
        return this.Status;
    }
}
