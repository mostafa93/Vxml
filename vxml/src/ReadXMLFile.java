import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class ReadXMLFile {
	String size;
	boolean confirm_size;
	String toppings;
	boolean confirm_topping;
	String crust;
	boolean confirm_crust;
	String thickness;
	boolean confirm_thickness;
	Scanner scanner;
	
	
	public ReadXMLFile(){
		this.size = null;
		this.confirm_size = false;
		this.toppings = null;
		this.confirm_topping = false;
		this.crust = null;
		this.confirm_crust = false;
		this.thickness = null;
		this.confirm_thickness = false;
		this.scanner = new Scanner(System.in);
	}
	
	public void beginParsing(){
		SAXBuilder builder = new SAXBuilder();
		  File xmlFile = new File("/users/mostafa/Desktop/vxml/vxml/dialog.vxml");
		  
		  try {
			  
			Document document = (Document) builder.build(xmlFile);
			
			// rootNode
			Element rootNode = document.getRootElement();
			
			// nodeI is the form
			Element nodeI = rootNode.getChild("form");
			
			//Everything inside the form
			List list = nodeI.getChildren();
			
			//the main function of this flag is to check
			//if the confirmation of one of the fields is declined
			boolean flag = true;
			
			//loops on every element inside the form tag
			for (int i = 0; i < list.size(); i++) {

			   //casts each element in the form to a node
			   Element node = (Element) list.get(i);
			   
			   //if the node is of type field. meaning the tag is a field
			   //a function is called to handle prompting the user and taking the input
			   if(node.getQualifiedName() == "field"){
				   
				   System.out.println("field name: " + node.getAttributeValue("name", "name"));
				   flag = expandField(node);
				   
				 // if the node of type block, simply prints the conten of the blocl
			   } else if(node.getQualifiedName() == "block"){
				   System.out.println(node.getText());
			   }
			   
			   //if the confirmation for one of the fields is declined, a roll back
			   //is done to prompt the user to re-input the field
			   if(flag == false){
				   i -= 2;
			   }

			}

		  } catch (IOException io) {
			System.out.println(io.getMessage());
		  } catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		  }
		
		
	}
	
	//This method handles the field
	public boolean expandField(Element node){
		
		//This string will hold the value of the input in the future
		String val = null;
		//This string holds the name of the field. size, crust, topping... etc,
		String fieldName = node.getAttributeValue("name", "name");
		
		//a call is done to a method that takes the input from the user
		val = getInput(node.getChild("prompt"), node.getChild("grammar"), fieldName);

		//checks the fieldName and saves the input value to the corresponding
		//class variable
		switch(fieldName){
		case "size":
			this.size = val;
			System.out.println(val);
			break;
		case "toppings":
			this.toppings = val;
			System.out.println(val);
			break;
		case "crust":
			this.crust = val;
			System.out.println(val);
			break;
		case "thickness":
			this.thickness = val;
			System.out.println(val);
			break;
		case "confirm_size":
			if(val.equals("yes")){
				this.confirm_size = true;
			}else if(val.equals("no")){
				this.confirm_size = false;
				return false;
			}
			System.out.println(val);
			break;
		case "confirm_toppings":
			if(val.equals("yes")){
				this.confirm_topping = true;
			}else if(val.equals("no")){
				this.confirm_topping = false;
				return false;
			}
			System.out.println(val);
			break;
		case "confirm_crust":
			if(val.equals("yes")){
				this.confirm_crust = true;
			}else if(val.equals("no")){
				this.confirm_crust = false;
				return false;
			}
			System.out.println(val);
			break;
		case "confirm_thickness":
			if(val.equals("yes")){
				this.confirm_thickness = true;
			}else if(val.equals("no")){
				this.confirm_thickness = false;
				return false;
			}
			System.out.println(val);
			break;
		}
		
		System.out.println("==========");
		return true;
	}
	
	//This method handles the input from the user
	//The paramters of this method are as follows
	//node1 holds the prompt
	//node2 holds the grammar
	//fieldName is simply the fieldName
	public String getInput(Element node1, Element node2, String fieldName){

		String [] fields = {"confirm_size",
				"confirm_toppings",
				"confirm_crust",
				"confirm_thickness"};
		String prompt = node1.getText();
		
		for(String field: fields){
			if(field.equals(fieldName)){
				String substring1 = prompt.substring(0, 14);
				String substring2 = prompt.substring(15);
				
				switch(fieldName){
				case "confirm_size":
					prompt = substring1 + this.size + " sized " + substring2;
					break;
				case "confirm_toppings":
					prompt = substring1 + this.toppings + " " + substring2;
					break;
				case "confirm_crust":
					prompt = substring1 + this.crust + " crust " + substring2;
					break;
				case "confirm_thickness":
					prompt = substring1 + this.thickness + " " + substring2;
					break;
				}
					
			}
		}
		
		System.out.println(prompt);
		
		Element rule = node2.getChild("rule");
		Element oneOf = rule.getChild("one-of");
		List options = oneOf.getChildren();
		
		System.out.print("Options are: ");
		
		for(int i = 0; i < options.size(); i++){
			Element option = (Element) options.get(i);
			System.out.print(option.getText());
		}
		
		System.out.println();
		
		
        String var = scanner.nextLine();
		return var;
	}
	
	public static void main(String[] args) {
		ReadXMLFile x = new ReadXMLFile();
		x.beginParsing();
	}
}