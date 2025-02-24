package com.qaautomation.models.Response.EntriesResponse;

import java.util.List;

public class ResponseWrapper {
    private List<Item> Items;
    private LastEvaluatedKey LastEvaluatedKey;

    public List<Item> getItems() { return Items; }
    public void setItems(List<Item> items) { this.Items = items; }

    public LastEvaluatedKey getLastEvaluatedKey() { return LastEvaluatedKey; }
    public void setLastEvaluatedKey(LastEvaluatedKey lastEvaluatedKey) { this.LastEvaluatedKey = lastEvaluatedKey; }
}
