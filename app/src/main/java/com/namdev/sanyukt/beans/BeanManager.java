package com.namdev.sanyukt.beans;

public class BeanManager {

    private static BeanManager instance=null;
    private Object members;

    private BeanManager(){}

    public static BeanManager getInstance()
    {
     if(instance==null)
     {
         Object object=new Object();
         synchronized (object)
         {
             instance=new BeanManager();
         }
     }
        return instance;
    }

    public Object getMembers() {
        return members;
    }

    public void setMembers(Object members) {
        this.members = members;
    }
}
