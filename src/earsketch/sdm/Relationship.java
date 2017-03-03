/*
 * Copyright CEISMC, 2015
 */
package earsketch.sdm;

/**
 *
 * @author Michael
 */
public class Relationship {
    private final Agent relAgent;
    private final double strengthOfRelationship;
   
    public Relationship(Agent a1, double str)
    {
        relAgent = a1;
        strengthOfRelationship = str;
    }    
    
    public double getStrength(){
        return strengthOfRelationship;
    }
        
    public Agent getAgent(){
        return relAgent;
    }
    
    
}

