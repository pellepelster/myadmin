package de.pellepelster.myadmin.server.test;

import org.apache.commons.io.FilenameUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.pellepelster.myadmin.client.web.entities.dictionary.FileVO;
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
		byte[] tempFileContent = new byte[] { 0x01, 0x02, 0x03 };

		String tempFileUUID = this.tempFileStore.storeTempFile(tempFileContent);
		FileVO tempFile = this.tempFileStore.getTempFile(tempFileUUID);

		Assert.assertArrayEquals(tempFileContent, tempFile.getFileContent());
		Assert.assertEquals(tempFileUUID, tempFile.getFileUUID());
	}

	@Test
	public void testStoreWithName()
	{
		String tempFileName = "test.txt";
		byte[] tempFileContent = new byte[] { 0x04, 0x05, 0x06 };

		String tempFileUUID = this.tempFileStore.storeTempFile(tempFileName, tempFileContent);
		Assert.assertNotSame(tempFileName, tempFileUUID);

		FileVO tempFile = this.tempFileStore.getTempFile(tempFileUUID);
		Assert.assertArrayEquals(tempFileContent, tempFile.getFileContent());
		Assert.assertEquals(tempFileName, tempFile.getFileName());
		Assert.assertEquals(tempFileUUID, tempFile.getFileUUID());
	}

	@Test
	public void testGetFileName()
	{
		String fileName = FilenameUtils.getName("c:\\fakepath\\abc.txt");
		Assert.assertEquals("abc.txt", fileName);
	}

}
