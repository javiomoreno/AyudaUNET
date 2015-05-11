package com.example.ayudaunet;

import java.text.DecimalFormat;

public class TablaConversion 
{
	float tabla[] = new float[101];
	float numero = 0;
	
	public TablaConversion()
	{
		for (int j = 0; j <= 100; j++) 
		{
			if(j < 8)
				numero = 10;
			else if(j == 17 || j == 18)
				numero = 20;
			else if(j == 28 || j == 29)
				numero = 30;
			else if(j == 39 || j == 40)
				numero = 40;
			else if(j == 48 || j == 49)
				numero = 48;
			else if(j == 54 || j == 55)
				numero = 53;
			else if(j == 62 || j == 63)
				numero = 60;
			else if(j == 73 || j == 74)
				numero = 70;
			else if(j == 84 || j == 85)
				numero = 80;
			else if(j > 94)
				numero = 90;
			else
				numero ++;
			tabla[j] = numero/10;
		}		
	}
	
	public float DevolverNumero(int posicion)
	{
		return tabla[posicion];
	}
	
	public int DevolverNota(float numero)
	{
		if(numero > 9.0)
			return -1;
		if(numero == 0)
			return 0;
		int nota = 0;
		for (int i = 0; i <= 100; i++) 
		{
			if(tabla[i] == numero)
			{
				nota = i;
				break;
			}
		}
		return nota;
	}
}
