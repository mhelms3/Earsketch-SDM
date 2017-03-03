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
    
    private static void setupRelationships(List<Agent> T)
    {
        //initialize teacher Relationships
        //DO FILE READS
        T.get(0).addRelationship(T.get(1), 1.5);
        T.get(1).addRelationship(T.get(0), 1.5);
        T.get(2).addRelationship(T.get(0), 2);
                
        for (Agent t:T)
        {
            System.out.println(t.name+" ");            
            for (Attribute a: t.attributes)
            {
                System.out.print("     "+a.getAttrType()+" ");
                System.out.println(a.getValue(0));
            }
            for (Relationship r:t.relationships)
            {
                System.out.print("     "+r.getStrength()+" ");
                System.out.println(r.getAgent().getName());
            }
        }
    }
    
    private static void setupInfluences(List<Agent> T)
    {
      //Add influences
        T.get(0).attributes.get(0).addInfluencer(T.get(1), T.get(1).attributes.get(0), 1, ABSOLUTE);
        T.get(0).attributes.get(1).addInfluencer(T.get(1), T.get(1).attributes.get(1), 1, ABSOLUTE);
        T.get(0).attributes.get(2).addInfluencer(T.get(1), T.get(1).attributes.get(2), 1, ABSOLUTE);
        T.get(0).attributes.get(3).addInfluencer(T.get(1), T.get(1).attributes.get(3), 1, ABSOLUTE);
        
        T.get(1).attributes.get(0).addInfluencer(T.get(0), T.get(0).attributes.get(0), 1, ABSOLUTE);
        T.get(1).attributes.get(1).addInfluencer(T.get(0), T.get(0).attributes.get(1), 1, ABSOLUTE);
        T.get(1).attributes.get(2).addInfluencer(T.get(0), T.get(0).attributes.get(2), 1, ABSOLUTE);
        T.get(1).attributes.get(3).addInfluencer(T.get(0), T.get(0).attributes.get(3), 1, ABSOLUTE);
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
    
    private static void processInput(List<String> s, List<Agent> agents)
    {
        String Attribute = s.get(1);
        int attrIndex = getAttributeIndex(Attribute, agents);
        System.out.print(s.get(0)+":");
        System.out.print(Attribute+"  Values-->");
        Agent a;
        Double d;
        for(int i = 0; i<s.size()-2; i++)
        {
            if(Attribute.equals("Name"))
            {
                agents.get(i).setName(s.get(i+2));
                System.out.print(agents.get(i).getName()+" ");
            }
            else
            {
                d = Double.parseDouble(s.get(i+2));
                a = agents.get(i);
                a.attributes.get(attrIndex).setValue(0, d);
                System.out.print("|"+s.get(i+2)+"||"+a.attributes.get(attrIndex).getValue(0));
            }
        }
        System.out.println("");
    }
    
    private static void getStartingValues(List<Agent> teachers, List<Agent> classrooms, List<Agent> students, List<Agent> admins, List<Agent> sponsoringOrg) throws IOException
    {
        String fileName;
        fileName = "C:\\Users\\Michael\\Documents\\CEISMC Data\\EarsketchModelInputData2015.csv";
        BufferedReader br = new BufferedReader(
        new InputStreamReader(new FileInputStream(fileName)));
        try {
            String line;
            List<String> list = new ArrayList<String>();
            while ((line = br.readLine()) != null) {
                list = Arrays.asList(line.split(","));
                if(list.size()>0)
                    switch(list.get(0)){
                    case "Teacher": processInput(list, teachers);
                        break;
                    case "Classroom": processInput(list, classrooms);
                        break;
                    case "Student": processInput(list, students);
                        break;
                    case "Administration": processInput(list, admins);
                        break;
                    case "SponsoringOrg": processInput(list, sponsoringOrg);
                        break;
                    default: System.out.println("Error in assigning attribute value to an agent "+list.get(0));
                        break;
                    }
            }
        } finally {
            br.close();
        }
    }
    
    public static void main(String[] args) throws IOException {
     
    
  
        int years = 3;
        int maxTime = years*13; //12 months + 1 annual cycle

        //build agent objects (2015: 3 Teachers, 3 Classrooms, 3 Students, 3 Admins, 1 Sponsoring Org)
        List<Agent> allAgents = new ArrayList<>();
        List<Agent> allTeachers = createAgents("teacher", 3, maxTime, allAgents);
        List<Agent> allClassrooms = createAgents("classroom", 3, maxTime, allAgents);
        List<Agent> allStudents = createAgents("student", 3, maxTime, allAgents);
        List<Agent> allAdmins = createAgents("administration", 3, maxTime, allAgents);
        List<Agent> sponsoringOrgs = createAgents("sponsor", 1, maxTime, allAgents);
        
        //load initial parameters for all agents
        getStartingValues(allTeachers, allClassrooms, allStudents, allAdmins, sponsoringOrgs);
        setupRelationships(allTeachers);
        setupInfluences(allTeachers);
        
        
        for (int i=1; i<maxTime; i++)
        { //run update for all teachers, all attributes
          for (Agent T:allTeachers)
          {
              for (Attribute A:T.attributes)
              {
                  A.update(i);
              }
          }
        }
        
        for (Agent T:allTeachers)
          { //print final values of all attributes
              for (Attribute A:T.attributes)                  
              {
                    System.out.print(T.getName()+" ");
                    A.printAttrValues();
              }
          }
     
            
    } //end Main

}//end EarSketchSDM

