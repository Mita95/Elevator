/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mj
 */
public class Person implements Runnable{
    int at,to,rank;
    Direction lift;
    Person(int rank,int at,int to, Direction lift){
        this.rank=rank;
        this.at=at;
        this.to=to;
        this.lift=lift;
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.


    }

}
