/*
 * Copyright CEISMC, 2015
 */
package earsketch.sdm;

/**
 *
 * @author Michael
 */
public class Students extends Agent{
    
    //Fixed variables    
    public Attribute musicArtAppreciation;
    public Attribute musicTechnicalKnowledge;
    public Attribute CS_BackgroundClasses;
    public Attribute CS_BackgroundInformal;
    public Attribute percentFreeLunch;
    public Attribute transienceRate;
        
    //Incremental or Annual Variables
    //*students change-over every year, so cannot be incremental*//
    
    //Dynamic Variables
    public Attribute CS_ContentKnowledge;
    public Attribute CS_Engagement;
    public Attribute CommunitySupport;
    public Attribute ActiveParticipation;
    
    
    //Irrevelant Variables
    //public Attribute collaboration --> part of active participation;
    //public Attribute CS_SelfEfficacy --> part of engagement;
    //public Attribute CS_Identity --> part of engagement;
    //public Attribute CS_Intention --> part of engagement;
    
    //public Attribute percentMinority  --> controversial, use %freeLunch/transienceRate
    //public Attribute percentFemale --> no hypothesis to support.
    //public Attribute percentIEP --> no data to support?
    //public Attribute electedClass --> no data to support?
    
    
    
    
    public Students(int maxTime, String studentsName)
    {
        //fixed variables
        musicArtAppreciation = new Attribute(this, 0, maxTime, "static", "Music and Art Appreciation",0, false);
        musicTechnicalKnowledge = new Attribute(this, 0, maxTime, "static", "Technical Music Knowledge",0, false);
        CS_BackgroundClasses = new Attribute(this, 0, maxTime, "static", "CS Background - # of Classes",0, false);
        CS_BackgroundInformal = new Attribute(this, 0, maxTime, "static", "CS Background - informal",0, false);
        percentFreeLunch = new Attribute(this, 0, maxTime, "static", "Percent Free Lunch",0, false);
        transienceRate = new Attribute(this, 0, maxTime, "static", "Transience Rate",0, false);
        attributes.add(musicArtAppreciation);
        attributes.add(musicTechnicalKnowledge);
        attributes.add(CS_BackgroundClasses);
        attributes.add(CS_BackgroundInformal);
        attributes.add(percentFreeLunch);
        attributes.add(transienceRate);
        
        //dynamic variables
        CS_ContentKnowledge = new Attribute(this, 0, maxTime, "balanced", "CS Content Knowledge",-0.1, false);
        CS_Engagement = new Attribute(this, 0, maxTime, "internalFocus", "CS Engagement",0.1, false);        
        ActiveParticipation = new Attribute(this, 0, maxTime, "internalFocus", "Active Participation",-0.1, false);
        CommunitySupport = new Attribute(this, 0, maxTime, "externalFocus", "Community Support",0.1, false);
        attributes.add(CS_ContentKnowledge);
        attributes.add(CS_Engagement);
        attributes.add(ActiveParticipation);
        attributes.add(CommunitySupport);
        
        name = studentsName;
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
