package com.estore.api.estoreapi.model;

import java.util.ArrayList;
import java.util.logging.Logger;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PurchaseHistory implements ListInterface<Transaction> {
    private static final Logger LOG = Logger.getLogger(PurchaseHistory.class.getName());
    @JsonProperty("allTransactions")
    private static ArrayList<Transaction> allTransactions = new ArrayList<>();
    @JsonProperty("items")
    private ArrayList<Transaction> items;

    public PurchaseHistory() {
        this.items = new ArrayList<>();
    }

    @Override
    public Transaction getItem(int id) {
        for (Transaction transaction : items) {
            if (transaction.getId().hashCode() == id) {
                return transaction;
            }
        }
        return null;
    }

    @Override
    public Transaction[] getItems() {
        return items.toArray(new Transaction[0]);
    }

    @Override
    public Transaction addItemToList(Transaction transaction) {
        if (items.contains(transaction))
            return null;
        items.add(transaction);
        allTransactions.add(transaction);
        return transaction;
    }

    @Override
    public void removeItemFromList(int id) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().hashCode() == id) {
                items.remove(i);
                break;
            }
        }
    }

    @Override
    public void removeAllFromList() {
        items.clear();
    }

    @Override
    public Transaction updateItemInList(Transaction newTransaction) {
        for (Transaction transaction : items) {
            if (transaction.equals(newTransaction)) {
                transaction.updateTransaction(newTransaction);
                return transaction;
            }
        }
        return null;
    }

}
