/*
 * Copyright CEISMC, 2015
 */
package earsketch.sdm;

/**
 *
 * @author Michael
 */
public class Administration extends Agent {
    //Fixed Variables
    
    //Incrmental or Annual Variables
    public Attribute experienceWithSponsor;
    public Attribute yearsOfIntervention;
        
    //Dynamic Variables
    public Attribute interventionSupport;
    public Attribute CSP_pathwaySupport;
    
    public Administration(int maxTime, String adminName){
        
        //Incremental and Annual Variables
        experienceWithSponsor = new Attribute(this, 0, maxTime, "annualOnly", "Experience with Sponsor",0, true, false);
        yearsOfIntervention = new Attribute(this, 0, maxTime, "incremental", "Years of Intervention",0, true, false);
        attributes.add(experienceWithSponsor);
        attributes.add(yearsOfIntervention);                
        
        //Dynamic Variables
        interventionSupport = new Attribute(this, 0, maxTime, "allInternalDelta", "Intervention Support",0, false, false);
        CSP_pathwaySupport = new Attribute(this, 0, maxTime, "externalFocus", "CSP Pathway Support",0.1, false, false);
        attributes.add(interventionSupport);
        attributes.add(CSP_pathwaySupport);                
        
        name = adminName;
    }
    
    public void initialize()
    {
       ///need to do a real initialize procedure...ideally, pass in an array, do name-matching, and then set-values?
       double noVar = 1.5;
       for (Attribute a:attributes)
        {
            a.setValue(0, noVar);
        }
    }

}


