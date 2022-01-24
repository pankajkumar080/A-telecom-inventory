package com.pankaj.kumar;

import java.util.*;

public class ConnectionDemo {

	/**
	 * @Pankaj
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Inventory in = new Inventory();
		Connection c1 = new PrePaid(1, 1, 200) ;
		Connection c2 = new PrePaid(2, 1, 200) ;
		Connection c3 = new PrePaid(3, 2, 200) ;

		System.out.println(in.addConnection(c1));
		System.out.println(in.addConnection(c2));
		System.out.println(in.addConnection(c3));
		System.out.println(in.addConnection(c1));
		System.out.println(in.recharge(1, 200));
		
		System.out.println(c1.deduct(200));
		System.out.println(c1.deduct(200));
		System.out.println(c1.deduct(200));
		
	}

}

class NewInventory
{
	TreeSet<Connection> connections = null;
	
	public NewInventory()
	{
		connections = new TreeSet<Connection>();
	}
	
	public TreeSet<Connection> getConnections() {
		return connections;
	}

	public int addConnection(Connection obj)
	{
		connections.add(obj);
		return connections.size();
	}
}

class Inventory
{
	private ArrayList<Connection> connections = null;	
	
	public Inventory()
	{
		connections = new ArrayList<Connection>();	
	}
	
	public boolean addConnection(Connection obj)
	{
		boolean code = true;
		
		for(Connection a : connections)
		{
			if(a.getConnectionId() == obj.getConnectionId())
			{
				code = false;
				break;
			}
			
		}
		if(code == true)
		{
			connections.add(obj);
		}
		return code;
	}
	
	public double recharge(int id, double amount)
	{
		double code = -1;
		
		for(Connection a: connections)
		{
			if(a.getConnectionId() == id)
			{
				code = a.recharge(amount);
				break;
			}
		}
		
		return code;
	}
	
	public double deduct(int id, double amount) throws ConnectionLockedException
	{
		double code = -1;
		
		for(Connection a: connections)
		{
			if(a.getConnectionId() == id)
			{
				double balance = a.getBalance();
				
				if(balance > 0)
				{
					code = a.deduct(amount);
				}
				else
				{
					throw new ConnectionLockedException(balance);
				}
			}
		}
				
		return code;
	}
	
	public Set<Integer> getUniqueCustomerIds(){
		
		Set<Integer> ids = new HashSet<Integer>();
		
		for(Connection a : connections)
		{
			ids.add(a.getCustomerId());
		}
		
		return ids;
	}
	
	public HashMap<Integer, Connection> getConnectionHashMap()
	{
		HashMap<Integer,Connection> accountsMap = new HashMap<Integer, Connection>();
		
		for(Connection a : connections)
		{
			accountsMap.put(a.getConnectionId(), a);
		}
		return accountsMap;
	}
}

abstract class Connection implements Comparable<Connection>
{
	private int connectionId;
	private int customerId;
	private double balance;
	
	public Connection(int connectionId, int customerId, double balance) {
		this.connectionId = connectionId;
		this.customerId = customerId;
		this.balance = balance;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public int getConnectionId() {
		return connectionId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public abstract double recharge(double amount);
	public abstract double deduct(double amount);
	
	@Override
	public int compareTo(Connection o) {
		
		int code = 0;
		if(o.getConnectionId()< connectionId)
		{
			code = 1;
		}
		else if(o.getConnectionId()> connectionId)
		{
			code = -1;
		}
		return code;
	}
}

class PrePaid extends Connection
{
	public PrePaid(int connectionId, int customerId, double balance) {
		super(connectionId, customerId, balance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double recharge(double amount) {
		// TODO Auto-generated method stub
		double balance = getBalance();
		balance += amount;
		setBalance(balance);
		return balance;
	}

	@Override
	public double deduct(double amount) {
		// TODO Auto-generated method stub
		double balance = getBalance();
		balance -= amount;
		setBalance(balance);
		return balance;
	}
	
}


class ConnectionLockedException extends Exception
{
	/**
	 * Pankaj
	 */
	private static final long serialVersionUID = 1L;
	private double currentBalance;
				
	public ConnectionLockedException(double currentBalance) {
		this.currentBalance = currentBalance;
		
	}

	public String getMessage() {
		// TODO Auto-generated method stub
		return (currentBalance) + "";
	}
}
