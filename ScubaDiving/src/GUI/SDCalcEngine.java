package GUI;


public class SDCalcEngine 
{
	/*
	 * 		! DON'T CHANGE THIS NOTE !
	 * 		========================================================================================
	 * 		Scuba Diving Calculator Engine Assistant Provide 4 Main Calculations :
	 * 		-MOD
	 * 		-PPO2
	 * 		-Best Mix
	 * 		-EAD
	 * 		
	 * 		Additional Calculator (All you need when calculating)
	 * 		-valueConverter (ata to meter / meter to ata)
	 * 		-convertStringtoInteger
	 * 		-convertStringtoDouble
	 * 
	 * 		Extends or Initialize this class to use calculation in Console or GUI based application
	 * 		Don't need any modification or error return all is available in this class :).
	 * 		For SMOD,PPT,or PPOT just modification your input method, example :
	 * 		SMOD : CountMOD(1.4,yourInput);
	 * 		=========================================================================================
	 * 		Code's Created by : 
	 * 		Riyan Saputra Irawan (141105151104) 
	 * 		Ibn Khaldun Bogor University
	 * 		Copyright 2016
         *              Version 0.0.1c(Fixed on 07/04/2016)
	 * 		=========================================================================================
	 * 		Keep it clean,don't modification without author permissions :).
	 * 		Contact Us      : riyansaputrai007@gmail.com 
	 * 		Visit our blog 	: tech4hd.blogspot.com
	 * 		=========================================================================================
	 * 		! DON'T CHANGE THIS NOTE !
	 */
	
	protected double MODResult,PPO2Result,BestMixResult,EADResult,tempDouble;
	protected boolean isErrorInput=false;
	protected String errorMsg=null;
	
	//Maximum Operating Depth (MOD)
	protected double CountMOD(double Part_Pressure,double Frac_Gas)
	{
		MODResult=0;
		if ((Part_Pressure >= 1.1 && Part_Pressure <= 1.6) && (Frac_Gas >=18 && Frac_Gas<=50))
		{
			MODResult=Part_Pressure/(Frac_Gas/100);
			isErrorInput=false;
		}
		else
		{
			MODResult=0;
			errorMsg=" ! Error ! \n-Part Presure can't be lower than 1.1 or bigger than 1.6"+
			"\n-Fraction Of The Gas can't be lower than 18% or higger than 50 %";
			isErrorInput=true;
		}
		MODResult=valueConverter("ata","meter",MODResult);
		return(MODResult);
	}
	
	//Partial Pressure of oxygen (PPO2)
	protected double CountPPO2(double Frac_Gas,double Absolute_Pressure)
	{
		PPO2Result=0;
		if ((Frac_Gas >=18 && Frac_Gas<=50) && (Absolute_Pressure>=3 && Absolute_Pressure<=70))
		{
			PPO2Result=(Frac_Gas/100)*valueConverter("meter","ata",Absolute_Pressure);
			isErrorInput=false;
		}
		else
		{
			PPO2Result=0;
			errorMsg=" ! Error !\n-Fraction Of The Gas can't be lower than 18 % or higger than 50 %"
					+ "\n-The Dephts should from 3 metres to 70 metres inclusive";
			isErrorInput=true;
		}
		return(PPO2Result);
	}
	
	//Fraction of Oxygen (Best Mix)
	protected double CountBestMix(double Part_Pressure,double Absolute_Pressure)
	{
		BestMixResult=0;
		if ((Part_Pressure >= 1.1 && Part_Pressure <= 1.6) && ((Absolute_Pressure>=3 && Absolute_Pressure<=70)))
		{
			BestMixResult=Part_Pressure/valueConverter("meter","ata",Absolute_Pressure);
			isErrorInput=false;
		}
		else
		{
			BestMixResult=0;
			errorMsg=" ! Error ! \n-Part Presure can't be lower than 1.1 or bigger than 1.6"
					+ "\n-The Dephts should from 3 metres to 70 metres inclusive";
			isErrorInput=true;
		}
		BestMixResult=BestMixResult*100;
		return(BestMixResult);
	}
	
	//Equivalent Air Depth (EAD)
	protected double CountEAD(double Frac_Gas,double Absolute_Pressure)
	{
		EADResult=0;
		if ((Frac_Gas >=18 && Frac_Gas<=50) && ((Absolute_Pressure>=3 && Absolute_Pressure<=70)))
		{
			EADResult=((1-(Frac_Gas/100))*valueConverter("meter","ata",Absolute_Pressure)) / 0.79;
			isErrorInput=false;
		}
		else
		{
			EADResult=0;
			errorMsg=" ! Error !\n-Fraction Of The Gas can't be lower than 18 % or higger than 50 %"
					+ "\n-The Dephts should from 3 metres to 70 metres inclusive";
			isErrorInput=true;
		}
		return(EADResult);
	}
	
	//valueConvert ata to meter or meter to ata
	protected double valueConverter(String convertFrom,String convertTo,double value)
	{
		if ((convertFrom.equals("ata")||convertFrom.equals("ATA")) && (convertTo.equals("m") || convertTo.equals("M") || convertTo.equals("Meter") || convertTo.equals("meter") || convertTo.equals("metre") || convertTo.equals("Metre") ))
		{
			value=(value-1)*10;
		}
		else if ((convertFrom.equals("m") || convertFrom.equals("M") || convertFrom.equals("Meter") || convertFrom.equals("meter") || convertFrom.equals("metre") || convertFrom.equals("Metre") ) && (convertTo.equals("ata")||convertTo.equals("ATA")))
		{
			value=(value/10)+1;
		}
		else
		{
			errorMsg="! Error ! Can't convert "+convertFrom+" to "+convertTo+" with value : "+value+"\n -Please Check your parameters or value !";
		}
		
		return(value);
	}
	
	//converter string to double 
	protected double convertStringtoDouble(String values)
	{
		tempDouble=0;

		try
		{
			tempDouble=Double.valueOf(values).doubleValue();
			isErrorInput=false;
		}
		catch(NumberFormatException nfe)
		{
			errorMsg="! Error ! Can't Convert Input Values To Double";
			isErrorInput=true;
			tempDouble=-1;
		}
		
		return(tempDouble);
	}
	
	//converter string to integer
	protected int convertStringtoInteger(String values)
	{
		int tempInt=0;

		try
		{
			tempInt=Integer.valueOf(values).intValue();
			isErrorInput=false;
		}
		catch(NumberFormatException nfe)
		{
			errorMsg="! Error ! Can't Convert Input Values To Integer";
			isErrorInput=true;
			tempInt=-1;
		}
		
		return(tempInt);
	}
}
