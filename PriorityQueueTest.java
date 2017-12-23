package csi403;

// Import required java libraries
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.json.*;
import java.util.*;
import java.lang.*;

// Extend HttpServlet class
public class PriorityQueueTest extends HttpServlet {

  public PrintWriter out;

  // Standard servlet method 
  public void init() throws ServletException
  {
      // Do any required initialization here - likely none
  }

  // Standard servlet method - handles a POST operation
  public void doPost(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
  {
      response.setContentType("application/json");
      out = response.getWriter();

      try {
          doService(request, response);
      } catch (Exception e){
          e.printStackTrace();
          out.println("{ \"message\" : \"Malformed JSON\"}");
      }
  }

  // Standard servlet method - does not respond to GET
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
  {
      // Set response content type and return an error message
      response.setContentType("application/json");
      out = response.getWriter();
      out.println("{ \"message\" : \"Use POST!\"}");
  }


  // Our main worker method
  private void doService(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
  {
      // Get received JSON data from HTTP request
      BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
      String jsonStr = "";
      if(br != null){
          jsonStr = br.readLine();
      }

      // Create JsonReader object
      StringReader strReader = new StringReader(jsonStr);
      JsonReader reader = Json.createReader(strReader);

      // Get the singular JSON object (name:value pair) in this message.    
      JsonObject obj = reader.readObject();

      //If more than one key:value pair (not only "inList" present), send an error message
      if(obj.size() > 1){
          out.println("{ \"message\" : \"Invalid number of key:value pairs\" }");
          return;
      }

      // From the object get the array named "inList"
      JsonArray inArray = obj.getJsonArray("inList");

      //Declare variable to hold each element of the inArray
      JsonObject element;
      //Instatiate PriorityQueue
      PriorityQueue<InputObject> pq = new PriorityQueue<InputObject>(inArray.size(), new InputObjectPriority());

      //Access and execute the commands from "inList"
      for(int i = 0; i < inArray.size(); i++){
          element = inArray.getJsonObject(i);

          //If element does not have "cmd", send an error message
          if (!element.containsKey("cmd")){
              out.println("{ \"message\" : \"No \"cmd\" present\" }");
              return;
          }

          //if statements for the command (either enqueue or dequeue)
          if (element.getString("cmd").equals("enqueue")) {
              //If element does not have "name" and "pri", send an error message
              if (!(element.containsKey("name") && element.containsKey("pri"))) {
                  out.println("{ \"message\" : \"No \"name\" or \"pri\" present\" }");
                  return;
              }

              //If "pri" is not an integer, send an error message
              if (element.getInt("pri", -911) == -911 || !element.getJsonNumber("pri").isIntegral()){
                  out.println("{ \"message\" : \"Invalid data type for \"pri\" key\" }");
                  return;
              }

              //If "pri" if less than 0, send an error message
              if (element.getInt("pri") < 0){
                  out.println("{ \"message\" : \"Key \"pri\" value cannot be less than 0\" }}");
                  return;
              }

              //Add (enqueue) the new element to pq
              pq.add(new InputObject(element.getString("name"), element.getInt("pri")));
          }
          else if(element.getString("cmd").equals("dequeue")) {
              //USE pq.poll()
              pq.poll();
          }
          else{
              //If the "cmd" is neither "enqueue" nor "dequeue", send an error message
              out.println("{ \"message\" : \"Invalid command\"}");
              return;
          }
      }

      // Print the priority queue in order
      JsonArrayBuilder outArrayBuilder = Json.createArrayBuilder();
      while(!pq.isEmpty())
          outArrayBuilder.add(pq.poll().getName());

      out.println("{ \"outList\" : " + outArrayBuilder.build().toString() + " }");
  }

  // Standard Servlet method
  public void destroy() {
      // Do any required tear-down here, likely nothing.
  }
}

