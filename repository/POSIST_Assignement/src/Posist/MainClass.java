package Posist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import Posist.Node;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class MainClass {

	static int count=0;
	public static void main(String[] args) throws Exception {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
		keyGenerator.init(168);
		Date date=new Date();
		Node genesis=null;
		ArrayList<Node> records=new ArrayList<Node>();
		HashMap<Integer,SecretKey> ownerKey = new HashMap<Integer,SecretKey>();
		
		while(true)
		{
			System.out.println("# Enter Choice:");
			System.out.println("1. Create Gensis Node:");
			System.out.println("2. Create Child Node:");
			int choice=Integer.parseInt(br.readLine());
			switch(choice)
			{
				case 1:
					if(count==0)
					{
						System.out.println("Enter Owner Name[String]");
						String ownerName=br.readLine();
						System.out.println("Enter Owner Id[Integer]");
						int ownerId=Integer.parseInt(br.readLine());
						System.out.println("Enter Value[Inetger]");
						int value=Integer.parseInt(br.readLine());
						SecretKey sk;
				        if (ownerKey.containsKey(ownerId)) 
				        {
				            sk = ownerKey.get(ownerId);
				        }
				        else
				        {
				        	sk = keyGenerator.generateKey();
				        	ownerKey.put(ownerId, sk);
				        }
						genesis=Node.createGenesisNode(date.getTime(),ownerId,value,ownerName,count,sk);
						records.add(genesis);
						if(genesis!=null)
						{
							System.out.println(genesis);
							count++;
						}
						else
						{
							System.out.println("There is some error. Please Try Again");
						}
						
					}
					else
					{
						System.out.println("Genesis Node already created. You can acces gensis node using gensis object.");
					}
					break;
				case 2:
					System.out.println("Enter the node number[parent node number]");
					int parent=Integer.parseInt(br.readLine());
					//check node exist or not
					Node parentNode=null;
					for(int i=0;i<records.size();i++)
					{
						if(records.get(i).getNodeNumber()==parent)
						{
							parentNode=records.get(i);
							break;
						}
					}
					if(parentNode==null)
					{
						System.out.println("No node found with this Number");
					}
					else
					{
						System.out.println("Enter Owner Name[String]");
						String ownerName=br.readLine();
						System.out.println("Enter Owner Id[Integer]");
						int ownerId=Integer.parseInt(br.readLine());
						System.out.println("Enter Value[Inetger]");
						int value=Integer.parseInt(br.readLine());
						SecretKey sk;
				        if (ownerKey.containsKey(ownerId)) 
				        {
				            sk = ownerKey.get(ownerId);
				        }
				        else
				        {
				        	sk = keyGenerator.generateKey();
				        	ownerKey.put(ownerId, sk);
				        }
				        
				        Node child=null;
				        child=Node.ChildNode(date.getTime(),ownerId,value,ownerName,count,sk,parentNode,genesis);
				        if(child!=null)
				        {
				        	count++;
				        	records.add(child);
				        }
				        else
				        {
				        	System.out.println("Child node not created");
				        }
					}
					
					break;
				default:
					System.out.println("Exit System");
					System.exit(0);
					break;
			}
		}
	}

}
