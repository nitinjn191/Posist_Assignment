package Posist;

import java.util.ArrayList;

import javax.crypto.SecretKey;

import Posist.JavaUtil;
public class Node {
	
	
	
	long timestamp;
	String data;
	int nodeNumber;
	String nodeId;
	Node referenceNodeId; //parent
	ArrayList<Node> childReferenceNodeId;
	Node genesisReferenceNodeId;
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	String hashValue;
	
	public Node(long t,String d,int nodeNumber,String nodeId,Node referenceNodeId,Node genesisReference)
	{
		this.timestamp=t;
		this.data=d;
		this.nodeNumber=nodeNumber;
		this.nodeId=nodeId;
		this.referenceNodeId=referenceNodeId;
		this.childReferenceNodeId=new ArrayList<Node>();
		this.genesisReferenceNodeId=genesisReference;
	}
	
	public static Node createGenesisNode(long t,int ownerId,int value,String ownerName,int count,SecretKey sk) throws Exception
	{
		JavaUtil util=new JavaUtil();
		String HashArr[]=new String[3];
		HashArr[0]=String.valueOf(ownerId);
		HashArr[1]=String.valueOf(value);
		HashArr[2]=ownerName;
		//data contain encrypted form of id,value,name and hash of all three
		String data=util.getEncrypt(String.valueOf(ownerId),sk)+" "+util.getEncrypt(String.valueOf(ownerId), sk)+" "+util.getEncrypt(String.valueOf(ownerName), sk)+" "+
		HashArr.hashCode();
		Node n=new Node(t,data,count,String.valueOf(HashArr.hashCode()),null,null);
		String hashValue=String.valueOf(n.getTimestamp())+n.getNodeNumber()+n.getNodeId()+n.getChildReferenceNodeId()+n.getGenesisReferenceNodeId();
		n.setHashValue(util.hashString(hashValue));
		return n;
	}
	
	public static Node ChildNode(long t,int ownerId,int value,String ownerName,int count,SecretKey sk,Node parent,Node genesis) throws Exception
	{
		int valid=verifyProperty(value,parent,sk);
		if(valid!=1)
		{
			System.out.println("Property not satisfied.");
			return null;
		}
		JavaUtil util=new JavaUtil();
		String HashArr[]=new String[3];
		HashArr[0]=String.valueOf(ownerId);
		HashArr[1]=String.valueOf(value);
		HashArr[2]=ownerName;
		//data contain encrypted form of id,value,name and hash of all three
		String data=util.getEncrypt(String.valueOf(ownerId),sk)+" "+util.getEncrypt(String.valueOf(ownerId), sk)+" "+util.getEncrypt(String.valueOf(ownerName), sk)+" "+
		HashArr.hashCode();
		Node n=new Node(t,data,count,String.valueOf(HashArr.hashCode()),parent,genesis);
		String hashValue=String.valueOf(n.getTimestamp())+n.getNodeNumber()+n.getNodeId()+n.getChildReferenceNodeId()+n.getGenesisReferenceNodeId();
		n.setHashValue(util.hashString(hashValue));
		return n;
	}
	static int verifyProperty(int value,Node Parent,SecretKey sk) throws Exception
	{
		JavaUtil util=new JavaUtil();
		String data=Parent.getData();
		String arr[]=data.split(" ");
		String v=arr[1]; //decrypt value of parent
		String val=util.getDecrypt(v, sk);
		int parentValue=Integer.parseInt(val);
		ArrayList<Node> childList=Parent.getChildReferenceNodeId();
		int sum=0;
		for(int i=0;i<childList.size();i++)
		{
			Node c=childList.get(i);
			String cdata=c.getData();
			String carr[]=data.split(" ");
			String cv=arr[1]; //decrypt value of child
			String cval=util.getDecrypt(cv, sk);
			int childValue=Integer.parseInt(val);
			sum=sum+childValue;
		}
		if(parentValue-sum>=value)
			return 1;
		else
			return 0;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public int getNodeNumber() {
		return nodeNumber;
	}
	public void setNodeNumber(int nodeNumber) {
		this.nodeNumber = nodeNumber;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public Node getReferenceNodeId() {
		return referenceNodeId;
	}
	public void setReferenceNodeId(Node referenceNodeId) {
		this.referenceNodeId = referenceNodeId;
	}
	public ArrayList<Node> getChildReferenceNodeId() {
		return childReferenceNodeId;
	}
	public void setChildReferenceNodeId(ArrayList<Node> childReferenceNodeId) {
		this.childReferenceNodeId = childReferenceNodeId;
	}
	public Node getGenesisReferenceNodeId() {
		return genesisReferenceNodeId;
	}
	public void setGenesisReferenceNodeId(Node genesisReferenceNodeId) {
		this.genesisReferenceNodeId = genesisReferenceNodeId;
	}
	public String getHashValue() {
		return hashValue;
	}
	public void setHashValue(String hashValue) {
		this.hashValue = hashValue;
	}
	
	
}
