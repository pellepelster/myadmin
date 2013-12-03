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
	public void testStoreWithoutName()
	{
		byte[] tempContent = new byte[] { 0x01, 0x02, 0x03 };

		String tempFileId = this.tempFileStore.storeTempFile(tempContent);

		byte[] tempContent1 = this.tempFileStore.getTempFile(tempFileId);

		Assert.assertArrayEquals(tempContent, tempContent1);
	}

	@Test
	public void testStoreWithName()
	{
		String fileName = "test.txt";

		byte[] tempContent = new byte[] { 0x04, 0x05, 0x06 };

		String tempFileId = this.tempFileStore.storeTempFile(fileName, tempContent);

		Assert.assertNotSame(fileName, tempFileId);

		byte[] tempContent1 = this.tempFileStore.getTempFile(tempFileId);

		Assert.assertArrayEquals(tempContent, tempContent1);
	}
}
