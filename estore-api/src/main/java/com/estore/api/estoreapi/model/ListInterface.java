package com.estore.api.estoreapi.model;


/**
 * @author Matthew Zhang
 */
public interface ListInterface<Item> {
    /**
     * 
     * @param id 
     * @return the item with matching id
     */
    public Item getItem(int id);

    /**
     * 
     * @return all items in the list
     */
    public Item[] getItems();

    /**
     * 
     * @param item
     * @return the item added to the list
     */
    public Item addItemToList(Item item);

    /**
     * removes item from list with matching id
     * @param id
     */
    public void removeItemFromList(int id);

    /**
     * removes all items from list
     */
    public void removeAllFromList();


    /**
     * updates item if item with matching id is found
     * @param item
     * @return
     */
    public Item updateItemInList(Item item);
}