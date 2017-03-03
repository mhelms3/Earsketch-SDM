/*
 * Copyright CEISMC, 2015
 */
package earsketch.sdm;

/**
 *
 * @author Michael
 */
public class Influencer {
    private final Agent influencingAgent;         //this should be a pointer to an agent maybe -- needed to find relaitonship value?
    private final Attribute influencingAttribute; //this should be a pointer to an attributbe of an Agent
    private final double weightInfluence;         //weight of influence [1 to 5], fixed    
    private final boolean isAbsolute;             //affects ABSOLUTE half of internal change equation, else affects DELTA half of internal change equation
    private final boolean isAnnual;               //applies only to annual changes 

    public Influencer(Agent a1, Attribute attr1, double w, boolean absolute, boolean annual)
    {
        influencingAgent = a1;
        influencingAttribute = attr1;
        weightInfluence = w;
        isAbsolute = absolute;
        isAnnual = annual;
    }
    
    public boolean getAbsolute()
    {
        return isAbsolute;
    }
    
    public boolean getAnnual()
    {
        return isAnnual;
    }
    
    public Agent getAgent()
    {
        return influencingAgent;
    }
    
    public Attribute getAttribute()
    {
        return influencingAttribute;
    }
    
    public double getWeight()
    {
        return weightInfluence;
    }
    
}
