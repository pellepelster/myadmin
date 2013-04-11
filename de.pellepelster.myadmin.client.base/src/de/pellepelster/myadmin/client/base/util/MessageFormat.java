package de.pellepelster.myadmin.client.base.util;

public class MessageFormat
{

	public static String format(String string, Object... tokens)
	{
		int i = 0;
		while (i < tokens.length)
		{
			String delimiter = "{" + i + "}";
			
			while (string.contains(delimiter))
			{
				string = string.replace(delimiter, String.valueOf(tokens[i]));
			}
			
			i++;
		}
		
		return string;
	}
}
