package de.pellepelster.myadmin.server.test;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.pellepelster.myadmin.server.test.base.BaseMyAdminJndiContextTest;
import de.pellepelster.myadmin.server.util.TempFileStore;

public class TempFileStoreTest extends BaseMyAdminJndiContextTest
{
	@Autowired
	private TempFileStore tempFileStore;

	public void setTempFileStore(TempFileStore tempFileStore)
	{
		this.tempFileStore = tempFileStore;
	}

	@Test
	public void testStore()
	{
		byte[] tempContent = new byte[] { 0x01, 0x02, 0x03 };

		String tempFileName = this.tempFileStore.storeTempFile(tempContent);

		byte[] tempContent1 = this.tempFileStore.getTempFile(tempFileName);

		Assert.assertArrayEquals(tempContent, tempContent1);
	}
}
