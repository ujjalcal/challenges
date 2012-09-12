package com;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Air_Duct {
	static int w,h;
	static int currPosW=0, CurrPosH=0;
	static int val=100;
	static int tot0s = 0;
	static int totPaths = 0;
	static int quit = 0;

	public static void main(String args[]) {
		long start = System.nanoTime();    
	    try {
	        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	        String s;
	        s = br.readLine();
	        String[] inputs = s.split(" ");
	        w = Integer.parseInt(inputs[0]);
	        h = Integer.parseInt(inputs[1]);
	        System.out.println("widht:"+w);
	        System.out.println("height:"+h);
	        
	        int[][] arr1 = new int[h][w];
	        int hei = 0;
	        while ((s= br.readLine()) != null && s.length()!=0)
	        {
	        	String inputlist[] = s.split(" ");
	        	for (int i = 0; i < inputlist.length; i++){ 
	        		int a = Integer.parseInt(inputlist[i]);
	        		arr1[hei][i] = a;
	        	}
        		System.out.println(Arrays.toString(arr1[hei]));
	        	hei++;
	        }
	        
	        calculate0s(arr1);
	        System.out.println("Total 0's:"+tot0s);
	        System.out.println("*****Begin Air Duct Game******");
	        gameEngine(arr1, tot0s, 0, 0, "");
	        
	        System.out.println("after game engine");
	        print2dArray(arr1, "");
	        System.out.println("totpaths:"+totPaths);
	    } catch (Exception e) {
	        System.err.println("Error:" + e.getMessage());
	    }
	    long elapsedTime = System.nanoTime() - start;
	    System.out.println("Total time - "+elapsedTime+" nano secs");
	    System.out.println("Total time - "+((double)elapsedTime / 1000000000.0)+" secs");


	}
	
	public static boolean gameEngine(int arr[][], int notVisited0s, int cpH, int cpW, String ind)
	{
/*		try {
		    Thread.sleep(1000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
*/
		boolean end = false;
		String recInd = ind+"* ";
		
		System.out.println(ind+"--- in game engine ---");
		print2dArray(arr, ind);
		System.out.println(ind+" notVisited0s:"+notVisited0s+", cpH:"+cpH+", cpW:"+cpW);
		System.out.println(ind+"--- start game ---\n");
		
		int r = checkRight(arr, cpH, cpW);System.out.println(ind+"r:"+r);
		boolean rb = checkProperEndPath(notVisited0s, r, ind); 
		if(rb)
		{
			totPaths++;
			return true;
		}
		else //if (notVisited0s >0)
		{
			System.out.println(ind+"Not proper end path - checkDeadEndPath?");
			rb = checkDeadEndPath(notVisited0s, r, ind);
		}
		
		int l = checkLeft(arr, cpH, cpW);System.out.println(ind+"l:"+l);
		boolean lb = checkProperEndPath(notVisited0s, l, ind);
		if(lb)
		{
			totPaths++;
			return true;
		}
		else
		{//if (notVisited0s >0)
			System.out.println(ind+"Not proper end path - checkDeadEndPath?");
			lb = checkDeadEndPath(notVisited0s, l, ind);
		}
		int u = checkUp(arr, cpH, cpW);System.out.println(ind+"u:"+u);
		boolean ub = checkProperEndPath(notVisited0s, u, ind);
		if(ub)
		{
			totPaths++;
			return true;
		}
		else //if (notVisited0s >0)
		{
			System.out.println(ind+"Not proper end path - checkDeadEndPath?");
			ub = checkDeadEndPath(notVisited0s, u, ind);
		}
		
		int d = checkDown(arr, cpH, cpW);System.out.println(ind+"d:"+d);
		boolean db = checkProperEndPath(notVisited0s, d, ind);
		if(db)
		{
			totPaths++;
			return true;
		}
		else //if (notVisited0s >0)
		{
			System.out.println(ind+"Not proper end path - checkDeadEndPath?");
			db = checkDeadEndPath(notVisited0s, d, ind);
		}
		
		if (rb && lb && ub && db)
		{
			System.out.println(ind+"====>> Quit "+(quit++)+" - totPath:"+totPaths+" <<====\n");
			return end;
		}
		
		
		if (!rb)
		{
			int [][] rarr = clone2dArray(arr);
			int rtot0s = moveRight(rarr, notVisited0s, cpH, cpW);
			System.out.println(ind+"-->after move right:rtot0s-"+rtot0s+", tot0s-"+tot0s);
			print2dArray(rarr, ind);
			gameEngine(rarr, rtot0s, cpH, cpW+1,recInd);
		}

		if (!lb)
		{
			int [][] larr = clone2dArray(arr);
			int ltot0s = moveleft(larr, notVisited0s, cpH, cpW);
			System.out.println(ind+"-->after move left:ltot0s-"+ltot0s+", tot0s-"+tot0s);
			print2dArray(larr, ind);
			gameEngine(larr, ltot0s, cpH, cpW-1,recInd);
		}

		if (!ub)
		{
			int [][] uarr = clone2dArray(arr);
			int utot0s = moveup(uarr, notVisited0s, cpH, cpW);
			System.out.println(ind+"-->after move up:utot0s-"+utot0s+", tot0s-"+tot0s);
			print2dArray(uarr, ind);
			gameEngine(uarr, utot0s, cpH-1, cpW,recInd);
		}


		if (!db)
		{
			int [][] darr = clone2dArray(arr);
			//System.out.println("---before move down - notVisited0s:"+notVisited0s);
			int dtot0s = movedown(darr, notVisited0s, cpH, cpW);
			System.out.println(ind+"-->after move down:dtot0s-"+dtot0s+", tot0s-"+tot0s);
			print2dArray(darr, ind);
			gameEngine(darr, dtot0s, cpH+1, cpW, recInd);
		}
		
		return end;
	}
	
	
	
	
	
	public static int moveRight(int arr[][], int notVisited0s, int cpH, int cpW)
	{	int i = checkRight(arr,cpH, cpW);
		if (i>=0)
		{
			cpW++;
			arr[cpH][cpW] = val++;
			notVisited0s--;
		}
		return notVisited0s;
	}
	public static int moveleft(int arr[][], int notVisited0s, int cpH, int cpW)
	{
		int i = checkLeft(arr, cpH, cpW);
		if (i>=0)
		{
			cpW--;
			arr[cpH][cpW] = val++;
			notVisited0s--;
		}
		return notVisited0s;
	}
	public static int moveup(int arr[][], int notVisited0s, int cpH, int cpW)
	{
		int i = checkUp(arr, cpH, cpW);
		if (i>=0)
		{
			cpH--;
			arr[cpH][cpW] = val++;
			notVisited0s--;
		}
		return notVisited0s;
	}
	public static int movedown(int arr[][], int notVisited0s, int cpH, int cpW)
	{
		int i = checkDown(arr, cpH, cpW);
		if (i>=0)
		{
			cpH++;
			arr[cpH][cpW] = val++;
			notVisited0s--;
		}
		return notVisited0s;

	}
	
	public static int checkRight(int arr[][], int cpH, int cpW)
	{
		int i = -1;
		if (cpW<=w-2)
			i = arr[cpH][cpW+1];
		return i;
	}
	public static int checkLeft(int arr[][], int cpH, int cpW)
	{
		int i = -1;
		if (cpW>0)
			i = arr[cpH][cpW-1];
		return i;
	}
	public static int checkUp(int arr[][], int cpH, int cpW)
	{
		int i = -1;
		if (cpH>0)
			i = arr[cpH-1][cpW];
		return i;
	}
	public static int checkDown(int arr[][], int cpH, int cpW)
	{
		int i = -1;
		if (cpH<=h-2)
			i = arr[cpH+1][cpW];
		return i;
	}
	
	public static void print2dArray(int [][] arr1, String ind)
	{
        for (int i = 0; i < arr1.length; i++) {
			System.out.println(ind+Arrays.toString(arr1[i]));
		}

	}
	
	public static void calculate0s(int arr[][])
	{
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				int ij = arr[i][j];
				if (ij==0)
					tot0s++;
				
			}
			
		}
	}
	
	public static int[][] clone2dArray(int matrix[][])
	{
		int [][] myInt = new int[matrix.length][];
		for(int i = 0; i < matrix.length; i++)
		  myInt[i] = matrix[i].clone();
		return myInt;
	}
	
	public static boolean checkProperEndPath(int notVisited0s, int currVal, String ind)
	{
		boolean r = false;
		//exit criteria
		if (notVisited0s == 0)
		{
			if (currVal == 3)
			{
				System.out.println(ind+"ending");
				return true;
			}
			else
			{
				System.out.println(ind+"path not possible - exhausted all 0s");
				return false;
			}
		}
		else if (currVal == 3)
		{
			System.out.println(ind+"path ends early - reached 3, notvisited0s:"+notVisited0s);
			return false;
		}
		System.out.println(ind+"default - check other cond...");
		return r;
	}
	public static boolean checkDeadEndPath(int notVisited0s, int currVal, String ind)
	{
		boolean r = false;
		//exit criteria
		if(currVal == 2)
		{
			System.out.println(ind+"start point - no path");return true;
		}
		if(currVal == 3)
		{
			System.out.println(ind+"end point - should have been caught in proper end path - i.e. no path");return true;
		}
		if(currVal == 1)
		{
			System.out.println(ind+"belongs to other - no path");return true;
		}
		if(currVal == -1)
		{
			System.out.println(ind+"border - no path");return true;
		}
		if (currVal >= 100)
		{
			System.out.println(ind+"already visited - no path");return true;
		}
		if(currVal == 0)
		{
			System.out.println(ind+"#### not dead end ####");return false;
		}
		return r;
	}
}
