/*
 * Copyright CEISMC, 2015
 */
package earsketch.sdm;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



/**
 *
 * @author Michael
 */
public class EarsketchSDM {

    /**
     * @param args the command line arguments
     */
    static boolean ABSOLUTE = true;
    static boolean DELTA = false;
    static int yearIncrements = 13;
    static int years = 3;
    
    
    private static Agent getAgentByName(String s, List<Agent> agents)
    {
        for(Agent a: agents)
        {
            if(a.getName().equals(s))
                return a;
        }
        System.out.println("Null attribute name exception in getAgentByName for:"+s);
        return null;
    }
    
    private static void setupRelationships(List<Agent> T)
    {
        Agent cAgent;
        //initialize teacher Relationships
        //DO FILE READS
        
        cAgent = getAgentByName("Berkmar Teacher", T);
        cAgent.addRelationship(cAgent, 5);
        cAgent.addRelationship(getAgentByName("Discovery Teacher", T), 1.5);
        cAgent.addRelationship(getAgentByName("Collins Hill Teacher", T), 1.5);
        cAgent.addRelationship(getAgentByName("Georgia Tech", T), 5);
        cAgent.addRelationship(getAgentByName("Berkmar Administration", T), 2);
        cAgent.addRelationship(getAgentByName("Berkmar Classroom", T), 5);
        
        cAgent = getAgentByName("Discovery Teacher", T);
        cAgent.addRelationship(cAgent, 5);
        cAgent.addRelationship(getAgentByName("Berkmar Teacher", T), 1.5);
        cAgent.addRelationship(getAgentByName("Collins Hill Teacher", T), 1.5);
        cAgent.addRelationship(getAgentByName("Georgia Tech", T), 5);
        cAgent.addRelationship(getAgentByName("Discovery Administration", T), 2);
        cAgent.addRelationship(getAgentByName("Discovery Classroom", T), 5);
        
        cAgent = getAgentByName("Collins Hill Teacher", T);
        cAgent.addRelationship(cAgent, 5);
        cAgent.addRelationship(getAgentByName("Discovery Teacher", T), 1.5);
        cAgent.addRelationship(getAgentByName("Berkmar Teacher", T), 1.5);
        cAgent.addRelationship(getAgentByName("Georgia Tech", T), 5);
        cAgent.addRelationship(getAgentByName("Collins Hill Administration", T), 2);
        cAgent.addRelationship(getAgentByName("Collins Hill Classroom", T), 5);
    }
    
    
    private static void processInfluences(List<String> s, List<Agent> theAgents)
    {
        Agent cAgent;
        Attribute cAttr;
        Agent iAgent;
        Attribute iAttr;
        double weight;
        boolean isAbsolute = false;
        boolean isAnnual = false;

        cAgent = getAgentByName(s.get(1), theAgents);
        cAttr = cAgent.getAttributeByName(s.get(2));
        iAgent = getAgentByName(s.get(3), theAgents);
        iAttr = iAgent.getAttributeByName(s.get(4));
        weight = Double.parseDouble(s.get(5));
        if(s.get(6).equals("TRUE"))
            isAbsolute = true;
        if(s.get(7).equals("TRUE"))
            isAnnual = true;
        cAttr.addInfluencer(iAgent, iAttr, weight, isAbsolute, isAnnual);
    }
    
    private static void addToAgents (List<Agent> a, List<Agent> b)
    {
        for (Agent A:b)
          {
              a.add(A);
          }
    }
    
    private static List<Agent> createAgents(String typeOfAgent, int agentCount, int mt, List<Agent> all)
    {
        List<Agent> T;
        T = new ArrayList<>();
        for (int i=0;i<agentCount; i++)
        { 
            switch(typeOfAgent){
                case "teacher": T.add(new Teacher(mt, "Teacher"));
                    break;
                case "classroom": T.add(new Classroom(mt, "Administration", false));
                    break;
                case "student": T.add(new Students(mt, "Students"));
                    break;
                case "administration": T.add(new Administration(mt, "School Admin"));
                    break;
                case "sponsor": T.add(new SponsoringOrg(mt, "Georgia Tech"));
                    break;
                default: System.out.println("Error in creating an agent "+i+" "+typeOfAgent);
                    break;
            }
        }
        addToAgents(all, T);
        return T;
    }

