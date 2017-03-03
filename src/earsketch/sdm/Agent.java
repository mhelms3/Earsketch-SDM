/*
 * Copyright CEISMC, 2015
 */
package earsketch.sdm;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author Michael
 */
abstract public class Agent {
    public List<Relationship> relationships;
    public List<Attribute> attributes;
    public String name;

    public Agent() {
        relationships = new ArrayList<>();
        attributes = new ArrayList<>();
    }
    public void addRelationship(Agent a1, double s)
    {
        Relationship r1 = new Relationship(a1, s);
        if(!relationships.add(r1))
        {
           System.out.print("Error adding relationship");
        }
    }
    
    public double findRelationship(Agent a)
    {
        for(Relationship r:relationships)
        {
            if(r.getAgent()==a)
                return r.getStrength();
        }        
        System.out.print("Error finding matching relaitonship between"+name+ "and "+a.getName());       
        return -999;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String s)
    {
        name = s;
    }
    
    public Attribute getAttributeByName(String s)
    {
        for(Attribute a: attributes)
        {
            if(a.getName().equals(s))
                return a;
        }
        return null;
    }
}

