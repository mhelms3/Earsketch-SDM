/*
 * Copyright CEISMC, 2015
 */
package earsketch.sdm;

/**
 *
 * @author Michael
 */
public class SponsoringOrg extends Agent{
    
     //Fixed Variables
    
    //Incrmental or Annual Variables
    public Attribute initialPD_CS;
    public Attribute initialPD_EarSketch;
    public Attribute initialPD_StudioBasedLearning;
    
    public Attribute TechnicalSupport;
    public Attribute Adaptability;
    
    //Dynamic Variables
    
    //Irrelevant Variables
    //public Attribute ongoingPD_CS; --> requires multi-school model (outside of current release scope)
    //public Attribute ongoingPD_EarSketch; --> requires multi-school model (outside of current release scope)
    //public Attribute ongoingPD_StudioBasedLearning;--> requires multi-school model (outside of current release scope)
    
    
    
    public SponsoringOrg(int maxTime, String sponsorName){
        
        //Incremetnal of Annual Variables
        initialPD_CS = new Attribute(this, 0, maxTime, "allInternalAbsolute", "Initial PD CS",0, true, false);
        initialPD_EarSketch = new Attribute(this, 0, maxTime, "allInternalAbsolute", "Initial PD EarSketch",0, true, false);
        initialPD_StudioBasedLearning = new Attribute(this, 0, maxTime, "allInternalAbsolutey", "Initial PD Studio",0, true, false);
        attributes.add(initialPD_CS);
        attributes.add(initialPD_EarSketch);                
        attributes.add(initialPD_StudioBasedLearning);                
        
        //Dynamic Variables
        TechnicalSupport = new Attribute(this, 0, maxTime, "annualOnly", "Technical Support",0, false, false);
        Adaptability = new Attribute(this, 0, maxTime, "allInternalDelta", "Adaptability",0, false, false);
        attributes.add(TechnicalSupport);                
        attributes.add(Adaptability);

        name = sponsorName;
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
