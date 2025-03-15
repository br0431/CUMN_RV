package com.example.cumn_rv;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class FirestoreResponse<T> {
    @SerializedName("documents")
    private List<T> documents;

    public List<T> getDocuments() {
        return documents;
    }
}



