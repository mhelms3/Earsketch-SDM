/*
 * Copyright CEISMC, 2015
 */
package earsketch.sdm;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.lang.Math;


/**
 *
 * @author Michael
 */
public class Attribute {
    private double attrValue[];
    private final String attrType; //standard, internalFocus, selfEfficacy, gaps, balanced
    private final List<Influencer> influence;
    private final Agent parentAgent;
    private final String name;
    private final double trend;
    
    private static Random randomVar = new Random();
    private static double maxLevel;
    private static double minLevel;
    private static double stepSize;
    
    

    private double weightInternal = .4;   //default value
    private double weightExternal = .6;   //default value
    private double K =    .1;             //default value
    
    private boolean isAnnual = false;


    public Attribute(Agent a) {
        parentAgent = a;
        attrValue[0] = 0;
        trend = 0;
        attrType = "InternalOnly";        
        influence = new ArrayList<>();        
        name = "standard";
        setGeneralParameters(-5, 5, .1);
        initializeChangeEquation();
        
    }
    
    public Attribute(Agent a, double defaultValue, int sizeOfAttrValue, String type, String attributeName, double attributeTrend, boolean annual) {
        parentAgent = a; 
        attrValue = new double[sizeOfAttrValue];
        Arrays.fill(attrValue, defaultValue);
        influence = new ArrayList<>();
        attrType = type;
        name = attributeName;
        trend = attributeTrend;
        isAnnual = annual;
        setGeneralParameters(5, -5, .1);
        initializeChangeEquation();
    }
    
    private void setGeneralParameters(double max, double min, double step)
    {
        maxLevel = max;
        minLevel = min;
        stepSize = step;
    }
    
    private void initializeChangeEquation ()
    {
        //need to figure this out
        switch(attrType){
            case "standard":      //default settings
                weightInternal = .4;
                weightExternal  = .6;
                K=.1;
                break;
            case "allInternalDelta": //focus weight all on internal
                weightInternal = 1;
                weightExternal  = 0;
                K=100000;
                break;
            case "allInternalAbsolute": //focus weight all on internal
                weightInternal = 1;
                weightExternal  = 0;
                K=0;
                break;
            case "allExternalAbsolute": //focus weight all on internal
                weightInternal = 0;
                weightExternal  = 1;
                K=0;
                break;
            case "selfEfficacy": //weight for self-efficacy related attributes?
                weightInternal = .1;
                weightExternal  = .9;
                K=100000;
                break;
            case "internalFocus":  //student engagement and classroom participation (high teacher controlled attributes)
                weightInternal = .7;
                weightExternal  = .3;
                K=0.1;
                break;
            case "externalFocus":  //weight for gap related attributes, and community support (high external factor attributes)?
                weightInternal = .2;
                weightExternal  = .8;
                K=0.1;
                break;
            case "balanced"://weight for year 4 test scores???
                weightInternal = .5;
                weightExternal  = .5;
                K=.1;
                break;
            case "static"://cannot change
                weightInternal = 0;
                weightExternal  = 0;
                K=0;
                break;
            case "incremental"://cannot change during year (increments by a fixed value each year)
                weightInternal = 0;
                weightExternal  = 0;
                K=0;
                break;
            case "annualOnly"://cannot change during year (increments by a variable value each year)
                weightInternal = 0;
                weightExternal  = 0;
                K=0;
                break;
            default:
                break;
        }
                
    }
    
    public void setValue(int t, double newAttrValue)
    {
        attrValue[t] = newAttrValue;
    }
    
   
    
    public void addInfluencer(Agent a, Attribute attr, double weight, boolean isAbsolute)
    {
        Influencer I = new Influencer(a, attr, weight, isAbsolute);
        influence.add(I);
        
    }
    
    public String returnName ()
    {
        return name;
    }
    
    public double getValue(int t)
    {
        return(attrValue[t]);
    }
    
    public String getAttrType()
    {
        return(attrType);
    }
    
    public void printAttrValues()
    {
        System.out.format("%25s Values:",name);
        for(double v: attrValue)
        {
            System.out.format("%5.2f, ",v);
        }
        System.out.println("");
    }
    
