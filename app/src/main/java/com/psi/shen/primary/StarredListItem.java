package com.psi.shen.primary;

public class StarredListItem{
    public boolean isCould,isSelfCreated,isToBeDeleted,isToBeClouded;
    private SingleAlloyItem AlloyItem;
    public StarredListItem(SingleAlloyItem singleAlloyItem,boolean isCould,
                           boolean isSelfCreated,boolean isToBeClouded,boolean isToBeDeleted){
        this.isCould=isCould;
        this.isSelfCreated=isSelfCreated;
        this.isToBeClouded=isToBeClouded;
        this.isToBeDeleted=isToBeDeleted;
        this.AlloyItem=singleAlloyItem;
    }

    public SingleAlloyItem getAlloyItem() {
        return AlloyItem;
    }

}
