/*
 * 佐藤瑠星 & ザイダトゥル 共同
 */
package thedisaster;
/**
 *
 * @author user
 */
public class item {
    private String name;
    private int PlusHP;
    private int knife=10;
    
    public item(String n, int h) {
        this.name = n;
        this.PlusHP = h;
    }
    
    public int getlow_item(){
        return PlusHP;
    }
    public int getknife(){
        return knife;   
    }
    public void setknife(int knife){
        this.knife=knife;
    }
    
}
