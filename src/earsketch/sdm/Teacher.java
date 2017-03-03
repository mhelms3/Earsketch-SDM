/*
 * Copyright CEISMC, 2015
 */
package earsketch.sdm;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Michael
 */
public class Teacher extends Agent {

    //static variables
    public Attribute qualifications;
    public Attribute strengthOfNetwork;
    public Attribute organizationalCitizenship;
    public Attribute personalTurnoverRate;
    public Attribute musicArtInterest;
    
    //incremental varaibles
    public Attribute CS_Experience;
    public Attribute TeachingExperience;
    
    //dynamic variables
    public Attribute studioTeaching;    
    public Attribute earsketchKnowledge;
    public Attribute contentKnowledge;
    public Attribute CS_SelfEfficacy;
    public Attribute interventionSupport;
    public Attribute technicalMusicKnowledge;
    
    public Teacher(int maxTime, String teacherName) {
    
        //Fixed Variables
        qualifications = new Attribute(this, 0, maxTime, "static", "Teaching Qualifications", 0, false);        
        musicArtInterest = new Attribute(this, 0, maxTime, "static", "Music and Art Interest", 0, false);
        attributes.add(qualifications);        
        attributes.add(musicArtInterest);
        
        //Incremental or Annual Variables
        CS_Experience = new Attribute(this, 0, maxTime, "incremental", "CS Experience",1, true); 
        TeachingExperience = new Attribute(this, 0, maxTime, "incremental", "Teaching Experience",1, true); 
        attributes.add(CS_Experience);
        attributes.add(TeachingExperience);
        
        //Dynamic Variables
        studioTeaching = new Attribute(this, 0, maxTime, "standard", "Studio Teaching",0, false); 
        earsketchKnowledge = new Attribute(this, 0, maxTime, "allInternalAbsolute", "Earsketch Knowledge",0, false);
        contentKnowledge = new Attribute(this, 0, maxTime, "standard", "Content Knowledge",0, false);
        CS_SelfEfficacy = new Attribute(this, 0, maxTime, "selfEfficacy", "CS Self Efficacy",-0.1, false); 
        interventionSupport = new Attribute(this, 0, maxTime, "allInternalDelta", "Intervention Support",0, false);
        technicalMusicKnowledge = new Attribute(this, 0, maxTime, "allInternalDelta", "Technical Music Knowledge",0, false);
        attributes.add(studioTeaching);
        attributes.add(earsketchKnowledge);
        attributes.add(contentKnowledge);
        attributes.add(CS_SelfEfficacy);
        attributes.add(interventionSupport);
        attributes.add(technicalMusicKnowledge);
        
        //Irrelevant Variables
        //strengthOfNetwork = new Attribute(this, 0, maxTime, "static", "Strength of Network", 0);
        //organizationalCitizenship = new Attribute(this, 0, maxTime, "static", "Organizational Citizenship",0); 
        //personalTurnoverRate = new Attribute(this, 0, maxTime, "static", "Personal Turnover Rate", 0);
        //attributes.add(strengthOfNetwork);
        //attributes.add(organizationalCitizenship);
        //attributes.add(personalTurnoverRate);
        
        name = teacherName;
    }
    
    public void initialize(double ck, double ek, double is, double se, double cse, double oc, double st, double ma)
    {
        //extend initialization needs to account for noVar (no variables)
        double noVar = 1.5;
        //static variables
        qualifications.setValue(0, noVar);
        musicArtInterest.setValue(0, ma);
        
        //incremental variables
        CS_Experience.setValue(0, cse);
        TeachingExperience.setValue(0, noVar);
        
        //dynamic variables
        studioTeaching.setValue(0, st);
        earsketchKnowledge.setValue(0, ek);
        contentKnowledge.setValue(0, ck);
        CS_SelfEfficacy.setValue(0, se);
        interventionSupport.setValue(0, is);
        technicalMusicKnowledge.setValue(0, noVar);
        
        //irrelevant
        //strengthOfNetwork.setValue(0, noVar); --> have not found a place for this in any formula/observation
        //organizationalCitizenship.setValue(0, oc); --> have not found a place for this in any formula/observation
        //personalTurnoverRate.setValue(0, noVar); --> have not found a place for this in any formula/observation
    }
}