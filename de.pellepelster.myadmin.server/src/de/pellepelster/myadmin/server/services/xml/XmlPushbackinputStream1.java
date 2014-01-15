package de.pellepelster.myadmin.server.services.xml;

import java.io.InputStream;
import java.io.PushbackInputStream;

public class XmlPushbackinputStream1 extends PushbackInputStream
{

	public XmlPushbackinputStream1(InputStream in)
	{
		super(in, XmlImportExportService.PEEK_SIZE);
	}

}
