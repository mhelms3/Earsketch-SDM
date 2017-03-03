/*
 * Copyright CEISMC, 2015
 */
package earsketch.sdm;

/**
 *
 * @author Michael
 */
public class Classroom extends Agent{
    
    //Fixed Variables
    public Attribute isAP;
    
    //Incremental or Annual Variables
    public Attribute technologySupport;
    public Attribute collaborationObserved;
    public Attribute classStudentNumber;
    public Attribute classAllocatedTime;
    public Attribute classInstructionalTime;
    public Attribute classPrepTime;
    
    //Dynamic Variables
    
    
    
    public Classroom(int maxTime, String classname, boolean AP)
    {
        //fixed Variables
        isAP = new Attribute(this, 5, maxTime, "static", "AP Class", 0, true);
        attributes.add(isAP);
        
        //Incremental or Annual Variables
        technologySupport = new Attribute(this, 0, maxTime, "annualOnly", "Technology Support",0, true);
        collaborationObserved = new Attribute(this, 0, maxTime, "annualOnly", "Observed Collaboration",0, true);
        classStudentNumber = new Attribute(this, 0, maxTime, "externalFocus", "Number of Students",-0.1, true);
        classAllocatedTime = new Attribute(this, 0, maxTime, "allExternalAbsolute", "Allocated Time",-0.1, true);
        classInstructionalTime = new Attribute(this, 0, maxTime, "externalFocus", "Instructional Time",-0.5, true);
        classPrepTime = new Attribute(this, 0, maxTime, "externalFocus", "Teacher Prep Time",-0.1, true);
        attributes.add(technologySupport);
        attributes.add(collaborationObserved);
        attributes.add(classStudentNumber);
        attributes.add(classAllocatedTime);
        attributes.add(classInstructionalTime);
        attributes.add(classPrepTime);
        
        name = classname;
    }
    
    public void initialize(double tech, double ps, double studentNumber, double allocTime, double ap)
    {
        
        double noVar = 1.5;
        //fixed Variables
        isAP.setValue(0, ap);
        
        //Incremental or Annual Variables
        technologySupport.setValue(0, tech);
        collaborationObserved.setValue(0, noVar);
        classStudentNumber.setValue(0, studentNumber);
        classAllocatedTime.setValue(0, allocTime);
        classInstructionalTime.setValue(0, noVar);
        classPrepTime.setValue(0, noVar);
        
    }
}