    private static int getAttributeIndex(String s, List<Agent> agents)
    {
        int index = 0;
        for (Attribute t:agents.get(0).attributes)
        {
            if(!t.returnName().equals(s))
            {
               index++;
            } 
            else 
            {
                return index;
            }
        }
        return 999;
    }
    
    private static void processAttributeInput(List<String> s, List<Agent> agents)
    {
        String Attribute = s.get(2);
        int attrIndex = getAttributeIndex(Attribute, agents);
        Agent a;
        Double d;
        
        for(int i = 1; i<s.size()-2; i++)
        {
            if(Attribute.equals("Name"))
            {
                agents.get(i-1).setName(s.get(i+2));
            }
            else
            {
                d = Double.parseDouble(s.get(i+2));
                a = agents.get(i-1);
                a.attributes.get(attrIndex).setValue(0, d);
            }
        }
    }
    
    private static void getStartingValues(List<Agent> allAgents, List<Agent> teachers, List<Agent> classrooms, List<Agent> students, List<Agent> admins, List<Agent> sponsoringOrg) throws IOException
    {
        String fileName;
        String workingDir = System.getProperty("user.dir");
	System.out.println("Current working directory : " + workingDir);
        fileName = "data\\EarsketchModelInputData2015.csv";
        BufferedReader br = new BufferedReader(
        new InputStreamReader(new FileInputStream(fileName)));
        try {
            String line;
            List<String> list = new ArrayList<String>();
            while ((line = br.readLine()) != null) {
                list = Arrays.asList(line.split(","));
                if(list.size()>0)
                {
                    if(list.get(0).equals("Attribute"))
                    {
                        switch(list.get(1)){
                        case "Teacher": processAttributeInput(list, teachers);
                            break;
                        case "Classroom": processAttributeInput(list, classrooms);
                            break;
                        case "Student": processAttributeInput(list, students);
                            break;
                        case "Administration": processAttributeInput(list, admins);
                            break;
                        case "SponsoringOrg": processAttributeInput(list, sponsoringOrg);
                            break;
                        default: System.out.println("Error in assigning attribute value to an agent "+list.get(0));
                            break;
                        }
                    }
                    else if(list.get(0).equals("Influence"))
                    {
                        processInfluences(list, allAgents);
                    }
                }
                
                
            }
        } finally {
            br.close();
        }
    }
    
    public static void main(String[] args) throws IOException {
     
        int maxTime = years*yearIncrements; //12 months + 1 annual cycle

        //build agent objects (2015: 3 Teachers, 3 Classrooms, 3 Students, 3 Admins, 1 Sponsoring Org)
        List<Agent> allAgents = new ArrayList<>();
        List<Agent> allTeachers = createAgents("teacher", 3, maxTime, allAgents);
        List<Agent> allClassrooms = createAgents("classroom", 3, maxTime, allAgents);
        List<Agent> allStudents = createAgents("student", 3, maxTime, allAgents);
        List<Agent> allAdmins = createAgents("administration", 3, maxTime, allAgents);
        List<Agent> sponsoringOrgs = createAgents("sponsor", 1, maxTime, allAgents);
        
        //load initial parameters for all agents
        getStartingValues(allAgents, allTeachers, allClassrooms, allStudents, allAdmins, sponsoringOrgs);
        setupRelationships(allAgents);
        //setupInfluences(allAgents);
        
        
        for (int i=1; i<maxTime; i++)
        { //run update for all teachers, all attributes
          for (Agent T:allAgents)
          {
              for (Attribute A:T.attributes)
              {
                  A.update(i);
              }
          }
        }

        for (Agent T:allAgents)
          { //print final values of all attributes
              for (Attribute A:T.attributes)                  
              {
                    System.out.print(T.getName()+" ");
                    A.printAttrValues();
              }
          }

     
            
    } //end Main

}//end EarSketchSDM