    private double getRelationshipWeight(Agent a, Agent b)
    {
        double weight;
        weight = a.findRelationship(b);        
        return weight;
    }
    
    //region Factor for S-curve 
        static public double f_s(double attribute)
        {
            double y = 0.2; //original is 0.2

            if (0 <= attribute && attribute < 0.4) return 1 - y;
            else if (0.4 <= attribute && attribute < 0.8) return 1 - 0.5 * y;
            else if (0.8 <= attribute && attribute < 1.2) return 1;
            else if (1.2 <= attribute && attribute < 1.6) return 1 - 0.5 * y;
            else return 1 - y;

        }
    
    public void changeEquation(int t, double trend, double p_abs, double p_delta)
        {            
            System.out.print("ABS:"+p_abs);
            System.out.print("  ++DELTA:"+p_delta);
            double change_prob = (weightInternal * ((Math.exp(-K * t) * p_abs * f_s(attrValue[t-1])) + ((1 - Math.exp(-K * t)) * p_delta))) + weightExternal * trend;            
            System.out.println("  --Change:"+change_prob);
            double R = randomVar.nextDouble();
            if (change_prob >= 0)
            {
                if ((R <= change_prob) && (attrValue[t-1] + stepSize <= maxLevel)) 
                {
                    attrValue[t] = attrValue[t-1] + stepSize;
                    System.out.println("Up");
                }
                else                 
                    attrValue[t]= attrValue[t-1];
            }
            else
            {
                if ((R <= -change_prob) && (attrValue[t-1] - stepSize >= minLevel)) 
                {
                    attrValue[t] = attrValue[t-1] - stepSize;    
                    System.out.println("Down");
                }
                else 
                    attrValue[t] = attrValue[t-1];
            }
        }
    
    static public double ind(double att1, double att0)
        {
            if (att1 - att0 > 0) return 1.0;
            else if (att1 - att0 < 0) return -1.0;
            else return 0.0;
        }
    
    
    public void update(int time)
    {
        double valueInfluenceAttr;
        double influenceWeight;
        double relationshipWeight;
        
        double totalValueAbsolute = 0;
        double totalWeightAbsolute = 0;
        double finalValueAbsolute = 0;
        
        double totalValueDelta = 0;
        double totalWeightDelta = 0;
        double finalValueDelta = 0;
        
        if (attrType == "static")
        {
            attrValue[time] = attrValue[time-1];
        }
        else if (!isAnnual)
        {
            for(Influencer I:influence)
            {
                Agent iAgent = I.getAgent();
                Attribute influenceAttr = I.getAttribute();
                valueInfluenceAttr = influenceAttr.getValue(time-1);
                influenceWeight = I.getWeight();
                //System.out.println("!!!VALUE:"+valueInfluenceAttr);
                if(I.getAbsolute())//for ABSOLUTE CALCULATION
                {
                    totalValueAbsolute += (valueInfluenceAttr * influenceWeight);
                    totalWeightAbsolute += influenceWeight;
                    //System.out.println("ABS-->"+this.getName()+" Value: "+totalValueAbsolute+ " Weight: "+ totalWeightAbsolute);
                }
                else //for DELTA CALCULATION
                {
                    relationshipWeight = getRelationshipWeight(parentAgent, iAgent);
                    totalValueDelta += (valueInfluenceAttr * influenceWeight * relationshipWeight);  //this needs to be changed to use time last period and looks for change (IND function)
                    totalWeightDelta += influenceWeight;
                    //System.out.println("DELTA-->"+this.getName()+" Value: "+totalValueDelta+ " Weight: "+ totalWeightDelta);
                }
            }        
            if (totalWeightAbsolute>0)
                finalValueAbsolute = totalValueAbsolute/totalWeightAbsolute;
            if (totalWeightDelta>0)
                    finalValueDelta = totalValueDelta/totalWeightDelta;
            changeEquation(time, trend, finalValueAbsolute, finalValueDelta);
        }
        else
        {
            //CODE ANNUAL CHANGES HERE
        }
            
    }
}
